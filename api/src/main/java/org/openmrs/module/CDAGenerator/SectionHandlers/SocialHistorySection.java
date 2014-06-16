package org.openmrs.module.CDAGenerator.SectionHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class SocialHistorySection extends BaseCdaSectionHandler
{
public SocialHistorySection()
{
	this.sectionName="Social History";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.3.16";
	this.code="29762-2";
	this.sectionDescription="The social history section shall contain a narrative description of the person’s beliefs, home life, community life, work life, hobbies, and risky habits";
    this.parentTemplateId="2.16.840.1.113883.10.20.1.15";
}

public static Section buildSocialHistorySection()
{
	Section section=CDAFactory.eINSTANCE.createSection();
    SocialHistorySection ccs=new SocialHistorySection();
    section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getParentTemplateId(),null ,null ));
    section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
    section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
    section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
    StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
    text.addText("Text as described above");
    section.setText(text);        
	return section;
}

}
