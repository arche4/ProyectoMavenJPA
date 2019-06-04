
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>

<html>
        <%@include file="/META-INF/jspf/header.jspf" %>

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
                                        Crear Usuario
                                    </button>
                                    <!-- The Modal -->
                                    <div class="modal fade" id="myModal">
                                        <div class="modal-dialog">
                                            <div class="modal-content" style="padding:40px 50px;">
                                                <!-- Modal Header -->
                                                <div class="modal-header">
                                                    <h4 class="modal-title">Crear Usuarios</h4>
                                                    <button type="button" class="close" data-dismiss="modal">×</button>
                                                </div>
                                                <form method="post" name="usuario" id="usuario"
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
                                                            <select name="rol" id="inputState" 
                                                                    class="form-control">
                                                                <option value="Administrador" selected>Administrador</option>
                                                                <option value="Coordinador" selected>Coordinador</option>
                                                            </select>
                                                        </div>

                                                    </div>
                                                    <div class="form-row">
                                                        <div class="form-group col-md-6">
                                                            <input type="password" class="form-control" id="clave" name="clave" placeholder="Contraseña">
                                                        </div>
                                                        <div class="form-group col-md-6">
                                                            <input type="password" class="form-control" id="comfirmarClave" name="comfirmarClave" placeholder="Comfirmar Contraseña">
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
                                        <strong>Usuarios</strong> 
                                    </div>
                                    <table id="table_id" class="display AllDataTables">
                                        <thead>
                                            <tr>
                                                <th scope="col">Accion</th>
                                                <th scope="col">ID</th>
                                                <th scope="col">Nombre</th>
                                                <th scope="col">Apellidos</th>
                                                <th scope="col">rol</th>
                                                
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="usuario" items="${sessionScope.Usuario}" varStatus="myIndex">
                                                <tr>
                                                      <td>
                                                        <form  method="post" action="${pageContext.servletContext.contextPath}/UsuarioServlet">
                                                            <button name="editar"   value="${usuario.getIdUsuario()}" type="submit" class="btn btn-primary">Ver</button>
                                                        </form>
                                      
                                                    </td> 
                                                    <td><c:out value="${usuario.getIdUsuario()}"/></td>
                                                    <td><c:out value="${usuario.getUsuarioNombre()}"/></td>
                                                    <td><c:out value="${usuario.getUsuarioApellido()}"/></td>
                                                    <td><c:out value="${usuario.getRolUsuario()}"/></td>
                                                  
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                           <div class="modal fade" id="editar">
                                        <div class="modal-dialog">
                                            <div class="modal-content" style="padding:40px 50px;">
                                                <!-- Modal Header -->
                                                <div class="modal-header">
                                                    <h4 class="modal-title">Crear Usuarios</h4>
                                                    <button type="button" class="close" data-dismiss="modal">×</button>
                                                </div>
                                        <c:forEach var="usuario" items="${sessionScope.Usuario}">
                                            <c:choose>
                                                <c:when test="${usuario.getIdUsuario() == sessionScope.documento}">
                                                    <form  method="post" action="${pageContext.servletContext.contextPath}/UsuariosServlet">
                                                        <div class="form-row">
                                                            <div class="form-group col-md-7">
                                                                <input  name="" disabled type="text" value="<c:out value='${usuario.getIdUsuario()}' />">
                                                            </div>
                                                            <div class="form-group col-md-7">
                                                                <input name="txtnombres" type="text" value="<c:out value='${usuario.getUsuarioNombre()}'/>">
                                                            </div>
                                                        </div>
                                                        <div class="form-row">
                                                            <div class="form-group col-md-6">
                                                                <input name="txtapellidos" type="text" value="<c:out value='${usuario.getUsuarioApellido()}'/>">
                                                            </div>
                                                            <div class="form-group col-md-6">
                                                                <input name="txtemail" type="text" value="<c:out value='${usuario.getRolUsuario()}'/>">

                                                            </div>

                                                        </div>

                                                    </form>
                                                </c:when>
                                                <c:otherwise>

                                                </c:otherwise>
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

    <script type="text/javascript">
        function validar()
        {

            var cedula = document.getElementById('cedula').value;
            var nombre = document.getElementById('nombre').value;
            var apellido = document.getElementById('apellido').value;
            var clave = document.getElementById('clave').value;
            var comfirmarClave = document.getElementById('comfirmarClave').value;

            if (nombre == "" && cedula == "" && apellido == "" && clave == "" && comfirmarClave == "") {
                toastr.error("No ha ingresado Cedula", "Aviso!");
                toastr.error("No ha ingresado Nombre", "Aviso!");
                toastr.error("No ha ingresado Apellido", "Aviso!");
                toastr.error("No ha ingresado Clave", "Aviso!");
                toastr.error("No ha ingresado Confirmar Clave", "Aviso!");
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
            if ($.trim(clave) == "") {
                toastr.error("No ha ingresado Clave", "Aviso!");
                return false;
            }
            if ($.trim(comfirmarClave) == "") {
                toastr.error("No ha ingresado Confirmar Clave", "Aviso!");
                return false;
            }
            if (!nombre == "" && !cedula == "" && !apellido == "" && !clave == "" && !comfirmarClave == "") {
                if (clave != comfirmarClave) {
                    toastr.error("Las contraseñas deben de coincidir");

                    return false;
                } else {
                    document.getElementById("usuario").action = "${pageContext.servletContext.contextPath}/UsuarioServlet";
                    document.getElementById("usuario").submit();
                    toastr.success("Usuario Guardado Correctamente");
                    return true;
                }

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
