<%-- 
    Document   : newjsp
    Created on : Aug 24, 2013, 12:10:38 PM
    Author     : conrongchautien
--%>

<%@page import="webLib.MyLib"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            request.setCharacterEncoding("UTF-8");
            String msg = MyLib.getParameter("msg", request);
            String name = (String) session.getAttribute("user");
            if (msg.length() > 60) {
        %>
        <span style="color: green">Bạn gửi quá nhiều kí tự.</span>
        <%} else {%>
        <span style="color: red"><b><%=name%>:</b></span><span style="color: brown"><%=msg%></span>
        <%}%>
    </body>
</html>
