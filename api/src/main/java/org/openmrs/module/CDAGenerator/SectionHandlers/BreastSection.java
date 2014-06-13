package org.openmrs.module.CDAGenerator.SectionHandlers;

public class BreastSection extends PhysicalExamSection 
{
	public BreastSection()
	{
		this.sectionName="BREASTS";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.28";
		this.code="10193-1";
		this.sectionDescription="The breast section shall contain a description of any type of breast exam.";
	}
}
