package org.openmrs.module.CDAGenerator.web.controller;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.CDAGenerator.SectionHandlers.BaseCdaSectionHandler;
import org.openmrs.module.CDAGenerator.api.CDAGeneratorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CdaSectionDetailPageController 
{
	@RequestMapping(value = "/module/CDAGenerator/CdaSectionDetailPage", method = RequestMethod.GET)
	public void manage(@RequestParam(required=false, value="templateid") String id,
ModelMap model) {
		
		List<BaseCdaSectionHandler> singleCdasection=null;
		CDAGeneratorService cdaser=(CDAGeneratorService)Context.getService(CDAGeneratorService.class);
		singleCdasection=cdaser.getAllCdaSectionHandlers();
		 for(int i=0;i<singleCdasection.size();i++)
		    {
		    	BaseCdaSectionHandler b=singleCdasection.get(i);
		    	if(b.templateid.equals(id))
		    	{
		    				    	model.addAttribute("sectionDetails", b);
		    	}
		    }
		
	}
}
