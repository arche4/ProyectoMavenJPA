
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
                                        Crear Formacion
                                    </button>
                                    <!-- The Modal -->
                                    <div class="modal fade" id="myModal">
                                        <div class="modal-dialog">
                                            <div class="modal-content" style="padding:40px 50px;">
                                                <!-- Modal Header -->
                                                <div class="modal-header">
                                                    <h4 class="modal-title">Crear Formacion</h4>
                                                    <button type="button" class="close" data-dismiss="modal">×</button>
                                                </div>
                                                <form method="post" name="formacion" id="formacion" action="">
                                                    <div class="form-row">
                                                        <div class="form-group col-md-6">
                                                            <input type="text" class="form-control" id="idFormacion" name="idFormacion"  placeholder="Id Formación">
                                                        </div>
                                                        <div class="form-group col-md-6">
                                                            <input type="text" class="form-control" id="tipoFormacion" name="tipoFormacion" placeholder="Tipo de Formación">
                                                        </div>
                                                    </div>
                                                        <div class="form-group">
                                                            <label for="formacion">Fecha  Formacion</label>
                                                            <input type="date" class="form-control" id="formacion" name="formacion" value="2011-08-08" id="example-month-input">
                                                        </div>
                                                     <div class="form-row">
                                                        <div class="form-group col-md-6">
                                                            <input type="text" class="form-control" id="tema" name="tema" placeholder="Tema">
                                                        </div>
                                                           <div class="form-group col-md-6">
                                                            <input type="text" class="form-control" id="nAsistentes" name="nAsistentes" placeholder="N° Ssistentes">
                                                        </div>
                                                     </div>
                                                     <div class="modal-footer">
                                                    <button name="crear" value="crear" type="submit" class="btn btn-success" onclick="return validar()">Guardar</button>
                                                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                                                </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>  
                            </div>  

                            <div class="col-lg-12">
                                <div class="card">
                                    <div class="card-header">
                                        <strong>Formaciones</strong> 
                                    </div>
                                    <table id="table_id" class="display AllDataTables">
                                        <thead>
                                            <tr>
                                                <th scope="col">Accion</th>
                                                <th scope="col">ID</th>
                                                <th scope="col">Formación</th>
                                                <th scope="col">Fecha</th>
                                                 <th scope="col">Temas</th>
                                                <th scope="col">N° Asistentes</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="formacion" items="${sessionScope.formacion}" varStatus="myIndex">
                                                <tr>
                                                    <td>
                                                        <form  method="post" action="${pageContext.servletContext.contextPath}/FormacionServlet">
                                                            <button name="verFormacion" value="${formacion.getIdFormacion()}" type="submit" class="btn btn-primary">Ver</button>
                                                        </form>
                                                    </td> 
                                                    <td><c:out value="${formacion.getIdFormacion()}"/></td>
                                                    <td><c:out value="${formacion.getTipoFormacion()}"/></td>
                                                    <td><c:out value="${formacion.getFechaFormacion()}"/></td>
                                                    <td><c:out value="${formacion.getTemas()}"/></td>
                                                    <td><c:out value="${formacion.getNumeroAsistentes()}"/></td>
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

                var idFormacion = document.getElementById('idFormacion').value;
                var tipoFormacion = document.getElementById('tipoFormacion').value;
                var tema = document.getElementById('tema').value;
                var nAsistentes = document.getElementById('nAsistentes').value;

                if (idFormacion == "" && tipoFormacion == "" && tema == "" && nAsistentes == "") {
                    toastr.error("No ha ingresado id Formacion", "Aviso!");
                    toastr.error("No ha ingresado Tipo Formacion", "Aviso!");
                    toastr.error("No ha ingresado Tema", "Aviso!");
                    toastr.error("No ha ingresado N° Asistentes", "Aviso!");
                    return false;
                }
                if ($.trim(idFormacion) == "") {
                    toastr.error("No ha ingresado id Formacion", "Aviso!");
                    return false;
                }
                if ($.trim(tipoFormacion) == "") {
                    toastr.error("No ha ingresado Tipo Formacion", "Aviso!");
                    return false;
                }
                if ($.trim(tema) == "") {
                    toastr.error("No ha ingresado Tema", "Aviso!");
                    return false;
                }
                if ($.trim(nAsistentes) == "") {
                    toastr.error("No ha ingresado N° Asistentes", "Aviso!");
                    return false;
                }
                
                if (!tipoFormacion == "" && !idFormacion == "" && !tema == "" && !nAsistentes == "") {

                    document.getElementById("formacion").action = "${pageContext.servletContext.contextPath}/FormacionServlet";
                    document.getElementById("formacion").submit();
                    toastr.success("Formacion Guardado Correctamente");
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
