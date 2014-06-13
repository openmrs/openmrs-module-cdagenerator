package org.openmrs.module.CDAGenerator.SectionHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openhealthtools.mdht.uml.hl7.datatypes.CE;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.ST;

public class ChiefComplaintSection extends BaseCdaSectionHandler
{
public ChiefComplaintSection()
{
	this.sectionName="Chief Complaint";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.13.2.1";
	this.sectionDescription="This contains a narrative description of the patient's chief complaint";
	this.code="10154-3";
}
public ClinicalDocument buildChiefComplaintSection(ClinicalDocument cd)
{
	Section section=CDAFactory.eINSTANCE.createSection();
    ChiefComplaintSection ccs=new ChiefComplaintSection();//this is bad approach though,just to test
    section.getTemplateIds().add(buildTemplateID(ccs.getTemplateid(),null ,null ));
    section.setCode(buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
    section.setTitle(buildTitle(ccs.getSectionDescription()));
    StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
    text.addText("Text as described above");
    section.setText(text);        
	cd.addSection(section);
	return cd;
	
}
public ST buildTitle(String title)
{
	ST displayTitle = DatatypesFactory.eINSTANCE.createST();
	displayTitle.addText(title);
	return displayTitle;
}



public CE buildCodeCE(String code , String codeSystem, String displayString, String codeSystemName)
{
	CE e = DatatypesFactory.eINSTANCE.createCE();
	if(code!=null)
	{
	e.setCode(code);
	}
	if(codeSystem!=null)
	{
	e.setCodeSystem(codeSystem);
	}
	if(displayString!=null)
	{
	e.setDisplayName(displayString);
	}
	if(displayString!=null)
	{
	e.setCodeSystemName(codeSystemName);
	}
	return e;

}
public   II buildTemplateID(String root , String extension ,String assigningAuthorityName)
{

		II templateID = DatatypesFactory.eINSTANCE.createII();
		if(root!=null)
		{
		templateID.setRoot(root);
		}
		if(extension!=null)
		{
		templateID.setExtension(extension);
		}
		if(assigningAuthorityName!=null)
		{
		templateID.setAssigningAuthorityName(assigningAuthorityName);
		}
		
		return templateID;

}
}
