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

	 /**
     * Create or Generate APHP Message
     *
     * @param patient
     * @param handler
     *                type of cda document(APHP or APS or Some other)
     *                
     * @return a Clinical Document(APHP) or Report errors
     * @should return a Clinical Document(APHP),if document passes validation
     * @should return errors in CDA(APHP) document
     */
	
	public ClinicalDocument buildAPHPMessage(Patient patient,BaseCdaTypeHandler handler)
	{
		ClinicalDocument doc = CDAFactory.eINSTANCE.createClinicalDocument();
		Section section=CDAFactory.eINSTANCE.createSection();
		
		//Builds header part of APHP CDA message
		CdaHeaderBuilder header=new CdaHeaderBuilder();
		doc=header.buildHeader(doc, handler, patient);
		
		//Builds History Of Present Illness Section of APHP CDA message
		section=HistoryOfPresentIllnessSection.buildHistoryOfPresentIllnessSection(patient);
		doc.addSection(section);
		
		//Builds Chief Complaint Section of APHP CDA message
		section=ChiefComplaintSection.buildChiefComplaintSection(patient);
		doc.addSection(section);
		
		//Builds Social History Section of APHP CDA message
		section=SocialHistorySection.buildSocialHistorySection(patient);
		doc.addSection(section);
		
		//Builds Review Of Systems Section of APHP CDA message
		section=ReviewOfSystemsSection.buildReviewOfSystemsSection(patient);
		doc.addSection(section);
		
		//Builds Family Medical History Section of APHP CDA message
		section=CodedFamilyMedicalHistorySection.buildCodedFamilyMedicalHistorySection(patient);
		doc.addSection(section);
		
		//Builds Pregnancy History Section of APHP CDA message
		section=PregnancyHistorySection.buildPregnancyHistorySection(patient);
		doc.addSection(section);
		
		//Builds  History Of Infection Section of APHP CDA message 
		section=HistoryOfInfectionSection.buildHistoryOfInfectionSection(patient);
		doc.addSection(section);

		//Builds Past Illness Section of APHP CDA message
		section=HistoryOfPastIllnessSection.buildHistoryOfPastIllnessSection(patient);
		doc.addSection(section);
		
		//Builds Physical Exam Section of APHP CDA message
		section=PhysicalExamSection.buildPhysicalExamSection(patient);
		doc.addSection(section);
			
		return doc;
	}
}