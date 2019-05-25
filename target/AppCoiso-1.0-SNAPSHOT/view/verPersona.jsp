
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
                                        <strong>Ver Persona</strong> 
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
                                                                <input type="text" class="form-control" id="nombre" name="nombre" value="<c:out value='${persona.getNombre()}'/>">
                                                            </div>
                                                            <div class="form-group col-md-4">
                                                                <label for="apellido" class="control-label mb-1">Primer Apellido</label>
                                                                <input type="text" class="form-control" id="primerApellido" name="primerApellido"  value="<c:out value='${persona.getApellidouno()}'/>">
                                                            </div>
                                                        </div>
                                                        <div class="form-row">
                                                            <div class="form-group col-md-4">
                                                                <label for="apellidodos" class="control-label mb-1">Segundo Apellido</label>
                                                                <input type="text" class="form-control" id="segundoApellido" name="segundoApellido"  value="<c:out value='${persona.getApellidodos()}'/>">
                                                            </div>
                                                            <div class="form-group col-md-4">
                                                                <label for="genero" class="control-label mb-1">Genero</label>
                                                                <input type="text" class="form-control" id="genero" name="genero"  value="<c:out value='${persona.getGenero()}'/>">
                                                            </div>
                                                            <div class="form-group col-md-4">
                                                                <label for="Fecha" class="control-label mb-1">Fecha Nacimiento</label>
                                                                <input type="text" class="form-control" id="cumpleaños" name="cumpleaños"  value="<c:out value='${persona.getFechanacimiento()}'/>">
                                                            </div>
                                                        </div>
                                                        <div class="form-row">
                                                            <div class="form-group col-md-4">
                                                                <label for="telefono" class="control-label mb-1">Telefono</label>
                                                                <input type="text" class="form-control" name="telefono" id="telefono" value="<c:out value='${persona.getTelefonofijo()}'/>">
                                                            </div>
                                                            <div class="form-group col-md-4">
                                                                <label for="celular" class="control-label mb-1">Celular</label>
                                                                <input type="text" class="form-control" name="celular"  id="celular" value="<c:out value='${persona.getCelular()}'/>">
                                                            </div>
                                                        </div>
                                                        <div class="form-row">
                                                            <div class="form-group col-md-6">
                                                                <label for="correo" class="control-label mb-1">Correo</label>
                                                                <input type="text" class="form-control" name="correo" id="correo" value="<c:out value='${persona.getCorreo()}'/>">
                                                            </div>
                                                            <div class="form-group col-md-6">
                                                                <label for="direccion" class="control-label mb-1">Dirección</label>
                                                                <input type="text" class="form-control" name="direccion" id="direccion" value="<c:out value='${persona.getDireccion()}'/>">
                                                            </div>
                                                        </div>
                                                        <h3>Datos Empresa Y Salud</h3>
                                                        <div class="form-row">
                                                            <div class="form-group col-md-4">
                                                                <label for="eps"  class="control-label mb-1">Eps</label>
                                                                <select name="eps"class="form-control-sm form-control">
                                                                    <option value="<c:out value='${persona.getCodigoepsFk().getCodigoeps()}'/>"><c:out value="${persona.getCodigoepsFk().getNombre()}"/></option>
                                                                    <c:forEach var="eps" items="${sessionScope.EPS}">
                                                                        <option value="${eps.getCodigoeps()}"><c:out value="${eps.getNombre()}"/></option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <div class="form-group col-md-4">
                                                                <label for="arl" class="control-label mb-1">Arl</label>
                                                                <select name="arl"class="form-control-sm form-control">
                                                                    <option value="<c:out value='${persona.getCodigoarlFk().getCodigoarl()}'/>"><c:out value="${persona.getCodigoarlFk().getNombre()}"/></option>
                                                                    <c:forEach var="arl" items="${sessionScope.ARL}">
                                                                        <option value="${arl.getCodigoarl()}"><c:out value="${arl.getNombre()}"/></option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <div class="form-group col-md-4">
                                                                <label for="afp" class="control-label mb-1">Afp</label>
                                                                <select name="afp"class="form-control-sm form-control">
                                                                    <option value="<c:out value='${persona.getCodigoafpFk().getCodigoafp()}'/>"><c:out value="${persona.getCodigoafpFk().getNombre()}"/></option>
                                                                    <c:forEach var="afp" items="${sessionScope.AFP}">
                                                                        <option value="${afp.getCodigoafp()}"><c:out value="${afp.getNombre()}"/></option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>           
                                                            <div class="form-group col-md-4">
                                                                <label for="profesion" class="control-label mb-1">Profesión</label>
                                                                <input type="text" class="form-control" id="profesion" name="profesion" value="<c:out value='${persona.getCodigoprofesionFk()}'/>">
                                                            </div>

                                                            <div class="form-group col-md-4">

                                                                <label for="empresa"  class="control-label mb-1">Empresa</label>
                                                                <c:forEach var="empresa" items="${sessionScope.empresa}">
                                                                    <c:choose>
                                                                        <c:when test="${empresa.getCedulaFk().getCedula() == sessionScope.cedula}">
                                                                            <input type="text" disabled class="form-control" name ="" value="<c:out value='${empresa.getNitempresaFk().getNombre()}'/>">
                                                                            <input name="empresa" type="hidden" value="<c:out value='${empresa.getNitempresaFk().getNit()}' />">
                                                                        </c:when>
                                                                    </c:choose>
                                                                </c:forEach>
                                                            </div>
                                                            <div class="form-group col-md-4">
                                                                <label for="cargo"  class="control-label mb-1">Cargo</label>
                                                                <input type="text" disabled class="form-control"  name="" value="<c:out value='${persona.getCargoCodigocargo().getNombre()}'/>">
                                                                <input name="cargo" type="hidden" value="<c:out value='${persona.getCargoCodigocargo().getCodigocargo()}' />">
                                                            </div>

                                                            <div class="form-group col-md-4">
                                                                <label for="area" class="control-label mb-1">Area</label>
                                                                <input type="text" class="form-control" id="area" name="area" value="<c:out value='${persona.getArea()}'/>">
                                                            </div>
                                                            <div class="form-group col-md-4">
                                                                <label for="anosExperiencia" class="control-label mb-1">Años Experiencia</label>
                                                                <input type="text" class="form-control" name="anosExperiencia" id="anosExperiencia" value="<c:out value='${persona.getAnosExperiencia()}'/>">
                                                            </div>
                                                            <div class="form-group col-md-4">
                                                                <label for="fechaClinica" class="control-label mb-1">Fecha Clinica</label>
                                                                <input type="text" class="form-control" name="fechaClinica" id="fechaClinica" value="<c:out value='${persona.getFechaClinica()}'/>">
                                                            </div>
                                                            <div class="form-group col-md-6">
                                                                <label for="recomendado" class="control-label mb-1">Recomendado</label>
                                                                <input type="text" class="form-control" name="recomendado" id="recomendado" value="<c:out value='${persona.getRecomendado()}'/>">
                                                            </div>
                                                        </div>
                                                        <h3>Datos Caso</h3>
                                                        <div class="form-row">
                                                            <c:forEach var="caso" items="${sessionScope.caso}">
                                                                <c:choose>
                                                                    <c:when test="${caso.getIdCaso() == sessionScope.casoid}">
                                                                        <div class="form-group col-md-4">
                                                                            <input type="idCaso" disabled class="form-control" name=""  value="<c:out value='${caso.getIdCaso()}'/>">
                                                                            <input name="idCaso" type="hidden" value="<c:out value='${caso.getIdCaso()}' />">
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group">                       
                                                                        <label for="textarea-input" class=" form-control-label">Descripcion Del Caso</label>
                                                                        <textarea name="descripcionCaso" id="descripcionCaso" rows="7" class="form-control"><c:out value='${caso.getDescripcionCaso()}'/></textarea>                                     
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label for="InicioAfectacion">Fecha de Inicio de la Afectacion</label>
                                                                        <input type="month" class="form-control" id="InicioAfectacion" name="InicioAfectacion" value="<c:out value='${caso.getFechaInicioAfectacion()}'/>" id="example-month-input">
                                                                    </div>
                                                                    <div class="form-group">                       
                                                                        <label for="textarea-input" class=" form-control-label">Origen del Diactamen</label>
                                                                        <textarea name="origenDictamen" id="origenDictamen" rows="7"  class="form-control"><c:out value='${caso.getOrigenDictamen()}'/></textarea>                                     
                                                                    </div>
                                                                    <div class="form-row">
                                                                        <div class="form-group col-md-4">
                                                                            <label for="pcl" class="control-label mb-1">PCL</label>
                                                                            <input type="text" class="form-control" id="pcl" name="pcl" value="<c:out value='${caso.getPcl()}'/>">
                                                                        </div>
                                                                        <div class="form-group col-md-4">
                                                                            <label for="parteAfectada" class="control-label mb-1">parte Afectada</label>
                                                                            <input type="text" class="form-control" id="parteAfectada" name="parteAfectada"  value="<c:out value='${caso.getParteAfectada()}'/>">
                                                                        </div>
                                                                        <div class="form-group col-md-4">
                                                                            <label for="cedula">Teimpo de Incapacidad</label>
                                                                            <input type="text" class="form-control" id="tiempoIncapacidad" name="tiempoIncapacidad"  value="<c:out value='${caso.getTiempoIncapacidad()}'/>">
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group">                       
                                                                        <label for="textarea-input" class=" form-control-label">Observaciones</label>
                                                                        <textarea name="observaciones" id="observaciones" rows="7" class="form-control"><c:out value='${caso.getObservacion()}'/></textarea>                                     
                                                                    </div>
                                                                    <div class="col">
                                                                        <button name="accion" value="editar" type="submit" class="btn btn-primary">Guardar Cambios</button>

                                                                    </div>
                                                                </c:when>
                                                            </c:choose>
                                                        </c:forEach>

                                                    </form>
                                                </div>
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

</body>
</html>
