
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
                                        <strong>Asignar Cita</strong> 
                                    </div>
                                    <div class="card-body card-block">
                                        <c:forEach var="persona" items="${sessionScope.Persona}">
                                            <c:choose>
                                                <c:when test="${persona.getCedula() == sessionScope.cedula}">
                                                    <form  method="post" name="editarPersona" id="editarPersona" action="${pageContext.servletContext.contextPath}/PersonaServlet">
                                                        <div class="form-row">
                                                            <div class="form-group col-md-4">
                                                                <label for="cedula" class="control-label mb-1">Cedula</label>
                                                                <input type="text" class="form-control" name="" disabled value="<c:out value='${persona.getCedula()}'/>"  >
                                                                <input name="cedula" type="hidden" value="<c:out value='${persona.getCedula()}' />">
                                                            </div>
                                                            <div class="form-group col-md-4">
                                                                <label for="nombre" class="control-label mb-1">Nombre</label>
                                                                <input type="text" class="form-control" name="" disabled value="<c:out value='${persona.getNombre()}'/>">
                                                                <input type="hidden" class="form-control" id="nombre" name="nombre" value="<c:out value='${persona.getNombre()}'/>">
                                                            </div>
                                                            <div class="form-group col-md-4">
                                                                <label for="apellido" class="control-label mb-1">Primer Apellido</label>
                                                                <input type="text" class="form-control"  name=""  disabled value="<c:out value='${persona.getApellidouno()}'/>">
                                                                <input type="hidden" class="form-control" id="primerApellido" name="primerApellido"  value="<c:out value='${persona.getApellidouno()}'/>">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="cedula">Cita</label>
                                                            <input type="date" class="form-control" id="cita" name="cita" value="2011-08-08" id="example-month-input">
                                                        </div>
                                                        <div class="form-group">                       
                                                            <label for="textarea-input" class=" form-control-label">Observaciones</label>
                                                            <textarea name="observaciones" id="observaciones" rows="9" placeholder="Observacion..." class="form-control"></textarea>                                     
                                                        </div>
                                                        <div class="col">
                                                            <button name="accion" value="guardarCita" type="submit" class="btn btn-success">Guardar Cambios</button>
                                                            <button name="accion" value="volver" type="submit" class="btn btn-primary">Volver</button>
                                                        </div>
                                                    </c:when>
                                                </c:choose>
                                            </c:forEach>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </body>
</html>
