package org.openmrs.module.CDAGenerator.SectionHandlers;

public class VisibleImplantedMedicalDevicesSection extends PhysicalExamSection 
{
	public VisibleImplantedMedicalDevicesSection()
	{
		this.sectionName="Visible Implanted Medical Devices Section";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.48";
	    this.code="TBD";
		this.sectionDescription="The visible implanted medical devices section shall contain a description of the medical devices apparent on physical exam that have been inserted into the patient, whether internal or partially external.";
	}
}
