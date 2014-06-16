package org.openmrs.module.CDAGenerator.SectionHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class VisibleImplantedMedicalDevicesSection extends PhysicalExamSection 
{
	public VisibleImplantedMedicalDevicesSection()
	{
		this.sectionName="Visible Implanted Medical Devices Section";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.48";
	    this.code="TBD";
		this.sectionDescription="The visible implanted medical devices section shall contain a description of the medical devices apparent on physical exam that have been inserted into the patient, whether internal or partially external.";
	}
	public  static Section buildVisibleImplantedMedicalDevicesSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		VisibleImplantedMedicalDevicesSection ccs=new  VisibleImplantedMedicalDevicesSection();
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
}
