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
	/*
	 * private static final String SELECT_ALL_INFO_FROM_TASKS_QUERY =
	 * "SELECT t.summary, t.due_date, t.start_date, t.description, proj.name AS project_name, pr.type AS priority , s.type AS state , i.type AS issue_type, cr.full_name AS creator, assignee.full_name AS assignee "
	 * + "      FROM tasks AS t " + "INNER JOIN projects AS p " +
	 * "        ON t.project_id = p.id " + "INNER JOIN priorities AS pr " +
	 * "        ON pr.id = t.priority_id " + "INNER JOIN states AS s " +
	 * "		ON s.id = t.state_id " + "INNER JOIN issues AS i " +
	 * "        ON i.id = t.issue_id " + "INNER JOIN projects AS proj " +
	 * "        ON proj.id = t.project_id " + "INNER JOIN users AS cr " +
	 * "        ON cr.id = t.creator_id " + "INNER JOIN users AS assignee " +
	 * "        ON assignee.id = t.assignee_id " +
	 * "     WHERE t.is_deleted = 0 AND p.is_deleted = 0 " + "ORDER BY t.due_date;";
	 */
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
				doc.add(new Paragraph("Date paid : " + rs.getTimestamp("DatePaid").toLocalDateTime().toString().trim(), bf12));
				doc.add(new Paragraph("Category Id: " + rs.getInt("CategoriesId"), bf12));
				doc.add(new Paragraph("Is Income: " + rs.getInt("IsIncome"), bf12));
			}
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
