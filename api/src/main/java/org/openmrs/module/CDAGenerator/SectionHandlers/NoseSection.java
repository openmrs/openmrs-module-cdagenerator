package org.openmrs.module.CDAGenerator.SectionHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class NoseSection extends PhysicalExamSection 
{
	public NoseSection()
	{
	this.sectionName="NOSE";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.22";
	this.code="10203-8";
	this.sectionDescription="The nose section shall contain a description of any type of nose exam";
	}
	public static Section buildNoseSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		NoseSection ccs=new  NoseSection();
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
}
