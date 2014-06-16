package org.openmrs.module.CDAGenerator.SectionHandlers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Entry;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.hl7.datatypes.CE;
import org.openhealthtools.mdht.uml.hl7.datatypes.CS;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVL_TS;
import org.openhealthtools.mdht.uml.hl7.vocab.ActClassObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActMoodDocumentObservation;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class PregnancyHistorySection extends BaseCdaSectionHandler 
{
	public PregnancyHistorySection()
	{
		this.sectionName="HISTORY OF PREGNANCIES";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.5.3.4";
		this.code="10162-6";
		this.sectionDescription="The pregnancy history section contains coded entries describing the patient history of pregnancies";
	}

	public static Section buildPregnancyHistorySection()
	{
	Section section=CDAFactory.eINSTANCE.createSection();
	PregnancyHistorySection ccs=new PregnancyHistorySection();
	section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
	section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
	Entry e=CDAFactory.eINSTANCE.createEntry();
	Observation o=CDAFactory.eINSTANCE.createObservation();
	o.setClassCode(ActClassObservation.OBS);
	o.setMoodCode(x_ActMoodDocumentObservation.EVN);
	o.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13",null,null));
	o.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13.5",null,null));
	o.getIds().add(CDAHelper.buildTemplateID("pregid",null,null));
	o.setCode(CDAHelper.buildCodeCE("48767-8","2.16.840.1.113883.6.1","Annotation Comment","LOINC"));
	
	o.setText(CDAHelper.buildEDText("#xxx"));
	e.setObservation(o);
	CS cs= DatatypesFactory.eINSTANCE.createCS();
	cs.setCode("completed");
	o.setStatusCode(cs);
	
	IVL_TS effectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
	Date d=new Date();
	SimpleDateFormat s = new SimpleDateFormat("yyyyMMddhhmmss");
	String creationDate = s.format(d);
	effectiveTime.setValue(creationDate);
	
	o.setEffectiveTime(effectiveTime);
	CE ce= DatatypesFactory.eINSTANCE.createCE();
	o.getValues().add(ce);
	
	e.setObservation(o);
	section.getEntries().add(e);
	return section;
	}
}
