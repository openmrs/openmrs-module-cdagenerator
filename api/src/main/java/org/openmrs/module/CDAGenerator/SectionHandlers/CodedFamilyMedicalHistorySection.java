package org.openmrs.module.CDAGenerator.SectionHandlers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Component4;
import org.openhealthtools.mdht.uml.cda.Entry;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.Organizer;
import org.openhealthtools.mdht.uml.cda.RelatedSubject;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openhealthtools.mdht.uml.cda.Subject;
import org.openhealthtools.mdht.uml.cda.SubjectPerson;
import org.openhealthtools.mdht.uml.hl7.datatypes.AD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CE;
import org.openhealthtools.mdht.uml.hl7.datatypes.CS;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.ED;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVL_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.PN;
import org.openhealthtools.mdht.uml.hl7.datatypes.TEL;
import org.openhealthtools.mdht.uml.hl7.datatypes.TS;
import org.openhealthtools.mdht.uml.hl7.vocab.ActClassObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.ActMood;
import org.openhealthtools.mdht.uml.hl7.vocab.NullFlavor;
import org.openhealthtools.mdht.uml.hl7.vocab.ParticipationTargetSubject;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActClassDocumentEntryOrganizer;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActMoodDocumentObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActRelationshipEntry;
import org.openhealthtools.mdht.uml.hl7.vocab.x_DocumentSubject;
import org.openmrs.Concept;
import org.openmrs.ConceptSet;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class CodedFamilyMedicalHistorySection extends FamilyMedicalHistorySection 
{
	private static Log log = LogFactory.getLog(CodedFamilyMedicalHistorySection.class);
	public CodedFamilyMedicalHistorySection()
	{
		this.sectionName="HISTORY OF FAMILY MEMBER DISEASES ";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.3.15";
		this.parentTemplateId="1.3.6.1.4.1.19376.1.5.3.1.3.14";
		this.code="10157-6";
		this.sectionDescription="The family history section shall include entries for family history as described in the Entry Content Modules";
	}
	/**
	 * Create CDA section(Family History section)
	 * @param patient
	 * @return CDA section or Report errors
	 * @should return CDA section
	 * @should return errors in Section
	 */
	public static Section buildCodedFamilyMedicalHistorySection(Patient patient)
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		CodedFamilyMedicalHistorySection ccs=new CodedFamilyMedicalHistorySection();
		FamilyMedicalHistorySection fmhs=new FamilyMedicalHistorySection();
		section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getParentTemplateId(),null ,null ));
		section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
		section.getTemplateIds().add(CDAHelper.buildTemplateID(fmhs.getParentTemplateId(),null ,null ));
		section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();    

        Map<String,String> mappings=new HashMap<String,String>();
        
        StringBuilder builder = new StringBuilder();
        String delimeter="\n";
        builder.append(delimeter);
        builder.append("<table>"+delimeter);
    	builder.append("<thead>"+delimeter);
    	builder.append("<tr>"+delimeter);
    	builder.append("<th>Family History Element</th>"+delimeter);
    	builder.append("<th>Description</th>"+delimeter);
    	builder.append("<th>Effective Dates</th>"+delimeter);
    	builder.append("</tr>"+delimeter);
    	builder.append("</thead>"+delimeter);
    	builder.append("<tbody>"+delimeter);
    	
    	 
    	 mappings.put("40108008", "SNOMED CT");
    	 mappings.put("253098009", "SNOMED CT");
    	 mappings.put("13213009", "SNOMED CT");
    	 mappings.put("41040004", "SNOMED CT");
    	 mappings.put("111385000", "SNOMED CT");
    	 mappings.put("80544005", "SNOMED CT");
    	 mappings.put("29159009", "SNOMED CT");
    	 mappings.put("417357006", "SNOMED CT");
    	 mappings.put("16402000", "SNOMED CT");
    	 mappings.put("90935002", "SNOMED CT");
    	 mappings.put("414022008", "SNOMED CT");
    	 mappings.put("73297009", "SNOMED CT");
    	 mappings.put("190905008", "SNOMED CT");
    	 mappings.put("58756001", "SNOMED CT");
    	 mappings.put("91138005", "SNOMED CT");
    	 mappings.put("408856003", "SNOMED CT");
    	 mappings.put("409709004", "SNOMED CT");
    	 mappings.put("75934005", "SNOMED CT");
    	 mappings.put("276720006", "SNOMED CT");
    	 mappings.put("102878001", "SNOMED CT");
    	 
    	 ConceptService service = Context.getConceptService();
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
    			boolean isDatatypeNA=concept.getDatatype().getName().equals("N/A");
    			if(!isDatatypeNA)
    			{
    			obsList.addAll(Context.getObsService().getObservationsByPersonAndConcept(patient, concept));	
    			}
    		
    		if(!isDatatypeNA && obsList.isEmpty())
    		{
    			 log.error(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoObservationsFound",new Object[]{CDAHelper.getConceptIdasString(concept.getConceptId()),concept.getName()},null));
    			 
    			    builder.append("<tr>"+delimeter);
    				builder.append("<td> No Observation Element with concept id: "+CDAHelper.getConceptIdasString(concept.getId())+" was found</td>"+delimeter);	
    				builder.append("<td>");
    				
    				builder.append("NULL"+"</td>"+delimeter);
    				builder.append("<td>"+"NULL"+"</td>"+delimeter);
    				builder.append("</tr>"+delimeter);
    				
    				
    				Entry e=CDAFactory.eINSTANCE.createEntry();
    		        e.setTypeCode(x_ActRelationshipEntry.DRIV);
    		        Organizer organizer=CDAFactory.eINSTANCE.createOrganizer();
    		        organizer.setClassCode(x_ActClassDocumentEntryOrganizer.CLUSTER);
    		        organizer.setMoodCode(ActMood.EVN);
    		        organizer.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.23",null,null));
    		        organizer.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.15",null,null));
    		        organizer.getIds().add(CDAHelper.buildID("4deef9ca-ada9-4008-b8f9-8a5aea4fa6ee",null));
    		        
    		        organizer.setStatusCode(CDAHelper.getStatusCode("completed"));
    		        organizer.setSubject(getSubject());
    		        
    		        Component4 component=CDAFactory.eINSTANCE.createComponent4();
    		        
    		        Observation observation=CDAFactory.eINSTANCE.createObservation();
    		     	observation.setClassCode(ActClassObservation.OBS);
    		     	observation.setMoodCode(x_ActMoodDocumentObservation.EVN);
    		     	observation.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13.3",null,null));
    		     	observation.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13",null,null));
    		     	observation.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.22",null,null));
    		     	observation.getIds().add(CDAHelper.buildTemplateID("a2323d84-1a83-48f2-9a63-44c8b01dd7da",null,null));
    		     	
    		     	observation.setCode(CDAHelper.buildCodeCD(mapentry.getKey(),CDAHelper.getCodeSystemByName(mapentry.getValue()),"Null", mapentry.getValue()));
    				observation.setText(CDAHelper.buildEDText("#_No Observation"));
    				
    				observation.setStatusCode(CDAHelper.getStatusCode("completed"));
    				
    				observation.setEffectiveTime(CDAHelper.buildDateTime(new Date()));
    				
    				ED value1=DatatypesFactory.eINSTANCE.createED(" No Observation");
    				observation.getValues().add(value1);
    				
    				CE interpretationcode=CDAHelper.buildCodeCE("N", "2.16.840.1.113883.5.83", null, null);
    				observation.getInterpretationCodes().add(interpretationcode);
    				
    				CE methodcode=CDAHelper.buildCodeCE(null,CDAHelper.getCodeSystemByName(mapentry.getValue()),null,mapentry.getValue());
    				observation.getMethodCodes().add(methodcode);
    				
    				CE targetsite=CDAHelper.buildCodeCE(null,CDAHelper.getCodeSystemByName(mapentry.getValue()),null,mapentry.getValue());
    				observation.getTargetSiteCodes().add(targetsite);
    				
    				component.setObservation(observation); 
    		        organizer.getComponents().add(component);
    		    	
    		        e.setOrganizer(organizer);
    				section.getEntries().add(e);

    		        
    		   }
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
   				
   				Entry e=CDAFactory.eINSTANCE.createEntry();
		        e.setTypeCode(x_ActRelationshipEntry.DRIV);
		        Organizer organizer=CDAFactory.eINSTANCE.createOrganizer();
		        organizer.setClassCode(x_ActClassDocumentEntryOrganizer.CLUSTER);
		        organizer.setMoodCode(ActMood.EVN);
		        organizer.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.23",null,null));
		        organizer.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.15",null,null));
		        organizer.getIds().add(CDAHelper.buildID(obs.getUuid(),null));
		        
		        organizer.setStatusCode(CDAHelper.getStatusCode("completed"));
		        organizer.setSubject(getSubject());
		        
		        Component4 component=CDAFactory.eINSTANCE.createComponent4();
		        

		        Observation observation=CDAFactory.eINSTANCE.createObservation();
		     	observation.setClassCode(ActClassObservation.OBS);
		     	observation.setMoodCode(x_ActMoodDocumentObservation.EVN);
		     	observation.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13.3",null,null));
		     	observation.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13",null,null));
		     	observation.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.22",null,null));
		     	observation.getIds().add(CDAHelper.buildID(obs.getUuid(),null));
		     	
		     	observation.setCode(CDAHelper.buildCodeCD(mapentry.getKey(),CDAHelper.getCodeSystemByName(mapentry.getValue()),obs.getConcept().getName().toString(), mapentry.getValue()));
				observation.setText(CDAHelper.buildEDText("#_"+obs.getId()));
				
				observation.setStatusCode(CDAHelper.getStatusCode("completed"));
				
				observation.setEffectiveTime(CDAHelper.buildDateTime(new Date()));
				
				ED value1=DatatypesFactory.eINSTANCE.createED(value);
				observation.getValues().add(value1);
				
				CE interpretationcode=CDAHelper.buildCodeCE("N", "2.16.840.1.113883.5.83", null, null);
				observation.getInterpretationCodes().add(interpretationcode);
				
				CE methodcode=CDAHelper.buildCodeCE(null,CDAHelper.getCodeSystemByName(mapentry.getValue()),null,mapentry.getValue());
				observation.getMethodCodes().add(methodcode);
				
				CE targetsite=CDAHelper.buildCodeCE(null,CDAHelper.getCodeSystemByName(mapentry.getValue()),null,mapentry.getValue());
				observation.getTargetSiteCodes().add(targetsite);
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
	public static Subject getSubject()
	{
		Subject subject=CDAFactory.eINSTANCE.createSubject();
        subject.setTypeCode(ParticipationTargetSubject.SBJ);
                 
         RelatedSubject relatedSubject=CDAFactory.eINSTANCE.createRelatedSubject();
         relatedSubject.setClassCode(x_DocumentSubject.PRS);
         relatedSubject.setCode(CDAHelper.buildCodeCE("10157-6","2.16.840.1.113883.5.111",null,"RoleCode"));
         
         AD relatedSubjectAddress=DatatypesFactory.eINSTANCE.createAD();
         relatedSubjectAddress.addCountry(" ");
         TEL relatedSubjectTelecon=DatatypesFactory.eINSTANCE.createTEL();
         relatedSubjectTelecon.setNullFlavor(NullFlavor.UNK);
         
         relatedSubject.getAddrs().add(relatedSubjectAddress);
         relatedSubject.getTelecoms().add(relatedSubjectTelecon);
         
         SubjectPerson subjectperson=CDAFactory.eINSTANCE.createSubjectPerson();
         CE administartivegender=DatatypesFactory.eINSTANCE.createCE();
         PN names=DatatypesFactory.eINSTANCE.createPN();
         administartivegender.setCode("A");
         subjectperson.setAdministrativeGenderCode(administartivegender);
         TS birthTime = DatatypesFactory.eINSTANCE.createTS();
			birthTime.setNullFlavor(NullFlavor.UNK);
			subjectperson.setBirthTime(birthTime );
         subjectperson.getNames().add(names);
         relatedSubject.setSubject(subjectperson);
         
         subject.setRelatedSubject(relatedSubject);
		return subject;
         
	}
}
