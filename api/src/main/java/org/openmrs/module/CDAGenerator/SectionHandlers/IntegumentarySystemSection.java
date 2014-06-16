package org.openmrs.module.CDAGenerator.SectionHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class IntegumentarySystemSection extends PhysicalExamSection 
{
	public  IntegumentarySystemSection()
	{
	this.sectionName="INTEGUMENTARY SYSTEM";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.17";
	this.code="29302-7";
	this.sectionDescription="The integumentary system section shall contain a description of any type of integumentary system exam.";
	}
	public static Section buildIntegumentarySystemSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		IntegumentarySystemSection ccs=new  IntegumentarySystemSection();
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
}
