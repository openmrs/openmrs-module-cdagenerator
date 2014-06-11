package org.openmrs.module.CDAGenerator.SectionHandlers;

public class HistoryOfPastIllnessSection extends BaseCdaSectionHandler 
{
	public HistoryOfPastIllnessSection()
	{
		this.sectionName="HISTORY OF PAST ILLNESS";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.3.8";
		this.code="11348-0";
		this.sectionDescription="The History of Past Illness section shall contain a narrative description of the conditions the patient suffered in the past. It shall include entries for problems as described in the Entry Content Modules";
	}
}
