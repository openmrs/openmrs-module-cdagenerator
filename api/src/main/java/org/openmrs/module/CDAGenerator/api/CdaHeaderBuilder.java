package org.openmrs.module.CDAGenerator.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openhealthtools.mdht.uml.cda.AssignedAuthor;
import org.openhealthtools.mdht.uml.cda.AssignedCustodian;
import org.openhealthtools.mdht.uml.cda.AssignedEntity;
import org.openhealthtools.mdht.uml.cda.AssociatedEntity;
import org.openhealthtools.mdht.uml.cda.Author;
import org.openhealthtools.mdht.uml.cda.AuthoringDevice;
import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.Custodian;
import org.openhealthtools.mdht.uml.cda.CustodianOrganization;
import org.openhealthtools.mdht.uml.cda.DocumentationOf;
import org.openhealthtools.mdht.uml.cda.InfrastructureRootTypeId;
import org.openhealthtools.mdht.uml.cda.Organization;
import org.openhealthtools.mdht.uml.cda.Participant1;
import org.openhealthtools.mdht.uml.cda.PatientRole;
import org.openhealthtools.mdht.uml.cda.Performer1;
import org.openhealthtools.mdht.uml.cda.Person;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.ServiceEvent;
import org.openhealthtools.mdht.uml.hl7.datatypes.AD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CE;
import org.openhealthtools.mdht.uml.hl7.datatypes.CS;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVL_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVXB_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.ON;
import org.openhealthtools.mdht.uml.hl7.datatypes.PN;
import org.openhealthtools.mdht.uml.hl7.datatypes.SC;
import org.openhealthtools.mdht.uml.hl7.datatypes.ST;
import org.openhealthtools.mdht.uml.hl7.datatypes.TEL;
import org.openhealthtools.mdht.uml.hl7.datatypes.TS;
import org.openhealthtools.mdht.uml.hl7.vocab.ActClassRoot;
import org.openhealthtools.mdht.uml.hl7.vocab.NullFlavor;
import org.openhealthtools.mdht.uml.hl7.vocab.ParticipationType;
import org.openhealthtools.mdht.uml.hl7.vocab.RoleClassAssociative;
import org.openhealthtools.mdht.uml.hl7.vocab.x_ServiceEventPerformer;
import org.openmrs.Patient;
import org.openmrs.PersonAddress;
import org.openmrs.Relationship;
import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.CDAHandlers.BaseCdaTypeHandler;

public class CdaHeaderBuilder 
{
	
	public ClinicalDocument buildHeader(ClinicalDocument doc,BaseCdaTypeHandler bh,Patient p)
	{
				
		InfrastructureRootTypeId typeId = CDAFactory.eINSTANCE.createInfrastructureRootTypeId();
		typeId.setExtension("POCD_HD000040");/*fixed*/
		typeId.setRoot("2.16.840.1.113883.1.3");
		doc.setTypeId(typeId);
		
		doc.getTemplateIds().clear();
		doc.getTemplateIds().add(buildTemplateID("2.16.840.1.113883.10","IMPL_CDAR2_LEVEL1",null));
		doc.getTemplateIds().add(buildTemplateID("2.16.840.1.113883.10.20.3",null,null));
		doc.getTemplateIds().add(buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.1.1",null,null));//Medical Documents 
		doc.getTemplateIds().add(buildTemplateID("1.3.6.1.4.1.19376.1.5.3.1.1.2",null,null));//Medical Summary
		doc.getTemplateIds().add(buildTemplateID(bh.templateid,null,null));//
		
		
		doc.setId(buildID(Context.getAdministrationService().getImplementationId().getImplementationId(),bh.documentShortName));//need to generate dynamically
		
		doc.setCode(buildCodeCE("34117-2","2.16.840.1.113883.6.1",null,"LOINC"));//need to generate dynamically template id according loinc code system if we choose snomed then it would be diffrent
		
		doc.setTitle(buildTitle(bh.documentFullName));
		
		Date d = new Date();
		doc.setEffectiveTime(buildEffectiveTime(d));
		
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
		patientRole.getIds().add(buildID(Context.getAdministrationService().getImplementationId().getImplementationId(),
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
		providerOrganization.getIds().add(buildID(Context.getAdministrationService().getImplementationId().getImplementationId(),null));
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
		author.setTime(buildEffectiveTime(new Date()));
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
		representedOrganization.getIds().add(buildID("2.16.840.1.113883.19.5",null));
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
	
	serviceEvent.setEffectiveTime(buildEffectiveTimeinIVL(new Date(),new Date()));
	Performer1 performer=CDAFactory.eINSTANCE.createPerformer1();
	
	performer.setTypeCode(x_ServiceEventPerformer.PPRF);
	performer.setFunctionCode(buildCodeCE("PCP","2.16.840.1.113883.5.88",null,null));
	performer.setTime(buildEffectiveTimeinIVL(new Date(),new Date()));
	
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

		
		
		
		doc=buildSection(doc);		
	return doc;
	}
	public IVL_TS  buildEffectiveTimeinIVL(Date d , Date d1)
	{
		IVL_TS effectiveTime = DatatypesFactory.eINSTANCE.createIVL_TS();
		SimpleDateFormat s = new SimpleDateFormat("yyyyMMddhhmmss");
		String creationDate = s.format(d);
		IVXB_TS low = DatatypesFactory.eINSTANCE.createIVXB_TS();
		low.setValue(creationDate);
		effectiveTime.setLow(low);
		IVXB_TS high = DatatypesFactory.eINSTANCE.createIVXB_TS();
		if(d1 != null)
			high.setValue(s.format(d1));
		effectiveTime.setHigh(high);
		return effectiveTime;
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
	public   II buildTemplateID(String root , String extension ,String assigningAuthorityName)
	{

			II templateID = DatatypesFactory.eINSTANCE.createII();
			if(root!=null)
			{
			templateID.setRoot(root);
			}
			if(extension!=null)
			{
			templateID.setExtension(extension);
			}
			if(assigningAuthorityName!=null)
			{
			templateID.setAssigningAuthorityName(assigningAuthorityName);
			}
			
			return templateID;

	}
	public ST buildTitle(String title)
	{
		ST displayTitle = DatatypesFactory.eINSTANCE.createST();
		displayTitle.addText(title);
		return displayTitle;
	}

	public II buildID(String root , String extension)
	{
		II id = DatatypesFactory.eINSTANCE.createII();
		if(root!=null)
		{
		id.setRoot(root);
		}
		if(extension!=null)
		{
		id.setExtension(extension);
		}
		return id;
		
	}
	
	public CE buildCodeCE(String code , String codeSystem, String displayString, String codeSystemName)
	{
		CE e = DatatypesFactory.eINSTANCE.createCE();
		if(code!=null)
		{
		e.setCode(code);
		}
		if(codeSystem!=null)
		{
		e.setCodeSystem(codeSystem);
		}
		if(displayString!=null)
		{
		e.setDisplayName(displayString);
		}
		if(displayString!=null)
		{
		e.setCodeSystemName(codeSystemName);
		}
		return e;

	}
	public  TS buildEffectiveTime(Date d)
	{
		TS effectiveTime = DatatypesFactory.eINSTANCE.createTS();
		SimpleDateFormat s = new SimpleDateFormat("yyyyMMddhhmmss");
		
		String creationDate = s.format(d);
	
		effectiveTime.setValue(creationDate);
		
		return effectiveTime;
	}
	
	public  ClinicalDocument buildSection(ClinicalDocument cd)
	{
		Section section=CDAFactory.eINSTANCE.createSection();
		section.setId(buildID("2.2.2.2.22222.2.2",null));
		cd.addSection(section);
		return cd;
	}
}
