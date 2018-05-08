package com.financetracker.controller;

import java.rmi.server.ExportException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financetracker.model.export.IExportDao;
import com.financetracker.model.users.IUserDAO;
import com.financetracker.model.users.User;

@Controller
@RequestMapping(value = "/export")
public class ExportController {
	private static final String NOT_LOGGED_MESSAGE = "You must logged in if you want download pdf with tasks!";
	
	@Autowired
	private IExportDao exportDao;
	@Autowired
	private IUserDAO userDao;
	

	@RequestMapping(method = RequestMethod.GET, value = "/pdf")
	public String exportTasksIntoPdf(HttpServletResponse response, HttpSession session, Model model)  {
		try {
			User loggedUser = this.userDao.getLoggedUser(session);
			if (loggedUser == null) {
				throw new ExportException(NOT_LOGGED_MESSAGE);
			}
			
			ServletOutputStream os = response.getOutputStream();
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + "allTasks.pdf" + "\"");
			this.exportDao.exportIntoPdf(os);

			return "";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", e);
			return "error";
		}
	}
}