package com.example;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    // Must match a "Post Logout Redirect URI" registered in your Okta app settings
    private static final String POST_LOGOUT_URI = "http://localhost:8080/okta-servlet-app/index.jsp";
    private static final String OKTA_LOGOUT_URL = "https://demo-blush-bee-8077.okta.com/oauth2/default/v1/logout";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        String idToken = null;

        if (session != null) {
            idToken = (String) session.getAttribute("id_token");
            session.invalidate();   // destroy local session
        }

        // Build Okta end-session URL
        // id_token_hint lets Okta know which session to kill without prompting the user
        StringBuilder logoutUrl = new StringBuilder(OKTA_LOGOUT_URL)
                .append("?post_logout_redirect_uri=")
                .append(java.net.URLEncoder.encode(POST_LOGOUT_URI, "UTF-8"));

        if (idToken != null && !idToken.isEmpty()) {
            logoutUrl.append("&id_token_hint=").append(java.net.URLEncoder.encode(idToken, "UTF-8"));
        }

        response.sendRedirect(logoutUrl.toString());
    }
}
