
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class CaptchaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //HttpSession session = request.getSession();
        
                // mag gegenerate ng captcha
//         String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//         StringBuilder captcha = new StringBuilder();
//         Random random = new Random();
//
//         Generated CAPTCHA is of length 6
//         for (int i = 0; i < 6; i++) {
//             captcha.append(chars.charAt(random.nextInt(chars.length())));
//         }
//
//        session.setAttribute("captcha", captcha.toString());
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/index.jsp");
//        dispatcher.forward(request, response);

         
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         HttpSession session = request.getSession();

        // Retrive user input and the stored CAPTCHA
        String userInput = request.getParameter("userInput");
        String originalCaptcha = (String) session.getAttribute("captcha");
       
        
        // If tama or mali ung captcha mo, ireredirect ka sa error page or sa successAdmin
        String result;
        if (userInput != null && originalCaptcha != null && userInput.equals(originalCaptcha)) {
            request.getRequestDispatcher("views/admin.jsp")
                       .forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("views/errorCaptcha.jsp");
            dispatcher.forward(request, response);
        }

        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
