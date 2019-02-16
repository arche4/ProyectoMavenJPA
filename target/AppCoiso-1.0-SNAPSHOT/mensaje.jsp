<%-- 
    Document   : mensaje
    Created on : 25/04/2018, 07:24:29 PM
    Author     : Usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <%@include file="/META-INF/jspf/header.jspf" %>
    <body>
        
        <h1>mensaje</h1>
        <c:out value="${sessionScope.MENSAJE}"/>
    </body>
</html>
