package controllers;

import model.JDBC;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private JDBC jdbc;

    @Override
    public void init() throws ServletException {
        jdbc = new JDBC("1527", "simplewebdb", "app", "app");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // show the login form
        request.getRequestDispatcher("/views/login.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            ResultSet rs = jdbc.getUser(username, password);

            if (rs.next()) {
                // set up session
                HttpSession session = request.getSession();
                session.setAttribute("username", username);

                String userRole = rs.getString("user_role");
                session.setAttribute("user_role", userRole);
                System.out.println("Logged in as: " + userRole);

                // redirect based on role
                if ("admin".equals(userRole)) {
                    response.sendRedirect("views/successAdmin.jsp");
                } else {
                    response.sendRedirect("views/successGuest.jsp");
                }

            } else {
                // invalid credentials
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("views/login.jsp")
                       .forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("views/login.jsp")
                   .forward(request, response);
        }
    }
}
