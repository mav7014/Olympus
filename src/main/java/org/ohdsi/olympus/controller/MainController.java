package org.ohdsi.olympus.controller;

import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ohdsi.olympus.model.Launchable;
import org.ohdsi.olympus.view.factory.CommonTemplateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

@Controller
@RequestMapping({"/home", "/"})
public class MainController {
	
	private static final String HOME_TEMPLATE_NAME = "home/index";
	private static final String CONFIGURATION_TEMPLATE_NAME = "home/config";
	
	private static final String ERROR_TEMPLATE_NAME = "templates/error";
	
	@Autowired
	CommonTemplateFactory templateFactory;
	
	@RequestMapping(value = "index")
	public ModelAndView handleIndexRequest(final HttpServletRequest request, final HttpServletResponse res,
			final HttpSession session) throws Exception {
		
		ModelAndView modelAndView = templateFactory.createMasterView(HOME_TEMPLATE_NAME, null);
		
		// load up launcher links
		URL url = Resources.getResource("launchable-links.json");
		String text = Resources.toString(url, Charsets.UTF_8);
		
		ObjectMapper mapper = new ObjectMapper();
		List<Launchable> myObjects = mapper.readValue(text, new TypeReference<List<Launchable>>(){});
		modelAndView.addObject("launchableLinks", myObjects);
		
		return modelAndView;
		
	}
	
	@RequestMapping(value = "config")
	public ModelAndView handleConfigurationRequest(final HttpServletRequest request, final HttpServletResponse res,
			final HttpSession session) throws Exception {
		ModelAndView modelAndView = templateFactory.createMasterView(CONFIGURATION_TEMPLATE_NAME, null);
		
		return modelAndView;
		
	}
	
	/**
	 * We handle error a litle differently because it's expected by SpringBoot to be mapped to /error in the
	 * templates section
	 * 
	 * @param request
	 * @param res
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "error")
	public ModelAndView handleErrorRequest(final HttpServletRequest request, final HttpServletResponse res,
			final HttpSession session) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView(ERROR_TEMPLATE_NAME);
		return modelAndView;
		
	}
}