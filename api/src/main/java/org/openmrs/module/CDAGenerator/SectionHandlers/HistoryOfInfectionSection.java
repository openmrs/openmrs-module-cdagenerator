package org.openmrs.module.CDAGenerator.SectionHandlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Entry;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openhealthtools.mdht.uml.hl7.datatypes.CD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CE;
import org.openhealthtools.mdht.uml.hl7.datatypes.CS;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.ED;
import org.openhealthtools.mdht.uml.hl7.datatypes.ST;
import org.openhealthtools.mdht.uml.hl7.vocab.ActClassObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActMoodDocumentObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActRelationshipEntry;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class HistoryOfInfectionSection extends BaseCdaSectionHandler 
{
	private static Log log = LogFactory.getLog(HistoryOfInfectionSection.class);
public HistoryOfInfectionSection()
{
	this.sectionName="HISTORY OF INFECTION";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.16.2.1.1";
	this.code="XX-HistoryOfInfection";
	this.sectionDescription="The history of infection section shall contain a narrative description of any infections the patient may have contracted prior to the patient's current condition";
}

public static Section buildHistoryOfInfectionSection(Patient patient)
{
	
	Map<String,String> mappings=new HashMap<String,String>();
	
	Section section=CDAFactory.eINSTANCE.createSection();
    HistoryOfInfectionSection ccs=new HistoryOfInfectionSection();
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
	builder.append("<th>History Of Infection Element</th>"+delimeter);
	builder.append("<th>Description</th>"+delimeter);
	builder.append("<th>Effective Dates</th>"+delimeter);
	builder.append("</tr>"+delimeter);
	builder.append("</thead>"+delimeter);
	builder.append("<tbody>"+delimeter);
	
	mappings.put("170464005", "SNOMED CT");
	mappings.put("402888002", "SNOMED CT");
	mappings.put("240480009", "SNOMED CT"); 
	mappings.put("49882001", "SNOMED CT");
	mappings.put("34014006", "SNOMED CT");
	mappings.put("235871004", "SNOMED CT");
	mappings.put("235872006","SNOMED CT");
	mappings.put("15628003","SNOMED CT");
	mappings.put("8098009","SNOMED CT");
	mappings.put("312099009","SNOMED CT");
	mappings.put("302812006","SNOMED CT");
	mappings.put("165816005","SNOMED CT");
	mappings.put("76272004","SNOMED CT");
	
	 ConceptService service = Context.getConceptService();
	 for(Map.Entry<String,String> mapentry:mappings.entrySet())
	{
		 List<Concept> ConceptsList=new ArrayList<Concept>();
	 for(Concept concepts:service.getConceptsByMapping(mapentry.getKey(), mapentry.getValue(),false))
	 {
	    if(concepts==null)
	    {
	    	throw new APIException(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoSuchConcept",new Object[]{mapentry.getKey(),mapentry.getValue()},null));
	    }
	    else
	    {
	    	
	    	ConceptsList.add(concepts);
	    }
	 }
	
	    
	    List<Obs> obsList = new ArrayList<Obs>();
		for (Concept concept : ConceptsList) 
		{
			if(!concept.getDatatype().getName().equals("N/A"))
			{
			obsList.addAll(Context.getObsService().getObservationsByPersonAndConcept(patient, concept));	
			}
		
		if(obsList.isEmpty())
		{
			 log.error(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoObservationsFound",new Object[]{concept.getConceptId(),concept.getName()},null));
			 
			    builder.append("<tr>"+delimeter);
				builder.append("<td> No Observation Element with concept id: "+concept.getId()+" was found</td>"+delimeter);	
				builder.append("<td>");
				
				builder.append("NULL"+"</td>"+delimeter);
				builder.append("<td>"+"NULL"+"</td>"+delimeter);
				builder.append("</tr>"+delimeter);
				
				Entry entry = CDAFactory.eINSTANCE.createEntry();
				entry.setTypeCode(x_ActRelationshipEntry.DRIV);
				Observation observation = CDAFactory.eINSTANCE.createObservation();
				observation.setClassCode(ActClassObservation.OBS);
				observation.setMoodCode(x_ActMoodDocumentObservation.EVN);
				observation.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13",null ,null ));
				observation.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.1.16.5.6",null ,null ));
				observation.getIds().add(CDAHelper.buildID("34a09968-82ea-49ce-a830-0bf8e3ff19b9",null));
				
				observation.setCode(CDAHelper.buildCodeCD(mapentry.getKey(),CDAHelper.getCodeSystemByName(mapentry.getValue()),"Null", mapentry.getValue()));
				observation.setText(CDAHelper.buildEDText("#_No Observation"));
				
				observation.setStatusCode(CDAHelper.getStatusCode("completed"));
				
				observation.setEffectiveTime(CDAHelper.buildDateTime(new Date()));
				
				ED value1=DatatypesFactory.eINSTANCE.createED(" No Observation");
				observation.getValues().add(value1);
				
				CE interpretationcode=CDAHelper.buildCodeCE("N", "2.16.840.1.113883.5.83", null, null);
				observation.getInterpretationCodes().add(interpretationcode);
				
				CE methodcode=CDAHelper.buildCodeCE(null,CDAHelper.getCodeSystemByName(mapentry.getValue()),null,mapentry.getValue());
				observation.getMethodCodes().add(methodcode);
				
				CE targetsite=CDAHelper.buildCodeCE(null,CDAHelper.getCodeSystemByName(mapentry.getValue()),null,mapentry.getValue());
				observation.getTargetSiteCodes().add(targetsite);
				
				
				entry.setObservation(observation);
				section.getEntries().add(entry);
		}
		}
		
		for (Obs obs : obsList) 
		 { 
			
			 
			    builder.append("<tr>"+delimeter);
				builder.append("<td ID = \"_"+obs.getId()+"\" >"+obs.getConcept().getName()+"</td>"+delimeter);	
				builder.append("<td>");
				int type = obs.getConcept().getDatatype().getId();
				String value=CDAHelper.getDatatypesValue(type,obs);
				
				builder.append(value+"</td>"+delimeter);
				builder.append("<td>"+CDAHelper.getDateFormat().format(obs.getObsDatetime())+"</td>"+delimeter);
				builder.append("</tr>"+delimeter);
				
				Entry entry = CDAFactory.eINSTANCE.createEntry();
				entry.setTypeCode(x_ActRelationshipEntry.DRIV);
				Observation observation = CDAFactory.eINSTANCE.createObservation();
				observation.setClassCode(ActClassObservation.OBS);
				observation.setMoodCode(x_ActMoodDocumentObservation.EVN);
				observation.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13",null ,null ));
				observation.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.1.16.5.6",null ,null ));
				observation.getIds().add(CDAHelper.buildID(obs.getUuid(),null));
			   
				observation.setCode(CDAHelper.buildCodeCD(mapentry.getKey(),CDAHelper.getCodeSystemByName(mapentry.getValue()),obs.getConcept().getName().toString(), mapentry.getValue()));
				observation.setText(CDAHelper.buildEDText("#_"+obs.getId()));
				
				observation.setStatusCode(CDAHelper.getStatusCode("completed"));
				
				observation.setEffectiveTime(CDAHelper.buildDateTime(new Date()));
				
				ED value1=DatatypesFactory.eINSTANCE.createED(" No Observation");
				observation.getValues().add(value1);
				
				CE interpretationcode=CDAHelper.buildCodeCE("N", "2.16.840.1.113883.5.83", null, null);
				observation.getInterpretationCodes().add(interpretationcode);
				
				CE methodcode=CDAHelper.buildCodeCE(null,CDAHelper.getCodeSystemByName(mapentry.getValue()),null,mapentry.getValue());
				observation.getMethodCodes().add(methodcode);
				
				CE targetsite=CDAHelper.buildCodeCE(null,CDAHelper.getCodeSystemByName(mapentry.getValue()),null,mapentry.getValue());
				observation.getTargetSiteCodes().add(targetsite);
				
				
				entry.setObservation(observation);
				section.getEntries().add(entry);
		     }			
		   }

	     builder.append("</tbody>"+delimeter);
		 builder.append("</table>"+delimeter);
			text.addText(builder.toString());
	        section.setText(text);        
	   
		
		return section;



}
}
