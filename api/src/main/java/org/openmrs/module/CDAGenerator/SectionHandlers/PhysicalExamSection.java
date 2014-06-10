package org.openmrs.module.CDAGenerator.SectionHandlers;

public class PhysicalExamSection extends BaseCdaSectionHandler 
{
	public PhysicalExamSection()
	{
		this.sectionName="PHYSICAL EXAMINATION";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.15";
		this.code="29545-1";
		this.parentTemplateId="1.3.6.1.4.1.19376.1.5.3.1.3.24";
		this.sectionDescription="The physical exam section shall contain only the required and optional subsections performed.";
	}
}
