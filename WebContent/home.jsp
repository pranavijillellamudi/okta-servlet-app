<%
if(session.getAttribute("user") == null) {
    response.sendRedirect("index.jsp");
}
%>
<h2>Logged in successfully</h2>
<a href="logout">Logout</a>