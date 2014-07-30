package org.openmrs.module.CDAGenerator.SectionHandlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class ReviewOfSystemsSection extends BaseCdaSectionHandler
{
	private static Log log = LogFactory.getLog(ReviewOfSystemsSection.class);
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
Map<String,String> mappings=new HashMap<String,String>();
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
        mappings.put("21840007", "SNOMED CT");
        mappings.put("364307006", "SNOMED CT");
        mappings.put("364306002", "SNOMED CT");
        mappings.put("xx-onbcp", "SNOMED CT");
        mappings.put("398700009","SNOMED CT");
                        
        for(Map.Entry<String,String> entry:mappings.entrySet())
     {
        Concept concepts=service.getConceptByMapping(entry.getKey(), entry.getValue(),false);
        if(concepts==null)
        {
         throw new APIException(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoSuchConcept",new Object[]{entry.getKey(),entry.getValue()},null));
        }
        else
        {
         ConceptsValueSetList.add(concepts);
        }
     }
        Concept concept=service.getConceptByMapping("10187-3", "LOINC",false);
        if(concept==null)
     {
     throw new APIException(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoSuchConcept",new Object[]{"10187-3","LOINC"},null));
     }
        Collection<ConceptAnswer> conceptAnswers=concept.getAnswers();
        List<Obs> obsOFAnswersOfReviewSystemConcept = new ArrayList<Obs>();
        for(ConceptAnswer conceptanswer:conceptAnswers)
        {
         Concept c=conceptanswer.getAnswerConcept();
         obsOFAnswersOfReviewSystemConcept.addAll(Context.getObsService().getObservationsByPersonAndConcept(patient, c));
         if(obsOFAnswersOfReviewSystemConcept.isEmpty())
         {
        	 log.error(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoObservationsFound",new Object[]{concept.getConceptId(),concept.getName()},null));
         }
        }
       
        List<Obs> obsList = new ArrayList<Obs>();
       
        for(Obs o:obsOFAnswersOfReviewSystemConcept)
        {
         obsList.add(o);
        }
 
     for (Concept concet : ConceptsValueSetList)
     {
     obsList.addAll(Context.getObsService().getObservationsByPersonAndConcept(patient, concet));
     if(obsList.isEmpty())
     {
       log.error(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoObservationsFound",new Object[]{concept.getConceptId(),concept.getName()},null));
     }
     }
     
     if(obsList.isEmpty())
     {
       StringBuilder noObsBuilder = new StringBuilder();
       noObsBuilder.append(delimeter);
       noObsBuilder.append("<Paragraph>"+delimeter);
       noObsBuilder.append("No Observations");
       noObsBuilder.append("</Paragraph>"+delimeter);
       text.addText(noObsBuilder.toString());
       section.setText(text);
        return section;
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
    	 }
     }
     builder.append("</tbody>"+delimeter);
     builder.append("</table>"+delimeter);
        text.addText(builder.toString());
        section.setText(text);
         return section;
     }
}
}