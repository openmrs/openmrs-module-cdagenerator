package org.openmrs.module.CDAGenerator.SectionHandlers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class ChiefComplaintSection extends BaseCdaSectionHandler
{
public ChiefComplaintSection()
{
	this.sectionName="Chief Complaint";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.13.2.1";
	this.sectionDescription="This contains a narrative description of the patient's chief complaint";
	this.code="10154-3";
}
public static Section buildChiefComplaintSection(Patient patient)
{
	Section section=CDAFactory.eINSTANCE.createSection();
    ChiefComplaintSection ccs=new ChiefComplaintSection();
    section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
    section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
    section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
    StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
    StringBuilder buffer = new StringBuilder();
    String delimeter="\n";
    buffer.append(delimeter);
	buffer.append("<list>"+delimeter);
        
    ConceptService service = Context.getConceptService();
    Concept concept = service.getConceptByMapping("10154-3", "LOINC");
    String dataType= concept.getDatatype().getName();
    SimpleDateFormat s = new SimpleDateFormat("yyyyMMddhhmmss");
    
    
    List<Obs> obsList = new ArrayList<Obs>();
    obsList.addAll(Context.getObsService().getObservationsByPersonAndConcept(patient, concept));
    
    
    for (Obs obs : obsList) {
    	
    	buffer.append("<item>"+delimeter);
		buffer.append("<content>"+delimeter);
		buffer.append("<Encounter>"+delimeter);
		buffer.append("<EncounterID>"+obs.getEncounter().getEncounterId()+"</EncounterID>"+delimeter);
		buffer.append("<EncounterType>"+obs.getEncounter().getEncounterType().getName()+"</EncounterType>"+delimeter);
		buffer.append("<EncounterDate>"+obs.getEncounter().getEncounterDatetime()+"</EncounterDate>"+delimeter);
		buffer.append("<EncounterLocation>"+obs.getEncounter().getLocation()+"</EncounterLocation>"+delimeter);
		buffer.append("</Encounter>"+delimeter);
		
		int datatypeID=obs.getConcept().getDatatype().getId();
		String answer=getDatatypesValue(datatypeID,obs);
		buffer.append("<Observation>"+delimeter);
		buffer.append("<ObservationID>"+obs.getId()+"</ObservationID>"+delimeter);
		buffer.append("<QuestionConcept>"+obs.getConcept().getPreferredName(new Locale("en"))+"</QuestionConcept>"+delimeter);
		buffer.append("<Value>"+answer+"</Value>"+delimeter);		
		buffer.append("<ObservationDate>"+obs.getObsDatetime()+"</ObservationDate>"+delimeter);
		buffer.append("</Observation>"+delimeter);
		
		
		buffer.append("</content>"+delimeter);
		buffer.append("</item>"+delimeter);
   
    //text.addText("Text as described above"+":"+concept.getUuid()+":"+concept.getDisplayString()+":"+concept.getConceptClass().getName()+":"+dataType);
    //section.setText(text);        
    }
    buffer.append("</list>"+delimeter);
	text.addText(buffer.toString());

   
	section.setText(text);
    
    
    
	return section;
	
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
}
