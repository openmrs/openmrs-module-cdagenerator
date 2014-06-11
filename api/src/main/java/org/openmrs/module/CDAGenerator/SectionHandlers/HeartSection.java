package org.openmrs.module.CDAGenerator.SectionHandlers;

public class HeartSection extends PhysicalExamSection 
{
	public HeartSection()
	{
		this.sectionName="HEART";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.29";
		this.code="10200-4";
		this.sectionDescription="The heart section shall contain a description of any type of heart exam";
	}
}
