package org.openmrs.module.CDAGenerator.SectionHandlers;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.openhealthtools.mdht.uml.cda.AssignedAuthor;
import org.openhealthtools.mdht.uml.cda.Author;
import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Component4;
import org.openhealthtools.mdht.uml.cda.Entry;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.ObservationRange;
import org.openhealthtools.mdht.uml.cda.Organizer;
import org.openhealthtools.mdht.uml.cda.Person;
import org.openhealthtools.mdht.uml.cda.ReferenceRange;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openhealthtools.mdht.uml.hl7.datatypes.AD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CE;
import org.openhealthtools.mdht.uml.hl7.datatypes.CS;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVL_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.PN;
import org.openhealthtools.mdht.uml.hl7.datatypes.PQ;
import org.openhealthtools.mdht.uml.hl7.datatypes.ST;
import org.openhealthtools.mdht.uml.hl7.datatypes.TEL;
import org.openhealthtools.mdht.uml.hl7.vocab.ActClassObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.ActMood;
import org.openhealthtools.mdht.uml.hl7.vocab.NullFlavor;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActClassDocumentEntryOrganizer;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActMoodDocumentObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActRelationshipEntry;
import org.openmrs.Concept;
import org.openmrs.ConceptNumeric;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class CodedVitalSignsSection extends VitalSignsSection 
{
	public CodedVitalSignsSection()
	{
		this.sectionName="Coded Vital Signs";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.5.3.2";
		this.parentTemplateId="1.3.6.1.4.1.19376.1.5.3.1.3.25";
		this.code="8716-3";
		this.sectionDescription="The vital signs section contains coded measurement results of a patient’s vital signs.";
	}

	public static Section buildCodedVitalSignsSection(Patient patient)
	{
		Map<String,String> mappings=new HashMap<String,String>();
		String units="";
		Section section=CDAFactory.eINSTANCE.createSection();
		VitalSignsSection vss=new VitalSignsSection();
		CodedVitalSignsSection ccs=new CodedVitalSignsSection();
		section.getTemplateIds().add(CDAHelper.buildTemplateID(vss.getParentTemplateId(),null ,null ));
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
    	builder.append("<th>Vital Signs Element</th>"+delimeter);
    	builder.append("<th>Description</th>"+delimeter);
    	builder.append("<th>Effective Dates</th>"+delimeter);
    	builder.append("</tr>"+delimeter);
    	builder.append("</thead>"+delimeter);
    	builder.append("<tbody>"+delimeter);
    	
    	mappings.put("9279-1", "LOINC");
    	mappings.put("2710-2", "LOINC");
    	mappings.put("8867-4", "LOINC");
    	mappings.put("8480-6", "LOINC");
    	mappings.put("8462-4", "LOINC");
    	mappings.put("8310-5", "LOINC");
    	mappings.put("8302-2", "LOINC");
    	mappings.put("3141-9", "LOINC");
    	mappings.put("8287-5", "LOINC");
    	
    	ConceptService service = Context.getConceptService();
	    for(Map.Entry<String,String> entry:mappings.entrySet())
		{
	    	List<Concept> ConceptsList=new ArrayList<Concept>();
	    Concept concepts=service.getConceptByMapping(entry.getKey(), entry.getValue(),false);
	    if(concepts==null)
	    {
	    	throw new APIException(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoSuchConcept",new Object[]{entry.getKey(),entry.getValue()},null));
	    }
	    else
	    {
	    	ConceptsList.add(concepts);
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
			if(obs.getConcept().isNumeric())
			{
			ConceptNumeric conceptNumeric =  Context.getConceptService().getConceptNumeric(obs.getConcept().getId());
			   units=conceptNumeric.getUnits();
			   units=units.replaceAll("\\s+","");
			   System.out.println(units);
			   
			}
			    builder.append("<tr>"+delimeter);
				builder.append("<td ID = \"_"+obs.getId()+"\" >"+obs.getConcept().getName()+"</td>"+delimeter);	
				builder.append("<td>");
				int type = obs.getConcept().getDatatype().getId();
				String value=CDAHelper.getDatatypesValue(type,obs);
				
				builder.append(value+"</td>"+delimeter);
				builder.append("<td>"+CDAHelper.getDateFormat().format(obs.getObsDatetime())+"</td>"+delimeter);
				builder.append("</tr>"+delimeter);
        
               
        Entry e=CDAFactory.eINSTANCE.createEntry();
        e.setTypeCode(x_ActRelationshipEntry.DRIV);
        Organizer organizer=CDAFactory.eINSTANCE.createOrganizer();
        organizer.setClassCode(x_ActClassDocumentEntryOrganizer.CLUSTER);
        organizer.setMoodCode(ActMood.EVN);
       
        
        organizer.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.32",null,null));
        organizer.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.35",null,null));
        organizer.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13.1",null,null));
        organizer.getIds().add(CDAHelper.buildTemplateID(obs.getUuid(),null,null));
        organizer.setCode(CDAHelper.buildCodeCD("46680005","2.16.840.1.113883.6.96","Vital signs","SNOMED CT"));
      
    	organizer.setStatusCode(CDAHelper.getStatusCode("completed"));
    	organizer.setEffectiveTime(CDAHelper.buildDateTime(new Date()));
    	
    	Component4 component=CDAFactory.eINSTANCE.createComponent4();
    	
        Observation observation=CDAFactory.eINSTANCE.createObservation();
     	observation.setClassCode(ActClassObservation.OBS);
     	observation.setMoodCode(x_ActMoodDocumentObservation.EVN);
     	observation.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13.2",null,null));
     	observation.getIds().add(CDAHelper.buildTemplateID(obs.getUuid(),null,null));
     	observation.setCode(CDAHelper.buildCodeCE(entry.getKey(),CDAHelper.getCodeSystemByName(entry.getValue()),obs.getConcept().getName().toString(),entry.getValue()));
     	
     	observation.setText(CDAHelper.buildEDText("#_"+obs.getId()));
     	
     	observation.setStatusCode(CDAHelper.getStatusCode("completed"));
     	observation.setEffectiveTime(CDAHelper.buildDateTime(new Date()));
		
		PQ value1=DatatypesFactory.eINSTANCE.createPQ();
		BigDecimal number=new BigDecimal(value);
		value1.setValue(number);
		value1.setUnit(units);
		observation.getValues().add(value1);
		CE interpretationcode=CDAHelper.buildCodeCE("N", "2.16.840.1.113883.5.83", null, null);
		observation.getInterpretationCodes().add(interpretationcode);
		
		CE methodcode=CDAHelper.buildCodeCE(null,CDAHelper.getCodeSystemByName(entry.getValue()),null,entry.getValue());
		observation.getMethodCodes().add(methodcode);
		
		CE targetsite=CDAHelper.buildCodeCE(null,CDAHelper.getCodeSystemByName(entry.getValue()),null,entry.getValue());
		observation.getTargetSiteCodes().add(targetsite);
		
		component.setObservation(observation); 
        organizer.getComponents().add(component);
    	
        organizer.setStatusCode(CDAHelper.getStatusCode("completed"));
        
        e.setOrganizer(organizer);
		section.getEntries().add(e);

     	component.setObservation(observation);    	
        organizer.getComponents().add(component);
        e.setOrganizer(organizer);
		section.getEntries().add(e); 	   
		 }
		}
		builder.append("</tbody>"+delimeter);
		 builder.append("</table>"+delimeter);
			text.addText(builder.toString());
	        section.setText(text); 
		return section;
	}
		 
}