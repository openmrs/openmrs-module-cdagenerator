package org.openmrs.module.CDAGenerator.SectionHandlers;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openmrs.Patient;
import org.openmrs.module.CDAGenerator.api.CDAHelper;

public class PhysicalExamSection extends BaseCdaSectionHandler 
{
	public PhysicalExamSection()
	{
		this.sectionName="PHYSICAL EXAMINATION";
		this.templateid="1.3.6.1.4.1.19376.1.5.3.1.1.9.15";
		this.code="29545-1";
		this.parentTemplateId="1.3.6.1.4.1.19376.1.5.3.1.3.24";
		this.sectionDescription="The physical exam section shall contain only the required and optional subsections performed.";
	}
	
public static Section buildPhysicalExamSection(Patient patient)
{
	Section section=CDAFactory.eINSTANCE.createSection();
    PhysicalExamSection ccs=new PhysicalExamSection();
    section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getParentTemplateId(),null ,null ));
    section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
    section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
    section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
    StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
    text.addText("Text as described above");
    section.setText(text);  
    
    Section OptionalSecs=CDAFactory.eINSTANCE.createSection();
    
    OptionalSecs=CodedVitalSignsSection.buildCodedVitalSignsSection(patient);
    section.addSection(OptionalSecs);
   
    OptionalSecs=GeneralAppearanceSection.buildGeneralAppearanceSection();
    section.addSection(OptionalSecs);
    
    OptionalSecs=VisibleImplantedMedicalDevicesSection.buildVisibleImplantedMedicalDevicesSection();
    section.addSection(OptionalSecs);
    
    OptionalSecs=IntegumentarySystemSection.buildIntegumentarySystemSection();
    section.addSection(OptionalSecs);
    
    OptionalSecs=HeadSection.buildHeadSection();
    section.addSection(OptionalSecs);
    
    OptionalSecs=OptionalEyesSection.buildOptionalEyesSection();
    section.addSection(OptionalSecs);
    
    OptionalSecs=EarNoseMouthThroatSection.buildEarNoseMouthThroatSection();
    section.addSection(OptionalSecs);
    
    OptionalSecs=EarsSection.buildEarsSection();
    section.addSection(OptionalSecs);

    OptionalSecs=NoseSection.buildNoseSection();
    section.addSection(OptionalSecs);

    OptionalSecs=MouthThroatTeethSection.buildMouthThroatTeethSection();
    section.addSection(OptionalSecs);
    
    OptionalSecs=NeckSection.buildNeckSection();
    section.addSection(OptionalSecs);
    
    OptionalSecs=EndocrineSystemSection.buildEndocrineSystemSection();
    section.addSection(OptionalSecs);
    
    OptionalSecs=ThoraxLungsSection.buildThoraxLungsSection();
    section.addSection(OptionalSecs);
    
    OptionalSecs=ChestWallSection.buildChestWallSection();
    section.addSection(OptionalSecs);
    
    OptionalSecs=BreastSection.buildBreastSection();
    section.addSection(OptionalSecs);
    
    OptionalSecs=HeartSection.buildHeartSection();
    section.addSection(OptionalSecs);
    
    OptionalSecs=RespiratorySystemSection.buildRespiratorySystemSection();
    section.addSection(OptionalSecs);
    
   	OptionalSecs=AbdomenSection.buildAbdomenSection();
   	section.addSection(OptionalSecs);
   	
   	OptionalSecs=LymphaticSystemSection.buildLymphaticSystemSection();
   	section.addSection(OptionalSecs);
   	
   	OptionalSecs=VesselsSection.buildVesselsSection();
   	section.addSection(OptionalSecs);
   	
   	OptionalSecs= MusculoskeletalSystemSection.buildMusculoskeletalSystemSection();
   	section.addSection(OptionalSecs);
   	
   	OptionalSecs=NeurologicSystemSection.buildNeurologicSystemSection();
   	section.addSection(OptionalSecs);
   	
   	OptionalSecs=GenitaliaSection.buildGenitaliaSection();
   	section.addSection(OptionalSecs);
   	
   	OptionalSecs=RectumSection.buildRectumSection();
   	section.addSection(OptionalSecs);
   	
	return section;
}

}
