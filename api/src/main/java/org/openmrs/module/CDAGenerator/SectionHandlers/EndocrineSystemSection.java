package org.openmrs.module.CDAGenerator.SectionHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class EndocrineSystemSection extends PhysicalExamSection 
{
	public EndocrineSystemSection()
	{
	this.sectionName="ENDOCRINE SYSTEM";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.25";
	this.code="29307-6";
	this.sectionDescription="The endocrine system section shall contain a description of any type of endocrine system exam";
	}
	public static Section buildEndocrineSystemSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		EndocrineSystemSection ccs=new EndocrineSystemSection();
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}	
}
