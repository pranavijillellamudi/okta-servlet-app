package com.example;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String CLIENT_ID = "0oa11gryn1zF53x7j698";
    private static final String ISSUER = "https://demo-blush-bee-8077.okta.com/oauth2/default";
    private static final String REDIRECT_URI = "http://localhost:8080/okta-servlet-app/callback";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String url = ISSUER + "/v1/authorize?" +
                "client_id=" + CLIENT_ID +
                "&response_type=code" +
                "&scope=openid profile email" +
                "&redirect_uri=" + REDIRECT_URI +
                "&state=xyz";

        response.sendRedirect(url);
    }
}