package org.openmrs.module.CDAGenerator.SectionHandlers;

public class VitalSignsSection extends PhysicalExamSection
{
public VitalSignsSection()
{
	this.sectionName="Vital Signs";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.3.25";
	this.parentTemplateId="2.16.840.1.113883.10.20.1.16";
	this.sectionDescription="The vital signs section shall contain a narrative description of the measurement results of a patient’s vital signs";
}
}
