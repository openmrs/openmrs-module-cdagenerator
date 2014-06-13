package org.openmrs.module.CDAGenerator.CDAHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openmrs.Patient;
import org.openmrs.module.CDAGenerator.SectionHandlers.ChiefComplaintSection;
import org.openmrs.module.CDAGenerator.api.CdaHeaderBuilder;

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

	public ClinicalDocument ApsCdaMessage(Patient patient,BaseCdaTypeHandler basecdatypehandler)
	{ 
		ClinicalDocument doc = CDAFactory.eINSTANCE.createClinicalDocument();
		CdaHeaderBuilder header=new CdaHeaderBuilder();
		doc=header.buildHeader(doc, basecdatypehandler, patient);
		
		 ChiefComplaintSection cheifcomplaintsection=new ChiefComplaintSection();
		 doc=cheifcomplaintsection.buildChiefComplaintSection(doc);
		 return doc;
	}
}