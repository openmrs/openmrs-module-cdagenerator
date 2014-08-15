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
package org.openmrs.module.CDAGenerator.api;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openmrs.Concept;
import org.openmrs.ConceptMap;
import org.openmrs.ConceptMapType;
import org.openmrs.ConceptName;
import org.openmrs.ConceptReferenceTerm;
import org.openmrs.ConceptSource;
import org.openmrs.ImplementationId;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PersonAddress;
import org.openmrs.PersonName;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.CDAHandlers.BaseCdaTypeHandler;
import org.openmrs.module.CDAGenerator.SectionHandlers.ChiefComplaintSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.HistoryOfPastIllnessSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.HistoryOfPresentIllnessSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.SocialHistorySection;
import org.openmrs.api.PatientService;
import org.openmrs.test.BaseContextSensitiveTest;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Tests {@link ${CDAGeneratorService}}.
 */
public class  CDAGeneratorServiceTest extends BaseModuleContextSensitiveTest 
{
	
	protected static final String INITIAL_CONCEPTS_XML = "include/dataset.xml";
	protected ConceptService conceptService = null;
	protected  PatientService patientService=null;
	protected  BaseCdaTypeHandler cdatype=null;
	protected ImplementationId implementationId=null;
	/**
	 * Run this before each unit test in this class. The "@Before" method in
	 * {@link BaseContextSensitiveTest} is run right before this method.
	 * 
	 * @throws Exception
	 */
	@Before
	public void runBeforeAllTests() throws Exception 
	{
		executeDataSet(INITIAL_CONCEPTS_XML);
		conceptService = Context.getConceptService();
		patientService=Context.getPatientService();
		
		 cdatype=new  BaseCdaTypeHandler();
		 cdatype.setDocumentFullName("Antepartum Summary");
		 cdatype.setDocumentShortName("APS");
		 cdatype.setDocumentDescription("information regarding the status of a patients pregnancy");
		 cdatype.setTemplateid("1.3.6.1.4.19376.1.5.3.1.1.11.2");
		 cdatype.setFormatCode("urn:ihe:pcc:aps:2007");
		 
		 implementationId=new ImplementationId();
		 implementationId.setImplementationId("CDA Junit Test#1");
		 implementationId.setDescription("Testing our module");
		 implementationId.setName("CDA Junit Test");
		 implementationId.setPassphrase("Testing our module");
		 
		 Context.getAdministrationService().setImplementationId(implementationId);
		 Context.getAdministrationService().setGlobalProperty("application.name", "OpenMRS");
	}
	
	
	@Test
	public void shouldSetupContext() 
	{
		assertNotNull(Context.getService(CDAGeneratorService.class));
	}
	
	@Test
	public void shouldGetNullwhenServiceMethodisCalledwithNullParameters() throws Exception 
	 {
	     CDAGeneratorService service=Context.getService(CDAGeneratorService.class);
	     ClinicalDocument document = service.produceCDA(null, null);
	     Assert.assertNull(document);
	 }
	
	@Test
	public void shouldGetNullWhenPatientorCdaTypeisNull() throws Exception 
	 {
	     CDAGeneratorService service=Context.getService(CDAGeneratorService.class);
	     ClinicalDocument document1 = service.produceCDA(new Patient(), null);
	     ClinicalDocument document2=service.produceCDA(null, new BaseCdaTypeHandler());
	     Assert.assertNull(document1);
	     Assert.assertNull(document2);
	 }
	
	
	@Test 
	public void ShouldpassIfServiceMethodisCalledwithAllParameters()  throws Exception
	{ 
		Patient patient=patientService.getPatient(1);
		
		
		 CDAGeneratorService service=Context.getService(CDAGeneratorService.class);
		 ClinicalDocument document1 = service.produceCDA(patient, cdatype);
		 
		 Assert.assertNotNull(document1);
	}
	
	
	@Test 
	public void ShouldReturnNUllIfCDAHeadercalledwithNullParams()  throws Exception
	{	
		
		Patient patient=patientService.getPatient(1);
		
	    CdaHeaderBuilder cdb=new CdaHeaderBuilder();
	    ClinicalDocument doc = CDAFactory.eINSTANCE.createClinicalDocument();
	    doc=cdb.buildHeader(null, null, null);
	    Assert.assertNull(doc);    
	
	}
	
		@Test 
		public void ShouldPassIfCDAHeadercalledwithAppropriateParams()  throws Exception
		{	
			
			Patient patient=patientService.getPatient(1);
			
		    CdaHeaderBuilder cdb=new CdaHeaderBuilder();
		    ClinicalDocument doc = CDAFactory.eINSTANCE.createClinicalDocument();
		    doc=cdb.buildHeader(doc, cdatype, patient);
		    Assert.assertNotNull(doc);
		
		}
		
		@Test 
		public void testFewMoreSections()  throws Exception
		{	
		Patient patient=patientService.getPatient(1);
			
		Section section=ChiefComplaintSection.buildChiefComplaintSection(patient);	
	    Assert.assertNotNull(section);
	    
	    section=HistoryOfPresentIllnessSection.buildHistoryOfPresentIllnessSection(patient);
	    Assert.assertNotNull(section);
	    
	    section=SocialHistorySection.buildSocialHistorySection(patient);
	    Assert.assertNotNull(section);
	    
	    section=HistoryOfPastIllnessSection.buildHistoryOfPastIllnessSection(patient);
	    Assert.assertNotNull(section);
		}
	
}