package controllers;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.JSONObject;
import model.JDBC;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CaptchaServlet extends HttpServlet {

    private JDBC jdbc;

    @Override
    public void init() throws ServletException {
        jdbc = new JDBC("1527", "simplewebdb", "app", "app");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

        if (gRecaptchaResponse == null || gRecaptchaResponse.isEmpty()) {
            request.setAttribute("error", "Captcha verification failed!");
            request.getRequestDispatcher("/views/captcha.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/admin");
    }

    private boolean verifyCaptcha(String gRecaptchaResponse) throws IOException {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        String params = "secret=YOUR_SECRET_KEY&response=" + gRecaptchaResponse;

        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            os.write(params.getBytes());
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        return jsonResponse.getBoolean("success");
    }
}
