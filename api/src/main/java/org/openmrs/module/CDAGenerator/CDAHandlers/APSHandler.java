package org.openmrs.module.CDAGenerator.CDAHandlers;

public class APSHandler extends BaseCdaTypeHandler
{
	
	public APSHandler()
	{
	this.documentFullName="Antepartum Summary";	
	this.documentShortName="APS";
	this.documentDescription="information regarding the status of a patients pregnancy";
	this.templateid="1.3.6.1.4.19376.1.5.3.1.1.11.2";
	this.formatCode="urn:ihe:pcc:aps:2007";
	}
	
}