package org.openmrs.module.CDAGenerator.SectionHandlers;

public class RespiratorySystemSection extends PhysicalExamSection 
{
	public RespiratorySystemSection()
	{
		this.sectionName="RESPIRATORY SYSTEM";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.30";
		this.code="11412-4";
		this.sectionDescription="The respiratory system section shall contain a description of any type of respiratory exam";
	}
}
