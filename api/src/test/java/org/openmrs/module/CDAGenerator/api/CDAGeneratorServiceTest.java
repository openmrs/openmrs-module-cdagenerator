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

import org.eclipse.emf.common.util.Diagnostic;
import org.junit.Test;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.util.BasicValidationHandler;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.CDAHandlers.BaseCdaTypeHandler;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Tests {@link ${CDAGeneratorService}}.
 */
public class  CDAGeneratorServiceTest extends BaseModuleContextSensitiveTest {
	
	@Test
	public void shouldSetupContext() 
	{
		assertNotNull(Context.getService(CDAGeneratorService.class));
	}
}
