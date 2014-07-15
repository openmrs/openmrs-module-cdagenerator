package org.openmrs.module.CDAGenerator.SectionHandlers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
	public CodedFamilyMedicalHistorySection()
	{
		this.sectionName="HISTORY OF FAMILY MEMBER DISEASES ";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.3.15";
		this.parentTemplateId="1.3.6.1.4.1.19376.1.5.3.1.3.14";
		this.code="10157-6";
		this.sectionDescription="The family history section shall include entries for family history as described in the Entry Content Modules";
	}
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
        
        ConceptService service = Context.getConceptService();
		
        
        List<Obs>ConceptsetRelation=new ArrayList<Obs>();
        List<Obs>ConceptsetAge=new ArrayList<Obs>();
        List<Obs>ConceptsetDiagnosis=new ArrayList<Obs>();
        
        List<List<Obs>> observationList = new ArrayList<List<Obs>>();
        Concept concept = service.getConceptByMapping("10157-6", "LOINC");
        if(concept==null)
       	{
       		throw new APIException(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoSuchConcept",new Object[]{"10154-3","LOINC"},null));
       	}
        Collection<ConceptSet> conset=concept.getConceptSets();
        
        for(ConceptSet cset:conset)
        {
        Concept csetConcept=cset.getConcept();
        
        if(csetConcept.getId().equals(1560))
        {
        ConceptsetRelation.addAll(Context.getObsService().getObservationsByPersonAndConcept(patient, csetConcept));

        if(ConceptsetRelation.isEmpty())
        {
        	throw new APIException(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoObservationsFound",new Object[]{csetConcept.getConceptId(),csetConcept.getName()},null));
        }
        }
        
        else if(csetConcept.getId().equals(160617))
        {
        	ConceptsetAge.addAll(Context.getObsService().getObservationsByPersonAndConcept(patient, csetConcept));

            if(ConceptsetAge.isEmpty())
            {
            	throw new APIException(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoObservationsFound",new Object[]{csetConcept.getConceptId(),csetConcept.getName()},null));
            }
        }
        
        else if(csetConcept.getId().equals(160592))
        {
        	ConceptsetDiagnosis.addAll(Context.getObsService().getObservationsByPersonAndConcept(patient, csetConcept));

            if(ConceptsetDiagnosis.isEmpty())
            {
            	throw new APIException(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoObservationsFound",new Object[]{csetConcept.getConceptId(),csetConcept.getName()},null));
            }
        	
        }
        else;
        }
        
        observationList=PopulateObservationSet(ConceptsetRelation,ConceptsetAge,ConceptsetDiagnosis);
       System.out.println(observationList);
       
       
        StringBuilder builder = new StringBuilder();
        String delimeter="\n";
        builder.append(delimeter);


        builder.append("<table>"+delimeter);
        builder.append("<thead>"+delimeter);
        builder.append("<tr>"+delimeter);
        builder.append("<th>Relation</th>"+delimeter);
        builder.append("<th>Age</th>"+delimeter);
        builder.append("<th>Diagnosis</th>"+delimeter);

        builder.append("</tr>"+delimeter);
        builder.append("</thead>"+delimeter);
        builder.append("<tbody>"+delimeter);
        String relation = "";
		String diagnosis = "";
		String age = "";
		String id="";
		
 for(List<Obs> l:observationList)    
 {	
for (Obs obs2 : l)
{		
  			switch(obs2.getConcept().getId())
				{
				case 1560:
					
					relation = obs2.getValueCoded().getDisplayString();
					
					break;
					
				case 160617:
					age = obs2.getValueNumeric().toString();
					
					break ;
					
			
				case 160592:
					diagnosis = obs2.getValueCoded().getDisplayString();
                      id=obs2.getId().toString();
					break;
					
				}
				
}
/*System.out.println("Relation"+relation);
System.out.println("age"+age);	
System.out.println("diagnosis"+diagnosis);*/
			
			builder.append("<tr>"+delimeter);
			
			builder.append("<td>"+relation+"</td>"+delimeter);
			
			builder.append("<td>"+age+"</td>"+delimeter);
  
			builder.append("<td ID = \"_"+id+"\" >"+diagnosis+"</td>"+delimeter);		
			builder.append("</tr>"+delimeter);
			
        	 
	        Entry e=CDAFactory.eINSTANCE.createEntry();
	         e.setTypeCode(x_ActRelationshipEntry.DRIV);
	        Organizer organizer=CDAFactory.eINSTANCE.createOrganizer();
	        organizer.setClassCode(x_ActClassDocumentEntryOrganizer.CLUSTER);
	        organizer.setMoodCode(ActMood.EVN);
	        organizer.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.23",null,null));
	        organizer.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.15",null,null));
	    	
	    	CS cs= DatatypesFactory.eINSTANCE.createCS();
	    	cs.setCode("completed");
	    	organizer.setStatusCode(cs);
	    	
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
	         organizer.setSubject(subject);
	         
	         Component4 component=CDAFactory.eINSTANCE.createComponent4();
	         
	        Observation observation=CDAFactory.eINSTANCE.createObservation();
	     	observation.setClassCode(ActClassObservation.OBS);
	     	observation.setMoodCode(x_ActMoodDocumentObservation.EVN);
	     	observation.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13.3",null,null));
	     	observation.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13",null,null));
	     	observation.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.22",null,null));
	     	observation.getIds().add(CDAHelper.buildTemplateID("id",null,null));
	     	observation.setCode(CDAHelper.buildCodeCE("10157-6",null,null,null));
	     	observation.setText(CDAHelper.buildEDText("#_"+id));
	     	
	     	CS statusCode1 = DatatypesFactory.eINSTANCE.createCS();
			statusCode1.setCode("completed");
			
	     	observation.setStatusCode(statusCode1);
	     	
	     	
	     	IVL_TS effectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
	     	Date d=new Date();
	     	SimpleDateFormat s = new SimpleDateFormat("yyyyMMddhhmmss");
	     	String creationDate = s.format(d);
	     	effectiveTime.setValue(creationDate);
	     	effectiveTime.setNullFlavor(NullFlavor.UNK);
	     	observation.setEffectiveTime(effectiveTime);
	     	
	     	CE ce= DatatypesFactory.eINSTANCE.createCE();
	     	ce.setCodeSystem("2.16.840.1.113883.6.26");
	     	observation.getValues().add(ce);
	         
	     	component.setObservation(observation); 
	        organizer.getComponents().add(component);
	    	
	        e.setOrganizer(organizer);
			section.getEntries().add(e);

        
        
}
    builder.append("</tbody>"+delimeter);
	builder.append("</table>"+delimeter);
        text.addText(builder.toString());
        section.setText(text);  
       	    return section;
	}

	public static List<List<Obs>> PopulateObservationSet(List<Obs> relation,List<Obs> age,List<Obs> diag)
	{
		
		int size=relation.size();
		List<List<Obs>> observations=new ArrayList<List<Obs>>();
		
		
		for(int i=0;i<size;i++)
		{
			List<Obs> observation=new ArrayList<Obs>();
			observation.add(relation.get(i));
			observation.add(age.get(i));
			observation.add(diag.get(i));
			observations.add(observation);
			
     	}
		
		return observations;
	}
	
}
