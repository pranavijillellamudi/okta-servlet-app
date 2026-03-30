<%@ page contentType="text/html;charset=UTF-8" %>
<%
    if (session.getAttribute("user") == null) { response.sendRedirect("index.jsp"); return; }
    String at  = (String) session.getAttribute("access_token");
    String idt = (String) session.getAttribute("id_token");
%>
<h2>Logged in!</h2>

<h4>Access Token</h4>
<textarea rows="4" cols="80"><%= at %></textarea><br>
<a href="https://jwt.io/#debugger-io?token=<%= at %>" target="_blank">Decode Access Token</a>

<h4>ID Token</h4>
<textarea rows="4" cols="80"><%= idt %></textarea><br>
<a href="https://jwt.io/#debugger-io?token=<%= idt %>" target="_blank">Decode ID Token</a>

<br><br><a href="logout">Logout</a>
