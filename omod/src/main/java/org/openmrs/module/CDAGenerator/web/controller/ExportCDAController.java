/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.CDAGenerator.web.controller;




import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.emf.common.util.Diagnostic;


import org.openhealthtools.mdht.uml.cda.AssignedAuthor;
import org.openhealthtools.mdht.uml.cda.Author;
import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.CDAPackage;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.InfrastructureRootTypeId;
import org.openhealthtools.mdht.uml.cda.Organization;
import org.openhealthtools.mdht.uml.cda.Patient;
import org.openhealthtools.mdht.uml.cda.PatientRole;
import org.openhealthtools.mdht.uml.cda.Person;
import org.openhealthtools.mdht.uml.cda.RecordTarget;
import org.openhealthtools.mdht.uml.cda.util.BasicValidationHandler;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.openhealthtools.mdht.uml.cda.util.ValidationResult;
import org.openhealthtools.mdht.uml.hl7.datatypes.CE;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.PN;
import org.openhealthtools.mdht.uml.hl7.datatypes.ST;
import org.openhealthtools.mdht.uml.hl7.datatypes.TS;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.CDAHandlers.BaseCdaTypeHandler;
import org.openmrs.module.CDAGenerator.api.CDAGeneratorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContext;

/**
 * The main controller.
 */

@Controller
public class ExportCDAController 
{
	List<String> eList=null;	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/CDAGenerator/exportcda*",method = RequestMethod.POST)
	public String manage(@RequestParam(value="patientId",required=true)org.openmrs.Patient p,@RequestParam(value="ChildCDAHandler",required=false)String ccth, BaseCdaTypeHandler bcth,HttpServletResponse response) {
		
		ClinicalDocument cda=null;
		
		String []arr=ccth.split(",");
		
	 CDAGeneratorService cdaservice=(CDAGeneratorService)Context.getService(CDAGeneratorService.class);
	 
	 
	 bcth.setDocumentFullName(arr[0]);
	 bcth.setDocumentShortName(arr[1]);
	 bcth.setDocumentDescription(arr[2]);
	 bcth.setTemplateid(arr[3]);
	 bcth.setFormatCode(arr[4]);
	 bcth.setParentTemplateId(arr[5]);
	 
	 cda=cdaservice.produceCDA(p, bcth);
	 CDAPackage.eINSTANCE.eClass();
	 final List<String> errorList=new ArrayList<String>();
	 final Map<String,List<String>> errorMap=new HashMap<String,List<String>>();
	 eList=new ArrayList<String>();
	 
	 boolean result = CDAUtil.validate(cda, new BasicValidationHandler() 
	 {   
		 
		    public void handleError(Diagnostic diagnostic) 
		    {
		        errorList.add(diagnostic.getMessage());
		    	errorMap.put("Error:",errorList);
		    }
		    public void handleWarning(Diagnostic diagnostic) 
		    {
		    	errorList.add(diagnostic.getMessage());
		    	errorMap.put("WARNING:",errorList);
		    }
		    public void handleInfo(Diagnostic diagnostic) 
		    {
		    	errorList.add(diagnostic.getMessage());
		    	errorMap.put("Info:",errorList );
		    }
		});


for (Map.Entry<String, List<String>> entry : errorMap.entrySet()) 
{
	String key = entry.getKey();
	List<String> values = entry.getValue();
	for(String s:values)
	{
		eList.add(key+" "+s);
	}
}

if(result==false)
{
	return "redirect:/module/CDAGenerator/CDA_Errors_Page.form";
}
else
 {
		 response.setHeader( "Content-Disposition", "attachment;filename="+p.getGivenName()+"sampleTest.xml");	
		   try 
		   {
			 StringWriter r = new StringWriter();
			 CDAUtil.save(cda, r);
			  String cdaDoc = r.toString();
			  cdaDoc=cdaDoc.replaceAll("&#xD;","\n");
			  cdaDoc = cdaDoc.replaceAll("&lt;", "<");
			  cdaDoc = cdaDoc.replaceAll("&quot;", "\"");
			  byte[] res = cdaDoc.getBytes(Charset.forName("UTF-8"));
			  response.setContentType("text/xml");
			  response.setCharacterEncoding("UTF-8");
			  response.getOutputStream().write(res);
			   response.flushBuffer();
		      } 
		   catch (IOException e) 
		   {
			
			e.printStackTrace();
		   } 
		   catch (Exception e)
		   {
			e.printStackTrace();
		   }
		   return null;
       }
	}
	@RequestMapping(value = "/module/CDAGenerator/exportcda*", method = RequestMethod.GET)
	public void PopulateCdaTypes(@RequestParam(value="patientId",required=false)Patient patient,ModelMap model) 
	{
		
		
			CDAGeneratorService cdaser=(CDAGeneratorService)Context.getService(CDAGeneratorService.class);
			List<BaseCdaTypeHandler> ListofCdatypes=cdaser.getAllCdaTypeChildHandlers();
			model.addAttribute("ListCdatypes", ListofCdatypes);
		
	}
	
	@RequestMapping(value = "/module/CDAGenerator/CDA_Errors_Page", method = RequestMethod.GET)
	public void manage(ModelMap model) 
	{
		
		model.addAttribute("CDA_Errors_Page", eList);
	}

	}

