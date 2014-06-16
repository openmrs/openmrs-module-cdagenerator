package org.openmrs.module.CDAGenerator.SectionHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class ChestWallSection extends PhysicalExamSection 
{
	public ChestWallSection()
	{
		this.sectionName="CHEST WALL";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.27";
		this.code="11392-8";
		this.sectionDescription="The chest wall section shall contain a description of any type of chest wall exam.";
	}
	public static Section buildChestWallSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		ChestWallSection ccs=new ChestWallSection();
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}	
}
