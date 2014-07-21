package org.openmrs.module.CDAGenerator.SectionHandlers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Component4;
import org.openhealthtools.mdht.uml.cda.Entry;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.Organizer;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openhealthtools.mdht.uml.hl7.datatypes.CE;
import org.openhealthtools.mdht.uml.hl7.datatypes.CS;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVL_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.ST;
import org.openhealthtools.mdht.uml.hl7.vocab.ActClassObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.ActMood;
import org.openhealthtools.mdht.uml.hl7.vocab.NullFlavor;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActClassDocumentEntryOrganizer;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActMoodDocumentObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActRelationshipEntry;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class PregnancyHistorySection extends BaseCdaSectionHandler 
{
	public PregnancyHistorySection()
	{
		this.sectionName="HISTORY OF PREGNANCIES";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.5.3.4";
		this.code="10162-6";
		this.sectionDescription="The pregnancy history section contains coded entries describing the patient history of pregnancies";
	}

	public static Section buildPregnancyHistorySection(Patient patient)
	{

	List<Concept> ConceptsList=new ArrayList<Concept>();
	Map<String,String> mappings=new HashMap<String,String>();
	Section section=CDAFactory.eINSTANCE.createSection();
	PregnancyHistorySection ccs=new PregnancyHistorySection();
	section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
	section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
	
	section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
    StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();

    StringBuilder builder = new StringBuilder();
    String delimeter="\n";
    builder.append(delimeter);
    builder.append("<table>"+delimeter);
	builder.append("<thead>"+delimeter);
	builder.append("<tr>"+delimeter);
	builder.append("<th>Pregnancy History Element</th>"+delimeter);
	builder.append("<th>Description</th>"+delimeter);
	builder.append("<th>Effective Dates</th>"+delimeter);
	builder.append("</tr>"+delimeter);
	builder.append("</thead>"+delimeter);
	builder.append("<tbody>"+delimeter);
	
	 
	 mappings.put("11636-8", "LOINC");
	 mappings.put("11637-6","LOINC");
	 mappings.put("11639-2","LOINC");
	 mappings.put("11638-4","LOINC");
	 mappings.put("11612-9","LOINC");
	 mappings.put("11996-6","LOINC");
	 mappings.put("49051-6","LOINC");
	 mappings.put("32396-4","LOINC");
	 mappings.put("8339-4","LOINC");
	 mappings.put("11449-6","LOINC");
	 mappings.put("8678-5","LOINC");
	 mappings.put("8665-2","LOINC");
	 mappings.put("11779-6","LOINC");
	 
	 ConceptService service = Context.getConceptService();
	    for(Map.Entry<String,String> entry:mappings.entrySet())
		{
	    Concept concepts=service.getConceptByMapping(entry.getKey(), entry.getValue());
	    if(concepts==null)
	    {
	    	throw new APIException(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoSuchConcept",new Object[]{entry.getKey(),entry.getValue()},null));
	    }
	    else
	    {
	    	ConceptsList.add(concepts);
	    }
		}
	    
	    List<Obs> obsList = new ArrayList<Obs>();
		for (Concept concept : ConceptsList) 
		{
			obsList.addAll(Context.getObsService().getObservationsByPersonAndConcept(patient, concept));	
			if(obsList.isEmpty())
			{
				throw new APIException(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoObservationsFound",new Object[]{concept.getConceptId(),concept.getName()},null));
			}
		}


		for (Obs obs : obsList) 
		 { 			 
			    builder.append("<tr>"+delimeter);
				builder.append("<td ID = \"_"+obs.getId()+ccs.getCode()+"\" >"+obs.getConcept().getName()+"</td>"+delimeter);	
				builder.append("<td>");
				int type = obs.getConcept().getDatatype().getId();
				String value=CDAHelper.getDatatypesValue(type,obs);
				
				builder.append(value+"</td>"+delimeter);
				builder.append("<td>"+CDAHelper.getDateFormat().format(obs.getObsDatetime())+"</td>"+delimeter);
				builder.append("</tr>"+delimeter);
				
				Entry e=CDAFactory.eINSTANCE.createEntry();
		         e.setTypeCode(x_ActRelationshipEntry.DRIV);
		        Organizer organizer=CDAFactory.eINSTANCE.createOrganizer();
		        organizer.setClassCode(x_ActClassDocumentEntryOrganizer.CLUSTER);
		        organizer.setMoodCode(ActMood.EVN);
		        organizer.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13.5.1",null,null));
		        
		        organizer.getIds().add(CDAHelper.buildTemplateID("id",null,null));
		        organizer.setCode(CDAHelper.buildCodeCD("118185001", "2.16.840.1.113883.6.96","Pregnancy Finding", "SNOMED CT"));
		        
		    	organizer.setStatusCode(CDAHelper.getStatusCode());
		    	organizer.setEffectiveTime(CDAHelper.buildDateTime(new Date()));
		    	
		    	Component4 component=CDAFactory.eINSTANCE.createComponent4();
		    	
		    	 Observation observation=CDAFactory.eINSTANCE.createObservation();
			     observation.setClassCode(ActClassObservation.OBS);
			     observation.setMoodCode(x_ActMoodDocumentObservation.EVN);
			     	
			     observation.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13",null,null));
			     observation.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13.5",null,null));
			     observation.getIds().add(CDAHelper.buildTemplateID("id",null,null));
			     observation.setCode(CDAHelper.buildCodeCE("10162-6","2.16.840.1.113883.6.1","HISTORY OF PREGNANCIES","LOINC"));
			     
			     observation.setText(CDAHelper.buildEDText("#_"+obs.getId()+ccs.getCode()));
			     
			    
					observation.setStatusCode(CDAHelper.getStatusCode());
		    	
					observation.setEffectiveTime(CDAHelper.buildDateTime(new Date()));
					
					ST value1 = CDAHelper.buildTitle(value);
					observation.getValues().add(value1);
					
					component.setObservation(observation); 
			        organizer.getComponents().add(component);
			    	
			        e.setOrganizer(organizer);
					section.getEntries().add(e);
		 }

	     builder.append("</tbody>"+delimeter);
		 builder.append("</table>"+delimeter);
			text.addText(builder.toString());
	        section.setText(text);        
	   
	return section;
	}
}
