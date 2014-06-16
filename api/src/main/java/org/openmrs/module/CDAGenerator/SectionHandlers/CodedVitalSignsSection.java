package org.openmrs.module.CDAGenerator.SectionHandlers;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.openhealthtools.mdht.uml.hl7.datatypes.CS;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVL_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.PN;
import org.openhealthtools.mdht.uml.hl7.datatypes.TEL;
import org.openhealthtools.mdht.uml.hl7.vocab.ActClassObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.ActMood;
import org.openhealthtools.mdht.uml.hl7.vocab.NullFlavor;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActClassDocumentEntryOrganizer;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActMoodDocumentObservation;
import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class CodedVitalSignsSection extends VitalSignsSection 
{
	public CodedVitalSignsSection()
	{
		this.sectionName="Vital Signs";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.5.3.2";
		this.parentTemplateId="1.3.6.1.4.1.19376.1.5.3.1.3.25";
		this.code="8716-3";
		this.sectionDescription="The vital signs section contains coded measurement results of a patient’s vital signs.";
	}

	public static Section buildCodedVitalSignsSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		VitalSignsSection vss=new VitalSignsSection();
		CodedVitalSignsSection ccs=new CodedVitalSignsSection();
		section.getTemplateIds().add(CDAHelper.buildTemplateID(vss.getParentTemplateId(),null ,null ));
		section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getParentTemplateId(),null ,null ));
		section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
		section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);
        
        Entry e=CDAFactory.eINSTANCE.createEntry();
        Organizer organizer=CDAFactory.eINSTANCE.createOrganizer();
        organizer.setClassCode(x_ActClassDocumentEntryOrganizer.CLUSTER);
        organizer.setMoodCode(ActMood.EVN);
        
        organizer.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.32",null,null));
        organizer.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.35",null,null));
        organizer.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13.1",null,null));
        organizer.getIds().add(CDAHelper.buildID("id",null));
        organizer.setCode(CDAHelper.buildCodeCD("46680005","2.16.840.1.113883.6.96","Vital signs","SNOMED CT"));
        CS cs= DatatypesFactory.eINSTANCE.createCS();
    	cs.setCode("completed");
    	organizer.setStatusCode(cs);
    	
    	IVL_TS effectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
     	Date d=new Date();
     	SimpleDateFormat s = new SimpleDateFormat("yyyyMMddhhmmss");
     	String creationDate = s.format(d);
     	effectiveTime.setValue(creationDate);
     	effectiveTime.setNullFlavor(NullFlavor.UNK);
     	organizer.setEffectiveTime(effectiveTime);
    	
     	Author author = CDAFactory.eINSTANCE.createAuthor();
		author.setTime(CDAHelper.buildEffectiveTime(new Date()));
		AssignedAuthor assignedAuthor = CDAFactory.eINSTANCE.createAssignedAuthor();
		II authorId = DatatypesFactory.eINSTANCE.createII();
		authorId.setRoot(Context.getAdministrationService().getImplementationId().getImplementationId());
		assignedAuthor.getIds().add(authorId);

		AD assignedAuthorAddress=DatatypesFactory.eINSTANCE.createAD();
		assignedAuthorAddress.addCountry(" ");
		TEL assignedAuthorTelecon = DatatypesFactory.eINSTANCE.createTEL();
		assignedAuthorTelecon.setNullFlavor(NullFlavor.UNK);
		
		assignedAuthor.getAddrs().add(assignedAuthorAddress);
		assignedAuthor.getTelecoms().add(assignedAuthorTelecon);
		
		Person assignedPerson = CDAFactory.eINSTANCE.createPerson(); 
		PN assignedPersonName = DatatypesFactory.eINSTANCE.createPN();
		assignedPerson.getNames().add(assignedPersonName);
		assignedAuthor.setAssignedPerson(assignedPerson);
  			
		author.setAssignedAuthor(assignedAuthor);
		organizer.getAuthors().add(author);
		
		Component4 component=CDAFactory.eINSTANCE.createComponent4();
        
        Observation observation=CDAFactory.eINSTANCE.createObservation();
     	observation.setClassCode(ActClassObservation.OBS);
     	observation.setMoodCode(x_ActMoodDocumentObservation.EVN);
     	observation.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13",null,null));
     	observation.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.31",null,null));
     	observation.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13.2",null,null));
     	observation.getIds().add(CDAHelper.buildTemplateID("id",null,null));
     	observation.setCode(CDAHelper.buildCodeCE("9279-1","2.16.840.1.113883.6.1",null,"LOINC"));
     	observation.setText(CDAHelper.buildEDText("#ignore"));
     	observation.setStatusCode(cs);
     	IVL_TS effectiveTime1 = DatatypesFactory.eINSTANCE.createIVL_TS();
     	effectiveTime1.setNullFlavor(NullFlavor.UNK);
     	observation.setEffectiveTime(effectiveTime1);
     	
     	ReferenceRange referenceRange=CDAFactory.eINSTANCE.createReferenceRange();
     	ObservationRange observationRange=CDAFactory.eINSTANCE.createObservationRange();
     	observationRange.setText(CDAHelper.buildEDText("Reference Range Text Here"));
     	referenceRange.setObservationRange(observationRange);
     	observation.getReferenceRanges().add(referenceRange);
     	component.setObservation(observation);    	
        organizer.getComponents().add(component);
        e.setOrganizer(organizer);
		section.getEntries().add(e); 	   
		
		return section;
	}

}

