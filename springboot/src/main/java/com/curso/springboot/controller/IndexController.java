package com.curso.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String home(ModelMap model) {
		
		model.addAttribute("saudacao", "Diogo");
		
		return "index";
	}
	
}
