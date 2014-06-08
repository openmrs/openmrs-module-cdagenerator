package org.openmrs.module.CDAGenerator.SectionHandlers;

public class BaseCdaSectionHandler {

	
	public String sectionName;
	public String sectionDescription;
	public String templateid;
	
	
	public String getSectionName()
	{
	return sectionName;
	}
	
	public String getSectionDescription()
	{
	return sectionDescription;
	}
	
	
	public String getTemplateid()
	{
	return templateid;
	}
	
	
	public void setTemplateid(String Templateid)
	{
	this.templateid=Templateid;
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
