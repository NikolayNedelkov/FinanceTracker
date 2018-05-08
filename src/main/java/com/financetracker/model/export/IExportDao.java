package com.financetracker.model.export;

import javax.servlet.ServletOutputStream;

import com.financetracker.model.users.User;

public interface IExportDao {


	void exportIntoPdf(ServletOutputStream os, User user);

}
