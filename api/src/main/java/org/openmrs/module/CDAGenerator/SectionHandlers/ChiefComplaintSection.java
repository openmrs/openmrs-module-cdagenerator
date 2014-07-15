package org.openmrs.module.CDAGenerator.SectionHandlers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
	builder.append("<paragraph>");
    
	ConceptService service = Context.getConceptService();
		
    
    Concept concept = service.getConceptByMapping("10154-3", "LOINC");
    if(concept==null)
   	{
   		throw new APIException(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoSuchConcept",new Object[]{"10154-3","LOINC"},null));
   	}
    
    List<Obs> observationList = new ArrayList<Obs>();
    observationList.addAll(Context.getObsService().getObservationsByPersonAndConcept(patient, concept));

    if(observationList.isEmpty())
    {
    	throw new APIException(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoObservationsFound",new Object[]{concept.getConceptId(),concept.getName()},null));
    }
    
    String value="";
    
        Obs obs=CDAHelper.getLatestObs(observationList);
        
    	int type = obs.getConcept().getDatatype().getId();

		value=CDAHelper.getDatatypesValue(type,obs);
		
		
    builder.append(value);
    builder.append("</paragraph>");
	
    text.addText(builder.toString());
     section.setText(text);        
    	return section;
	
}
}
