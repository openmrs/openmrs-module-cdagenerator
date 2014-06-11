package org.openmrs.module.CDAGenerator.SectionHandlers;

public class PregnancyHistorySection extends BaseCdaSectionHandler 
{
	public PregnancyHistorySection()
	{
		this.sectionName="HISTORY OF PREGNANCIES";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.5.3.4";
		this.code="10162-6";
		this.sectionDescription="The pregnancy history section contains coded entries describing the patient history of pregnancies";
	}
}
