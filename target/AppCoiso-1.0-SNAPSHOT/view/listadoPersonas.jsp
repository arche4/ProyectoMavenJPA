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
                        <h3 class="title-3 m-b-30">Personas</h3>
                        <div class="table-responsive table--no-card m-b-30">
                            <table id="table_id"  class="table table-borderless table-striped table-earning">
                                <thead>
                                    <tr>
                                        <th scope="col">Accion</th>
                                        <th scope="col">Asignar Citas</th>
                                        <th scope="col">Cedula</th>
                                        <th scope="col">Nombre</th>
                                        <th scope="col">Apellidos</th>
                                        <th scope="col">Genero</th>
                                        <th scope="col">Nacimiento</th>
                                        <th scope="col">Telefono</th>
                                        <th scope="col">Celular</th>
                                        <th scope="col">Correo</th>
                                        <th scope="col">Direccion</th>
                                        <th scope="col">Eps</th>
                                        <th scope="col">Arl</th>
                                        <th scope="col">Afp</th>
                                        <th scope="col">Profesion</th>
                                        <th scope="col">Area</th>
                                        <th scope="col">Años de Experiencia</th>
                                        <th scope="col">Recomendado</th>
                                        <th scope="col">Fecha Clinica</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="personas" items="${sessionScope.Persona}" varStatus="myIndex">
                                        <tr>
                                            <td>
                                                <form  method="post" action="${pageContext.servletContext.contextPath}/PersonaServlet">
                                                    <button name="ver" value="${personas.getCedula()}" type="submit" class="btn btn-primary">Ver</button>
                                                </form>
                                            </td> 
                                             <td>
                                                <form  method="post" action="${pageContext.servletContext.contextPath}/PersonaServlet">
                                                    <button name="asginar" value="${personas.getCedula()}" type="submit" class="btn btn-primary">Asignar Citas</button>
                                                </form>
                                            </td> 
                                            <td><c:out value="${personas.getCedula()}"/></td>
                                            <td><c:out value="${personas.getNombre()}"/></td>
                                            <td><c:out value="${personas.getApellidouno()}"/></td>
                                            <td><c:out value="${personas.getGenero()}"/></td>
                                            <td><c:out value="${personas.getFechanacimiento()}"/></td>
                                            <td><c:out value="${personas.getTelefonofijo()}"/></td>
                                            <td><c:out value="${personas.getCelular()}"/></td>
                                            <td><c:out value="${personas.getCorreo()}"/></td>
                                            <td><c:out value="${personas.getDireccion()}"/></td>
                                            <td><c:out value="${personas.getCodigoepsFk().getCodigoeps()}"/></td>
                                            <td><c:out value="${personas.getCodigoarlFk().getCodigoarl()}"/></td>
                                            <td><c:out value="${personas.getCodigoafpFk().getCodigoafp()}"/></td>
                                            <td><c:out value="${personas.getCodigoprofesionFk()}"/></td>
                                            <td><c:out value="${personas.getArea()}"/></td>
                                            <td><c:out value="${personas.getAnosExperiencia()}"/></td>
                                            <td><c:out value="${personas.getRecomendado()}"/></td>
                                            <td><c:out value="${personas.getFechaClinica()}"/></td>
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
