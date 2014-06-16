package org.openmrs.module.CDAGenerator.SectionHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class HistoryOfPresentIllnessSection extends BaseCdaSectionHandler
{
public HistoryOfPresentIllnessSection()
{
	this.sectionName="HISTORY OF PRESENT ILLNES";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.3.4";
	this.sectionDescription="This contains a narrative description of the patient's Present Illness histroy";
	this.code="10164-2";
}
public static Section buildHistoryOfPresentIllnessSection()
{
	Section section=CDAFactory.eINSTANCE.createSection();
    HistoryOfPresentIllnessSection ccs=new HistoryOfPresentIllnessSection();
    section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
    section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
    section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
    StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
    text.addText("Text as described above");
    section.setText(text);        
	return section;
}
}