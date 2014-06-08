package org.openmrs.module.CDAGenerator.CDAHandlers;

public class APHPHandler extends BaseCdaTypeHandler
{
	
	public APHPHandler()
	{
	this.documentFullName="Antepartum History and Physical";	
	this.documentShortName="APHP";
	this.documentDescription="Contains a record of initial history and physical";
	this.templateid="1.3.6.1.4.19376.1.5.3.1.1.16.1.1";
	this.formatCode="urn:ihe:pcc:aphp:2008";
	}
		
}