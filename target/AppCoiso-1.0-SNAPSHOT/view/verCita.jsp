
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
                                        <strong>Citas</strong> 
                                    </div>
                                    <div class="card-body card-block">
                                        <c:forEach var="citas" items="${sessionScope.citas}">
                                            <c:choose>
                                                <c:when test="${citas.getIdCitas() == sessionScope.codigoCita}">
                                                    <form method="post" name="verCita" id="verCita" action="">
                                                        <div class="form-row">
                                                            <div class="form-group col-md-4">
                                                                <label for="idCita" class="control-label mb-1">Codigo Cita</label>
                                                                <input  name="" class="form-control" disabled type="text" value="<c:out value='${citas.getIdCitas()}' />">
                                                                <input name="idCita" type="hidden" value="<c:out value='${citas.getIdCitas()}' />">
                                                            </div>

                                                            <div class="form-group col-md-4">
                                                                <label for="cedula" class="control-label mb-1">Cedula</label>
                                                                <input  name="" class="form-control" disabled type="text" value="<c:out value='${citas.getPersonasCedula().getCedula()}' />">
                                                                <input name="cedula" type="hidden" value="<c:out value='${citas.getPersonasCedula().getCedula()}' />">
                                                            </div>
                                                            <div class="form-group col-md-4">
                                                                <label for="nombre" class="control-label mb-1">Nombre</label>
                                                                <input type="text" class="form-control" name="nombre" disabled value="<c:out value='${citas.getPersonasCedula().getNombre()}'/>">
                                                            </div>
                                                        </div>
                                                        <div class="form-row">
                                                            <div class="form-group col-md-6">
                                                                <label for="apellido" class="control-label mb-1">Primer Apellido</label>
                                                                <input type="text" class="form-control"  name="primerApellido"  disabled value="<c:out value='${citas.getPersonasCedula().getApellidouno()}'/>">
                                                            </div>
                                                            <div class="form-group col-md-6">
                                                                <label for="cedula">Cita</label>
                                                                <input type="date" class="form-control" id="cita" name="cita"  value="<c:out value='${citas.getFechaCita()}'/>" id="example-month-input">
                                                            </div>
                                                        </div>
                                                        <div class="form-group col-md-12">                       
                                                            <label for="textarea-input" class=" form-control-label">Observaciones</label>
                                                            <textarea name="observaciones" id="observaciones" rows="7" class="form-control"><c:out value='${citas.getObservacion()}'/></textarea>                                     
                                                        </div>

                                                        <div class="modal-footer">
                                                            <button name="editarCita" value="editarCita" type="submit" class="btn btn-success" onclick="return validar()">Guardar</button>
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
        </main>
        <script>
            function validar() {
                document.getElementById("verCita").action = "${pageContext.servletContext.contextPath}/PersonaServlet";
                document.getElementById("verCita").submit();
                toastr.success("Cita Guardado Correctamente");
                return true;


            }
            function volver() {
                document.getElementById("verCita").action = "${pageContext.servletContext.contextPath}/PersonaServlet";
                document.getElementById("verCita").submit();
            }
        </script>
    </body>
</html>
