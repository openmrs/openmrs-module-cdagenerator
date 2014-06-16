package org.openmrs.module.CDAGenerator.SectionHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class OptionalEyesSection extends PhysicalExamSection 
{
	public OptionalEyesSection()
	{
		this.sectionName="EYE";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.19";
		this.code="10197-2";
		this.sectionDescription="The eyes section shall contain a description of any type of eye exam";
	}
	public static Section buildOptionalEyesSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		OptionalEyesSection ccs=new  OptionalEyesSection();
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
}
