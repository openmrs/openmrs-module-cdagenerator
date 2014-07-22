package org.openmrs.module.CDAGenerator.SectionHandlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Entry;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openhealthtools.mdht.uml.hl7.datatypes.CD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CS;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
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
public HistoryOfInfectionSection()
{
	this.sectionName="HISTORY OF INFECTION";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.16.2.1.1";
	this.code="XX-HistoryOfInfection";
	this.sectionDescription="The history of infection section shall contain a narrative description of any infections the patient may have contracted prior to the patient's current condition";
}

public static Section buildHistoryOfInfectionSection(Patient patient)
{
	List<Concept> ConceptsList=new ArrayList<Concept>();
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
			   
				observation.setCode(CDAHelper.buildCodeCD(obs.getConcept().toString(),"2.16.840.1.113883.6.96",obs.getConcept().getName().toString(), null));
				observation.setText(CDAHelper.buildEDText("#_"+obs.getId()));
				
				observation.setStatusCode(CDAHelper.getStatusCode());
				
				observation.setEffectiveTime(CDAHelper.buildDateTime(new Date()));
				
				
				CD value1=DatatypesFactory.eINSTANCE.createCD();
				value1.setCode(obs.getConcept().toString());
				value1.setCodeSystem("2.16.840.1.113883.6.96");
				value1.setDisplayName(obs.getConcept().getName().toString());
				value1.setCodeSystemName("SNOMED CT");
				observation.getValues().add(value1);
				
				entry.setObservation(observation);
				section.getEntries().add(entry);
				
		 }

	     builder.append("</tbody>"+delimeter);
		 builder.append("</table>"+delimeter);
			text.addText(builder.toString());
	        section.setText(text);        
	   
		
		return section;



}
}
