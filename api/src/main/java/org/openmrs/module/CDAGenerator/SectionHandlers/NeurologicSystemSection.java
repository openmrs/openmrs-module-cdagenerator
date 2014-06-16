package org.openmrs.module.CDAGenerator.SectionHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class NeurologicSystemSection extends PhysicalExamSection
{
	public NeurologicSystemSection()
	{
		this.sectionName="NEUROLOGIC SYSTEM";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.35";
		this.code="10202-0";
		this.sectionDescription="The neurologic system section shall contain a description of any type of neurologic exam";
	}
	public static Section buildNeurologicSystemSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		NeurologicSystemSection ccs=new NeurologicSystemSection();
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
}
