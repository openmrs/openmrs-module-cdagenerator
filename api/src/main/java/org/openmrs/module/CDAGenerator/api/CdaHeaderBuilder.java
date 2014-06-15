package org.openmrs.module.CDAGenerator.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.openhealthtools.mdht.uml.cda.Act;
import org.openhealthtools.mdht.uml.cda.AssignedAuthor;
import org.openhealthtools.mdht.uml.cda.AssignedCustodian;
import org.openhealthtools.mdht.uml.cda.AssignedEntity;
import org.openhealthtools.mdht.uml.cda.AssociatedEntity;
import org.openhealthtools.mdht.uml.cda.Author;
import org.openhealthtools.mdht.uml.cda.AuthoringDevice;
import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.Component4;
import org.openhealthtools.mdht.uml.cda.Custodian;
import org.openhealthtools.mdht.uml.cda.CustodianOrganization;
import org.openhealthtools.mdht.uml.cda.DocumentationOf;
import org.openhealthtools.mdht.uml.cda.Entry;
import org.openhealthtools.mdht.uml.cda.EntryRelationship;
import org.openhealthtools.mdht.uml.cda.InfrastructureRootTypeId;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.ObservationRange;
import org.openhealthtools.mdht.uml.cda.Organization;
import org.openhealthtools.mdht.uml.cda.Organizer;
import org.openhealthtools.mdht.uml.cda.Participant1;
import org.openhealthtools.mdht.uml.cda.PatientRole;
import org.openhealthtools.mdht.uml.cda.Performer1;
import org.openhealthtools.mdht.uml.cda.Person;
import org.openhealthtools.mdht.uml.cda.ReferenceRange;
import org.openhealthtools.mdht.uml.cda.RelatedSubject;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.ServiceEvent;
import org.openhealthtools.mdht.uml.cda.StrucDocText;
import org.openhealthtools.mdht.uml.cda.Subject;
import org.openhealthtools.mdht.uml.cda.SubjectPerson;
import org.openhealthtools.mdht.uml.hl7.datatypes.AD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CE;
import org.openhealthtools.mdht.uml.hl7.datatypes.CS;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.ED;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVL_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVXB_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.ON;
import org.openhealthtools.mdht.uml.hl7.datatypes.PN;
import org.openhealthtools.mdht.uml.hl7.datatypes.SC;
import org.openhealthtools.mdht.uml.hl7.datatypes.ST;
import org.openhealthtools.mdht.uml.hl7.datatypes.TEL;
import org.openhealthtools.mdht.uml.hl7.datatypes.TS;
import org.openhealthtools.mdht.uml.hl7.vocab.ActClassObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.ActClassRoot;
import org.openhealthtools.mdht.uml.hl7.vocab.ActMood;
import org.openhealthtools.mdht.uml.hl7.vocab.NullFlavor;
import org.openhealthtools.mdht.uml.hl7.vocab.ParticipationTargetSubject;
import org.openhealthtools.mdht.uml.hl7.vocab.ParticipationType;
import org.openhealthtools.mdht.uml.hl7.vocab.RoleClassAssociative;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActClassDocumentEntryAct;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActClassDocumentEntryOrganizer;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActMoodDocumentObservation;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ActRelationshipEntryRelationship;
import org.openhealthtools.mdht.uml.hl7.vocab.x_DocumentActMood;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ServiceEventPerformer;
import org.openmrs.Patient;
import org.openmrs.PersonAddress;
import org.openmrs.Relationship;
import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.CDAHandlers.BaseCdaTypeHandler;
import org.openmrs.module.CDAGenerator.SectionHandlers.AbdomenSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.BreastSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.ChestWallSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.ChiefComplaintSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.CodedFamilyMedicalHistorySection;
import org.openmrs.module.CDAGenerator.SectionHandlers.CodedVitalSignsSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.EarNoseMouthThroatSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.EarsSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.EndocrineSystemSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.FamilyMedicalHistorySection;
import org.openmrs.module.CDAGenerator.SectionHandlers.GeneralAppearanceSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.GenitaliaSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.HeadSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.HeartSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.HistoryOfInfectionSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.HistoryOfPastIllnessSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.HistoryOfPresentIllnessSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.IntegumentarySystemSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.LymphaticSystemSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.MouthThroatTeethSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.MusculoskeletalSystemSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.NeckSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.NeurologicSystemSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.NoseSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.OptionalEyesSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.PhysicalExamSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.PregnancyHistorySection;
import org.openmrs.module.CDAGenerator.SectionHandlers.RectumSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.RespiratorySystemSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.ReviewOfSystemsSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.SocialHistorySection;
import org.openmrs.module.CDAGenerator.SectionHandlers.ThoraxLungsSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.VesselsSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.VisibleImplantedMedicalDevicesSection;
import org.openmrs.module.CDAGenerator.SectionHandlers.VitalSignsSection;

public class CdaHeaderBuilder 
{
	
	public ClinicalDocument buildHeader(ClinicalDocument doc,BaseCdaTypeHandler bh,Patient p)
	{
				
		InfrastructureRootTypeId typeId = CDAFactory.eINSTANCE.createInfrastructureRootTypeId();
		typeId.setExtension("POCD_HD000040");/*fixed*/
		typeId.setRoot("2.16.840.1.113883.1.3");
		doc.setTypeId(typeId);
		
		doc.getTemplateIds().clear();
		doc.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10","IMPL_CDAR2_LEVEL1",null));
		doc.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.3",null,null));
		doc.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.1.1",null,null));//Medical Documents 
		doc.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.1.2",null,null));//Medical Summary
		doc.getTemplateIds().add(CDAHelper.buildTemplateID(bh.templateid,null,null));//
		
		
		doc.setId(CDAHelper.buildID(Context.getAdministrationService().getImplementationId().getImplementationId(),bh.documentShortName));//need to generate dynamically
		
		doc.setCode(CDAHelper.buildCodeCE("34117-2","2.16.840.1.113883.6.1",null,"LOINC"));//need to generate dynamically template id according loinc code system if we choose snomed then it would be diffrent
		
		doc.setTitle(CDAHelper.buildTitle(bh.documentFullName));
		
		Date d = new Date();
		doc.setEffectiveTime(CDAHelper.buildEffectiveTime(d));
		
		CE confidentialityCode = DatatypesFactory.eINSTANCE.createCE();
		confidentialityCode.setCode("N");//this can change N,M,L,R,V
		confidentialityCode.setCodeSystem("2.16.840.1.113883.5.25");/*fixed*/
		doc.setConfidentialityCode(confidentialityCode);
		
		CS languageCode = DatatypesFactory.eINSTANCE.createCS();
		languageCode.setCode("en-US");/*fixed*/
		doc.setLanguageCode(languageCode);
     
        CS realmcode=DatatypesFactory.eINSTANCE.createCS("US");
        doc.getRealmCodes().add(realmcode);
		
		
		
		PatientRole patientRole = CDAFactory.eINSTANCE.createPatientRole();
		patientRole.getIds().add(CDAHelper.buildID(Context.getAdministrationService().getImplementationId().getImplementationId(),
				p.getPatientIdentifier().getIdentifier()));//get dynamically from patient service
		
		Set<PersonAddress> addresses = p.getAddresses();
		
		AD patientAddress = DatatypesFactory.eINSTANCE.createAD();
		
		patientAddress=buildAddresses(patientAddress, addresses);
		patientRole.getAddrs().add(patientAddress);
		
		TEL patientTelecom = DatatypesFactory.eINSTANCE.createTEL();
		patientTelecom.setNullFlavor(NullFlavor.UNK);
		patientRole.getTelecoms().add(patientTelecom);
		org.openhealthtools.mdht.uml.cda.Patient cdapatient = CDAFactory.eINSTANCE.createPatient();
		
		patientRole.setPatient(cdapatient);
		PN name = DatatypesFactory.eINSTANCE.createPN();
		if(p.getPersonName().getFamilyNamePrefix()!=null)
		{
			name.addPrefix(p.getPersonName().getFamilyNamePrefix());
		}
		name.addGiven(p.getPersonName().getGivenName());/* dynamically get patient name*/
		name.addFamily(p.getPersonName().getFamilyName());
		if(p.getPersonName().getFamilyNameSuffix()!=null)
		{
		name.addSuffix(p.getPersonName().getFamilyNameSuffix());
		}
		cdapatient.getNames().add(name);

		
		CE gender = DatatypesFactory.eINSTANCE.createCE();
		gender.setCode(p.getGender());//dynamic
		gender.setCodeSystem("2.16.840.1.113883.5.1");//fixed
		cdapatient.setAdministrativeGenderCode(gender);
		
		
		
		TS dateOfBirth = DatatypesFactory.eINSTANCE.createTS();
		SimpleDateFormat s1 = new SimpleDateFormat("yyyyMMdd");
		Date dobs=p.getBirthdate();
		String dob = s1.format(dobs);
		dateOfBirth.setValue(dob);
		cdapatient.setBirthTime(dateOfBirth); 
		
		
		CE codes = DatatypesFactory.eINSTANCE.createCE();
		codes.setCode("S");
		cdapatient.setMaritalStatusCode(codes);
		
		CE codes1 = DatatypesFactory.eINSTANCE.createCE();
		codes1.setCode("AAA");				
		cdapatient.setEthnicGroupCode(codes1);
		
		Organization providerOrganization = CDAFactory.eINSTANCE.createOrganization();
		AD providerOrganizationAddress = DatatypesFactory.eINSTANCE.createAD();
		providerOrganizationAddress.addCounty(" ");
		providerOrganization.getIds().add(CDAHelper.buildID(Context.getAdministrationService().getImplementationId().getImplementationId(),null));
		providerOrganization.getAddrs().add(providerOrganizationAddress);

		ON organizationName = DatatypesFactory.eINSTANCE.createON();
		organizationName.addText(Context.getAdministrationService().getImplementationId().getName());
		providerOrganization.getNames().add(organizationName);

		TEL providerOrganizationTelecon = DatatypesFactory.eINSTANCE.createTEL();
		providerOrganizationTelecon.setNullFlavor(NullFlavor.UNK);
		providerOrganization.getTelecoms().add(providerOrganizationTelecon);

		patientRole.setProviderOrganization(providerOrganization);

			
		doc.addPatientRole(patientRole);

		
		
		Author author = CDAFactory.eINSTANCE.createAuthor();
		author.setTime(CDAHelper.buildEffectiveTime(new Date()));
		//in this case we consider the assigned author is the one generating the document i.e the logged in user exporting the document
		AssignedAuthor assignedAuthor = CDAFactory.eINSTANCE.createAssignedAuthor();
		II authorId = DatatypesFactory.eINSTANCE.createII();
		authorId.setRoot(Context.getAdministrationService().getImplementationId().getImplementationId());
		assignedAuthor.getIds().add(authorId);
		
		AD assignedAuthorAddress=DatatypesFactory.eINSTANCE.createAD();
		assignedAuthorAddress.addCountry(" ");
		TEL assignedAuthorTelecon = DatatypesFactory.eINSTANCE.createTEL();
		assignedAuthorTelecon.setNullFlavor(NullFlavor.UNK);
		
		assignedAuthor.getAddrs().add(assignedAuthorAddress);
		assignedAuthor.getTelecoms().add(assignedAuthorTelecon);
		
			

		Person assignedPerson = CDAFactory.eINSTANCE.createPerson(); //assigned person must be system
		PN assignedPersonName = DatatypesFactory.eINSTANCE.createPN();
		assignedPersonName.addPrefix("Dr.");  
		assignedPersonName.addGiven("Robert");
		assignedPersonName.addFamily("Dolin");
		assignedPerson.getNames().add(assignedPersonName);
		assignedAuthor.setAssignedPerson(assignedPerson);
		Organization representedOrganization = CDAFactory.eINSTANCE.createOrganization();
		representedOrganization.getIds().add(CDAHelper.buildID("2.16.840.1.113883.19.5",null));
		ON representedOrganizationName=DatatypesFactory.eINSTANCE.createON();
		representedOrganizationName.addText(Context.getAdministrationService().getImplementationId().getName());
		representedOrganization.getNames().add(representedOrganizationName);
		AD representedOrganizationAddress = DatatypesFactory.eINSTANCE.createAD();
		representedOrganizationAddress.addCounty(" ");
		representedOrganization.getAddrs().add(representedOrganizationAddress);
		TEL representedOrganizationTelecon = DatatypesFactory.eINSTANCE.createTEL();
		representedOrganizationTelecon.setNullFlavor(NullFlavor.UNK);
		representedOrganization.getTelecoms().add(representedOrganizationTelecon);
		assignedAuthor.setRepresentedOrganization(representedOrganization);

		
		
		
		
		
		AuthoringDevice authoringDevice = CDAFactory.eINSTANCE.createAuthoringDevice();
		SC authoringDeviceName = DatatypesFactory.eINSTANCE.createSC();
		authoringDeviceName.addText(Context.getAdministrationService().getGlobalProperty("application.name"));
		authoringDevice.setSoftwareName(authoringDeviceName);
		assignedAuthor.setAssignedAuthoringDevice(authoringDevice);
		
		author.setAssignedAuthor(assignedAuthor);
		doc.getAuthors().add(author);
		

		Custodian custodian = CDAFactory.eINSTANCE.createCustodian();
		AssignedCustodian assignedCustodian = CDAFactory.eINSTANCE.createAssignedCustodian();
		CustodianOrganization custodianOrganization = CDAFactory.eINSTANCE.createCustodianOrganization();
		AD custodianOrganizationAddress=DatatypesFactory.eINSTANCE.createAD();
		II custodianId = DatatypesFactory.eINSTANCE.createII();
		custodianId.setRoot("2.16.840.1.113883.19.5");
		custodianOrganization.getIds().add(custodianId);
		custodianOrganizationAddress.addCountry(" ");
		custodianOrganization.setAddr(custodianOrganizationAddress);
		ON custodianOrganizationName=DatatypesFactory.eINSTANCE.createON();
		custodianOrganizationName.addText(Context.getAdministrationService().getImplementationId().getName());
		custodianOrganization.setName(custodianOrganizationName);
		TEL custodianOrganizationTelecon=DatatypesFactory.eINSTANCE.createTEL();
		custodianOrganizationTelecon.setNullFlavor(NullFlavor.UNK);
		custodianOrganization.setTelecom(custodianOrganizationTelecon);
		assignedCustodian.setRepresentedCustodianOrganization(custodianOrganization);
		custodian.setAssignedCustodian(assignedCustodian);
		doc.setCustodian(custodian);

		
		List<Relationship> relationShips= Context.getPersonService().getRelationshipsByPerson(p);
		System.out.println(relationShips);
		List<Participant1> participantList = new ArrayList<Participant1>(relationShips.size());
		System.out.print(participantList);
		for (int i = 0; i< relationShips.size();i++) {
			Participant1 e = CDAFactory.eINSTANCE.createParticipant1();

			e.setTypeCode(ParticipationType.IND);
			II pid1 = DatatypesFactory.eINSTANCE.createII();
			pid1.setRoot("1.3.6.1.4.1.19376.1.5.3.1.2.4");

			II pid2 = DatatypesFactory.eINSTANCE.createII();
			pid2.setRoot("1.3.6.1.4.1.19376.1.5.3.1.2.4.1");

			e.getTemplateIds().add(pid1);
			e.getTemplateIds().add(pid2);
			
			IVL_TS time = DatatypesFactory.eINSTANCE.createIVL_TS();
			time.setHigh(time.getHigh());
			time.setLow(time.getLow());
			//time.setNullFlavor(NullFlavor.UNK);
			e.setTime(time);
			Relationship relationship = relationShips.get(i);
			AssociatedEntity patientRelationShip = CDAFactory.eINSTANCE.createAssociatedEntity();
			patientRelationShip.setClassCode(RoleClassAssociative.PRS);
			CE relationShipCode = DatatypesFactory.eINSTANCE.createCE();
			relationShipCode.setCodeSystemName("Loinc");
			relationShipCode.setCodeSystem("2.16.840.1.113883.6.1");
			Person associatedPerson = CDAFactory.eINSTANCE.createPerson();
			PN associatedPersonName = DatatypesFactory.eINSTANCE.createPN();
			Iterator<PersonAddress> patientAddressIterator = null;
            TEL associatedPersonTelecon=DatatypesFactory.eINSTANCE.createTEL();
            associatedPersonTelecon.setNullFlavor(NullFlavor.UNK);
			switch (relationship.getRelationshipType().getId()) {
			case 1:
				
				relationShipCode.setDisplayName("Doctor");
				associatedPersonName.addFamily(relationship.getPersonA().getFamilyName());
				associatedPersonName.addGiven(relationship.getPersonA().getGivenName());
				patientAddressIterator = relationship.getPersonB().getAddresses().iterator();
				break;
			case 2:

				relationShipCode.setDisplayName("Sibling");
				associatedPersonName.addFamily(relationship.getPersonA().getFamilyName());
				associatedPersonName.addGiven(relationship.getPersonA().getGivenName());
				patientAddressIterator = relationship.getPersonA().getAddresses().iterator();
				break;
			case 3:
				if(p.getId() == relationship.getPersonA().getId())
				{
					relationShipCode.setDisplayName("Child");
					associatedPersonName.addFamily(relationship.getPersonB().getFamilyName());
					associatedPersonName.addGiven(relationship.getPersonB().getGivenName());
					patientAddressIterator = relationship.getPersonB().getAddresses().iterator();
				}else
				{
					relationShipCode.setDisplayName("Parent");
					associatedPersonName.addFamily(relationship.getPersonA().getFamilyName());
					associatedPersonName.addGiven(relationship.getPersonA().getGivenName());
					patientAddressIterator = relationship.getPersonA().getAddresses().iterator();

				}
				break;
			case 4:
				if(p.getId() == relationship.getPersonA().getId())
				{
					if(relationship.getPersonB().getGender().equalsIgnoreCase("M"))
						relationShipCode.setDisplayName("Nephew");
						else
					relationShipCode.setDisplayName("Neice");
					associatedPersonName.addFamily(relationship.getPersonB().getFamilyName());
					associatedPersonName.addGiven(relationship.getPersonB().getGivenName());
					patientAddressIterator = relationship.getPersonB().getAddresses().iterator();
				}else
				{
					if(relationship.getPersonA().getGender().equalsIgnoreCase("M"))
						relationShipCode.setDisplayName("Uncle");
					else
					relationShipCode.setDisplayName("Aunt");
					associatedPersonName.addFamily(relationship.getPersonA().getFamilyName());
					associatedPersonName.addGiven(relationship.getPersonA().getGivenName());
					patientAddressIterator = relationship.getPersonB().getAddresses().iterator();

				}	

				break;

			}

			patientRelationShip.setCode(relationShipCode);
			AD associatedPersonAddress = DatatypesFactory.eINSTANCE.createAD();

			if(patientAddressIterator.hasNext())
			{
				PersonAddress padd = patientAddressIterator.next();
				associatedPersonAddress.addStreetAddressLine(padd.getAddress1()+ padd.getAddress2())	;
			}

			patientRelationShip.getAddrs().add(associatedPersonAddress);
			patientRelationShip.getTelecoms().add(associatedPersonTelecon);
			associatedPerson.getNames().add(associatedPersonName );
			patientRelationShip.setAssociatedPerson(associatedPerson );
			e.setAssociatedEntity(patientRelationShip);
			participantList.add(e);


		}
		doc.getParticipants().addAll(participantList);
		
	DocumentationOf dof=CDAFactory.eINSTANCE.createDocumentationOf();
	ServiceEvent serviceEvent=CDAFactory.eINSTANCE.createServiceEvent();
	serviceEvent.setClassCode(ActClassRoot.PCPR);
	
	serviceEvent.setEffectiveTime(CDAHelper.buildEffectiveTimeinIVL(new Date(),new Date()));
	Performer1 performer=CDAFactory.eINSTANCE.createPerformer1();
	
	performer.setTypeCode(x_ServiceEventPerformer.PPRF);
	performer.setFunctionCode(CDAHelper.buildCodeCE("PCP","2.16.840.1.113883.5.88",null,null));
	performer.setTime(CDAHelper.buildEffectiveTimeinIVL(new Date(),new Date()));
	
	AssignedEntity assignedEntity=CDAFactory.eINSTANCE.createAssignedEntity();
	II  assignedEntityId= DatatypesFactory.eINSTANCE.createII();
	assignedEntityId.setRoot(Context.getAdministrationService().getImplementationId().getImplementationId());
	assignedEntity.getIds().add(assignedEntityId);
	AD assignedPersonAddress=DatatypesFactory.eINSTANCE.createAD();
	assignedPersonAddress.addCountry(" ");
    TEL assignedPersonTelecon=DatatypesFactory.eINSTANCE.createTEL();
    assignedPersonTelecon.setNullFlavor(NullFlavor.UNK);
	assignedEntity.setAssignedPerson(assignedPerson);
	assignedEntity.getAddrs().add(assignedPersonAddress);
	assignedEntity.getTelecoms().add(assignedPersonTelecon);
	assignedEntity.getRepresentedOrganizations().add(representedOrganization);
	
	performer.setAssignedEntity(assignedEntity);
	
	
	serviceEvent.getPerformers().add(performer);
	dof.setServiceEvent(serviceEvent);
	doc.getDocumentationOfs().add(dof);
	
	
	
	doc=buildHistoryOfPresentIllnessSection(doc);
	
	doc=buildChiefComplaintSection(doc);
	
	doc=buildSocialHistorySection(doc);
	
	doc=buildHistoryOfInfectionSection(doc);
	
	doc=buildHistoryOfPastIllnessSection(doc);
	
	doc=buildPregnancyHistorySection(doc);
	
	doc=buildCodedFamilyMedicalHistorySection(doc);
	
	doc=buildReviewOfSystemsSection(doc);
	
	doc=buildPhysicalExamSection(doc);
	
	
	return doc;
	}
	
	public AD buildAddresses(AD documentAddress,Set<PersonAddress> addresses)
	{
		for(PersonAddress address : addresses)
		{
 
			if(address.getAddress1()!=null &&address.getAddress2()!=null)
			{
				documentAddress.addStreetAddressLine(address.getAddress1().concat(address.getAddress2()));
			}
			else
			{
				documentAddress.addStreetAddressLine(" ");
			}
			if(address.getCityVillage()!=null)
			{
				documentAddress.addCity(address.getCityVillage());
			}
			else
			{
				documentAddress.addCity(" ");	
			}
			if(address.getStateProvince()!=null)
			{
				documentAddress.addState(address.getStateProvince());
			}
			else
			{
				documentAddress.addState(" ");
			}
			if(address.getCountry()!=null)
			{
				documentAddress.addCountry(address.getCountry());
			}
			else
			{
				documentAddress.addCountry(" ");
			}
			if(address.getPostalCode()!=null)
			{
				documentAddress.addPostalCode(address.getPostalCode());
			}
			else
			{
				documentAddress.addPostalCode(" ");
			}
			}
		
		

		return documentAddress;
	}
	
	
	public ClinicalDocument buildChiefComplaintSection(ClinicalDocument cd)
	{
		Section section=CDAFactory.eINSTANCE.createSection();
      //  section.setSectionId(UUID.randomUUID().toString());
        ChiefComplaintSection ccs=new ChiefComplaintSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);        
		cd.addSection(section);
		return cd;
		
	}
	
	public ClinicalDocument buildHistoryOfPresentIllnessSection(ClinicalDocument cd)
	{
		Section section=CDAFactory.eINSTANCE.createSection();
     //   section.setSectionId(UUID.randomUUID().toString());
        HistoryOfPresentIllnessSection ccs=new HistoryOfPresentIllnessSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);        
		cd.addSection(section);
		return cd;
	}
	public ClinicalDocument buildHistoryOfInfectionSection(ClinicalDocument cd)
	{
		Section section=CDAFactory.eINSTANCE.createSection();
   //     section.setSectionId(UUID.randomUUID().toString());
        HistoryOfInfectionSection ccs=new HistoryOfInfectionSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);        
		cd.addSection(section);
		return cd;
	}
	public ClinicalDocument buildSocialHistorySection(ClinicalDocument cd)
	{
		Section section=CDAFactory.eINSTANCE.createSection();
   //     section.setSectionId(UUID.randomUUID().toString());
        SocialHistorySection ccs=new SocialHistorySection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getParentTemplateId(),null ,null ));
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);        
		cd.addSection(section);
		return cd;
	}
	
	public ClinicalDocument buildReviewOfSystemsSection(ClinicalDocument cd)
	{
		Section section=CDAFactory.eINSTANCE.createSection();
   //     section.setSectionId(UUID.randomUUID().toString());
        ReviewOfSystemsSection ccs=new ReviewOfSystemsSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);        
		cd.addSection(section);
		return cd;
	}
	public ClinicalDocument buildHistoryOfPastIllnessSection(ClinicalDocument cd)
	{
	Section section=CDAFactory.eINSTANCE.createSection();
	HistoryOfPastIllnessSection ccs=new HistoryOfPastIllnessSection();
	section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
	section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
	section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
	StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
    text.addText("Text as described above");
    section.setText(text); 
    
    Entry e=CDAFactory.eINSTANCE.createEntry();
    Act act=CDAFactory.eINSTANCE.createAct();
    act.setClassCode(x_ActClassDocumentEntryAct.ACT);
    act.setMoodCode(x_DocumentActMood.EVN);
    act.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.5.2",null,null));
    act.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.5.1",null,null));
    act.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.27",null,null));
    act.getIds().add(CDAHelper.buildID("id",null));
    CD code=DatatypesFactory.eINSTANCE.createCD();
    CS cs=DatatypesFactory.eINSTANCE.createCS();
    cs.setCode("active");
    code.setNullFlavor(NullFlavor.NA);
    act.setCode(code);
    act.setStatusCode(cs);
    IVL_TS effectiveTime=DatatypesFactory.eINSTANCE.createIVL_TS();
    IVXB_TS low=DatatypesFactory.eINSTANCE.createIVXB_TS();
    low.setValue("20071011");
    IVXB_TS high=DatatypesFactory.eINSTANCE.createIVXB_TS();
    high.setValue("20071012");
    effectiveTime.setLow(low);
    effectiveTime.setLow(high);
    act.setEffectiveTime(effectiveTime);
    
    EntryRelationship entryRelationship1=CDAFactory.eINSTANCE.createEntryRelationship();
    entryRelationship1.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
    entryRelationship1.setInversionInd(false);
    
    Observation observation1=CDAFactory.eINSTANCE.createObservation();
    observation1.setClassCode(ActClassObservation.OBS);
    observation1.setMoodCode(x_ActMoodDocumentObservation.EVN);
    observation1.setNegationInd(false);
    observation1.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.28",null,null));
    observation1.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.5",null,null));
    observation1.getIds().add(CDAHelper.buildID("2.1.160",null));
    observation1.setCode(CDAHelper.buildCodeCD("64572001","2.16.840.1.113883.6.96",null,null));
    cs.setCode("completed");
    observation1.setStatusCode(cs);
    
    IVL_TS effectiveTime1=DatatypesFactory.eINSTANCE.createIVL_TS();
    IVXB_TS low1=DatatypesFactory.eINSTANCE.createIVXB_TS();
    low1.setNullFlavor(NullFlavor.UNK);
    observation1.setEffectiveTime(effectiveTime1);
    
    CD codecd=DatatypesFactory.eINSTANCE.createCD();
    codecd.setCode("thing");
    codecd.setCodeSystem("thing");
    codecd.setDisplayName("myname");
    codecd.setCodeSystemName("myname");
    observation1.getValues().add(codecd);
    entryRelationship1.setObservation(observation1);
    
    EntryRelationship entryRelationship2=CDAFactory.eINSTANCE.createEntryRelationship();
    entryRelationship2.setTypeCode(x_ActRelationshipEntryRelationship.REFR);
    entryRelationship2.setInversionInd(false);
    
    Observation observation2=CDAFactory.eINSTANCE.createObservation();
    observation2.setClassCode(ActClassObservation.OBS);
    observation2.setMoodCode(x_ActMoodDocumentObservation.EVN);
    observation2.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.1.2",null,null));
    observation2.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.51",null,null));
    observation2.setCode(CDAHelper.buildCodeCD("11323-3","2.16.840.1.113883.6.1","Health Status","LOINC"));
    observation2.setText(CDAHelper.buildEDText("#ignore"));
    observation2.setStatusCode(cs);
    observation2.getValues().add(CDAHelper.buildCodeCE("81323004","2.16.840.1.113883.6.96","Alive and well","SNOMED CT"));
    entryRelationship2.setObservation(observation2);
    
    
    EntryRelationship entryRelationship3=CDAFactory.eINSTANCE.createEntryRelationship();
    entryRelationship3.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
    entryRelationship3.setInversionInd(true);
    
    Observation observation3=CDAFactory.eINSTANCE.createObservation();
    observation3.setClassCode(ActClassObservation.OBS);
    observation3.setMoodCode(x_ActMoodDocumentObservation.EVN);
    observation3.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.1",null,null));
    observation3.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.55",null,null));
    observation3.setCode(CDAHelper.buildCodeCD("SEV","2.16.840.1.113883.5.4",null,null));
    observation3.setText(CDAHelper.buildEDText("#ignore"));
    observation3.setStatusCode(cs);
    observation3.getValues().add(CDAHelper.buildCodeCD("H","2.16.840.1.113883.5.1063",null,null));
    entryRelationship3.setObservation(observation3);
    
    
    EntryRelationship entryRelationship4=CDAFactory.eINSTANCE.createEntryRelationship();
    entryRelationship4.setTypeCode(x_ActRelationshipEntryRelationship.REFR);
    
    Observation observation4=CDAFactory.eINSTANCE.createObservation();
    observation4.setClassCode(ActClassObservation.OBS);
    observation4.setMoodCode(x_ActMoodDocumentObservation.EVN);
    observation4.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.57",null,null));
    observation4.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.50",null,null));
    observation4.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.1.1",null,null));
    observation4.setCode(CDAHelper.buildCodeCE("33999-4","2.16.840.1.113883.6.1",null,null));
    observation4.setText(CDAHelper.buildEDText("#ignore"));
    observation4.setStatusCode(cs);
    observation4.getValues().add(CDAHelper.buildCodeCE("55561003","2.16.840.1.113883.6.96"," ","SNOMED CT"));
    entryRelationship4.setObservation(observation4);
    
    EntryRelationship entryRelationship5=CDAFactory.eINSTANCE.createEntryRelationship();
    Act act1=CDAFactory.eINSTANCE.createAct();
    act1.setClassCode(x_ActClassDocumentEntryAct.ACT);
    act1.setMoodCode(x_DocumentActMood.EVN);
    act1.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.40",null,null));
    act1.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.2",null,null));
    act1.setCode(CDAHelper.buildCodeCD("48767-8","2.16.840.1.113883.6.1","Annotation Comment","LOINC"));
    act1.setText(CDAHelper.buildEDText("#ignore"));
    act1.setStatusCode(cs);   
    
        Author author = CDAFactory.eINSTANCE.createAuthor();
		author.setTime(CDAHelper.buildEffectiveTime(new Date()));
		AssignedAuthor assignedAuthor = CDAFactory.eINSTANCE.createAssignedAuthor();
				
		AD assignedAuthorAddress=DatatypesFactory.eINSTANCE.createAD();
		assignedAuthorAddress.addCountry(" ");
		TEL assignedAuthorTelecon = DatatypesFactory.eINSTANCE.createTEL();
		assignedAuthorTelecon.setNullFlavor(NullFlavor.UNK);
		
		assignedAuthor.getAddrs().add(assignedAuthorAddress);
		assignedAuthor.getTelecoms().add(assignedAuthorTelecon);
		
		Person assignedPerson = CDAFactory.eINSTANCE.createPerson(); //assigned person must be system
		PN assignedPersonName = DatatypesFactory.eINSTANCE.createPN();
		assignedPerson.getNames().add(assignedPersonName);
		assignedAuthor.setAssignedPerson(assignedPerson);
  		
		Organization representedOrganization = CDAFactory.eINSTANCE.createOrganization();
		ON representedOrganizationName=DatatypesFactory.eINSTANCE.createON();
		representedOrganizationName.addText(Context.getAdministrationService().getImplementationId().getName());
		representedOrganization.getNames().add(representedOrganizationName);
		AD representedOrganizationAddress = DatatypesFactory.eINSTANCE.createAD();
		representedOrganizationAddress.addCounty(" ");
		representedOrganization.getAddrs().add(representedOrganizationAddress);
		TEL representedOrganizationTelecon = DatatypesFactory.eINSTANCE.createTEL();
		representedOrganizationTelecon.setNullFlavor(NullFlavor.UNK);
		representedOrganization.getTelecoms().add(representedOrganizationTelecon);
		assignedAuthor.setRepresentedOrganization(representedOrganization);
		
		author.setAssignedAuthor(assignedAuthor);
		act1.getAuthors().add(author);
		entryRelationship5.setAct(act1);

		EntryRelationship entryRelationship6=CDAFactory.eINSTANCE.createEntryRelationship();
	    entryRelationship6.setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);
	    
	    Observation observation5=CDAFactory.eINSTANCE.createObservation();
	    observation5.setClassCode(ActClassObservation.OBS);
	    observation5.setMoodCode(x_ActMoodDocumentObservation.EVN);
	    observation5.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.5.3",null,null));
	    observation5.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.27",null,null));
	    entryRelationship6.setObservation(observation5);
	    
		
       act.getEntryRelationships().add(entryRelationship1);
       act.getEntryRelationships().add(entryRelationship2);
       act.getEntryRelationships().add(entryRelationship3);
       act.getEntryRelationships().add(entryRelationship4);
       act.getEntryRelationships().add(entryRelationship5);
       act.getEntryRelationships().add(entryRelationship6);
       
       e.setAct(act);
       section.getEntries().add(e);
       cd.addSection(section);
          
	return cd;
	}
	public ClinicalDocument buildPregnancyHistorySection(ClinicalDocument cd)
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
	
	cd.addSection(section);
	return cd;
	}
	
	public ClinicalDocument buildCodedFamilyMedicalHistorySection(ClinicalDocument cd)
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		CodedFamilyMedicalHistorySection ccs=new CodedFamilyMedicalHistorySection();
		FamilyMedicalHistorySection fmhs=new FamilyMedicalHistorySection();
		section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getParentTemplateId(),null ,null ));
		section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
		section.getTemplateIds().add(CDAHelper.buildTemplateID(fmhs.getParentTemplateId(),null ,null ));
		section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  

        Entry e=CDAFactory.eINSTANCE.createEntry();
        Organizer organizer=CDAFactory.eINSTANCE.createOrganizer();
        organizer.setClassCode(x_ActClassDocumentEntryOrganizer.CLUSTER);
        organizer.setMoodCode(ActMood.EVN);
        
        organizer.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.23",null,null));
        organizer.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.15",null,null));
    	
    	CS cs= DatatypesFactory.eINSTANCE.createCS();
    	cs.setCode("completed");
    	organizer.setStatusCode(cs);
    	
    	Subject subject=CDAFactory.eINSTANCE.createSubject();
         subject.setTypeCode(ParticipationTargetSubject.SBJ);
         
         RelatedSubject relatedSubject=CDAFactory.eINSTANCE.createRelatedSubject();
         relatedSubject.setCode(CDAHelper.buildCodeCE("ignore","2.16.840.1.113883.5.111",null,"RoleCode"));
         
         AD relatedSubjectAddress=DatatypesFactory.eINSTANCE.createAD();
         relatedSubjectAddress.addCountry(" ");
         TEL relatedSubjectTelecon=DatatypesFactory.eINSTANCE.createTEL();
         relatedSubjectTelecon.setNullFlavor(NullFlavor.UNK);
         
         relatedSubject.getAddrs().add(relatedSubjectAddress);
         relatedSubject.getTelecoms().add(relatedSubjectTelecon);
         
         SubjectPerson subjectperson=CDAFactory.eINSTANCE.createSubjectPerson();
         CE administartivegender=DatatypesFactory.eINSTANCE.createCE();
         PN names=DatatypesFactory.eINSTANCE.createPN();
         administartivegender.setCode("A");
         subjectperson.setAdministrativeGenderCode(administartivegender);
         subjectperson.getNames().add(names);
         relatedSubject.setSubject(subjectperson);
         
         subject.setRelatedSubject(relatedSubject);
         organizer.setSubject(subject);
         
         Component4 component=CDAFactory.eINSTANCE.createComponent4();
         
        Observation observation=CDAFactory.eINSTANCE.createObservation();
     	observation.setClassCode(ActClassObservation.OBS);
     	observation.setMoodCode(x_ActMoodDocumentObservation.EVN);
     	observation.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13.3",null,null));
     	observation.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13",null,null));
     	observation.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.22",null,null));
     	observation.getIds().add(CDAHelper.buildTemplateID("id",null,null));
     	observation.setCode(CDAHelper.buildCodeCE("64572001",null,null,null));
     	observation.setText(CDAHelper.buildEDText("#ignore"));
     	observation.setStatusCode(cs);
     	
     	
     	IVL_TS effectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
     	Date d=new Date();
     	SimpleDateFormat s = new SimpleDateFormat("yyyyMMddhhmmss");
     	String creationDate = s.format(d);
     	effectiveTime.setValue(creationDate);
     	effectiveTime.setNullFlavor(NullFlavor.UNK);
     	observation.setEffectiveTime(effectiveTime);
     	
     	CE ce= DatatypesFactory.eINSTANCE.createCE();
     	ce.setCodeSystem("2.16.840.1.113883.6.26");
     	observation.getValues().add(ce);
         
     	component.setObservation(observation); 
        organizer.getComponents().add(component);
    	
        e.setOrganizer(organizer);
		section.getEntries().add(e);
	    cd.addSection(section);
		return cd;
	}
	
	public  ClinicalDocument buildPhysicalExamSection(ClinicalDocument cd)
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		
		
       // section.setSectionId(UUID.randomUUID().toString());
        PhysicalExamSection ccs=new PhysicalExamSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getParentTemplateId(),null ,null ));
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
        
        Section OptionalSecs=CDAFactory.eINSTANCE.createSection();
        
        OptionalSecs=buildCodedVitalSignsSection();
        section.addSection(OptionalSecs);
        
        OptionalSecs=buildGeneralAppearanceSection();
        section.addSection(OptionalSecs);
        
        OptionalSecs=buildVisibleImplantedMedicalDevicesSection();
        section.addSection(OptionalSecs);
        
        OptionalSecs=buildIntegumentarySystemSection();
        section.addSection(OptionalSecs);
        
        OptionalSecs=buildHeadSection();
        section.addSection(OptionalSecs);
        
        OptionalSecs=buildOptionalEyesSection();
        section.addSection(OptionalSecs);
        
        OptionalSecs=buildEarNoseMouthThroatSection();
        section.addSection(OptionalSecs);
        
        OptionalSecs=buildEarsSection();
        section.addSection(OptionalSecs);

        OptionalSecs=buildNoseSection();
        section.addSection(OptionalSecs);

        OptionalSecs=buildMouthThroatTeethSection();
        section.addSection(OptionalSecs);
        
        OptionalSecs=buildNeckSection();
        section.addSection(OptionalSecs);
        
        OptionalSecs=buildEndocrineSystemSection();
        section.addSection(OptionalSecs);
        
        OptionalSecs=buildThoraxLungsSection();
        section.addSection(OptionalSecs);
        
        OptionalSecs=buildThoraxLungsSection();
        section.addSection(OptionalSecs);
        
        OptionalSecs=buildBreastSection();
        section.addSection(OptionalSecs);
        
        OptionalSecs=buildHeartSection();
        section.addSection(OptionalSecs);
        
        OptionalSecs=buildRespiratorySystemSection();
        section.addSection(OptionalSecs);
        
       	OptionalSecs=buildAbdomenSection();
       	section.addSection(OptionalSecs);
       	
       	OptionalSecs=buildLymphaticSystemSection();
       	section.addSection(OptionalSecs);
       	
       	OptionalSecs=buildVesselsSection();
       	section.addSection(OptionalSecs);
       	
       	OptionalSecs= buildMusculoskeletalSystemSection();
       	section.addSection(OptionalSecs);
       	
       	OptionalSecs=buildNeurologicSystemSection();
       	section.addSection(OptionalSecs);
       	
       	OptionalSecs=buildGenitaliaSection();
       	section.addSection(OptionalSecs);
       	
       	OptionalSecs=buildRectumSection();
       	section.addSection(OptionalSecs);
       	
		cd.addSection(section);		
		return cd;
	}
	
	public  Section buildGeneralAppearanceSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		GeneralAppearanceSection ccs=new  GeneralAppearanceSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
	public  Section buildVisibleImplantedMedicalDevicesSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		VisibleImplantedMedicalDevicesSection ccs=new  VisibleImplantedMedicalDevicesSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
	public  Section buildIntegumentarySystemSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		IntegumentarySystemSection ccs=new  IntegumentarySystemSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
	public  Section buildHeadSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		HeadSection ccs=new  HeadSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
	public  Section buildOptionalEyesSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		OptionalEyesSection ccs=new  OptionalEyesSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
	public  Section buildEarNoseMouthThroatSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		EarNoseMouthThroatSection ccs=new  EarNoseMouthThroatSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
	public  Section buildEarsSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		EarsSection ccs=new  EarsSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
	public  Section buildNoseSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		NoseSection ccs=new  NoseSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
	public  Section buildMouthThroatTeethSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		MouthThroatTeethSection ccs=new  MouthThroatTeethSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}	
	public  Section buildNeckSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		NeckSection ccs=new  NeckSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}	
	public  Section buildEndocrineSystemSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		EndocrineSystemSection ccs=new EndocrineSystemSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}	
	public  Section buildThoraxLungsSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		ThoraxLungsSection ccs=new ThoraxLungsSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}	
	public  Section buildChestWallSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		ChestWallSection ccs=new ChestWallSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}	
	public  Section buildBreastSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		BreastSection ccs=new BreastSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
	public  Section buildHeartSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		HeartSection ccs=new HeartSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}	
	public  Section buildRespiratorySystemSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		RespiratorySystemSection ccs=new RespiratorySystemSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
	public  Section buildAbdomenSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		AbdomenSection ccs=new AbdomenSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
	public  Section buildLymphaticSystemSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		LymphaticSystemSection ccs=new LymphaticSystemSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
	public  Section buildVesselsSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		VesselsSection ccs=new VesselsSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
	public  Section buildMusculoskeletalSystemSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		MusculoskeletalSystemSection ccs=new MusculoskeletalSystemSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
	public  Section buildNeurologicSystemSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		NeurologicSystemSection ccs=new NeurologicSystemSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
	public  Section buildGenitaliaSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		GenitaliaSection ccs=new GenitaliaSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
	public  Section buildRectumSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		RectumSection ccs=new RectumSection();//this is bad approach though,just to test
        section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
        section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);  
		return section;
	}
	public Section buildCodedVitalSignsSection()
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		VitalSignsSection vss=new VitalSignsSection();
		CodedVitalSignsSection ccs=new CodedVitalSignsSection();
		section.getTemplateIds().add(CDAHelper.buildTemplateID(vss.getParentTemplateId(),null ,null ));
		section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getParentTemplateId(),null ,null ));
		section.getTemplateIds().add(CDAHelper.buildTemplateID(ccs.getTemplateid(),null ,null ));
		section.setCode(CDAHelper.buildCodeCE(ccs.getCode(),ccs.getCodeSystem(),ccs.getSectionName(),ccs.getCodeSystemName()));
        section.setTitle(CDAHelper.buildTitle(ccs.getSectionDescription()));
        StrucDocText text=CDAFactory.eINSTANCE.createStrucDocText();
        text.addText("Text as described above");
        section.setText(text);
        
        Entry e=CDAFactory.eINSTANCE.createEntry();
        Organizer organizer=CDAFactory.eINSTANCE.createOrganizer();
        organizer.setClassCode(x_ActClassDocumentEntryOrganizer.CLUSTER);
        organizer.setMoodCode(ActMood.EVN);
        
        organizer.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.32",null,null));
        organizer.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.35",null,null));
        organizer.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13.1",null,null));
        organizer.getIds().add(CDAHelper.buildID("id",null));
        organizer.setCode(CDAHelper.buildCodeCD("46680005","2.16.840.1.113883.6.96","Vital signs","SNOMED CT"));
        CS cs= DatatypesFactory.eINSTANCE.createCS();
    	cs.setCode("completed");
    	organizer.setStatusCode(cs);
    	
    	IVL_TS effectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
     	Date d=new Date();
     	SimpleDateFormat s = new SimpleDateFormat("yyyyMMddhhmmss");
     	String creationDate = s.format(d);
     	effectiveTime.setValue(creationDate);
     	effectiveTime.setNullFlavor(NullFlavor.UNK);
     	organizer.setEffectiveTime(effectiveTime);
    	
     	Author author = CDAFactory.eINSTANCE.createAuthor();
		author.setTime(CDAHelper.buildEffectiveTime(new Date()));
		AssignedAuthor assignedAuthor = CDAFactory.eINSTANCE.createAssignedAuthor();
				
		AD assignedAuthorAddress=DatatypesFactory.eINSTANCE.createAD();
		assignedAuthorAddress.addCountry(" ");
		TEL assignedAuthorTelecon = DatatypesFactory.eINSTANCE.createTEL();
		assignedAuthorTelecon.setNullFlavor(NullFlavor.UNK);
		
		assignedAuthor.getAddrs().add(assignedAuthorAddress);
		assignedAuthor.getTelecoms().add(assignedAuthorTelecon);
		
		Person assignedPerson = CDAFactory.eINSTANCE.createPerson(); //assigned person must be system
		PN assignedPersonName = DatatypesFactory.eINSTANCE.createPN();
		assignedPerson.getNames().add(assignedPersonName);
		assignedAuthor.setAssignedPerson(assignedPerson);
  			
		author.setAssignedAuthor(assignedAuthor);
		organizer.getAuthors().add(author);
		
		Component4 component=CDAFactory.eINSTANCE.createComponent4();
        
        Observation observation=CDAFactory.eINSTANCE.createObservation();
     	observation.setClassCode(ActClassObservation.OBS);
     	observation.setMoodCode(x_ActMoodDocumentObservation.EVN);
     	observation.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13",null,null));
     	observation.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.1.31",null,null));
     	observation.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.4.13.2",null,null));
     	observation.getIds().add(CDAHelper.buildTemplateID("id",null,null));
     	observation.setCode(CDAHelper.buildCodeCE("9279-1","2.16.840.1.113883.6.1",null,"LOINC"));
     	observation.setText(CDAHelper.buildEDText("#ignore"));
     	observation.setStatusCode(cs);
     	IVL_TS effectiveTime1 = DatatypesFactory.eINSTANCE.createIVL_TS();
     	effectiveTime1.setNullFlavor(NullFlavor.UNK);
     	observation.setEffectiveTime(effectiveTime1);
     	
     	ReferenceRange referenceRange=CDAFactory.eINSTANCE.createReferenceRange();
     	ObservationRange observationRange=CDAFactory.eINSTANCE.createObservationRange();
     	observationRange.setText(CDAHelper.buildEDText("Reference Range Text Here"));
     	referenceRange.setObservationRange(observationRange);
     	observation.getReferenceRanges().add(referenceRange);
     	component.setObservation(observation);    	
        organizer.getComponents().add(component);
        e.setOrganizer(organizer);
		section.getEntries().add(e); 	   
		
		return section;
	}
}
