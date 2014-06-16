package org.openmrs.module.CDAGenerator.SectionHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class AbdomenSection extends PhysicalExamSection 
{
	public AbdomenSection()
	{
		this.sectionName="ABDOMEN";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.31";
		this.code="10191-5";
		this.sectionDescription="he abdomen system section shall contain a description of any type of abdominal exam";
	}

	public static Section buildAbdomenSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		AbdomenSection ccs=new AbdomenSection();
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
}
