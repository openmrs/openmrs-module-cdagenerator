package org.openmrs.module.CDAGenerator.CDAHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.Patient;
import org.openmrs.module.CDAGenerator.SectionHandlers.ChiefComplaintSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.CodedFamilyMedicalHistorySection;
import org.openmrs.module.CDAGenerator.SectionHandlers.CodedVitalSignsSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.HistoryOfInfectionSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.HistoryOfPastIllnessSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.HistoryOfPresentIllnessSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.PhysicalExamSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.PregnancyHistorySection;
import org.openmrs.module.CDAGenerator.SectionHandlers.ReviewOfSystemsSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.SocialHistorySection;
import org.openmrs.module.CDAGenerator.api.CdaHeaderBuilder;

public class APHPHandler extends BaseCdaTypeHandler
{
	
	public APHPHandler()
	{
	this.documentFullName="Antepartum History and Physical";	
	this.documentShortName="APHP";
	this.documentDescription="Contains a record of initial history and physical";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.16.1.1";
	this.parentTemplateId="1.3.6.1.4.1.19376.1.5.3.1.1.16.1.4";
	this.formatCode="urn:ihe:pcc:aphp:2008";
	}
	
	public ClinicalDocument buildAPHPMessage(Patient patient,BaseCdaTypeHandler handler)
	{
		ClinicalDocument doc = CDAFactory.eINSTANCE.createClinicalDocument();
		Section section=CDAFactory.eINSTANCE.createSection();
		CdaHeaderBuilder header=new CdaHeaderBuilder();
		
		doc=header.buildHeader(doc, handler, patient);
		
		
		section=HistoryOfPresentIllnessSection.buildHistoryOfPresentIllnessSection(patient);
		doc.addSection(section);
		
		section=ChiefComplaintSection.buildChiefComplaintSection(patient);
		doc.addSection(section);
		
		section=SocialHistorySection.buildSocialHistorySection(patient);
		doc.addSection(section);
		
		section=ReviewOfSystemsSection.buildReviewOfSystemsSection(patient);
		doc.addSection(section);
		
		section=CodedFamilyMedicalHistorySection.buildCodedFamilyMedicalHistorySection(patient);
		doc.addSection(section);
		
		section=PregnancyHistorySection.buildPregnancyHistorySection(patient);
		doc.addSection(section);


		section=PhysicalExamSection.buildPhysicalExamSection(patient);
		doc.addSection(section);
		
		
		/*
		 * commented following four calls because they consist of hard coded observation and 
		 * produces few errors in document
		 * we are now using gazelle cda validator 
		*/
		
        /*
		
		
		section=HistoryOfInfectionSection.buildHistoryOfInfectionSection();
		doc.addSection(section);
		
		section=HistoryOfPastIllnessSection.buildHistoryOfPastIllnessSection();
		doc.addSection(section);
	
		
		*/
		return doc;
	}
}