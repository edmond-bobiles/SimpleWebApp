package controllers;

import model.JDBC;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AdminReport extends HttpServlet {
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
        
        try {
            // To be modified later
            ResultSet reportData = jdbc.getAllUsers(); 
            request.setAttribute("reportData", reportData);
            request.getRequestDispatcher("/views/adminReport.jsp").forward(request, response);
        } catch(SQLException e) {
            request.setAttribute("error", "Report generation error");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }
}