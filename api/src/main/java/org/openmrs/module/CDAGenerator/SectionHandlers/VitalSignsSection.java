package org.openmrs.module.CDAGenerator.SectionHandlers;

public class VitalSignsSection extends BaseCdaSectionHandler 
{
public VitalSignsSection()
{
	this.sectionName="Vital Signs";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.5.3.2";
	this.sectionDescription="The vital signs section contains coded measurement results of a patient’s vital signs.";
}
}
