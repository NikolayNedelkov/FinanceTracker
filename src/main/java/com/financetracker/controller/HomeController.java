package com.financetracker.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/home")
public class HomeController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String goHome(HttpServletRequest request, HttpServletResponse response, HttpSession session)  {
		if ((session == null) || (session.getAttribute("user") == null)) {
			return "redirect:/";
		}
		return "home";
	}

}
