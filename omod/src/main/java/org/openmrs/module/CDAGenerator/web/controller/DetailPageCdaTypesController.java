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

package org.openmrs.module.CDAGenerator.web.controller;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.CDAHandlers.BaseCdaTypeHandler;
import org.openmrs.module.CDAGenerator.SectionHandlers.BaseCdaSectionHandler;
import org.openmrs.module.CDAGenerator.api.CDAGeneratorService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The main controller.
 */

@Controller
public class DetailPageCdaTypesController {
			
			protected final Log log = LogFactory.getLog(getClass());
			
			@RequestMapping(value = "/module/CDAGenerator/detailPagecdatypes", method = RequestMethod.GET)
			public void manage(@RequestParam(required=false, value="templateid") String id,
		ModelMap model) {
				
				List<BaseCdaTypeHandler> singleCdaType=null;
				CDAGeneratorService cdaser=(CDAGeneratorService)Context.getService(CDAGeneratorService.class);
				singleCdaType=cdaser.getAllCdaTypeChildHandlers();
				 for(int i=0;i<singleCdaType.size();i++)
				    {
				    	BaseCdaTypeHandler b=singleCdaType.get(i);
				    	if(b.templateid.equals(id))
				    	{
				    				    	model.addAttribute("detailcda", b);
				    	}
				    }
				
			}
			
		/*	@RequestMapping(value = "/module/CDAGenerator/savecdatypes")
			public void save(@ModelAttribute(value="detailcda")BaseCdaTypeHandler bct,ModelMap model) {
				
				model.addAttribute("detailcda", bct);
				
			}*/
			
		}

