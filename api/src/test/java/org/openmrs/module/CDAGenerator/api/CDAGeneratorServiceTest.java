/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.CDAGenerator.api;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openmrs.Concept;
import org.openmrs.ConceptMap;
import org.openmrs.ConceptMapType;
import org.openmrs.ConceptName;
import org.openmrs.ConceptReferenceTerm;
import org.openmrs.ConceptSource;
import org.openmrs.ImplementationId;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PersonAddress;
import org.openmrs.PersonName;
import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.CDAHandlers.BaseCdaTypeHandler;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Tests {@link ${CDAGeneratorService}}.
 */
public class  CDAGeneratorServiceTest extends BaseModuleContextSensitiveTest 
{
	
	@Test
	public void shouldSetupContext() 
	{
		assertNotNull(Context.getService(CDAGeneratorService.class));
	}
	
	@Test
	public void shouldGetNullwhenServiceMethodisCalledwithNullParameters() throws Exception 
	 {
	     CDAGeneratorService service=Context.getService(CDAGeneratorService.class);
	     ClinicalDocument document = service.produceCDA(null, null);
	     Assert.assertNull(document);
	 }
	
	@Test
	public void shouldGetNullWhenPatientorCdaTypeisNull() throws Exception 
	 {
	     CDAGeneratorService service=Context.getService(CDAGeneratorService.class);
	     ClinicalDocument document1 = service.produceCDA(new Patient(), null);
	     ClinicalDocument document2=service.produceCDA(null, new BaseCdaTypeHandler());
	     Assert.assertNull(document1);
	     Assert.assertNull(document2);
	 }
}