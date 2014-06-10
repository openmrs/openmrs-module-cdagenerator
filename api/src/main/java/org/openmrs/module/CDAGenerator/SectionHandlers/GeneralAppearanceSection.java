package org.openmrs.module.CDAGenerator.SectionHandlers;

public class GeneralAppearanceSection extends PhysicalExamSection 
{
	public GeneralAppearanceSection()
	{
		this.sectionName="General Appearance Section";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.16";
	    this.code="10210-3";
		this.sectionDescription="The general appearance section shall contain a description of the overall, visibly-apparent condition of the patient";
	}
}
