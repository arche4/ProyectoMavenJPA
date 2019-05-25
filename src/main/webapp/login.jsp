<%-- 
    Document   : login
    Created on : 27/11/2017, 01:28:57 PM
    Author     : jorge.lopez.torr
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Sofcoiso</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.6 -->
    
        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="dist/css/AdminLTE.min.css">
        <!-- Page style -->
        <link rel="stylesheet" href="dist/css/login.css">
        <link rel="stylesheet" href="dist/css/skins/skin-blue.min.css">

    </head>
    <body>
        <div class="wrapper">
            <div class="row" style="height: 100%; overflow: auto; overflow-x: hidden">
                <div class="col-xs-7 contLe">
                    <div id="formLog">
                        <div class="row display-table">
                            <div class="col-xs-6 display-cell" style="border-right: #002852 solid 3px; height: 100%">
                                <img src="images/logo.png" id="minLog">
                            </div>
                            <div class="col-xs-6 display-cell">
                                <h2 id="textApp">SofCoiso</h2>
                            </div>
                        </div>
                        <div class="row" id="formSect">
                            <form id="frmLogin" action="Login" id="frmLogin" method="POST" onsubmit="valUsPs();">
                                <div class="form-group">
                                    <label>Usuario</label>
                                    <input type="text" name="username" class="form-control" id="fmUser" placeholder="nombre.apellido">
                                </div>
                                <div class="form-group">
                                    <label>Clave</label>
                                    <input type="password" name="password" class="form-control" id="fmPass" placeholder="********">
                                </div>
                                <button type="submit" class="btn btn-primary" id="btnLog" href="home.jsp">Enviar</button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-xs-5 contRi">
                    <img src="img/Reporting.PNG" id="iconLog">
                </div>
            </div>
        </div>

        <!--Modal-->

        <div class="modal" id="modalUsPs">
            <div class="modal-dialog" style="top: 40%">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">x</span>
                        </button>
                        <h4 class="modal-title">Error</h4>
                    </div>
                    <div class="modal-body">
                        <p>Debe ingresar usuario y contrase&ntilde;a</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" id="btnErr" data-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal" id="modalLog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">x</span>
                        </button>
                        <h4 class="modal-title">Error</h4>
                    </div>
                    <div class="modal-body">
                        <p>El usuario y/o contrase&ntilde;a ingresados no son v&aacute;lidos</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" id="btnErr" data-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal" id="modalSkt">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">x</span>
                        </button>
                        <h4 class="modal-title">Error</h4>
                    </div>
                    <div class="modal-body">
                        <p>Se presentan problemas con el componente de comunicaci&oacute;n, por favor
                            contactar a un administrador.</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" id="btnErr" data-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal" id="modalSktN">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">x</span>
                        </button>
                        <h4 class="modal-title">Error</h4>
                    </div>
                    <div class="modal-body">
                        <p>Se ha presentado un error consultando la base de datos.</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" id="btnErr" data-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal" id="modalInf">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">x</span>
                        </button>
                        <h4 class="modal-title">Error</h4>
                    </div>
                    <div class="modal-body" id="modInf">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" id="btnErr" data-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal" id="modalNAut">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">x</span>
                        </button>
                        <h4 class="modal-title">Error</h4>
                    </div>
                    <div class="modal-body">
                        <p>Su usuario no se encuentra autorizado para ingresar a la aplicaci&oacute;n.</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" id="btnErr" data-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal" id="modalErr">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">x</span>
                        </button>
                        <h4 class="modal-title">Error</h4>
                    </div>
                    <div class="modal-body">
                        <p>Se ha presentado un error, por favor validar con un administrador.</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" id="btnErr" data-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- scripts -->
        <script src="plugins/jQuery/jquery-2.2.3.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="bootstrap/js/bootstrap.min.js"></script>
        <!-- AdminLTE App -->
        <script src="dist/js/app.min.js"></script>
        <!-- Funcionalidad js -->
        <script src="dist/js/login.js"></script>

        <% String errores = (String) request.getSession().getAttribute("errores");%> 
        <% if (errores != null) {%>
        <% if (errores.equals("Usuario o clave no válidos")) {%>
        <script>  
            $('#modalLog').modal('show');
        </script>
        <%} else if (errores.equals("Problema con el socket")) {%>
        <script>  
            $('#modalSkt').modal('show');
        </script>
        <%} else if (errores.equals("Respuesta no exitosa")) {%>
        <script>  
            $('#modalSktN').modal('show');
        </script>
        <%} else if (errores.equals("No autorizado")) {%>
        <script>  
            $('#modalNAut').modal('show');
        </script>
        <%} else {%> 
        <script>  
            $('#modalErr').modal('show');
        </script>
        <%}%>
        <% request.getSession().setAttribute("errores", null);%>
        <%}%>

    </body>
</html>

