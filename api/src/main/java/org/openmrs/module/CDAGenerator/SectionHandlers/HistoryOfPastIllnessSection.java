package org.openmrs.module.CDAGenerator.SectionHandlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openhealthtools.mdht.uml.cda.Act;
import org.openhealthtools.mdht.uml.cda.AssignedAuthor;
import org.openhealthtools.mdht.uml.cda.Author;
import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Entry;
import org.openhealthtools.mdht.uml.cda.EntryRelationship;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.Organization;
import org.openhealthtools.mdht.uml.cda.Person;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openhealthtools.mdht.uml.hl7.datatypes.AD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CS;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.ED;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVL_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVXB_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.ON;
import org.openhealthtools.mdht.uml.hl7.datatypes.PN;
import org.openhealthtools.mdht.uml.hl7.datatypes.TEL;
import org.openhealthtools.mdht.uml.hl7.vocab.ActClassObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.NullFlavor;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActClassDocumentEntryAct;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActMoodDocumentObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActRelationshipEntryRelationship;
import org.openhealthtools.mdht.uml.hl7.vocab.x_DocumentActMood;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class HistoryOfPastIllnessSection extends BaseCdaSectionHandler 
{
	private static Log log = LogFactory.getLog(HistoryOfPastIllnessSection.class);
	public HistoryOfPastIllnessSection()
	{
		this.sectionName="HISTORY OF PAST ILLNESS";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.3.8";
		this.code="11348-0";
		this.sectionDescription="The History of Past Illness section shall contain a narrative description of the conditions the patient suffered in the past. It shall include entries for problems as described in the Entry Content Modules";
	}
	/**
	 * Create CDA section(History of past illness section)
	 * @param patient
	 * @return CDA section or Report errors
	 * @should return CDA section
	 * @should return errors in Section
	 */
	public static Section buildHistoryOfPastIllnessSection(Patient patient)
	{
	Map<String,String> mappings=new HashMap<String,String>();
	Section section=CDAFactory.eINSTANCE.createSection();
	HistoryOfPastIllnessSection ccs=new HistoryOfPastIllnessSection();
	section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
	section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
	section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
	StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
    text.addText("Text as described above");
    section.setText(text); 
    ConceptService service = Context.getConceptService();
    mappings.put("64572001", "SNOMED CT");
    mappings.put("418799008", "SNOMED CT");
    mappings.put("404684003", "SNOMED CT");
    mappings.put("409586006", "SNOMED CT");
    mappings.put("248536006", "SNOMED CT");
    mappings.put("282291009", "SNOMED CT");
    mappings.put("11348-0", "LOINC");
       
    
    Entry e=CDAFactory.eINSTANCE.createEntry();
    Act act=CDAFactory.eINSTANCE.createAct();
    act.setClassCode(x_ActClassDocumentEntryAct.ACT);
    act.setMoodCode(x_DocumentActMood.EVN);
    act.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.27",null,null));
    act.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.5.1",null,null));
    act.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.5.2",null,null));
    act.getIds().add(CDAHelper.buildID("ea645215-f9cf-4277-8edc-3cb18b2b77be",null));
    CD code=DatatypesFactory.eINSTANCE.createCD();
    code.setNullFlavor(NullFlavor.NA);
    act.setCode(code);
    act.setStatusCode(CDAHelper.getStatusCode("active"));
    act.setEffectiveTime(CDAHelper.buildEffectiveTimeinIVL(null));
        
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
			else
			{
				if(concept.getSetMembers()!=null)
				{
				List<Concept> conceptsetmemebers=concept.getSetMembers();    
		        for(Concept conceptmem:conceptsetmemebers)
		        {
		        	List<Obs> obsOFconceptsetmemebers = new ArrayList<Obs>();
		         obsOFconceptsetmemebers.addAll(Context.getObsService().getObservationsByPersonAndConcept(patient, conceptmem));
		         if(!obsOFconceptsetmemebers.isEmpty())
		           {
		             for(Obs o:obsOFconceptsetmemebers)
		             {
		              obsList.add(o);
		             }
		           }
		         }
			  }
		    }
			if(obsList.isEmpty())
			{
				 log.error(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoObservationsFound",new Object[]{concept.getConceptId(),concept.getName()},null));
				 
				 EntryRelationship entryRelationship1=CDAFactory.eINSTANCE.createEntryRelationship();
			        entryRelationship1.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
			        entryRelationship1.setInversionInd(false);
			            
			        Observation observation1=CDAFactory.eINSTANCE.createObservation();
			        observation1.setClassCode(ActClassObservation.OBS);
			        observation1.setMoodCode(x_ActMoodDocumentObservation.EVN);
			        observation1.setNegationInd(false);
			        
			        observation1.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.28",null,null));
			        observation1.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.5",null,null));
			
			       
			    	observation1.getIds().add(CDAHelper.buildID("45e5b079-5f94-4d1e-8707-ebc207200fd3",null));
			    	
			    	observation1.setCode(CDAHelper.buildCodeCD(mapentry.getKey(),"2.16.840.1.113883.6.96",null,"SNOMED CT"));
			        ED answerTxt=DatatypesFactory.eINSTANCE.createED(); 
			        observation1.setText(answerTxt.addText("No Observations found for concept Name:"+concept.getName()+"and Concept id:"+concept.getId()));
			        observation1.setStatusCode(CDAHelper.getStatusCode("completed"));
			        observation1.setEffectiveTime(CDAHelper.buildEffectiveTimeinIVL(null, null));
			        
			        CD codecd=DatatypesFactory.eINSTANCE.createCD();
			        codecd.setCode("396782006");
			        codecd.setCodeSystem("2.16.840.1.113883.6.96");
			        codecd.setDisplayName("Past Medical History Unknown");
			        codecd.setCodeSystemName("SNOMED CT");
			        observation1.getValues().add(codecd);
			    
			        entryRelationship1.setObservation(observation1);
			        act.getEntryRelationships().add(entryRelationship1);
			
			}
	     }
	
		
		
    for (Obs obs : obsList) 
	 { 
    
    	EntryRelationship entryRelationship1=CDAFactory.eINSTANCE.createEntryRelationship();
        entryRelationship1.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
        entryRelationship1.setInversionInd(false);
            
        Observation observation1=CDAFactory.eINSTANCE.createObservation();
        observation1.setClassCode(ActClassObservation.OBS);
        observation1.setMoodCode(x_ActMoodDocumentObservation.EVN);
        observation1.setNegationInd(false);
        
        observation1.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.28",null,null));
        observation1.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.5",null,null));
   
    int type = obs.getConcept().getDatatype().getId();
	String value=CDAHelper.getDatatypesValue(type,obs);
	
    observation1.getIds().add(CDAHelper.buildID(obs.getUuid(),null));
    if(mapentry.getKey().equals("11348-0"))
    {
    observation1.setCode(CDAHelper.buildCodeCD("282291009","2.16.840.1.113883.6.96",null,"SNOMED CT"));
    }
    else
    {
    	observation1.setCode(CDAHelper.buildCodeCD(mapentry.getKey(),"2.16.840.1.113883.6.96",null,"SNOMED CT"));
    }
    ED answerTxt=DatatypesFactory.eINSTANCE.createED(); 
    observation1.setText(answerTxt.addText(value));
    observation1.setStatusCode(CDAHelper.getStatusCode("completed"));
    observation1.setEffectiveTime(CDAHelper.buildEffectiveTimeinIVL(null, null));
   
    
    CD codecd=DatatypesFactory.eINSTANCE.createCD();
    codecd.setCode(obs.getConcept().getId().toString());
    codecd.setCodeSystem(CDAHelper.getCodeSystemByName(mapentry.getValue()));
    codecd.setDisplayName(obs.getConcept().getName().toString());
    codecd.setCodeSystemName(mapentry.getValue());
    observation1.getValues().add(codecd);
    
    entryRelationship1.setObservation(observation1);
    act.getEntryRelationships().add(entryRelationship1);
	   }
	}
    e.setAct(act);
    section.getEntries().add(e);
    return section;
	}   
}