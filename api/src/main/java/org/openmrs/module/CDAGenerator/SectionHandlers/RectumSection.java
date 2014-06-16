package org.openmrs.module.CDAGenerator.SectionHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class RectumSection extends PhysicalExamSection 
{
	public RectumSection()
	{
		this.sectionName="RECTUM";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.37";
		this.code="10205-3";
		this.sectionDescription="The rectum section shall contain a description of any type of rectal exam";
	}
	public static Section buildRectumSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		RectumSection ccs=new RectumSection();
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
}
