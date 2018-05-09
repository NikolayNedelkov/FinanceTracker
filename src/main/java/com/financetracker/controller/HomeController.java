package com.financetracker.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financetracker.model.transactions.PlannedTransactionThread;
import com.financetracker.model.users.EmailNotificationThread;

@Controller
@RequestMapping(value = "/home")
public class HomeController {
	
	@Autowired
	private EmailNotificationThread emialNotificationThread;
	
	@RequestMapping(method = RequestMethod.GET)
	public String goHome(HttpServletRequest request, HttpServletResponse response, HttpSession session)  {
		if (!emialNotificationThread.isAlive()) {
			emialNotificationThread.start();
		}
		if ((session == null) || (session.getAttribute("user") == null)) {
			return "redirect:/";
		}
		return "home";
	}

}
