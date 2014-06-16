package org.openmrs.module.CDAGenerator.SectionHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class MouthThroatTeethSection extends PhysicalExamSection 
{
	public MouthThroatTeethSection()
	{
	this.sectionName="MOUTH and THROAT and TEETH";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.23";
	this.code="10201-2";
	this.sectionDescription="The mouth, throat, and teeth section shall contain a description of any type of mouth, throat, or teeth exam";
	}
	public static Section buildMouthThroatTeethSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		MouthThroatTeethSection ccs=new  MouthThroatTeethSection();
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}	
}
