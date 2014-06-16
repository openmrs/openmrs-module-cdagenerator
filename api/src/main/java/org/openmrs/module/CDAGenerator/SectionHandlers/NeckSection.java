package org.openmrs.module.CDAGenerator.SectionHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class NeckSection extends PhysicalExamSection 
{
	public NeckSection()
	{
	this.sectionName="NECK";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.24";
	this.code="11411-6";
	this.sectionDescription="The neck section shall contain a description of any type of neck exam";
	}
	public static Section buildNeckSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		NeckSection ccs=new  NeckSection();
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}	
}
