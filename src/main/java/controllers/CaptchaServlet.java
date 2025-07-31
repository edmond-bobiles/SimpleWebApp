
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.JDBC;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.servlet.*;
import javax.servlet.http.*;

public class CaptchaServlet extends HttpServlet {
    
    private JDBC jdbc;

    @Override
    public void init() throws ServletException {
        jdbc = new JDBC("1527", "simplewebdb", "app", "app");
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try{
            ResultSet rs = jdbc.getUserCredentials(username, password);

            // Retrive user input and the stored CAPTCHA
            String userInput = request.getParameter("userInput");
            String originalCaptcha = (String) session.getAttribute("captcha");

            // If tama or mali ung captcha mo, ireredirect ka sa error page or sa successAdmin
            String result;
            if (userInput != null && originalCaptcha != null && userInput.equals(originalCaptcha)) {
                ResultSet users = jdbc.getAllUsersRecords();
                request.setAttribute("results", users);
                request.getRequestDispatcher("views/admin.jsp").forward(request, response);
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("views/error.jsp");
                dispatcher.forward(request, response);
            }

        } catch (SQLException e){
           request.setAttribute("error", "Database error: " + e.getMessage());
           request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}