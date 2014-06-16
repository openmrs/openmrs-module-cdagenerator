package org.openmrs.module.CDAGenerator.SectionHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class GeneralAppearanceSection extends PhysicalExamSection 
{
	public GeneralAppearanceSection()
	{
		this.sectionName="General Appearance Section";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.16";
	    this.code="10210-3";
		this.sectionDescription="The general appearance section shall contain a description of the overall, visibly-apparent condition of the patient";
	}
	public static Section buildGeneralAppearanceSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		GeneralAppearanceSection ccs=new  GeneralAppearanceSection();
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
}
