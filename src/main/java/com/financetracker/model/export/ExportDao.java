package com.financetracker.model.export;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletOutputStream;

import org.springframework.stereotype.Component;

import com.financetracker.database.DBConnection;
import com.financetracker.model.users.User;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class ExportDao implements IExportDao {
	
	private static final String ALL_TRANSACTIONS_TRANS_FOR_USER = "SELECT t.`payee/payer` as Payee, t.amount as Amount, t.date_paid as DatePaid, t.accounts_id as AccountID, t.categories_id as CategoriesId, t.is_income as IsIncome FROM transactions t JOIN accounts a ON (a.id=t.accounts_id) JOIN users u ON (u.id=a.user_id) WHERE u.id=?;";

	@Override
	public void exportIntoPdf(ServletOutputStream os, User user) {
		try {
			Document doc = new Document();
			Font bfBold18 = new Font(FontFamily.TIMES_ROMAN, 18, Font.BOLD, new BaseColor(0, 0, 0));
			Font bf12 = new Font(FontFamily.TIMES_ROMAN, 12);

			PdfWriter.getInstance(doc, os);

			doc.setPageSize(PageSize.LETTER);
			doc.open();

			// add a new paragraph
			doc.add(new Paragraph(user.getFirstName() + " " + user.getLastName() + " transaction history", bfBold18));

			PreparedStatement pr = DBConnection.getInstance().getConnection()
					.prepareStatement(ALL_TRANSACTIONS_TRANS_FOR_USER);
			pr.setInt(1, user.getId());
			ResultSet rs = pr.executeQuery();

			rs.first();
			while (rs.next()) {
				doc.add(new Paragraph(System.lineSeparator()));
				doc.add(new Paragraph("--------------------------------"));
				doc.add(new Paragraph("Account ID: " + rs.getInt("AccountID"), bf12));
				doc.add(new Paragraph("Payee/Payer: " + rs.getString("Payee").trim(), bf12));
				doc.add(new Paragraph("Amount of transaction: " + rs.getDouble("Amount"), bf12));
				doc.add(new Paragraph("Date paid : " + rs.getTimestamp("DatePaid").toLocalDateTime().toLocalDate().toString().trim(), bf12));
				doc.add(new Paragraph("Category Id: " + rs.getInt("CategoriesId"), bf12));
				doc.add(new Paragraph("Is Income: " + rs.getInt("IsIncome"), bf12));
			}
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
