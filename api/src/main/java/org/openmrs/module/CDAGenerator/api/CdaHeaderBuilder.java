package org.openmrs.module.CDAGenerator.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PersonAddress;
import org.openmrs.PersonAttribute;
import org.openmrs.Relationship;
import org.openmrs.RelationshipType;
import org.openmrs.api.APIException;
import org.openmrs.api.ConceptService;
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
	/**
     * Create CDA Header
     *
     * @param handler
     *               type of cda document(APHP or APS or Some other)
     * @param doc 
     *               CDA document instance on which cda header will be appended
     * @param patient
     * @return CDA header appended on document or Report errors
     * @should return CDA headerappended on document,if document passes validation
     * @should return errors in CDA header
     */
	public ClinicalDocument buildHeader(ClinicalDocument doc,BaseCdaTypeHandler bh,Patient patient)
	{
				
		InfrastructureRootTypeId typeId = CDAFactory.eINSTANCE.createInfrastructureRootTypeId();
		typeId.setExtension("POCD_HD000040");
		typeId.setRoot("2.16.840.1.113883.1.3");
		doc.setTypeId(typeId);
		
		doc.getTemplateIds().clear();
		doc.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10","IMPL_CDAR2_LEVEL1",null));
		doc.getTemplateIds().add(CDAHelper.buildTemplateID("2.16.840.1.113883.10.20.3",null,null));
		doc.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.1.1",null,null)); 
		doc.getTemplateIds().add(CDAHelper.buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.1.2",null,null));
		
		if(bh.getParentTemplateId()!=null)
		doc.getTemplateIds().add(CDAHelper.buildTemplateID(bh.getParentTemplateId(),null,null));
		
		doc.getTemplateIds().add(CDAHelper.buildTemplateID(bh.getTemplateid(),null,null));
		
		
		try
		{
		doc.setId(CDAHelper.buildID(Context.getAdministrationService().getImplementationId().getImplementationId(),bh.documentShortName));
		}
		catch(NullPointerException e)
		{
			throw new APIException(Context.getMessageSourceService().getMessage("CDAGenerator.error.NullImplentationId"));
		}
		
		doc.setCode(CDAHelper.buildCodeCE("34117-2","2.16.840.1.113883.6.1",null,"LOINC"));
		
		doc.setTitle(CDAHelper.buildTitle(bh.getDocumentFullName()));
		
		Date d = new Date();
		doc.setEffectiveTime(CDAHelper.buildEffectiveTime(d));
		
		CE confidentialityCode = DatatypesFactory.eINSTANCE.createCE();
		confidentialityCode.setCode("N");
		confidentialityCode.setCodeSystem("2.16.840.1.113883.5.25");
		doc.setConfidentialityCode(confidentialityCode);
		
		CS languageCode = DatatypesFactory.eINSTANCE.createCS();
		languageCode.setCode("en-US");
		doc.setLanguageCode(languageCode);
     
        CS realmcode=DatatypesFactory.eINSTANCE.createCS("US");
        doc.getRealmCodes().add(realmcode);
		
		
		
		PatientRole patientRole = CDAFactory.eINSTANCE.createPatientRole();
		patientRole.getIds().add(CDAHelper.buildID(Context.getAdministrationService().getImplementationId().getImplementationId(),
				patient.getPatientIdentifier().getIdentifier()));
		
		Set<PersonAddress> addresses = patient.getAddresses();
		
		AD patientAddress = DatatypesFactory.eINSTANCE.createAD();
		
		patientAddress=buildAddresses(patientAddress, addresses);
		patientRole.getAddrs().add(patientAddress);
		
		TEL patientTelecom = DatatypesFactory.eINSTANCE.createTEL();
		patientTelecom.setNullFlavor(NullFlavor.UNK);
		patientRole.getTelecoms().add(patientTelecom);
		org.openhealthtools.mdht.uml.cda.Patient cdapatient = CDAFactory.eINSTANCE.createPatient();
		
		patientRole.setPatient(cdapatient);
		PN name = DatatypesFactory.eINSTANCE.createPN();
		if(patient.getPersonName().getFamilyNamePrefix()!=null)
		{
			name.addPrefix(patient.getPersonName().getFamilyNamePrefix());
		}
		name.addGiven(patient.getPersonName().getGivenName());
		name.addFamily(patient.getPersonName().getFamilyName());
		if(patient.getPersonName().getFamilyNameSuffix()!=null)
		{
		name.addSuffix(patient.getPersonName().getFamilyNameSuffix());
		}
		cdapatient.getNames().add(name);

		
		CE gender = DatatypesFactory.eINSTANCE.createCE();
		gender.setCode(patient.getGender());
		gender.setCodeSystem("2.16.840.1.113883.5.1");
		cdapatient.setAdministrativeGenderCode(gender);
		
		
		
		TS dateOfBirth = DatatypesFactory.eINSTANCE.createTS();
		SimpleDateFormat s1 = new SimpleDateFormat("yyyyMMdd");
		Date dobs=patient.getBirthdate();
		String dob = s1.format(dobs);
		dateOfBirth.setValue(dob);
		cdapatient.setBirthTime(dateOfBirth); 
		
	
		
		PersonAttribute civil_status=null;
		PersonAttribute ethnic_code=null;
		
		Map<String,PersonAttribute> personattributes=patient.getAttributeMap();
		
		for(Map.Entry<String,PersonAttribute> entry:personattributes.entrySet())
		{
			if(entry.getKey().equalsIgnoreCase("Civil Status"))
			{
				civil_status=entry.getValue();
			}
			
			if(entry.getKey().equalsIgnoreCase("Race"))
			{
				ethnic_code=entry.getValue();
			}
		}
		
		CE codes = DatatypesFactory.eINSTANCE.createCE();
		if(civil_status!=null)
		codes.setCode(civil_status.toString());
		else
		codes.setCode("S");	
		cdapatient.setMaritalStatusCode(codes);
		
		CE codes1 = DatatypesFactory.eINSTANCE.createCE();
		if(ethnic_code!=null)
		codes1.setCode(ethnic_code.toString());
		else
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
		assignedPersonName.addPrefix(" ");  
		assignedPersonName.addGiven(" ");
		assignedPersonName.addFamily(" ");
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
		custodianId.setRoot(Context.getAdministrationService().getImplementationId().getImplementationId());
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

		
		doc.getParticipants().add(buildSpouseElement(civil_status,patient));
		
		doc.getParticipants().add(buildNaturalFatherOfFetusElement(patient));
		
		doc.getParticipants().addAll(otherParticipants(patient));
		

		
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
		
	return doc;
	}
	
	/**
     * Builds person addresses 
     * @param documentAddress
     * @param addresses
     * @return Complete Address  or empty Address of  a person
     */
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
	
	/**
     * Builds Spouse data element in cda header
     * @param civil_status
     *                    it is person attribute which tells about martial status of patient
     * @param patient
     * @return Spouse data element
     */
	public Participant1 buildSpouseElement(PersonAttribute civil_status,Patient patient)
	{
		Participant1 participant = CDAFactory.eINSTANCE.createParticipant1();
participant.setTypeCode(ParticipationType.IND);
		
		II pid = DatatypesFactory.eINSTANCE.createII();
		pid.setRoot("1.3.6.1.4.1.19376.1.5.3.1.2.4");
		
		II pid1 = DatatypesFactory.eINSTANCE.createII();
		pid1.setRoot("1.3.6.1.4.1.19376.1.5.3.1.2.4.1");
		
		participant.getTemplateIds().add(pid);
        participant.getTemplateIds().add(pid1);
        IVL_TS time =CDAHelper.buildEffectiveTimeinIVL(new Date());
		time.setValue(time.toString());
		
		AssociatedEntity patientRelationShip = CDAFactory.eINSTANCE.createAssociatedEntity();
		patientRelationShip.setClassCode(RoleClassAssociative.PRS);
		
patientRelationShip.setCode(CDAHelper.buildCodeCE("184142008", "2.16.840.1.113883.5.111","patient's next of kin" , "SNOMED CT"));

	if(civil_status!=null)
	{
	
List<Relationship> relationShips=Context.getPersonService().getRelationshipsByPerson(patient);
Iterator<PersonAddress> patientAddressIterator = null;

for(Relationship relation:relationShips)
{
	if(relation.getRelationshipType().getId()==5)
	{
	 patientAddressIterator = relation.getPersonA().getAddresses().iterator();
	 AD spouseAddress=DatatypesFactory.eINSTANCE.createAD();
	 if(patientAddressIterator.hasNext())
		{
		PersonAddress padd = patientAddressIterator.next();
		spouseAddress.addStreetAddressLine(padd.getAddress1()+ padd.getAddress2());
		spouseAddress.addCity(padd.getCityVillage());
		spouseAddress.addCountry(padd.getCountry());
		}
        patientRelationShip.getAddrs().add(spouseAddress);

		Person associatedPerson = CDAFactory.eINSTANCE.createPerson();
		PN associatedPersonName = DatatypesFactory.eINSTANCE.createPN();
		associatedPersonName.addGiven(relation.getPersonA().getGivenName());
		associatedPersonName.addFamily(relation.getPersonA().getFamilyName());
		associatedPerson.getNames().add(associatedPersonName );
		patientRelationShip.setAssociatedPerson(associatedPerson );
		
        TEL associatedPersonTelecon=DatatypesFactory.eINSTANCE.createTEL();
        associatedPersonTelecon.setValue("tel: + ");
        associatedPersonTelecon.getUses().add(null);
		patientRelationShip.getTelecoms().add(associatedPersonTelecon);
	}
		
}
	}
	else
	{
		AD spouseAddress=DatatypesFactory.eINSTANCE.createAD();
		spouseAddress.addStreetAddressLine(" ");
		spouseAddress.addCity(" ");
		spouseAddress.addCountry(" ");
		 patientRelationShip.getAddrs().add(spouseAddress);
		 Person associatedPerson = CDAFactory.eINSTANCE.createPerson();
			PN associatedPersonName = DatatypesFactory.eINSTANCE.createPN();
			associatedPersonName.addGiven(" ");
			associatedPersonName.addFamily(" ");
			associatedPerson.getNames().add(associatedPersonName);
			
	patientRelationShip.setAssociatedPerson(associatedPerson );
			
	        TEL associatedPersonTelecon=DatatypesFactory.eINSTANCE.createTEL();
	        associatedPersonTelecon.setValue("tel: + ");
	        associatedPersonTelecon.getUses().add(null);
			patientRelationShip.getTelecoms().add(associatedPersonTelecon);
	}
		participant.setAssociatedEntity(patientRelationShip);
		
		
	
	return participant;
	}
	
	/**
     * Builds Natural Father Of Fetus data element in cda header
     * @param patient
     * @throws API Exception
     *                     Exception will be thrown if No Concept could be found with  '1144-9' SNOMED CT mapping
     * @return Spouse data element
     */
	public Participant1 buildNaturalFatherOfFetusElement(Patient patient)
	{
	Concept patientPregnentConcept = Context.getConceptService().getConceptByMapping("11449-6", "SNOMED CT");
	if(patientPregnentConcept==null)
	{
		throw new APIException(Context.getMessageSourceService().getMessage("CDAGenerator.error.NoSuchConcept",new Object[]{"11449-6","SNOMED CT"},null));
	}
	
	 List<Obs> obsList = new ArrayList<Obs>();
	 obsList.addAll(Context.getObsService().getObservationsByPersonAndConcept(patient, patientPregnentConcept));	
	 
	Obs obs=CDAHelper.getLatestObs(obsList);
	int type=0;
	String value="";
	if(obs!=null)
	{
	 type = obs.getConcept().getDatatype().getId();
	value=CDAHelper.getDatatypesValue(type,obs);
	}
	
	Participant1 participant = CDAFactory.eINSTANCE.createParticipant1();
	
	
		
		participant.setTypeCode(ParticipationType.IND);
		
		II pid = DatatypesFactory.eINSTANCE.createII();
		pid.setRoot("1.3.6.1.4.1.19376.1.5.3.1.2.4");
	   
		II pid1 = DatatypesFactory.eINSTANCE.createII();
		pid1.setRoot("1.3.6.1.4.1.19376.1.5.3.1.2.4.2");
		
		participant.getTemplateIds().add(pid);
        participant.getTemplateIds().add(pid1);
        IVL_TS time =CDAHelper.buildEffectiveTimeinIVL(new Date());
		time.setValue(time.toString());
		
		AssociatedEntity patientRelationShip = CDAFactory.eINSTANCE.createAssociatedEntity();
		patientRelationShip.setClassCode(RoleClassAssociative.PRS);
		
patientRelationShip.setCode(CDAHelper.buildCodeCE("xx-fatherofbaby", "2.16.840.1.113883.5.111","Father of Baby" , "SNOMED CT"));

if(patient.getGender().equals("F") && value.equalsIgnoreCase("Yes"))
{	
List<Relationship> relationShips=Context.getPersonService().getRelationshipsByPerson(patient);
Iterator<PersonAddress> patientAddressIterator = null;

for(Relationship relation:relationShips)
{
	if(relation.getRelationshipType().getId()==5)
	{
	 patientAddressIterator = relation.getPersonA().getAddresses().iterator();
	 AD spouseAddress=DatatypesFactory.eINSTANCE.createAD();
	 if(patientAddressIterator.hasNext())
		{
		PersonAddress padd = patientAddressIterator.next();
		spouseAddress.addStreetAddressLine(padd.getAddress1()+ padd.getAddress2());
		spouseAddress.addCity(padd.getCityVillage());
		spouseAddress.addCountry(padd.getCountry());
		}
        patientRelationShip.getAddrs().add(spouseAddress);

		Person associatedPerson = CDAFactory.eINSTANCE.createPerson();
		PN associatedPersonName = DatatypesFactory.eINSTANCE.createPN();
		associatedPersonName.addGiven(relation.getPersonA().getGivenName());
		associatedPersonName.addFamily(relation.getPersonA().getFamilyName());
		associatedPerson.getNames().add(associatedPersonName );
		patientRelationShip.setAssociatedPerson(associatedPerson );
		
        TEL associatedPersonTelecon=DatatypesFactory.eINSTANCE.createTEL();
        associatedPersonTelecon.setValue("tel: + ");
        associatedPersonTelecon.getUses().add(null);
		patientRelationShip.getTelecoms().add(associatedPersonTelecon);
	}
 }	
}

else
{
	AD spouseAddress=DatatypesFactory.eINSTANCE.createAD();
	spouseAddress.addStreetAddressLine(" ");
	spouseAddress.addCity(" ");
	spouseAddress.addCountry(" ");
	 patientRelationShip.getAddrs().add(spouseAddress);
	 Person associatedPerson = CDAFactory.eINSTANCE.createPerson();
		PN associatedPersonName = DatatypesFactory.eINSTANCE.createPN();
		associatedPersonName.addGiven(" ");
		associatedPersonName.addFamily(" ");
		associatedPerson.getNames().add(associatedPersonName);
		
patientRelationShip.setAssociatedPerson(associatedPerson );
		
        TEL associatedPersonTelecon=DatatypesFactory.eINSTANCE.createTEL();
        associatedPersonTelecon.setValue("tel: + ");
        associatedPersonTelecon.getUses().add(null);
		patientRelationShip.getTelecoms().add(associatedPersonTelecon);
	
}
participant.setAssociatedEntity(patientRelationShip);
          return participant;
	
  }
	
	/**
     * Add other persons/participants information who have relationship with patient in the cda header
     * @param p
     *            patient
     * @return other persons/participants information
     */
	public List<Participant1> otherParticipants(Patient p)
	{
		List<Relationship> relationShips= Context.getPersonService().getRelationshipsByPerson(p);
		List<Participant1> participantList = new ArrayList<Participant1>(relationShips.size());
		for (int i = 0; i<relationShips.size();i++) {
		Participant1 e = CDAFactory.eINSTANCE.createParticipant1();

		e.setTypeCode(ParticipationType.IND);
		II pid1 = DatatypesFactory.eINSTANCE.createII();
		pid1.setRoot("1.3.6.1.4.1.19376.1.5.3.1.2.4");

		e.getTemplateIds().add(pid1);

		IVL_TS time = DatatypesFactory.eINSTANCE.createIVL_TS();
		time.setHigh(time.getHigh());
		time.setLow(time.getLow());
		e.setTime(time);
		
		Relationship relationship = relationShips.get(i);
		AssociatedEntity patientRelationShip = CDAFactory.eINSTANCE.createAssociatedEntity();
		patientRelationShip.setClassCode(RoleClassAssociative.PRS);
		CE relationShipCode = DatatypesFactory.eINSTANCE.createCE();
		relationShipCode.setCodeSystemName("RoleCode");
		relationShipCode.setCodeSystem("2.16.840.1.113883.5.111");
		Person associatedPerson = CDAFactory.eINSTANCE.createPerson();
		PN associatedPersonName = DatatypesFactory.eINSTANCE.createPN();
		Iterator<PersonAddress> patientAddressIterator = null;
		            TEL associatedPersonTelecon=DatatypesFactory.eINSTANCE.createTEL();
		            associatedPersonTelecon.setNullFlavor(NullFlavor.UNK);
		        
		switch (relationship.getRelationshipType().getId()) 
		{
		case 1:

		relationShipCode.setDisplayName("Doctor");
		associatedPersonName.addFamily(relationship.getPersonA().getFamilyName());
		associatedPersonName.addGiven(relationship.getPersonA().getGivenName());
		patientAddressIterator = relationship.getPersonA().getAddresses().iterator();
		relationShipCode.setCode("DOCTOR");
		break;
		case 2:

		relationShipCode.setDisplayName("Sibling");
		associatedPersonName.addFamily(relationship.getPersonA().getFamilyName());
		associatedPersonName.addGiven(relationship.getPersonA().getGivenName());
		patientAddressIterator = relationship.getPersonA().getAddresses().iterator();
		relationShipCode.setCode("SIBLING");
		break;
		case 3:
		if(p.getId() == relationship.getPersonA().getId())
		{
		relationShipCode.setDisplayName("Child");
		associatedPersonName.addFamily(relationship.getPersonB().getFamilyName());
		associatedPersonName.addGiven(relationship.getPersonB().getGivenName());
		patientAddressIterator = relationship.getPersonB().getAddresses().iterator();
		relationShipCode.setCode("CHILD");
		}
		else
		{
		relationShipCode.setDisplayName("Parent");
		associatedPersonName.addFamily(relationship.getPersonA().getFamilyName());
		associatedPersonName.addGiven(relationship.getPersonA().getGivenName());
		patientAddressIterator = relationship.getPersonA().getAddresses().iterator();
		relationShipCode.setCode("PARENT");
		}
		break;
		case 4:
		if(p.getId() == relationship.getPersonA().getId())
		{
		if(relationship.getPersonB().getGender().equalsIgnoreCase("M"))
		{
		relationShipCode.setDisplayName("Nephew");
		associatedPersonName.addFamily(relationship.getPersonB().getFamilyName());
		associatedPersonName.addGiven(relationship.getPersonB().getGivenName());
		patientAddressIterator = relationship.getPersonB().getAddresses().iterator();
		relationShipCode.setCode("NEPHEW");
		}
		else
		{
		relationShipCode.setDisplayName("Neice");
		associatedPersonName.addFamily(relationship.getPersonB().getFamilyName());
		associatedPersonName.addGiven(relationship.getPersonB().getGivenName());
		patientAddressIterator = relationship.getPersonB().getAddresses().iterator();
		relationShipCode.setCode("NIECE");
		}
		
		}
		else
		{
		if(relationship.getPersonA().getGender().equalsIgnoreCase("M"))
		{
		relationShipCode.setDisplayName("Uncle");
		associatedPersonName.addFamily(relationship.getPersonB().getFamilyName());
		associatedPersonName.addGiven(relationship.getPersonB().getGivenName());
		patientAddressIterator = relationship.getPersonB().getAddresses().iterator();
		relationShipCode.setCode("UNCLE");
		}
		else
		{
		relationShipCode.setDisplayName("Aunt");
		associatedPersonName.addFamily(relationship.getPersonB().getFamilyName());
		associatedPersonName.addGiven(relationship.getPersonB().getGivenName());
		patientAddressIterator = relationship.getPersonB().getAddresses().iterator();
		relationShipCode.setCode("AUNT");
		}
		}	

		break;
		case 5:
			relationShipCode.setDisplayName("Spouse");
			associatedPersonName.addFamily(relationship.getPersonA().getFamilyName());
			associatedPersonName.addGiven(relationship.getPersonA().getGivenName());
			patientAddressIterator = relationship.getPersonB().getAddresses().iterator();
			relationShipCode.setCode("Spouse");
			break;
		
		}

		patientRelationShip.setCode(relationShipCode);
		AD associatedPersonAddress = DatatypesFactory.eINSTANCE.createAD();
 
		if(patientAddressIterator.hasNext())
		{
		PersonAddress padd = patientAddressIterator.next();
		associatedPersonAddress.addStreetAddressLine(padd.getAddress1()+" "+padd.getAddress2());
		associatedPersonAddress.addCity(padd.getCityVillage());
		associatedPersonAddress.addCountry(padd.getCountry());
		}

		patientRelationShip.getAddrs().add(associatedPersonAddress);
		patientRelationShip.getTelecoms().add(associatedPersonTelecon);
		associatedPerson.getNames().add(associatedPersonName );
		patientRelationShip.setAssociatedPerson(associatedPerson );
		e.setAssociatedEntity(patientRelationShip);
		participantList.add(e);

	 }
		 return participantList;
  }
}