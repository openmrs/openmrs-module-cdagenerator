package org.openmrs.module.CDAGenerator.SectionHandlers;

public class ChestWallSection extends PhysicalExamSection 
{
	public ChestWallSection()
	{
		this.sectionName="CHEST WALL";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.27 ";
		this.code="11392-8";
		this.sectionDescription="The chest wall section shall contain a description of any type of chest wall exam.";
	}
}
