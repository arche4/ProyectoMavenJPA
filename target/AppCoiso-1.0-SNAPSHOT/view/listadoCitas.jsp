<%-- 
    Document   : listadoPersonas
    Created on : 17/05/2018, 08:37:29 AM
    Author     : Usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<html>
    <%--  <head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>
    <%@include file="/META-INF/jspf/header.jspf" %>
    <%--</head>--%>
    <body>
        <%@include file="/META-INF/jspf/navmenu.jspf" %>
        <hr class="line-seprate">
        <div class="section__content section__content--p30">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12">
                        <h3 class="title-3 m-b-30">Citas</h3>
                        <div class="table-responsive table--no-card m-b-30">
                            <table id="table_id"  class="table table-borderless table-striped table-earning">
                                <thead>
                                    <tr>
                                        <th scope="col">Accion</th>
                                        <th scope="col">Codigo Cita</th>
                                        <th scope="col">Cedula</th>
                                        <th scope="col">Fecha</th>
                                        <th scope="col">Observaciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="citas" items="${sessionScope.citas}" varStatus="myIndex">
                                        <tr>
                                            <td>
                                                <form  method="post" action="${pageContext.servletContext.contextPath}/PersonaServlet">
                                                    <button name="verCita" value="${citas.getIdCitas()}" type="submit" class="btn btn-primary">Ver</button>
                                                </form>
                                            </td> 
                                            <td><c:out value="${citas.getIdCitas()}"/></td>
                                            <td><c:out value="${citas.getPersonasCedula().getCedula()}"/></td>
                                            <td><c:out value="${citas.getFechaCita()}"/></td>
                                            <td><c:out value="${citas.getObservacion()}"/></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            $.noConflict();
            jQuery(document).ready(function ($) {
                $('#table_id').DataTable();
            });
        </script>
    </body>
</html>
