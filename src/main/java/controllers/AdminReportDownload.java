package controllers;

import model.JDBC;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminReportDownload extends HttpServlet {

    private JDBC jdbc;

    @Override
    public void init() throws ServletException {
        jdbc = new JDBC("1527", "simplewebdb", "app", "app");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || !"admin".equals(session.getAttribute("user_role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"UserReport.pdf\"");

        try {
            // Footer
            String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String generatedBy = (String) session.getAttribute("username");

            OutputStream out = response.getOutputStream();
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, out);

            FooterHandler footer = new FooterHandler(dateStr, generatedBy);
            writer.setPageEvent(footer);

            document.open();

            // Fonts
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font subTitleFont = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC);
            Font textFont = new Font(Font.FontFamily.HELVETICA, 11);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

            // Header
            document.add(new Paragraph("User Role Report", titleFont));
            document.add(new Paragraph("List of users and their roles", subTitleFont));
            document.add(Chunk.NEWLINE);

            // Table
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2, 2, 2, 2, 2});

            PdfPCell h1 = new PdfPCell(new Phrase("Username", headerFont));
            h1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            h1.setPaddingTop(10f);
            h1.setPaddingBottom(10f);
            table.addCell(h1);

            PdfPCell h2 = new PdfPCell(new Phrase("Role", headerFont));
            h2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            h2.setPaddingTop(10f);
            h2.setPaddingBottom(10f);
            table.addCell(h2);

            PdfPCell h3 = new PdfPCell(new Phrase("Name", headerFont));
            h3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            h3.setPaddingTop(10f);
            h3.setPaddingBottom(10f);
            table.addCell(h3);

            PdfPCell h4 = new PdfPCell(new Phrase("Age", headerFont));
            h4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            h4.setPaddingTop(10f);
            h4.setPaddingBottom(10f);
            table.addCell(h4);

            PdfPCell h5 = new PdfPCell(new Phrase("Country", headerFont));
            h5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            h5.setPaddingTop(10f);
            h5.setPaddingBottom(10f);
            table.addCell(h5);

            ResultSet rs = jdbc.getAllUserRolesRecords();
            while (rs.next()) {
                PdfPCell c1 = new PdfPCell(new Phrase(rs.getString("USER_NAME"), textFont));
                c1.setPaddingTop(5f);
                c1.setPaddingBottom(5f);
                table.addCell(c1);

                PdfPCell c2 = new PdfPCell(new Phrase(rs.getString("USER_ROLE"), textFont));
                c2.setPaddingTop(5f);
                c2.setPaddingBottom(5f);
                table.addCell(c2);

                PdfPCell c3 = new PdfPCell(new Phrase(rs.getString("NAME"), textFont));
                c3.setPaddingTop(5f);
                c3.setPaddingBottom(5f);
                table.addCell(c3);

                PdfPCell c4 = new PdfPCell(new Phrase(rs.getString("AGE"), textFont));
                c4.setPaddingTop(5f);
                c4.setPaddingBottom(5f);
                table.addCell(c4);

                PdfPCell c5 = new PdfPCell(new Phrase(rs.getString("COUNTRY"), textFont));
                c5.setPaddingTop(5f);
                c5.setPaddingBottom(5f);
                table.addCell(c5);
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            throw new ServletException("PDF generation failed", e);
        }
    }

    // Footer class
    static class FooterHandler extends PdfPageEventHelper {

        private PdfTemplate total;
        private String timestamp;
        private String generatedBy;
        private BaseFont baseFont;

        public FooterHandler(String timestamp, String generatedBy) {
            this.timestamp = timestamp;
            this.generatedBy = generatedBy;
        }

        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
            total = writer.getDirectContent().createTemplate(30, 16);
            try {
                baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            cb.saveState();
            try {
                cb.setFontAndSize(baseFont, 9);

                float leftX = document.leftMargin();
                float rightX = document.right() + document.rightMargin();
                float y = document.bottom() - 20;

                String pageText = "Page " + writer.getPageNumber() + " of ";
                String leftText = "Generated on: " + timestamp;
                String rightText = "Generated by: " + generatedBy;

                // Left side
                cb.beginText();
                cb.setTextMatrix(leftX, y);
                cb.showText(pageText);
                cb.endText();
                cb.addTemplate(total, leftX + baseFont.getWidthPoint(pageText, 9), y);

                cb.beginText();
                cb.setTextMatrix(leftX + baseFont.getWidthPoint(pageText, 9) + 5, y);
                cb.showText(" | " + leftText);
                cb.endText();

                // Right side
                float rightTextWidth = baseFont.getWidthPoint(rightText, 9);
                cb.beginText();
                cb.setTextMatrix(rightX - rightTextWidth - 35, y);
                cb.showText(rightText);
                cb.endText();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cb.restoreState();
            }
        }

        @Override
        public void onCloseDocument(PdfWriter writer, Document document) {
            total.beginText();
            try {
                total.setFontAndSize(baseFont, 9);
                total.setTextMatrix(0, 0);
                total.showText(String.valueOf(writer.getPageNumber()));
                total.endText();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
