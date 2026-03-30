package com.example;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/callback")
public class CallbackServlet extends HttpServlet {

    private static final String CLIENT_ID     = "0oa11gryn1zF53x7j698";
    private static final String CLIENT_SECRET = "A_iDh_gPB1XrdIGI59hmJeKbDN2PMxbItOehqmkEVkcb_1oOuIfIsUkeU7Hn1z3c";
    private static final String TOKEN_URL     = "https://demo-blush-bee-8077.okta.com/oauth2/default/v1/token";
    private static final String REDIRECT_URI  = "http://localhost:8080/okta-servlet-app/callback";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String code = request.getParameter("code");

        // --- Token exchange ---
        java.net.URI uri = java.net.URI.create(TOKEN_URL);
        java.net.URL url = uri.toURL();
        java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String params = "grant_type=authorization_code"
                + "&code=" + code
                + "&redirect_uri=" + java.net.URLEncoder.encode(REDIRECT_URI, "UTF-8")
                + "&client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET;

        try (OutputStream os = conn.getOutputStream()) {
            os.write(params.getBytes("UTF-8"));
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
        }

        String json = sb.toString();

        // --- Parse tokens from JSON (no external lib needed) ---
        String accessToken = extractJson(json, "access_token");
        String idToken     = extractJson(json, "id_token");

        // --- Store in session ---
        HttpSession session = request.getSession();
        session.setAttribute("access_token", accessToken);
        session.setAttribute("id_token",     idToken);
        session.setAttribute("user",         json);   // keep raw JSON too

        response.sendRedirect("home.jsp");
    }

    /** Minimal JSON string extractor — no external deps needed for a demo. */
    private String extractJson(String json, String key) {
        String search = "\"" + key + "\":\"";
        int start = json.indexOf(search);
        if (start == -1) return "";
        start += search.length();
        int end = json.indexOf("\"", start);
        return end == -1 ? "" : json.substring(start, end);
    }
}
