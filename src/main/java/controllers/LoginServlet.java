package controllers;

import model.JDBC;
import model.Security;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    private JDBC jdbc;

    @Override
    public void init() throws ServletException {
        jdbc = new JDBC("1527", "simplewebdb", "app", "app");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/login.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String username = request.getParameter("username");
        String inputPassword = request.getParameter("password");

        try {
            // Gets the encrypted password from db
            String storedEncrypted = jdbc.getEncryptedPassword(username);

            // Encrypts the user input password
            String encryptedInput = Security.encrypt(inputPassword);

            // Compares the encrypted password from db and encrypted input password
            if (storedEncrypted != null && storedEncrypted.equals(encryptedInput)) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);

                // Gets the user role
                String role = jdbc.getUserRole(username);
                session.setAttribute("user_role", role);

                if ("admin".equals(role)) {
                    request.setAttribute("results", jdbc.getAllUsersRecords());
                    request.getRequestDispatcher("/views/admin.jsp").forward(request, response);
                } else {
                    request.setAttribute("results", jdbc.getUserRecords(username));
                    request.getRequestDispatcher("/views/guest.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("/views/error.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }
}