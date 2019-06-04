<%@include file="/META-INF/jspf/header.jspf" %>
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
    <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
          page. However, you can choose any other skin. Make sure you
          apply the skin class to the body tag so the changes take effect.
    -->
    <link rel="stylesheet" href="dist/css/skins/skin-blue.min.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="shortcut icon" type="img/logo_S.png"  href="img/logo_S.png">
</head>

<body>
    <div class="wrapper">
        <div class="row" style="height: 100%; overflow: auto; overflow-x: hidden">
            <div class="col-xs-7 contLe" style="padding-top: 17em;">
                <div id="formLog">
                    <div class="row display-table">
                        <div class="col-xs-6 display-cell" style="border-right: #002852 solid 3px; height: 100%">
                            <img src="images/logo.png" id="minLog">
                        </div>
                        <div class="col-xs-6 display-cell">
                            <h2 id="textApp">SofCoiso</h2>
                        </div>
                    </div>
                    <span class="centrado" style="color:red;"> <c:out value="${sessionScope.MENSAJE}"/></span>

                    <div class="row" id="formSect">
                        <form id="frmLogin" method="post" 
                              action="${pageContext.servletContext.contextPath}/usuarioLogin">
                            <div class="form-group">
                                <label>Usuario</label>
                                <input class="form-control" type="text" id="fmUser" name="txtid" placeholder="Usuario">
                            </div>
                            <div class="form-group">
                                <label>Contraseña</label>
                                <input class="form-control" type="password" id="fmPass" name="txtclave" placeholder="Clave">
                            </div>
                        
                            <button class="btn btn-primary" id="btnLog" type="submit">Iniciar Sesion</button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-xs-5 contRi">
                <img src="img/Reporting.PNG" id="iconLog">
            </div>
        </div>
    </div>
                            
</body>

</html>
<!-- end document-->