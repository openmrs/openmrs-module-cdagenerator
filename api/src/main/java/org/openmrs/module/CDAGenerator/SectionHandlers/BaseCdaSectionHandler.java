package org.openmrs.module.CDAGenerator.SectionHandlers;

public class BaseCdaSectionHandler {

	
	public String sectionName;
	public String sectionDescription;
	public String templateid;
	public String parentTemplateId=" ";
	public String code;
	public String codeSystem="2.16.840.1.113883.6.1";
	public String codeSystemName="LOINC";
	
	public String getSectionName()
	{
	return sectionName;
	}
	
	public String getCodeSystemName()
	{
		return codeSystemName;
	}
	
	public String getCode()
	{
	return code;
	}
	
	public String getCodeSystem()
	{
	return codeSystem;
	}
	
	public String getSectionDescription()
	{
	return sectionDescription;
	}
	
	
	public String getTemplateid()
	{
	return templateid;
	}
	
	public String getParentTemplateId()
	{
	return parentTemplateId;
	}
	
	public void setCode(String code)
	{
	this.code=code;
	}
	
	public void setCodeSystem(String codeSystem)
	{
	this.codeSystem=codeSystem;
	}
	
	public void setCodeSystemName(String codeSystemName)
	{
		this.codeSystemName=codeSystemName;
	}
	
	public void setTemplateid(String Templateid)
	{
	this.templateid=Templateid;
	}
	
	public void setParentTemplateId(String ParentTemplateId)
	{
	this.parentTemplateId=ParentTemplateId;
	}
	
	public void setSectionDescription(String description)
	{
	this.sectionDescription=description;
	}
	
		
	public void setSectionName(String name)
	{
	this.sectionName=name;
	}
	
}
