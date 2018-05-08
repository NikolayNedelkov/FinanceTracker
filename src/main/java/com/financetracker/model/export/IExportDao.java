package com.financetracker.model.export;

import javax.servlet.ServletOutputStream;

public interface IExportDao {

	void exportIntoPdf(ServletOutputStream os);

}
