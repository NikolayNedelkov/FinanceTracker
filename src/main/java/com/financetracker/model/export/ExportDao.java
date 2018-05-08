package com.financetracker.model.export;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletOutputStream;

import org.springframework.stereotype.Component;

import com.financetracker.database.DBConnection;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class ExportDao implements IExportDao{
	private static final String SELECT_ALL_INFO_FROM_TASKS_QUERY = "SELECT t.summary, t.due_date, t.start_date, t.description, proj.name AS project_name, pr.type AS priority , s.type AS state , i.type AS issue_type, cr.full_name AS creator, assignee.full_name AS assignee "
			+ "      FROM tasks AS t " + "INNER JOIN projects AS p " + "        ON t.project_id = p.id "
			+ "INNER JOIN priorities AS pr " + "        ON pr.id = t.priority_id " + "INNER JOIN states AS s "
			+ "		ON s.id = t.state_id " + "INNER JOIN issues AS i " + "        ON i.id = t.issue_id "
			+ "INNER JOIN projects AS proj " + "        ON proj.id = t.project_id " + "INNER JOIN users AS cr "
			+ "        ON cr.id = t.creator_id " + "INNER JOIN users AS assignee "
			+ "        ON assignee.id = t.assignee_id " + "     WHERE t.is_deleted = 0 AND p.is_deleted = 0 "
			+ "ORDER BY t.due_date;";
	
	@Override
	public void exportIntoPdf(ServletOutputStream os) {
		try {
			Document doc = new Document();
			Font bfBold18 = new Font(FontFamily.TIMES_ROMAN, 18, Font.BOLD, new BaseColor(0, 0, 0));
			Font bf12 = new Font(FontFamily.TIMES_ROMAN, 12);

			PdfWriter.getInstance(doc, os);

			doc.setPageSize(PageSize.LETTER);
			doc.open();

			// add a new paragraph
			doc.add(new Paragraph("All tasks...", bfBold18));

			PreparedStatement pr = DBConnection.getInstance().getConnection().prepareStatement(SELECT_ALL_INFO_FROM_TASKS_QUERY);
			ResultSet rs = pr.executeQuery();

			rs.first();

			while (rs.next()) {
				doc.add(new Paragraph(System.lineSeparator()));
				doc.add(new Paragraph("--------------------------------"));
				doc.add(new Paragraph("Project name: " + rs.getString("project_name").trim(), bf12));
				doc.add(new Paragraph("Summary: " + rs.getString("summary").trim(), bf12));
				doc.add(new Paragraph("Description: " + rs.getString("description").trim(), bf12));
				doc.add(new Paragraph("Start date: " + rs.getDate("start_date").toLocalDate().toString().trim(), bf12));
				doc.add(new Paragraph("Due date: " + rs.getDate("due_date").toLocalDate().toString().trim(), bf12));
				doc.add(new Paragraph("Priority: " + rs.getString("priority").trim(), bf12));
				doc.add(new Paragraph("State: " + rs.getString("state").trim(), bf12));
				doc.add(new Paragraph("Issue type: " + rs.getString("issue_type").trim(), bf12));
				doc.add(new Paragraph("Creator: " + rs.getString("creator").trim(), bf12));
				doc.add(new Paragraph("Assignee: " + rs.getString("assignee").trim(), bf12));
			}

			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
