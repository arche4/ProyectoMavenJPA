
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
                                        <strong>Cargo</strong> 
                                    </div>
                                    <div class="card-body card-block">
                                        <c:forEach var="Cargo" items="${sessionScope.Cargo}">
                                            <c:choose>
                                                <c:when test="${Cargo.getCodigocargo() == sessionScope.codigoCargo}">
                                                    <form method="post" name="crearCargo" id="crearCargo" action="">
                                                        <div class="form-row">
                                                            <div class="form-group col-md-6">
                                                                <label for="codigoCargo" class="control-label mb-1">Codigo Cargo</label>
                                                                <input  name="" class="form-control" disabled type="text" value="<c:out value='${Cargo.getCodigocargo()}' />">
                                                                <input name="codigoCargo" type="hidden" value="<c:out value='${Cargo.getCodigocargo()}' />">
                                                            </div>
                                                            <div class="form-group col-md-6">
                                                                <label for="nombre" class="control-label mb-1">Nombre</label>
                                                                <input name="nombre" type="text" class="form-control" value="<c:out value='${Cargo.getNombre()}'/>">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="riesgo" class="control-label mb-1">riesgo</label>
                                                            <input name="riesgo" type="text" class="form-control" value="<c:out value='${Cargo.getRiesgoCargo()}'/>">
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="actividadEconomica" class="control-label mb-1">Actividad Economica</label>
                                                            <select name="codigoEmpresa" id="afp"  class="form-control-sm form-control">
                                                                <option value="">Empresa</option>
                                                                <option value="${Cargo.getEmpresaNit().getNit()}"><c:out value="${Cargo.getEmpresaNit().getNombre()}"/></option>
                                                                <c:forEach var="empresa" items="${sessionScope.Empresa}">
                                                                    <option value="${empresa.getNit()}"><c:out value="${empresa.getNombre()}"/></option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>

                                                        <div class="modal-footer">
                                                            <c:choose>
                                                                <c:when test="${sessionScope.USUARIO.getRolUsuario() == sessionScope.rol}">      
                                                                    <button name="editar" value="editar" type="submit" class="btn btn-success" onclick="return validarEmpresa()">Guardar</button>
                                                                </c:when> 
                                                            </c:choose>
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
            function validarEmpresa() {
                document.getElementById("crearCargo").action = "${pageContext.servletContext.contextPath}/CargoServlet";
                document.getElementById("crearCargo").submit();
                toastr.success("Cargo Guardado Correctamente");
                return true;


            }
            function volver() {
                document.getElementById("crearCargo").action = "${pageContext.servletContext.contextPath}/CargoServlet";
                document.getElementById("crearCargo").submit();
            }
        </script>
    </body>
</html>
