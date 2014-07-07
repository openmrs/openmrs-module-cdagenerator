package org.openmrs.module.CDAGenerator.SectionHandlers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class HistoryOfPresentIllnessSection extends BaseCdaSectionHandler
{
public HistoryOfPresentIllnessSection()
{
	this.sectionName="HISTORY OF PRESENT ILLNES";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.3.4";
	this.sectionDescription="This contains a narrative description of the patient's Present Illness histroy";
	this.code="10164-2";
}
public static Section buildHistoryOfPresentIllnessSection(Patient patient)
{
	Section section=CDAFactory.eINSTANCE.createSection();
    HistoryOfPresentIllnessSection ccs=new HistoryOfPresentIllnessSection();
    section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
    section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
    section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
    StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
    StringBuilder builder = new StringBuilder();
	builder.append("<paragraph>");
    
	ConceptService service = Context.getConceptService();
		
    Map<String,Date> latestObsdate=new HashMap<String,Date>();
    Concept concept = service.getConceptByMapping("10164-2", "LOINC");

    List<Obs> observationList = new ArrayList<Obs>();
    observationList.addAll(Context.getObsService().getObservationsByPersonAndConcept(patient, concept));
    String value="";
    
    for (Obs obs : observationList) 
    {
		int type = obs.getConcept().getDatatype().getId();
	
		
		if(latestObsdate.isEmpty())
		{
		latestObsdate.put("Latest date", obs.getObsDatetime());
		value=CDAHelper.getDatatypesValue(type,obs);
		}
		else
		{
			Date date=latestObsdate.get("Latest date");
			if(date.before(obs.getObsDatetime()))
			{
				latestObsdate.put("Latest date", obs.getObsDatetime());
				value=CDAHelper.getDatatypesValue(type,obs);
			}
		}
		
    }
    builder.append(value);
    builder.append("</paragraph>");
	 text.addText(builder.toString());
    section.setText(text);        
	return section;
}


}