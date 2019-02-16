
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>

<html>
    <%@include file="/META-INF/jspf/header.jspf" %>

    <script>
        $('#editar').modal({show: true});
    </script>
    <body>
        <%@include file="/META-INF/jspf/navmenu.jspf" %>
        <main role="main">
            <div class="jumbotron">
                <div class="container">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-lg-2">
                                <div class="card">

                                    <!-- Button to Open the Modal -->
                                    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#myModal">
                                        Crear Asistente
                                    </button>
                                    <!-- The Modal -->
                                    <div class="modal fade" id="myModal">
                                        <div class="modal-dialog">
                                            <div class="modal-content" style="padding:40px 50px;">
                                                <!-- Modal Header -->
                                                <div class="modal-header">
                                                    <h4 class="modal-title">Crear Asistente</h4>
                                                    <button type="button" class="close" data-dismiss="modal">×</button>
                                                </div>
                                                <form method="post" name="asistente" id="asistente"
                                                      action="">
                                                    <div class="form-row">
                                                        <div class="form-group col-md-6">
                                                            <input type="text" class="form-control" id="cedula" name="cedula"  placeholder="Cedula">
                                                        </div>
                                                        <div class="form-group col-md-6">
                                                            <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre">
                                                        </div>
                                                    </div>
                                                    <div class="form-row">
                                                        <div class="form-group col-md-6">
                                                            <input type="text" class="form-control" id="apellido" name="apellido"  placeholder="Apellidos">
                                                        </div>
                                                        <div class="form-group col-md-6">
                                                            <input type="text" class="form-control" id="celular" name="celular"  placeholder="Celular">
                                                        </div>
                                                    </div>
                                                    <div class="form-row">
                                                        <div class="form-group col-md-6">
                                                            <input type="text" class="form-control" id="telefono" name="telefono"  placeholder="Telefono">
                                                        </div>
                                                        <div class="form-group col-md-6">
                                                            <input type="text" class="form-control" id="correo" name="correo"  placeholder="Correo">
                                                        </div>
                                                    </div>
                                                    <div class="form-row">
                                                        <div class="form-group col-md-6">
                                                            <input type="text" class="form-control" id="asistencia" name="asistencia"  placeholder="Asistencia">
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button name="accion" value="crear" type="submit" class="btn btn-success" onclick="return validar()">Guardar</button>
                                                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                                                    </div>
                                                </form>
                                                <!-- Modal footer -->

                                            </div>
                                        </div>
                                    </div>
                                </div>  
                            </div>  

                            <div class="col-lg-12">
                                <div class="card">
                                    <div class="card-header">
                                        <strong>Asistente</strong> 
                                    </div>
                                    <table id="table_id" class="display AllDataTables">
                                        <thead>
                                            <tr>
                                                <th scope="col">Ver</th>
                                                <th scope="col">Cedula</th>
                                                <th scope="col">Nombre</th>
                                                <th scope="col">Apellidos</th>
                                                <th scope="col">celular</th>
                                                <th scope="col">Telefono</th>
                                                <th scope="col">Correo</th>
                                                <th scope="col">Asistencia</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="asistente" items="${sessionScope.asistente}" varStatus="myIndex">
                                                <tr>
                                                    <td>
                                                        <form  method="post" action="${pageContext.servletContext.contextPath}/AsistentesServlet">
                                                            <button name="verAsistente" value="${asistente.getCedulaPersona()}" type="submit" class="btn btn-primary">Ver</button>
                                                        </form>
                                                    </td> 
                                                    <td><c:out value="${asistente.getCedulaPersona()}"/></td>
                                                    <td><c:out value="${asistente.getNombrePersona()}"/></td>
                                                    <td><c:out value="${asistente.getApellidoPersona()}"/></td>
                                                    <td><c:out value="${asistente.getCelular()}"/></td>
                                                    <td><c:out value="${asistente.getTelefono()}"/></td>
                                                    <td><c:out value="${asistente.getCorreo()}"/></td>
                                                    <td><c:out value="${asistente.getAsistencia()}"/></td>
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
        </main> 

        <script type="text/javascript">
            function validar()
            {

                var cedula = document.getElementById('cedula').value;
                var nombre = document.getElementById('nombre').value;
                var apellido = document.getElementById('apellido').value;
                var celular = document.getElementById('celular').value;
                var correo = document.getElementById('correo').value;
                var asistencia = document.getElementById('asistencia').value;

                if (nombre == "" && cedula == "" && apellido == "" && celular == "" && correo == "" && asistencia == "") {
                    toastr.error("No ha ingresado Cedula", "Aviso!");
                    toastr.error("No ha ingresado Nombre", "Aviso!");
                    toastr.error("No ha ingresado Apellido", "Aviso!");
                    toastr.error("No ha ingresado Celular", "Aviso!");
                    toastr.error("No ha ingresado Correo", "Aviso!");
                    toastr.error("No ha ingresado Asistencia", "Aviso!");
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
                if ($.trim(apellido) == "") {
                    toastr.error("No ha ingresado Apellido", "Aviso!");
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
                if ($.trim(asistencia) == "") {
                    toastr.error("No ha ingresado Asistencia", "Aviso!");
                    return false;
                }
                if (!nombre == "" && !cedula == "" && !apellido == "" && !celular == "" && !correo == "" && !asistencia == "") {
                    document.getElementById("asistente").action = "${pageContext.servletContext.contextPath}/AsistentesServlet";
                    document.getElementById("asistente").submit();
                    toastr.success("Usuario Guardado Correctamente");
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
    </body>
</html>
