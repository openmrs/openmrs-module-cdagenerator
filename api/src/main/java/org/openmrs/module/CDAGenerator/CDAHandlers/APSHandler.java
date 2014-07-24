package org.openmrs.module.CDAGenerator.CDAHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.Patient;
import org.openmrs.module.CDAGenerator.SectionHandlers.ChiefComplaintSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.CodedFamilyMedicalHistorySection;
import org.openmrs.module.CDAGenerator.SectionHandlers.HistoryOfInfectionSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.HistoryOfPastIllnessSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.HistoryOfPresentIllnessSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.PhysicalExamSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.PregnancyHistorySection;
import org.openmrs.module.CDAGenerator.SectionHandlers.ReviewOfSystemsSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.SocialHistorySection;
import org.openmrs.module.CDAGenerator.api.CDAHelper;
import org.openmrs.module.CDAGenerator.api.CdaHeaderBuilder;

public class APSHandler extends BaseCdaTypeHandler
{
	
	public APSHandler()
	{
	this.documentFullName="Antepartum Summary";	
	this.documentShortName="APS";
	this.documentDescription="information regarding the status of a patients pregnancy";
	this.templateid="1.3.6.1.4.19376.1.5.3.1.1.11.2";
	this.formatCode="urn:ihe:pcc:aps:2007";
	}
	public ClinicalDocument buildAPSMessage(Patient patient,BaseCdaTypeHandler handler)
	{
		ClinicalDocument doc = CDAFactory.eINSTANCE.createClinicalDocument();
		CdaHeaderBuilder header=new CdaHeaderBuilder();
		
		doc=header.buildHeader(doc, handler, patient);
		Section section=CDAFactory.eINSTANCE.createSection();
	    section.getTemplateIds().add(CDAHelper.buildTemplateID(templateid,null ,null ));
	    section.setCode(CDAHelper.buildCodeCE(null,null,null,null));
	    section.setTitle(CDAHelper.buildTitle(documentDescription));
	    StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
	    text.addText("Demo Section for Aps profile");
	    section.setText(text); 
	    doc.addSection(section);
		return doc;
	}
}