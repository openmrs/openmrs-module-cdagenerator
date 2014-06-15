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
}