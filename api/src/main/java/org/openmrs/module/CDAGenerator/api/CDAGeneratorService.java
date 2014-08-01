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

import java.util.List;

import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openmrs.Patient;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.CDAGenerator.CDAHandlers.BaseCdaTypeHandler;
import org.openmrs.module.CDAGenerator.SectionHandlers.BaseCdaSectionHandler;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service exposes module's core functionality. It is a Spring managed bean which is configured in moduleApplicationContext.xml.
 * <p>
 * It can be accessed only via Context:<br>
 * <code>
 * Context.getService(CDAGeneratorService.class).someMethod();
 * </code>
 * 
 * @see org.openmrs.api.context.Context
 */
@Transactional
public interface CDAGeneratorService extends OpenmrsService {
     
	/**
      * Gets all child handlers (Profiles like APHP,APS,e.t.c)
      *
      * @return List of Child handlers or empty list
      * @should return list of Child handlers that extends BaseCdaTypeHandler,if exists
      * @should return empty list if does not exist
      */
		public List<BaseCdaTypeHandler> getAllCdaTypeChildHandlers();
		
		/**
	      * Gets all section child handlers (Sections like Chief Complaint,Vital signs,e.t.c)
	      *
	      * @return List of section Child handlers or empty list
	      * @should return list of Child handlers that extends BaseCdaSectionHandler,if exists
	      * @should return empty list if does not exist
	      */
        public List<BaseCdaSectionHandler> getAllCdaSectionHandlers();
        
        /**
	      * Create or Generate CDA Document
	      *
	      * @param patient
	      * @param CdaProfileType
	      * 
	      * @return a Clinical Document(CDA) or Report errors
	      * @should return a Clinical Document(CDA),if document passes validation
	      * @should return errors in CDA document
	      */
        public ClinicalDocument produceCDA(Patient patient,BaseCdaTypeHandler CdaProfileType);
        
	
	
}