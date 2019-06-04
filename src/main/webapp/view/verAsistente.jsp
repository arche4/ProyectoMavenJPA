
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
                            <div class="col-lg-12">
                                <div class="card">
                                    <div class="card-header">
                                        <strong>Asistente</strong> 
                                    </div>
                                    <div class="card-body card-block">
                                        <c:forEach var="asistente" items="${sessionScope.asistente}">
                                            <c:choose>
                                                <c:when test="${asistente.getCedulaPersona() == sessionScope.asistenteVer}">
                                                    <form method="post" name="editarAsistente" id="editarAsistente" action="">
                                                        <div class="form-row">
                                                            <div class="form-group col-md-6">
                                                                <label for="nit" class="control-label mb-1">Cedula</label>
                                                                <input  name="" class="form-control" disabled type="text" value="<c:out value='${asistente.getCedulaPersona()}' />">
                                                                <input name="cedula" type="hidden" value="<c:out value='${asistente.getCedulaPersona()}' />">
                                                            </div>
                                                            <div class="form-group col-md-6">
                                                                <label for="nombre" class="control-label mb-1">Nombre</label>
                                                                <input name="nombre" type="text" class="form-control" value="<c:out value='${asistente.getNombrePersona()}'/>">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="apellido" class="control-label mb-1">Apellidos</label>
                                                            <input name="apellido" type="text" class="form-control" value="<c:out value='${asistente.getApellidoPersona()}'/>">
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="celular" class="control-label mb-1">Celular</label>
                                                            <input name="celular" type="text" class="form-control" value="<c:out value='${asistente.getCelular()}'/>">
                                                        </div>
                                                        <div class="form-row">
                                                            <div class="form-group col-md-6">
                                                                <label for="telefono" class="control-label mb-1">Telefono</label>
                                                                <input name="telefono" type="text" class="form-control" value="<c:out value='${asistente.getTelefono()}'/>">
                                                            </div>
                                                            <div class="form-group col-md-6">
                                                                <label for="correo" class="control-label mb-1">Telefono</label>
                                                                <input name="correo" type="text" class="form-control" value="<c:out value='${asistente.getCorreo()}'/>">
                                                            </div>
                                                        </div>
                                                        <div class="form-row">
                                                            <div class="form-group col-md-6">
                                                                <label for="asistencia" class="control-label mb-1">Asistencia</label>
                                                                <input name="" type="text" class="form-control" value="<c:out value='${asistente.getAsistencia()}'/>">
                                                                <input name="asistencia" type="hidden" class="form-control" value="<c:out value='${asistente.getAsistencia()}'/>">
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button name="accion" value="editar" type="submit" class="btn btn-success" onclick="return validar()">Guardar</button>
                                                            <button type="submit" name="volver" value="volver"  class="btn btn-primary" onclick="return volver()">Volver</button>
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
                document.getElementById("editarAsistente").action = "${pageContext.servletContext.contextPath}/AsistentesServlet";
                document.getElementById("editarAsistente").submit();
                toastr.success("Asistente Guardado Correctamente");
                return true;


            }
            function volver() {
                document.getElementById("editarAsistente").action = "${pageContext.servletContext.contextPath}/AsistentesServlet";
                document.getElementById("editarAsistente").submit();
            }
        </script>
    </body>
</html>
