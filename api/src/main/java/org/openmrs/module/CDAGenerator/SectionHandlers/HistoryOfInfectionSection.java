package org.openmrs.module.CDAGenerator.SectionHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class HistoryOfInfectionSection extends BaseCdaSectionHandler 
{
public HistoryOfInfectionSection()
{
	this.sectionName="HISTORY OF INFECTION";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.16.2.1.1";
	this.code="XX-HistoryOfInfection";
	this.sectionDescription="The history of infection section shall contain a narrative description of any infections the patient may have contracted prior to the patient's current condition";
}

public static Section buildHistoryOfInfectionSection()
{
	Section section=CDAFactory.eINSTANCE.createSection();
    HistoryOfInfectionSection ccs=new HistoryOfInfectionSection();
    section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
    section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
    section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
    StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
    text.addText("Text as described above");
    section.setText(text);        
	return section;
}
}
