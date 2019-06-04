
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
                                        <strong>Empresa</strong> 
                                    </div>
                                    <div class="card-body card-block">
                                        <c:forEach var="Empresa" items="${sessionScope.Empresa}">
                                            <c:choose>
                                                <c:when test="${Empresa.getNit() == sessionScope.nit}">
                                                    <form method="post" name="crearEmpresa" id="crearEmpresa" action="">
                                                        <div class="form-row">
                                                            <div class="form-group col-md-6">
                                                                <label for="nit" class="control-label mb-1">Nit Empresa</label>
                                                                <input  name="" class="form-control" disabled type="text" value="<c:out value='${Empresa.getNit()}' />">
                                                                <input name="nit" type="hidden" value="<c:out value='${Empresa.getNit()}' />">
                                                            </div>
                                                            <div class="form-group col-md-6">
                                                                <label for="nombre" class="control-label mb-1">Nombre</label>
                                                                <input name="nombre" type="text" class="form-control" value="<c:out value='${Empresa.getNombre()}'/>">
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="sector" class="control-label mb-1">Sector</label>
                                                            <input name="sector" type="text" class="form-control" value="<c:out value='${Empresa.getSector()}'/>">
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="actividadEconomica" class="control-label mb-1">Actividad Economica</label>
                                                            <input name="actividadEconomica" type="text" class="form-control" value="<c:out value='${Empresa.getActividadEconomica()}'/>">
                                                        </div>
                                                        <div class="form-row">
                                                            <div class="form-group col-md-6">
                                                                <label for="antiguedad" class="control-label mb-1">Años Antiguedad</label>
                                                                <input name="antiguedad" type="text" class="form-control" value="<c:out value='${Empresa.getAnosAtiguedad()}'/>">
                                                            </div>
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
                document.getElementById("crearEmpresa").action = "${pageContext.servletContext.contextPath}/EmpresaServlet";
                document.getElementById("crearEmpresa").submit();
                toastr.success("Empresa Guardado Correctamente");
                return true;


            }
            function volver() {
                document.getElementById("crearEmpresa").action = "${pageContext.servletContext.contextPath}/EmpresaServlet";
                document.getElementById("crearEmpresa").submit();
            }
        </script>
    </body>
</html>
