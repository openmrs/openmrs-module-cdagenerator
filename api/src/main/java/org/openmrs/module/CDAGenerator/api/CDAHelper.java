package org.openmrs.module.CDAGenerator.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openhealthtools.mdht.uml.hl7.datatypes.CD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CE;
import org.openhealthtools.mdht.uml.hl7.datatypes.CS;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.ED;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVL_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVXB_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.ST;
import org.openhealthtools.mdht.uml.hl7.datatypes.TS;
import org.openmrs.Obs;

public class CDAHelper
{
	public static SimpleDateFormat getDateFormat()
	{
		SimpleDateFormat simpledateformat = new SimpleDateFormat("MM-dd-yyyy");
		return simpledateformat;
	}
	public static IVL_TS  buildEffectiveTimeinIVL(Date d , Date d1)
	{
		IVL_TS effectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
		SimpleDateFormat s = new SimpleDateFormat("yyyyMMddhhmmss");
		String creationDate = s.format(d);
		IVXB_TS low = DatatypesFactory.eINSTANCE.createIVXB_TS();
		low.setValue(creationDate);
		effectiveTime.setLow(low);
		IVXB_TS high = DatatypesFactory.eINSTANCE.createIVXB_TS();
		if(d1 != null)
			high.setValue(s.format(d1));
		effectiveTime.setHigh(high);
		return effectiveTime;
	}
	public static IVL_TS  buildEffectiveTimeinIVL(Date d)
	{
		IVL_TS effectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
		SimpleDateFormat s = getDateFormat();
		String creationDate = s.format(d);
		IVXB_TS low = DatatypesFactory.eINSTANCE.createIVXB_TS();
		low.setValue(creationDate);
		effectiveTime.setLow(low);
		return effectiveTime;
	}
	public static   II buildTemplateID(String root , String extension ,String assigningAuthorityName)
	{

			II templateID = DatatypesFactory.eINSTANCE.createII();
			if(root!=null)
			{
			templateID.setRoot(root);
			}
			if(extension!=null)
			{
			templateID.setExtension(extension);
			}
			if(assigningAuthorityName!=null)
			{
			templateID.setAssigningAuthorityName(assigningAuthorityName);
			}
			
			return templateID;

	}
	public static ST buildTitle(String title)
	{
		ST displayTitle = DatatypesFactory.eINSTANCE.createST();
		displayTitle.addText(title);
		return displayTitle;
	}

	public static II buildID(String root , String extension)
	{
		II id = DatatypesFactory.eINSTANCE.createII();
		if(root!=null)
		{
		id.setRoot(root);
		}
		if(extension!=null)
		{
		id.setExtension(extension);
		}
		return id;
		
	}
	
	public static CE buildCodeCE(String code , String codeSystem, String displayString, String codeSystemName)
	{
		CE e = DatatypesFactory.eINSTANCE.createCE();
		if(code!=null)
		{
		e.setCode(code);
		}
		if(codeSystem!=null)
		{
		e.setCodeSystem(codeSystem);
		}
		if(displayString!=null)
		{
		e.setDisplayName(displayString);
		}
		if(codeSystemName!=null)
		{
		e.setCodeSystemName(codeSystemName);
		}
		return e;

	}
	public static CD buildCodeCD(String code , String codeSystem, String displayString, String codeSystemName)
	{
		CD e = DatatypesFactory.eINSTANCE.createCD();
		if(code!=null)
		{
		e.setCode(code);
		}
		if(codeSystem!=null)
		{
		e.setCodeSystem(codeSystem);
		}
		if(displayString!=null)
		{
		e.setDisplayName(displayString);
		}
		if(codeSystemName!=null)
		{
		e.setCodeSystemName(codeSystemName);
		}
		return e;

	}
	public static  TS buildEffectiveTime(Date d)
	{
		TS effectiveTime = DatatypesFactory.eINSTANCE.createTS();
		SimpleDateFormat s = new SimpleDateFormat("yyyyMMddhhmmss");
		
		String creationDate = s.format(d);
	
		effectiveTime.setValue(creationDate);
		
		return effectiveTime;
	}
	
	public static ED buildEDText(String value)
	{
		ED text = DatatypesFactory.eINSTANCE.createED();
		text.addText("<reference value=\""+value+"\"/>");
		return text;
	}
	public static String getDatatypesValue(Integer datatypeId,Obs obs)
	{
		String value = "";
		switch(datatypeId)
		{
		case 1:
			value = obs.getValueNumeric().toString();
			break;
			
		case 2:
			value = obs.getValueCoded().getDisplayString();
			break;

		case 3:
			value = obs.getValueText();
			break;

		case 6:
			value = obs.getValueDate().toString();
			break;

		case 7:
			value = obs.getValueTime().toString();
			break;

		case 8:
			value = obs.getValueDatetime().toString();
			break;

		case 10:
			value = obs.getValueAsBoolean().toString();
			break;

		case 13:
			value = obs.getValueComplex();
			break;
		}
		return value;
	}
	
	public static Obs getLatestObs(List<Obs> observationList)
	{
		Map<String,Obs> latestObsdate=new HashMap<String,Obs>();
		if(observationList.isEmpty())
		{
			return null;
		}
		else
		{
		for (Obs obs : observationList) 
	   {
		  if(latestObsdate.isEmpty())
		  {
		  latestObsdate.put("Latest date", obs);
		  }
		   else
		  {
			      Date date=latestObsdate.get("Latest date").getObsDatetime();
			      if(date.before(obs.getObsDatetime()))
			      {
				    latestObsdate.put("Latest date", obs);
			      }
		   }
		}
		return latestObsdate.get("Latest date");
		}
	}
   public static CS getStatusCode()
   {
	   CS cs=DatatypesFactory.eINSTANCE.createCS();
	   cs.setCode("completed");
	   return cs;
   }
   public static IVL_TS buildDateTime(Date date)
   {
	   IVL_TS effectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
	   SimpleDateFormat s = new SimpleDateFormat("yyyyMMddhhmmss");
    	String creationDate = s.format(date);
    	effectiveTime.setValue(creationDate);
   	    return effectiveTime;
   }
   public static String getCodeSystemByName(String codeSystemName)
   {
	   String result="";
	   if(codeSystemName!=null)
	   {
		   if(codeSystemName.equalsIgnoreCase("SNOMED CT"))
		   {
			   result="2.16.840.1.113883.6.96";
		   }
		   else if(codeSystemName.equalsIgnoreCase("LOINC"))
		   {
			   result="2.16.840.1.113883.6.1";
		   }
	   }
	   return result;
   }
}
