package org.openmrs.module.CDAGenerator.SectionHandlers;

import java.text.SimpleDateFormat;
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
import org.openmrs.PersonAttribute;
import org.openmrs.api.APIException;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class SocialHistorySection extends BaseCdaSectionHandler
{
 private static Log log = LogFactory.getLog(SocialHistorySection.class);
public SocialHistorySection()
{
	this.sectionName="Social History";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.3.16";
	this.code="29762-2";
	this.sectionDescription="The social history section shall contain a narrative description of the person’s beliefs, home life, community life, work life, hobbies, and risky habits";
    this.parentTemplateId="2.16.840.1.113883.10.20.1.15";
}

public static Section buildSocialHistorySection(Patient p)
{
	
	Map<String,String> mappings=new HashMap<String,String>();
	Section section=CDAFactory.eINSTANCE.createSection();
    SocialHistorySection ccs=new SocialHistorySection();
    section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getParentTemplateId(),null ,null));
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
	builder.append("<th>Social History Element</th>"+delimeter);
	builder.append("<th>Description</th>"+delimeter);
	builder.append("<th>Effective Dates</th>"+delimeter);
	builder.append("</tr>"+delimeter);
	builder.append("</thead>"+delimeter);
	builder.append("<tbody>"+delimeter);
	
	mappings.put("160573003", "SNOMED CT");
    mappings.put("266918002","SNOMED CT");
    mappings.put("xx-illicitdrugs", "SNOMED CT");
    mappings.put("29762-2", "LOINC");
    
	
    ConceptService service = Context.getConceptService();
    for(Map.Entry<String,String> mapentry:mappings.entrySet())
	{
    List<Concept> socialHistoryConceptsList=new ArrayList<Concept>();
    Concept concepts=service.getConceptByMapping(mapentry.getKey(), mapentry.getValue(),false);
    if(concepts==null)
    {
    	throw new APIException(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoSuchConcept",new Object[]{mapentry.getKey(),mapentry.getValue()},null));
    }
    else
    {
    	socialHistoryConceptsList.add(concepts);
    }
	
    
    List<Obs> obsList = new ArrayList<Obs>();
	for (Concept concept : socialHistoryConceptsList) 
	{
		obsList.addAll(Context.getObsService().getObservationsByPersonAndConcept(p, concept));
	}
	if(obsList.isEmpty())
	{
		Concept concept= service.getConceptByMapping(mapentry.getKey(),mapentry.getValue(),false);
		 log.error(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoObservationsFound",new Object[]{concept.getConceptId(),concept.getName()},null));
	}
	else
	{
	 for (Obs obs : obsList) 
	 { 
		if(obs!=null)
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
			observation.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.22.4.38",null ,null ));
			observation.getIds().add(CDAHelper.buildID(obs.getUuid(),null));
		   
			CD ce=DatatypesFactory.eINSTANCE.createCD();
			ce.setCode(mapentry.getKey());
			ce.setCodeSystem(CDAHelper.getCodeSystemByName(mapentry.getValue()));
			ce.setDisplayName(obs.getConcept().getName().toString());
			ce.setCodeSystemName(mapentry.getValue());
			ce.setOriginalText(CDAHelper.buildEDText("#_"+obs.getId()));
			
			observation.setCode(ce);
			
			observation.setStatusCode(CDAHelper.getStatusCode("completed"));
			
			observation.setEffectiveTime(CDAHelper.buildDateTime(new Date()));
			
			ED value1=DatatypesFactory.eINSTANCE.createED(value);
			observation.getValues().add(value1);
			
			entry.setObservation(observation);
			section.getEntries().add(entry);
			
	  }
	 }
	}      
	}
	
    builder.append("</tbody>"+delimeter);
    builder.append("</table>"+delimeter);
	text.addText(builder.toString());
    section.setText(text);
	    return section;
	
	
}

}
