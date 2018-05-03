package com.financetracker.controller;



import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financetracker.exceptions.UserException;
import com.financetracker.model.users.User;
import com.financetracker.model.users.UserDAO;

@Controller
public class UserController {

	@Autowired
	private UserDAO userDAO;

	
	@RequestMapping(method = RequestMethod.GET, value="/index")
	private String startPage(){
		return "signup-login";
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	private String login(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		try {
			if (userDAO.login(email, password)) {
				
				User user = userDAO.getUserByEmail(email);
				session.setAttribute("user", user);

				return "redirect:/home";
			} else {
				return "signup-login";
			}
		} catch (UserException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/register")
	private String register(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User user = null;
		user = new User(email, password, firstName, lastName);
		try {
			if (userDAO.register(user) > 0) {
				return "signup-login";
			} else {
				return "signup-login";
			}
		} catch (UserException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/logout")
	private String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/myProfile")
	private String myProfile(HttpServletRequest request, HttpServletResponse response) {
		return "myProfile";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/changePassword")
	private String changePassword() {
		return "updatePassword";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/changePassword")
	private String changePassword(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String newPass = request.getParameter("newPass");
		String newPass2 = request.getParameter("newPass2");
		String currPass = request.getParameter("currPass");
		int userId = ((User)session.getAttribute("user")).getId();
		
		try {
			userDAO.changePassword(userId, currPass, newPass, newPass2);
			return "redirect:/home";
		} catch (UserException e) {
			return "redirect:/myProfile";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/forgotPassword")
	private String forgotPassword(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		return "forgotPassword";
	}
}

