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
package org.openmrs.module.CDAGenerator.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openhealthtools.mdht.uml.cda.CDAFactory;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openmrs.Patient;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.CDAGenerator.CDAHandlers.BaseCdaTypeHandler;
import org.openmrs.module.CDAGenerator.SectionHandlers.BaseCdaSectionHandler;
import org.openmrs.module.CDAGenerator.api.CDAGeneratorService;
import org.openmrs.module.CDAGenerator.api.CdaHeaderBuilder;
import org.openmrs.module.CDAGenerator.api.db.CDAGeneratorDAO;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

/**
 * It is a default implementation of {@link CDAGeneratorService}.
 */
public class CDAGeneratorServiceImpl extends BaseOpenmrsService implements CDAGeneratorService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private CDAGeneratorDAO dao;
	
	/**
     * @param dao the dao to set
     */
    public void setDao(CDAGeneratorDAO dao) {
	    this.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public CDAGeneratorDAO getDao() {
	    return dao;
    }

	@Override
	public List<BaseCdaTypeHandler> getAllCdaTypeChildHandlers() {
		
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
		
		provider.addIncludeFilter(new AssignableTypeFilter(BaseCdaTypeHandler.class));
		
		List<BaseCdaTypeHandler> handlers = new ArrayList<BaseCdaTypeHandler>();
		
		// scan in org.openmrs.module.CDAGenerator.CDAHandlers package
		Set<BeanDefinition> components = provider.findCandidateComponents("org.openmrs.module.CDAGenerator.CDAHandlers");
		
			for (BeanDefinition component : components)
			{
			try {
				
				
				Class cls = Class.forName(component.getBeanClassName());
			
				BaseCdaTypeHandler p = (BaseCdaTypeHandler) cls.newInstance();
				if(p.templateid!=null)
				{
				handlers.add(p);
				}
			
			}
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}
			catch (InstantiationException e) 
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e) 
			{
				e.printStackTrace();
			}
		}
		return handlers;
	}

	@Override
	public List<BaseCdaSectionHandler> getAllCdaSectionHandlers() {
ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
		
		provider.addIncludeFilter(new AssignableTypeFilter(BaseCdaSectionHandler.class));
		
		List<BaseCdaSectionHandler> sectionHandlers = new ArrayList<BaseCdaSectionHandler>();
		
		// scan in org.openmrs.module.CDAGenerator.Sectionhandlers package
				Set<BeanDefinition> components = provider.findCandidateComponents("org.openmrs.module.CDAGenerator.SectionHandlers");
				
					for (BeanDefinition component : components)
					{
					try {
						
						
						Class cls = Class.forName(component.getBeanClassName());
					
						BaseCdaSectionHandler p = (BaseCdaSectionHandler) cls.newInstance();
						if(p.templateid!=null)
						{
						sectionHandlers.add(p);
						}
						
					
					}
					catch (ClassNotFoundException e) 
					{
						e.printStackTrace();
					}
					catch (InstantiationException e) 
					{
						e.printStackTrace();
					}
					catch (IllegalAccessException e) 
					{
						e.printStackTrace();
					}
				}
				return sectionHandlers;
	}

	@Override
	public ClinicalDocument produceCDA(Patient p, BaseCdaTypeHandler bh) 
	{
		ClinicalDocument doc = CDAFactory.eINSTANCE.createClinicalDocument();
		CdaHeaderBuilder header=new CdaHeaderBuilder();
		doc=header.buildHeader(doc, bh, p);
		return doc;
	}
	
}