package org.openmrs.module.CDAGenerator.SectionHandlers;

public class SocialHistorySection extends BaseCdaSectionHandler
{
public SocialHistorySection()
{
	this.sectionName="Social History";
	this.templateid="1.3.6.1.4.1.19376.1.5.3.1.3.16";
	this.code="29762-2";
	this.sectionDescription="The social history section shall contain a narrative description of the person’s beliefs, home life, community life, work life, hobbies, and risky habits";
    this.parentTemplateId="2.16.840.1.113883.10.20.1.15";
}
}
