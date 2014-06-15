package org.openmrs.module.CDAGenerator.SectionHandlers;

public class FamilyMedicalHistorySection extends BaseCdaSectionHandler 
{
	public FamilyMedicalHistorySection()
	{
		this.sectionName="HISTORY OF FAMILY MEMBER DISEASES";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.3.14";
		this.parentTemplateId="2.16.840.1.113883.10.20.1.4";
		this.code="10157-6";
		this.sectionDescription="The family history section shall contain a narrative description of the genetic family members, to the extent that they are known, the diseases they suffered from, their ages at death, and other relevant genetic information";
	}
}
