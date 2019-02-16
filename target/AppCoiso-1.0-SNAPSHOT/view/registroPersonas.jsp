

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <%--  <head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>
    <%@include file="/META-INF/jspf/header.jspf" %>

    <%--</head>--%>
    <body>
        <%@include file="/META-INF/jspf/navmenu.jspf" %>
        <main role="main">
            <div class="jumbotron">
                <div class="container">
                    <div class="modal fade" id="myModal">
                        <div class="modal-dialog">
                            <div class="modal-content" style="padding:40px 50px;">
                                <!-- Modal Header -->
                                <c:choose>
                                    <c:when test="${sessionScope.USUARIO.getRolUsuario() == sessionScope.rol}"> 
                                        <div class="modal-header">
                                            <h4 class="modal-title">Crear Empresa</h4>
                                            <button type="button" class="close" data-dismiss="modal">×</button>
                                        </div>
                                    </c:when> 
                                </c:choose>
                                <form method="post" name="crearEmpresa" id="crearEmpresa" action="">
                                    <div class="form-row">
                                        <div class="form-group col-md-6">
                                            <input type="text" class="form-control" id="nit" name="nit"  placeholder="Nit Empresa">
                                        </div>
                                        <div class="form-group col-md-6">
                                            <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre">
                                        </div>
                                    </div>
                                    <div class="form-row">
                                        <div class="form-group col-md-6">
                                            <input type="text" class="form-control" id="sector" name="sector"  placeholder="Sector">
                                        </div>
                                        <div class="form-group col-md-6">
                                            <input type="text" class="form-control" id="actividadEconomica" name="actividadEconomica"  placeholder="Actividad Economica">
                                        </div>

                                    </div>
                                    <div class="form-row">
                                        <div class="form-group col-md-6">
                                            <input type="text" class="form-control" id="antiguedad" name="antiguedad" placeholder="Años de Antiguedad">
                                        </div>
                                    </div>
                                    <!-- Modal footer -->
                                    <div class="modal-footer">
                                        <button name="btnaccion" value="btnaccion" type="submit" class="btn btn-success" onclick="return validarEmpresa()">Guardar</button>
                                        <button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>
                    <div class="section__content section__content--p30">
                        <div class="container-fluid">
                            <div class="row">

                                <div class="col-lg-6">
                                    <div class="card">
                                        <div class="card-header">
                                            <strong>Persona</strong> 
                                        </div>
                                        <div class="card-body card-block">
                                            <h3>Datos Personales</h3>
                                            <form method="post" name="persona" id="persona" action="">
                                                <div class="form-row">
                                                    <div class="form-group col-md-6">
                                                        <input type="text" class="form-control" id="cedula" name="cedula"  placeholder="Cedula">
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <input type="text" class="form-control" id="primerApellido" name="primerApellido"  placeholder="Primer Apellido">
                                                </div>
                                                <div class="form-group">
                                                    <input type="text" class="form-control" id="segundoApellido" name="segundoApellido"  placeholder="Segundo Apellido">
                                                </div>
                                                <div class="form-group">
                                                    <div class="col col-md-3">
                                                        <label class=" form-control-label">Genero</label>
                                                    </div>
                                                    <div class="form-check form-check-inline">
                                                        <input class="form-check-input" type="radio" name="genero" id="femenino" value="femenino">
                                                        <label class="form-check-label" for="inlineRadio1">F</label>
                                                    </div>
                                                    <div class="form-check form-check-inline">
                                                        <input class="form-check-input" type="radio" name="genero" id="Masculino" value="Masculino">
                                                        <label class="form-check-label" for="inlineRadio2">M</label>
                                                    </div>

                                                </div>
                                                <div class="form-group">
                                                    <label for="cedula">Fecha  Nacimiento</label>
                                                    <input type="date" class="form-control" id="cumpleaños" name="cumpleaños" value="2011-08-08" id="example-month-input">
                                                </div>
                                                <div class="form-row">
                                                    <div class="form-group col-md-6">
                                                        <input type="text" class="form-control" name="telefono" id="telefono" placeholder="Telefono">
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <input type="text" class="form-control" name="celular"  id="celular" placeholder="Celular">
                                                    </div>
                                                </div>
                                                <div class="form-row">
                                                    <div class="form-group col-md-6">
                                                        <input type="text" class="form-control" name="correo" id="correo" placeholder="Correo Electronico">
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <input type="text" class="form-control" name="direccion" id="direccion" placeholder="Direccion">
                                                    </div>
                                                </div>
                                                <h3>Datos Empresa Y Salud</h3>
                                                <div class="form-row">
                                                    <div class="form-group col-md-6">
                                                        <select name="eps" id="eps" class="form-control-sm form-control">
                                                            <option value="">EPS</option>
                                                            <c:forEach var="eps" items="${sessionScope.EPS}">
                                                                <option value="${eps.getCodigoeps()}"><c:out value="${eps.getNombre()}"/></option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>

                                                    <div class="form-group col-md-6">
                                                        <select name="arl" id="arl"  class="form-control-sm form-control">
                                                            <option value="">ARL</option>
                                                            <c:forEach var="arl" items="${sessionScope.ARL}">
                                                                <option value="${arl.getCodigoarl()}"><c:out value="${arl.getNombre()}"/></option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <select name="afp" id="afp"  class="form-control-sm form-control">
                                                            <option value="">AFP</option>
                                                            <c:forEach var="afp" items="${sessionScope.AFP}">
                                                                <option value="${afp.getCodigoafp()}"><c:out value="${afp.getNombre()}"/></option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <input type="text" class="form-control" id="profesion" name="profesion" placeholder="Profesion">
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <input type="text" class="form-control" id="empresa" name="empresa" placeholder="Nit empresa">
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <input type="text" class="form-control" id="cargo" name="cargo" placeholder="cargo">
                                                    </div>

                                                    <div class="form-group col-md-6">
                                                        <input type="text" class="form-control" id="area" name="area" placeholder="Area">
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <input type="number" class="form-control" name="anosExperiencia" id="anosExperiencia" placeholder="Años Experiencia">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="cedula">Fecha de Clinica</label>
                                                    <input type="date" class="form-control" id="FechaClinica" name="FechaClinica" placeholder="MM/DD/YYY" id="example-month-input">
                                                </div>
                                                <div class="form-group">
                                                    <input type="recomendado" class="form-control" name="recomendado" id="recomendado" placeholder="Recomendado">
                                                </div>
                                                <h3>Datos Caso</h3>
                                                <div class="form-group">
                                                    <input type="idCaso" class="form-control" name="idCaso" id="idCaso" placeholder="Numero del Caso">
                                                </div>
                                                <div class="form-group">                       
                                                    <label for="textarea-input" class=" form-control-label">Descripcion Del Caso</label>
                                                    <textarea name="descripcionCaso" id="descripcionCaso" rows="9" placeholder="Descripción..." class="form-control"></textarea>                                     
                                                </div>
                                                <div class="form-group">
                                                    <label for="InicioAfectacion">Fecha de Inicio de la Afectacion</label>
                                                    <input type="date" class="form-control" id="InicioAfectacion" name="InicioAfectacion" placeholder="MM/DD/YYY" id="example-month-input">
                                                </div>
                                                <div class="form-group">                       
                                                    <label for="textarea-input" class=" form-control-label">Origen del Diactamen</label>
                                                    <textarea name="origenDictamen" id="origenDictamen" rows="9" placeholder="Origen..." class="form-control"></textarea>                                     
                                                </div>
                                                <div class="form-row">
                                                    <div class="form-group col-md-6">
                                                        <input type="text" class="form-control" id="pcl" name="pcl" placeholder="PCL">
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <input type="text" class="form-control" id="parteAfectada" name="parteAfectada"  placeholder="Parte Afectada">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="cedula">Teimpo de Incapacidad</label>
                                                    <input type="text" class="form-control" id="tiempoIncapacidad" name="tiempoIncapacidad"  placeholder="Tiempo Incapacidad">
                                                </div>
                                                <div class="form-group">                       
                                                    <label for="textarea-input" class=" form-control-label">Observaciones</label>
                                                    <textarea name="observaciones" id="observaciones" rows="9" placeholder="Observacion..." class="form-control"></textarea>                                     
                                                </div>
                                                <div class="col">
                                                    <button name="accion" value="crear" type="submit" class="btn btn-success" onclick="return validar()">
                                                        Registrar
                                                    </button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="card">
                                        <div class="card-header">
                                            <strong>Empresa</strong> 
                                        </div>
                                        <div class="card-body card-block">
                                            <c:choose>
                                                <c:when test="${sessionScope.USUARIO.getRolUsuario() == sessionScope.rol}">   
                                                    <div class="col">
                                                        <button name="accion" value="crear" type="button" class="btn btn-success" data-toggle="modal" data-target="#myModal">
                                                            Crear Empresa
                                                        </button>
                                                    </div>
                                                </c:when> 
                                            </c:choose>
                                            <hr class="line-seprate">
                                            <div class="table-responsive table--no-card m-b-30">
                                                <table id="table_id" class="display AllDataTables">
                                                    <thead>
                                                        <tr>
                                                            <th scope="col">Acción</th>
                                                            <th scope="col">Nit</th>
                                                            <th scope="col">Nombre</th>
                                                            <th scope="col">Sector</th>
                                                            <th scope="col">Actividad Economica</th>
                                                            <th scope="col">Años de Antiguedad</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="empresa" items="${sessionScope.Empresa}" varStatus="myIndex">
                                                            <tr>
                                                                <td>
                                                                    <form  method="post"  action="${pageContext.servletContext.contextPath}/EmpresaServlet">
                                                                        <button name="ver" value="${empresa.getNit()}" type="submit" class="btn btn-primary">ver</button>
                                                                    </form>
                                                                </td>
                                                                <td><c:out value="${empresa.getNit()}"/></td>
                                                                <td><c:out value="${empresa.getNombre()}"/></td>
                                                                <td><c:out value="${empresa.getSector()}"/></td>
                                                                <td><c:out value="${empresa.getActividadEconomica()}"/></td>
                                                                <td><c:out value="${empresa.getAnosAtiguedad()}"/></td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card">
                                        <div class="card-header">
                                            <strong>Cargo</strong> 
                                        </div>
                                        <div class="card-body card-block">
                                            <div class="col">
                                                <button name="accion" value="crear" type="button" class="btn btn-success" data-toggle="modal" data-target="#crearCargo">
                                                    Crear Cargo
                                                </button>
                                            </div>
                                            <hr class="line-seprate">
                                            <div class="table-responsive table--no-card m-b-30">
                                                <table id="table_id" class="display AllDataTables">
                                                    <thead>
                                                        <tr>
                                                            <th scope="col">Acción</th>
                                                            <th scope="col">Codigo</th>
                                                            <th scope="col">Nombre</th>
                                                            <th scope="col">Riesgo</th>
                                                            <th scope="col">Empresa</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="Cargo" items="${sessionScope.Cargo}" varStatus="myIndex">
                                                            <tr>
                                                                <td>
                                                                    <form  method="post" action="${pageContext.servletContext.contextPath}/CargoServlet">
                                                                        <button name="ver" value="${Cargo.getCodigocargo()}" type="submit" class="btn btn-primary">ver</button>
                                                                    </form>
                                                                </td>
                                                                <td><c:out value="${Cargo.getCodigocargo()}"/></td>
                                                                <td><c:out value="${Cargo.getNombre()}"/></td>
                                                                <td><c:out value="${Cargo.getRiesgoCargo()}"/></td>
                                                                <td><c:out value="${Cargo.getEmpresaNit().getNombre()}"/></td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div> 
                    </div>
                    <div class="modal fade" id="crearCargo">
                        <div class="modal-dialog">
                            <div class="modal-content" style="padding:40px 50px;">
                                <!-- Modal Header -->
                                <div class="modal-header">
                                    <h4 class="modal-title">Crear Cargo</h4>
                                    <button type="button" class="close" data-dismiss="modal">×</button>
                                </div>
                                <form method="post" name="crearC" id="crearC" action="">
                                    <div class="form-row">
                                        <div class="form-group col-md-6">
                                            <input type="text" class="form-control" id="codigoCargo" name="codigoCargo"  placeholder="Codigo Cargo">
                                        </div>
                                        <div class="form-group col-md-6">
                                            <input type="text" class="form-control" id="nombreCargo" name="nombreCargo" placeholder="Nombre">
                                        </div>
                                    </div>
                                    <div class="form-row">
                                        <div class="form-group col-md-6">
                                            <input type="text" class="form-control" id="riesgo" name="riesgo"  placeholder="Riesgo">
                                        </div>
                                        <div class="form-group col-md-6">
                                            <select name="empresa" id="afp"  class="form-control-sm form-control">
                                                <option value="">Empresa</option>
                                                <c:forEach var="empresa" items="${sessionScope.Empresa}">
                                                    <option value="${empresa.getNit()}"><c:out value="${empresa.getNombre()}"/></option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button name="guardar" value="guardar" type="submit" class="btn btn-success" onclick="return validarCargo()">Guardar</button>
                                        <button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>
                    <c:out value="${sessionScope.mensaje}"/>
                </div>
            </div>
        </main>
        <script type="text/javascript">
            function validar() {
                var cedula = document.getElementById('cedula').value;
                var nombre = document.getElementById('nombre').value;
                var primerApellido = document.getElementById('primerApellido').value;
                var segundoApellido = document.getElementById('segundoApellido').value;
                var celular = document.getElementById('celular').value;
                var correo = document.getElementById('correo').value;
                var eps = document.getElementById('eps').value;
                var arl = document.getElementById('arl').value;
                var afp = document.getElementById('afp').value;
                var empresa = document.getElementById('empresa').value;
                var cargo = document.getElementById('cargo').value;
                var recomendado = document.getElementById('recomendado').value;
                var idCaso = document.getElementById('idCaso').value;
                var observaciones = document.getElementById('observaciones').value;


                if (nombre == "" && cedula == "" && primerApellido == "" && segundoApellido == ""
                        && celular == "" && correo == "" && eps == "" && arl == "" && afp == "" && empresa == ""
                        && cargo == "" && recomendado == "" && idCaso == "" && observaciones == "") {
                    toastr.error("No ha ingresado Cedula", "Aviso!");
                    toastr.error("No ha ingresado Nombre", "Aviso!");
                    toastr.error("No ha ingresado Primer Apellido", "Aviso!");
                    toastr.error("No ha ingresado Segundo Apellido", "Aviso!");
                    toastr.error("No ha ingresado Celular", "Aviso!");
                    toastr.error("No ha ingresado Correo", "Aviso!");
                    toastr.error("No ha ingresado Eps", "Aviso!");
                    toastr.error("No ha ingresado Arl", "Aviso!");
                    toastr.error("No ha ingresado Afp", "Aviso!");
                    toastr.error("No ha ingresado Empresa", "Aviso!");
                    toastr.error("No ha ingresado Cargo", "Aviso!");
                    toastr.error("No ha ingresado Recomendado", "Aviso!");
                    toastr.error("No ha ingresado N° Caso", "Aviso!");
                    toastr.error("No ha ingresado Observaciones", "Aviso!");
                    return false;
                }
                if ($.trim(cedula) == "") {
                    toastr.error("No ha ingresado Cedula", "Aviso!");
                    return false;
                }
                if ($.trim(nombre) == "") {
                    toastr.error("No ha ingresado Nombre", "Aviso!");
                    return false;
                }
                if ($.trim(primerApellido) == "") {
                    toastr.error("No ha ingresado Primer Apellido", "Aviso!");
                    return false;
                }
                if ($.trim(segundoApellido) == "") {
                    toastr.error("No ha ingresado Segundo Apellido", "Aviso!");
                    return false;
                }

                if ($.trim(celular) == "") {
                    toastr.error("No ha ingresado Celular", "Aviso!");
                    return false;
                }
                if ($.trim(correo) == "") {
                    toastr.error("No ha ingresado Correo", "Aviso!");
                    return false;
                }
                if ($.trim(eps) == "") {
                    toastr.error("No ha ingresado Eps", "Aviso!");
                    return false;
                }
                if ($.trim(arl) == "") {
                    toastr.error("No ha ingresado Arl", "Aviso!");
                    return false;
                }
                if ($.trim(afp) == "") {
                    toastr.error("No ha ingresado Afp", "Aviso!");
                    return false;
                }
                if ($.trim(empresa) == "") {
                    toastr.error("No ha ingresado Empresa", "Aviso!");
                    return false;
                }
                if ($.trim(cargo) == "") {
                    toastr.error("No ha ingresado Cargo", "Aviso!");
                    return false;
                }
                if ($.trim(recomendado) == "") {
                    toastr.error("No ha ingresado Recomendado", "Aviso!");
                    return false;
                }
                if ($.trim(idCaso) == "") {
                    toastr.error("No ha ingresado N° Caso", "Aviso!");
                    return false;
                }
                if ($.trim(observaciones) == "") {
                    toastr.error("No ha ingresado Observaciones", "Aviso!");
                    return false;
                }
                if (!nombre == "" && !cedula == "" && !primerApellido == "" && !segundoApellido == ""
                        && !celular == "" && !correo == "" && !eps == "" && !arl == "" && !afp == "" && !empresa == ""
                        && !cargo == "" && !recomendado == "" && !idCaso == "" && !observaciones == "") {
                    document.getElementById("persona").action = "${pageContext.servletContext.contextPath}/PersonaServlet";
                    document.getElementById("persona").submit();
                    toastr.success("persona Guardado Correctamente");
                    return true;
                }
            }

        </script> 
        <script>
            $.noConflict();
            jQuery(document).ready(function ($) {
                $('.AllDataTables').DataTable();
            });
        </script>
        <script>
            function validarEmpresa() {
                var nit = document.getElementById('nit').value;
                var nombre = document.getElementById('nombre').value;
                var sector = document.getElementById('sector').value;
                var actividadEconomica = document.getElementById('actividadEconomica').value;
                var antiguedad = document.getElementById('antiguedad').value;

                if (nit == "" && nombre == "" && sector == "" && actividadEconomica == "" && añosAntiguedad == "") {
                    toastr.error("No ha ingresado Nit Empresa", "Aviso!");
                    toastr.error("No ha ingresado Nombre", "Aviso!");
                    toastr.error("No ha ingresado Sector", "Aviso!");
                    toastr.error("No ha ingresado Actividad Economica", "Aviso!");
                    toastr.error("No ha ingresado Años Antiguedad Clave", "Aviso!");
                    return false;
                }
                if ($.trim(nit) == "") {
                    toastr.error("No ha ingresado NIT de la Empresa", "Aviso!");
                    return false;
                }
                if ($.trim(nombre) == "") {
                    toastr.error("No ha ingresado Nombre", "Aviso!");
                    return false;
                }
                if ($.trim(sector) == "") {
                    toastr.error("No ha ingresado Sector", "Aviso!");
                    return false;
                }
                if ($.trim(actividadEconomica) == "") {
                    toastr.error("No ha ingresado Actividad Economica", "Aviso!");
                    return false;
                }
                if ($.trim(antiguedad) == "") {
                    toastr.error("No ha ingresado Confirmar Años Antiguedad", "Aviso!");
                    return false;
                }
                if (!nit == "" && !nombre == "" && !sector == "" && !actividadEconomica == "" && !antiguedad == "") {

                    document.getElementById("crearEmpresa").action = "${pageContext.servletContext.contextPath}/EmpresaServlet";
                    document.getElementById("crearEmpresa").submit();
                    toastr.success("Empresa Guardado Correctamente");
                    return true;
                }

            }
        </script>
        <script>

            function myFunction() {
                document.getElementById("verEmpresa").action = "${pageContext.servletContext.contextPath}/EmpresaServlet";
                document.getElementById("verEmpresa").submit();
                return true;
            }
        </script> 
        <script>
            function validarCargo() {
                var codigoCargo = document.getElementById('codigoCargo').value;
                var nombreCargo = document.getElementById('nombreCargo').value;
                var riesgo = document.getElementById('riesgo').value;


                if (codigoCargo == "" && nombreCargo == "" && riesgo == "") {
                    toastr.error("No ha ingresado Codigo Cargo", "Aviso!");
                    toastr.error("No ha ingresado Nombre", "Aviso!");
                    toastr.error("No ha ingresado Riesgo", "Aviso!");
                    return false;
                }
                if ($.trim(codigoCargo) == "") {
                    toastr.error("No ha ingresado Codigo Cargo", "Aviso!");
                    return false;
                }
                if ($.trim(nombreCargo) == "") {
                    toastr.error("No ha ingresado Nombre", "Aviso!");
                    return false;
                }
                if ($.trim(riesgo) == "") {
                    toastr.error("No ha ingresado Riesgo", "Aviso!");
                    return false;
                }

                if (!codigoCargo == "" && !nombreCargo == "" && !riesgo == "") {

                    document.getElementById("crearC").action = "${pageContext.servletContext.contextPath}/CargoServlet";
                    document.getElementById("crearC").submit();
                    toastr.success("Cargo Guardado Correctamente");
                    return true;
                }

            }
        </script>
    </body>
</html>
