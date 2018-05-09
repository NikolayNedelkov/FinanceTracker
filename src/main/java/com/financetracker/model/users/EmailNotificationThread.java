package com.financetracker.model.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.financetracker.exceptions.UserException;
import com.financetracker.model.transactions.PlannedTransaction;
import com.financetracker.util.EmailSender;

@Component
public class EmailNotificationThread extends Thread {

	private static final int TWENTYFOUR_HOURS_PERIOD = 86400000;
	public static final String SUBJECT_TEXT_FT = "Finance Tracker ";
	public static final String NOT_LOGGED_IN_TEXT = "Hello partner,you haven't logged in a long time. You can start managing your finances now. Go to http://localhost:8080/FinalProject/";

	@Autowired
	UserDAO userDAO;

	public EmailNotificationThread() {
		System.out.println("Email notification thread started");
		this.setDaemon(true);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(TWENTYFOUR_HOURS_PERIOD);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
				return;
			}
			try {
				List<String> allOldEmails = userDAO.getAllUsersOldEmails();
				for (String email : allOldEmails) {
					EmailSender.sendSimpleEmail(email, SUBJECT_TEXT_FT, NOT_LOGGED_IN_TEXT);
				}
			} catch (UserException e) {
				e.printStackTrace();
			}
		}
	}
}
