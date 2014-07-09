package org.openmrs.module.CDAGenerator.SectionHandlers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Entry;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openhealthtools.mdht.uml.hl7.datatypes.CD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CE;
import org.openhealthtools.mdht.uml.hl7.datatypes.CS;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.ST;
import org.openhealthtools.mdht.uml.hl7.vocab.ActClassObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActMoodDocumentObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActRelationshipEntry;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class SocialHistorySection extends BaseCdaSectionHandler
{
	
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
	List<Concept> socialHistoryConceptsList=new ArrayList<Concept>();
	Section section=CDAFactory.eINSTANCE.createSection();
    SocialHistorySection ccs=new SocialHistorySection();
    section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getParentTemplateId(),null ,null ));
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
        
    ConceptService service = Context.getConceptService();

    
    
    socialHistoryConceptsList.add(service.getConceptByMapping("160573003", "SNOMED CT"));// concept we get is Alcohol use status 
    socialHistoryConceptsList.add(service.getConceptByMapping("266918002", "SNOMED CT"));//concept we get is type of tobacco product
    socialHistoryConceptsList.add(service.getConceptByMapping("xx-illicitdrugs", "SNOMED CT"));// concept we get is Illicit drug consumption (this is newly created concept)
    socialHistoryConceptsList.add(service.getConceptByMapping("29762-2", "LOINC"));//concept we get is social history of patient 
   // System.out.println(socialHistoryConceptsList);
    List<Obs> obsList = new ArrayList<Obs>();
	for (Concept concept : socialHistoryConceptsList) {
		obsList.addAll(Context.getObsService().getObservationsByPersonAndConcept(p, concept));	
	}

	 for (Obs obs : obsList) 
	 { 
		/* System.out.println(obs);
		 System.out.println(obs.getObsDatetime());
		 System.out.println(CDAHelper.getDateFormat().format(obs.getObsDatetime()));*/
		 
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
			ce.setCode(obs.getConcept().toString());
			ce.setCodeSystem("2.16.840.1.113883.6.96");
			ce.setDisplayName(obs.getConcept().getName().toString());
			ce.setOriginalText(CDAHelper.buildEDText("#_"+obs.getId()));
			
			observation.setCode(ce);
			
			CS statusCode = DatatypesFactory.eINSTANCE.createCS();
			statusCode.setCode("completed");
			observation.setStatusCode(statusCode);
			
			observation.setEffectiveTime(CDAHelper.buildEffectiveTimeinIVL(obs.getObsDatetime(), null));
			
			ST value1 = CDAHelper.buildTitle(value);
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
