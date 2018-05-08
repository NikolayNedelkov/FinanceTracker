package com.financetracker.controller;

import java.sql.SQLException;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financetracker.exceptions.AccountException;
import com.financetracker.exceptions.UserException;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.accounts.AccountDAO;
import com.financetracker.model.users.IUserDAO;
import com.financetracker.model.users.User;
import com.financetracker.util.EmailSender;

@Controller
public class UserController {

	public static final String SUBJECT_TEXT_FORGOTTEN_PASSWORD = "Finance Tracker FORGOTTEN PASSWORD";
	public static final String FORGOTTEN_PASSWORD_EMAIL_TEXT = "Hello %s Click on the link to change password: http://localhost:8080/FinalProject/resetPassword/%s";
	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private AccountDAO accountDao;

	@RequestMapping(method = RequestMethod.GET, value = "/index")
	private String startPage() {
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
				HashSet<Account> userAccounts = accountDao.getAllAccountsForUser(user);
				session.setAttribute("user", user);
				user.setAccounts(userAccounts);
				return "redirect:/home";
			} else {
				return "signup-login";
			}
		} catch (UserException | ClassNotFoundException | SQLException | AccountException e) {
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
		int userId = ((User) session.getAttribute("user")).getId();

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

	@RequestMapping(value = "/forgottenPassword", method = RequestMethod.POST)
	public String sendEmail(HttpServletRequest request, Model model) throws UserException {
		String email = request.getParameter("email");
		if (userDAO.getUserByEmail(email) != null) {
			User user = userDAO.getUserByEmail(email);
			EmailSender.sendSimpleEmail(email, SUBJECT_TEXT_FORGOTTEN_PASSWORD,
					String.format(FORGOTTEN_PASSWORD_EMAIL_TEXT, user.getFirstName(), user.getPasswordToken()));
		} else {
			model.addAttribute("forgotPassword", "Invalid email");
		}
		return "forgotPassword";
	}

	@RequestMapping(value = "/resetPassword/{passwordToken}", method = RequestMethod.GET)
	public String resetPassword(Model model, @PathVariable("passwordToken") String passwordToken) {
		model.addAttribute("token", passwordToken);
		return "resetPassword";
	}

	@RequestMapping(value = "/resetPassword/{passwordToken}", method = RequestMethod.POST)
	public String getNewPassword(HttpServletRequest request, Model model,
			@PathVariable("passwordToken") String passwordToken) throws UserException {
		String password = request.getParameter("password");
		String repeatPassword = request.getParameter("repeatPassword");
		System.out.println(passwordToken);

		if (userDAO.checkIfUserExistsByToken(passwordToken) && password.equals(repeatPassword)) {
			User user = userDAO.getUserByToken(passwordToken);
			userDAO.changePasswordByToken(user, password);
		} else {
			model.addAttribute("ressetPassword", "Some message");
			return "resetPassword";
		}
		User user = new User();
		model.addAttribute("user", user);
		return "redirect:/";
	}
}
