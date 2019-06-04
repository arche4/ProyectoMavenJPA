
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
                                        <strong>Formación</strong> 
                                    </div>
                                    <div class="card-body card-block">
                                        <c:forEach var="formacion" items="${sessionScope.formacion}">
                                            <c:choose>
                                                <c:when test="${formacion.getIdFormacion() == sessionScope.formacionver}">
                                                    <form method="post" name="editarFormacion" id="editarFormacion" action="">
                                                        <div class="form-row">
                                                            <div class="form-group col-md-6">
                                                                <label for="nit" class="control-label mb-1">Id Formación</label>
                                                                <input  name="" class="form-control" disabled type="text" value="<c:out value='${formacion.getIdFormacion()}' />">
                                                                <input name="idFormacion" type="hidden" value="<c:out value='${formacion.getIdFormacion()}' />">
                                                            </div>
                                                            <div class="form-group col-md-6">
                                                                <label for="tipoFormacion" class="control-label mb-1">Tipo de Formacion</label>
                                                                <input name="tipoFormacion" type="text" class="form-control" value="<c:out value='${formacion.getTipoFormacion()}'/>">
                                                            </div>
                                                        </div>
                                                        <div class="form-row">
                                                            <div class="form-group col-md-6">
                                                                <label for="formacion" class="control-label mb-1">Formación</label>
                                                                <input name="formacion" type="text" class="form-control" value="<c:out value='${formacion.getFechaFormacion()}'/>">
                                                            </div>
                                                             <div class="form-group col-md-6">
                                                                <label for="tema" class="control-label mb-1">Temas</label>
                                                                <input name="tema" type="text" class="form-control" value="<c:out value='${formacion.getTemas()}'/>">
                                                            </div>
                                                            <div class="form-group col-md-6">
                                                                <label for="tema" class="control-label mb-1">N° Asistentes</label>
                                                                <input name="nAsistentes" type="text" class="form-control" value="<c:out value='${formacion.getNumeroAsistentes()}'/>">
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button name="editar" value="editar" type="submit" class="btn btn-success" onclick="return validar()">Guardar</button>
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
                document.getElementById("editarFormacion").action = "${pageContext.servletContext.contextPath}/FormacionServlet";
                document.getElementById("editarFormacion").submit();
                toastr.success("Formacion Guardado Correctamente");
                return true;


            }
            function volver() {
                document.getElementById("editarFormacion").action = "${pageContext.servletContext.contextPath}/FormacionServlet";
                document.getElementById("editarFormacion").submit();
            }
        </script>
    </body>
</html>
