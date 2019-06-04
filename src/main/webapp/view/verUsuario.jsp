<%-- 
    Document   : menu
    Created on : 2/05/2018, 06:36:48 PM
    Author     : giovanny

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
    <%@include file="/META-INF/jspf/header.jspf" %>

    <body>

        <%@include file="/META-INF/jspf/navmenu.jspf" %>

        <main role="main">
            <div class="jumbotron">
                <div class="container">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-lg-10">
                                <div class="card">
                                    <div class="card-header">
                                        <strong>Usuario</strong> 
                                    </div>
                                    <div class="card-body card-block">
                                        <c:forEach var="usuario" items="${sessionScope.Usuario}">
                                            <c:choose>
                                                <c:when test="${usuario.getIdUsuario() == sessionScope.documento}">
                                                    <form  method="post"  name="editarUsuario" id="editarUsuario" action="">
                                                        <div class="form-row">
                                                            <div class="form-group col-md-6">
                                                                <input  name="cedula" class="form-control"  type="hidden" value="<c:out value='${usuario.getIdUsuario()}' />">
                                                                <input  name="" disabled  class="form-control" type="text" value="<c:out value='${usuario.getIdUsuario()}' />">
                                                            </div>
                                                            <div class="form-group col-md-6">
                                                                <input name="nombre" class="form-control" type="text" value="<c:out value='${usuario.getUsuarioNombre()}'/>">
                                                            </div>
                                                        </div>
                                                        <div class="form-row">
                                                            <div class="form-group col-md-6">
                                                                <input name="apellido" class="form-control" type="text" value="<c:out value='${usuario.getUsuarioApellido()}'/>">
                                                            </div>
                                                            <div class="form-group col-md-6">
                                                                <select name="rol" id="inputState" 
                                                                        class="form-control">
                                                                    <option value="<c:out value='${usuario.getRolUsuario()}'/>"selected><c:out value='${usuario.getRolUsuario()}'/></option>
                                                                    <option value="Administrador" selected>Administrador</option>
                                                                    <option value="Coordinador" selected>Coordinador</option>
                                                                </select>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <input type="submit" onclick="validacion()" name="eliminar"   class="btn btn-danger" value="Eliminar" />
                                                                <button name="accion" value="modificar" type="submit" class="btn btn-success" onclick="return validar()">Guardar</button>
                                                                <button type="submit" name="accion" value="volver"  class="btn btn-primary" onclick="return volver()">Volver</button>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </c:when>
                                            </c:choose>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <script>

            function validar() {
                document.getElementById("editarUsuario").action = "${pageContext.servletContext.contextPath}/UsuarioServlet";
                document.getElementById("editarUsuario").submit();
                toastr.success("Usuario Guardado Correctamente"); 
               return true;
            }

            function volver() {
                document.getElementById("editarUsuario").action = "${pageContext.servletContext.contextPath}/UsuarioServlet";
                document.getElementById("editarUsuario").submit();
            }
        </script>
        <script type="text/javascript">
            function validacion() {
                var mensaje = confirm("¿Estas Seguro que quieres eliminar este usuario?");
                if (mensaje) {
                    document.getElementById("editarUsuario").action = "${pageContext.servletContext.contextPath}/UsuarioServlet";
                    document.getElementById("editarUsuario").submit();
                    toastr.error("Usuario Eliminado Correctamente");
                }else{
                    alert("¡Haz denegado el mensaje!");
                }
            }
        </script>
    </body>
</html>
