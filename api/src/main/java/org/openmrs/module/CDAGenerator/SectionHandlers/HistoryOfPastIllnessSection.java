package org.openmrs.module.CDAGenerator.SectionHandlers;

import java.util.Date;

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
import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class HistoryOfPastIllnessSection extends BaseCdaSectionHandler 
{
	public HistoryOfPastIllnessSection()
	{
		this.sectionName="HISTORY OF PAST ILLNESS";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.3.8";
		this.code="11348-0";
		this.sectionDescription="The History of Past Illness section shall contain a narrative description of the conditions the patient suffered in the past. It shall include entries for problems as described in the Entry Content Modules";
	}

	public static Section buildHistoryOfPastIllnessSection()
	{
	Section section=CDAFactory.eINSTANCE.createSection();
	HistoryOfPastIllnessSection ccs=new HistoryOfPastIllnessSection();
	section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
	section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
	section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
	StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
    text.addText("Text as described above");
    section.setText(text); 
    
    Entry e=CDAFactory.eINSTANCE.createEntry();
    Act act=CDAFactory.eINSTANCE.createAct();
    act.setClassCode(x_ActClassDocumentEntryAct.ACT);
    act.setMoodCode(x_DocumentActMood.EVN);
    act.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.5.2",null,null));
    act.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.5.1",null,null));
    act.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.27",null,null));
    act.getIds().add(CDAHelper.buildID("id",null));
    CD code=DatatypesFactory.eINSTANCE.createCD();
    CS cs=DatatypesFactory.eINSTANCE.createCS();
    cs.setCode("active");
    code.setNullFlavor(NullFlavor.NA);
    act.setCode(code);
    act.setStatusCode(cs);
    IVL_TS effectiveTime=DatatypesFactory.eINSTANCE.createIVL_TS();
    IVXB_TS low=DatatypesFactory.eINSTANCE.createIVXB_TS();
    low.setValue("20071011");
    IVXB_TS high=DatatypesFactory.eINSTANCE.createIVXB_TS();
    high.setValue("20071012");
    effectiveTime.setLow(low);
    effectiveTime.setLow(high);
    act.setEffectiveTime(effectiveTime);
    
    EntryRelationship entryRelationship1=CDAFactory.eINSTANCE.createEntryRelationship();
    entryRelationship1.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
    entryRelationship1.setInversionInd(false);
    
    Observation observation1=CDAFactory.eINSTANCE.createObservation();
    observation1.setClassCode(ActClassObservation.OBS);
    observation1.setMoodCode(x_ActMoodDocumentObservation.EVN);
    observation1.setNegationInd(false);
    observation1.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.28",null,null));
    observation1.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.5",null,null));
    observation1.getIds().add(CDAHelper.buildID("2.1.160",null));
    observation1.setCode(CDAHelper.buildCodeCD("64572001","2.16.840.1.113883.6.96",null,null));
    cs.setCode("completed");
    observation1.setStatusCode(cs);
    
    IVL_TS effectiveTime1=DatatypesFactory.eINSTANCE.createIVL_TS();
    IVXB_TS low1=DatatypesFactory.eINSTANCE.createIVXB_TS();
    low1.setNullFlavor(NullFlavor.UNK);
    observation1.setEffectiveTime(effectiveTime1);
    
    CD codecd=DatatypesFactory.eINSTANCE.createCD();
    codecd.setCode("thing");
    codecd.setCodeSystem("thing");
    codecd.setDisplayName("myname");
    codecd.setCodeSystemName("myname");
    observation1.getValues().add(codecd);
    entryRelationship1.setObservation(observation1);
    
    EntryRelationship entryRelationship2=CDAFactory.eINSTANCE.createEntryRelationship();
    entryRelationship2.setTypeCode(x_ActRelationshipEntryRelationship.REFR);
    entryRelationship2.setInversionInd(false);
    
    Observation observation2=CDAFactory.eINSTANCE.createObservation();
    observation2.setClassCode(ActClassObservation.OBS);
    observation2.setMoodCode(x_ActMoodDocumentObservation.EVN);
    observation2.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.1.2",null,null));
    observation2.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.51",null,null));
    observation2.setCode(CDAHelper.buildCodeCD("11323-3","2.16.840.1.113883.6.1","Health Status","LOINC"));
    observation2.setText(CDAHelper.buildEDText("#ignore"));
    observation2.setStatusCode(cs);
    observation2.getValues().add(CDAHelper.buildCodeCE("81323004","2.16.840.1.113883.6.96","Alive and well","SNOMED CT"));
    entryRelationship2.setObservation(observation2);
    
    
    EntryRelationship entryRelationship3=CDAFactory.eINSTANCE.createEntryRelationship();
    entryRelationship3.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
    entryRelationship3.setInversionInd(true);
    
    Observation observation3=CDAFactory.eINSTANCE.createObservation();
    observation3.setClassCode(ActClassObservation.OBS);
    observation3.setMoodCode(x_ActMoodDocumentObservation.EVN);
    observation3.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.1",null,null));
    observation3.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.55",null,null));
    observation3.setCode(CDAHelper.buildCodeCD("SEV","2.16.840.1.113883.5.4",null,null));
    observation3.setText(CDAHelper.buildEDText("#ignore"));
    observation3.setStatusCode(cs);
    observation3.getValues().add(CDAHelper.buildCodeCD("H","2.16.840.1.113883.5.1063",null,null));
    entryRelationship3.setObservation(observation3);
    
    
    EntryRelationship entryRelationship4=CDAFactory.eINSTANCE.createEntryRelationship();
    entryRelationship4.setTypeCode(x_ActRelationshipEntryRelationship.REFR);
    
    Observation observation4=CDAFactory.eINSTANCE.createObservation();
    observation4.setClassCode(ActClassObservation.OBS);
    observation4.setMoodCode(x_ActMoodDocumentObservation.EVN);
    observation4.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.57",null,null));
    observation4.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.50",null,null));
    observation4.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.1.1",null,null));
    observation4.setCode(CDAHelper.buildCodeCE("33999-4","2.16.840.1.113883.6.1",null,null));
    observation4.setText(CDAHelper.buildEDText("#ignore"));
    observation4.setStatusCode(cs);
    observation4.getValues().add(CDAHelper.buildCodeCE("55561003","2.16.840.1.113883.6.96"," ","SNOMED CT"));
    entryRelationship4.setObservation(observation4);
    
    EntryRelationship entryRelationship5=CDAFactory.eINSTANCE.createEntryRelationship();
    entryRelationship5.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
    Act act1=CDAFactory.eINSTANCE.createAct();
    act1.setClassCode(x_ActClassDocumentEntryAct.ACT);
    act1.setMoodCode(x_DocumentActMood.EVN);
    act1.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.40",null,null));
    act1.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.2",null,null));
    act1.setCode(CDAHelper.buildCodeCD("48767-8","2.16.840.1.113883.6.1","Annotation Comment","LOINC"));
    act1.setText(CDAHelper.buildEDText("#ignore"));
    act1.setStatusCode(cs);   
    
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
		
		Person assignedPerson = CDAFactory.eINSTANCE.createPerson(); //assigned person must be system
		PN assignedPersonName = DatatypesFactory.eINSTANCE.createPN();
		assignedPerson.getNames().add(assignedPersonName);
		assignedAuthor.setAssignedPerson(assignedPerson);
  		
		Organization representedOrganization = CDAFactory.eINSTANCE.createOrganization();
		ON representedOrganizationName=DatatypesFactory.eINSTANCE.createON();
		representedOrganizationName.addText(Context.getAdministrationService().getImplementationId().getName());
		representedOrganization.getNames().add(representedOrganizationName);
		AD representedOrganizationAddress = DatatypesFactory.eINSTANCE.createAD();
		representedOrganizationAddress.addCounty(" ");
		representedOrganization.getAddrs().add(representedOrganizationAddress);
		TEL representedOrganizationTelecon = DatatypesFactory.eINSTANCE.createTEL();
		representedOrganizationTelecon.setNullFlavor(NullFlavor.UNK);
		representedOrganization.getTelecoms().add(representedOrganizationTelecon);
		assignedAuthor.setRepresentedOrganization(representedOrganization);
		
		author.setAssignedAuthor(assignedAuthor);
		act1.getAuthors().add(author);
		entryRelationship5.setAct(act1);

		EntryRelationship entryRelationship6=CDAFactory.eINSTANCE.createEntryRelationship();
	    entryRelationship6.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
	    
	    Observation observation5=CDAFactory.eINSTANCE.createObservation();
	    observation5.setClassCode(ActClassObservation.OBS);
	    observation5.setMoodCode(x_ActMoodDocumentObservation.EVN);
	    observation5.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.5.3",null,null));
	    observation5.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.27",null,null));
	    entryRelationship6.setObservation(observation5);
	    
		
       act.getEntryRelationships().add(entryRelationship1);
       act.getEntryRelationships().add(entryRelationship2);
       act.getEntryRelationships().add(entryRelationship3);
       act.getEntryRelationships().add(entryRelationship4);
       act.getEntryRelationships().add(entryRelationship5);
       act.getEntryRelationships().add(entryRelationship6);
       
       e.setAct(act);
       section.getEntries().add(e);
             
	return section;
	}
}
