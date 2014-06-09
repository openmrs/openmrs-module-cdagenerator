package org.openmrs.module.CDAGenerator.SectionHandlers;

public class HistoryOfInfectionSection extends BaseCdaSectionHandler 
{
public HistoryOfInfectionSection()
{
	this.sectionName="HISTORY OF INFECTION";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.16.2.1.1";
	this.code="XX-HistoryOfInfection";
	this.sectionDescription="The history of infection section shall contain a narrative description of any infections the patient may have contracted prior to the patient's current condition";
}
}
