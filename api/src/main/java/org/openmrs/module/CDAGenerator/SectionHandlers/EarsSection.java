package org.openmrs.module.CDAGenerator.SectionHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class EarsSection extends PhysicalExamSection 
{

	public EarsSection()
	{
	this.sectionName="EAR";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.21";
	this.code="10195-6";
	this.sectionDescription="The ears section shall contain a description of any type of ear exam";
	}
	public static Section buildEarsSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		EarsSection ccs=new  EarsSection();
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
}
