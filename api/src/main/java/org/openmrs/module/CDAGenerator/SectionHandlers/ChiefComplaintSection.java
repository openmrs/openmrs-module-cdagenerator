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
    
    StringBuilder builder = new StringBuilder();
    String delimeter="\n";
    builder.append(delimeter);
    builder.append("<table Border=\"1\" width=\"100%\">"+delimeter);
	builder.append("<thead>"+delimeter);
	builder.append("<tr>"+delimeter);
	builder.append("<th>Chief Complaint Section</th>"+delimeter);
	builder.append("<th>Description</th>"+delimeter);
	builder.append("<th>Effective Dates</th>"+delimeter);
	builder.append("</tr>"+delimeter);
	builder.append("</thead>"+delimeter);
	builder.append("<tbody>"+delimeter);
    
	ConceptService service = Context.getConceptService();
	SimpleDateFormat s = new SimpleDateFormat("MMddyyyy");
	
    
    Concept concept = service.getConceptByMapping("10154-3", "LOINC");

    
    
    
    List<Obs> observationList = new ArrayList<Obs>();
    observationList.addAll(Context.getObsService().getObservationsByPersonAndConcept(patient, concept));
    
    
    for (Obs obs : observationList) 
    {
    	builder.append("<tr>"+delimeter);
		builder.append("<td> <content ID = \""+obs.getId()+"\" >"+obs.getConcept().getName()+"</content></td>"+delimeter);	
		builder.append("<td>");
		int type = obs.getConcept().getDatatype().getId();
		String value=getDatatypesValue(type,obs);
		
		builder.append(value+"</td>"+delimeter);
		builder.append("<td>"+s.format(obs.getObsDatetime())+"</td>"+delimeter);
		builder.append("</tr>"+delimeter);
		
    }
     builder.append("</tbody>"+delimeter);
	 builder.append("</table>"+delimeter);
	 text.addText(builder.toString());
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
