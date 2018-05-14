package com.financetracker.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.financetracker.SpringWebConfig;
import com.financetracker.exceptions.UserException;
import com.financetracker.model.users.User;
import com.financetracker.model.users.UserDAO;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserLoginTest {
	
	@Autowired
	UserDAO userDao;

	@Test(expected=UserException.class)
	public void testBadPassword() throws ClassNotFoundException, UserException, SQLException {
		User u = new User("niki@abv.bg", "5555");
		Boolean checkLogin = userDao.login(u.getEmail(), u.getPassword());
		
		assertThat(checkLogin,is(false));
	}
	
	@Test(expected=UserException.class)
	public void testBadUsername() throws ClassNotFoundException, UserException, SQLException {
		User u = new User("niky@abv.bg", "123");
		userDao.login(u.getEmail(), u.getPassword());
	}
	
	@Test
	public void testSuccesLogin() throws UserException, ClassNotFoundException, SQLException {
		User u = new User("niki@abv.bg", "123");
		userDao.login(u.getEmail(), u.getPassword());
	}

}
