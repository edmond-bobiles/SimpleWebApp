package controllers;

import model.JDBC;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class GuestServlet extends HttpServlet {
    private JDBC jdbc;
    
    @Override
    public void init() throws ServletException {
        jdbc = new JDBC("1527", "simplewebdb", "app", "app");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check if user is logged in and is a guest
        if (session == null || !"guest".equals(session.getAttribute("user_role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get current username from session
        String username = (String) session.getAttribute("username");
        if (username == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // Get only the current guest's records
            ResultSet user = jdbc.getUserRecords(username);
            request.setAttribute("results", user);
            request.getRequestDispatcher("/views/guest.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }
}