package controllers;

import model.JDBC;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AdminServlet extends HttpServlet {
    private JDBC jdbc;
    
    @Override
    public void init() throws ServletException {
        jdbc = new JDBC("1527", "simplewebdb", "app", "app");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Checks if user is logged in and an admin
        if (session == null || !"admin".equals(session.getAttribute("user_role"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // Gets all the records
            ResultSet users = jdbc.getAllUsersRecords();
            request.setAttribute("results", users);
            request.getRequestDispatcher("/views/admin.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Database error");
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }
}