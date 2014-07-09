package org.openmrs.module.CDAGenerator.SectionHandlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

public class ReviewOfSystemsSection extends BaseCdaSectionHandler 
{
	public ReviewOfSystemsSection()
	{
		this.sectionName="REVIEW OF SYSTEMS";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.3.18";
		this.code="10187-3";
		this.sectionDescription="The review of systems section shall contain a narrative description of the responses the patient gave to a set of routine questions on the functions of each anatomic body system.";
	}

	public static Section buildReviewOfSystemsSection(Patient patient)
	{
		List<Concept> ConceptsValueSetList=new ArrayList<Concept>();
		Section section=CDAFactory.eINSTANCE.createSection();
        ReviewOfSystemsSection ccs=new ReviewOfSystemsSection();
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
    	builder.append("<th>Review of System Element</th>"+delimeter);
    	builder.append("<th>Description</th>"+delimeter);
    	builder.append("<th>Effective Dates</th>"+delimeter);
    	builder.append("</tr>"+delimeter);
    	builder.append("</thead>"+delimeter);
    	builder.append("<tbody>"+delimeter);
            
        ConceptService service = Context.getConceptService();
        ConceptsValueSetList.add(service.getConceptByMapping("21840007", "SNOMED CT")); 
        ConceptsValueSetList.add(service.getConceptByMapping("364307006", "SNOMED CT"));
        ConceptsValueSetList.add(service.getConceptByMapping("364306002", "SNOMED CT"));
        ConceptsValueSetList.add(service.getConceptByMapping("xx-onbcp", "SNOMED CT"));
        ConceptsValueSetList.add(service.getConceptByMapping("398700009", "SNOMED CT"));
                
        Concept concept=service.getConceptByMapping("10187-3", "LOINC");
        Collection<ConceptAnswer> conceptAnswers=concept.getAnswers();
        List<Obs> obsOFAnswersOfReviewSystemConcept = new ArrayList<Obs>();
        for(ConceptAnswer conceptanswer:conceptAnswers)
        {
        	Concept c=conceptanswer.getAnswerConcept();
        	obsOFAnswersOfReviewSystemConcept.addAll(Context.getObsService().getObservationsByPersonAndConcept(patient, c));
        	
        }
       
        List<Obs> obsList = new ArrayList<Obs>();
       
        for(Obs o:obsOFAnswersOfReviewSystemConcept)
        {
        	obsList.add(o);
        }
 
    	for (Concept concet : ConceptsValueSetList) 
    	{
    		obsList.addAll(Context.getObsService().getObservationsByPersonAndConcept(patient, concet));
 
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
    	 }        
    	 builder.append("</tbody>"+delimeter);
    	 builder.append("</table>"+delimeter);
        text.addText(builder.toString());
        section.setText(text);        
		return section;
	}
}
