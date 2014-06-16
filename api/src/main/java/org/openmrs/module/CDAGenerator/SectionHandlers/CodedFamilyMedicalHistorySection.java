package org.openmrs.module.CDAGenerator.SectionHandlers;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.openhealthtools.mdht.uml.hl7.vocab.ActClassObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.ActMood;
import org.openhealthtools.mdht.uml.hl7.vocab.NullFlavor;
import org.openhealthtools.mdht.uml.hl7.vocab.ParticipationTargetSubject;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActClassDocumentEntryOrganizer;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActMoodDocumentObservation;
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
	public static Section buildCodedFamilyMedicalHistorySection()
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
        text.addText("Text as described above");
        section.setText(text);  

        Entry e=CDAFactory.eINSTANCE.createEntry();
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
         relatedSubject.setCode(CDAHelper.buildCodeCE("ignore","2.16.840.1.113883.5.111",null,"RoleCode"));
         
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
     	observation.setCode(CDAHelper.buildCodeCE("64572001",null,null,null));
     	observation.setText(CDAHelper.buildEDText("#ignore"));
     	observation.setStatusCode(cs);
     	
     	
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
	    return section;
	}

}
