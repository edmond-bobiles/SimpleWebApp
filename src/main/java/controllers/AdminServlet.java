package controllers;

import model.JDBC;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {
    private JDBC jdbc;
    
    @Override
    public void init() throws ServletException {
        jdbc = new JDBC("1527", "simplewebdb", "app", "app");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        
        if (username == null) { 
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            ResultSet users = jdbc.getAllUsers();
            System.out.println("DEBUG: ResultSet obtained? " + (users != null)); // Add this
            if (users != null) {
                users.beforeFirst(); // Reset cursor
                System.out.println("DEBUG: First user name: " + (users.next() ? users.getString("name") : "empty"));
                users.beforeFirst(); // Reset again
            }
            request.setAttribute("users", users);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error occurred: " + e.getMessage());
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/successAdmin.jsp");
        dispatcher.forward(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Displays all user records for admin.";
    }
    
    

}