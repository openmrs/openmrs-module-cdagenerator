package org.openmrs.module.CDAGenerator.SectionHandlers;

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
}
