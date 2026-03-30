package com.example;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/callback")

public class CallbackServlet extends HttpServlet {

    private static final String CLIENT_ID = "0oa11gryn1zF53x7j698";
    private static final String CLIENT_SECRET = "A_iDh_gPB1XrdIGI59hmJeKbDN2PMxbItOehqmkEVkcb_1oOuIfIsUkeU7Hn1z3c";
    private static final String TOKEN_URL = "https://demo-blush-bee-8077.okta.com/oauth2/default/v1/token";
    private static final String REDIRECT_URI = "http://localhost:8080/okta-servlet-app/callback";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String code = request.getParameter("code");

        java.net.URI uri = java.net.URI.create(TOKEN_URL);
        java.net.URL url = uri.toURL();
        java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        String params = "grant_type=authorization_code" +
                "&code=" + code +
                "&redirect_uri=" + REDIRECT_URI +
                "&client_id=" + CLIENT_ID +
                "&client_secret=" + CLIENT_SECRET;

        OutputStream os = conn.getOutputStream();
        os.write(params.getBytes());
        os.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String responseStr = "";
        String line;

        while ((line = br.readLine()) != null) {
            responseStr += line;
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", responseStr);

        response.sendRedirect("home.jsp");
    }
}