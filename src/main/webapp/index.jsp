<!DOCTYPE html>
<html lang="en">

    <%@include file="/META-INF/jspf/header.jspf" %>

    <body class="animsition">
        <div class="page-wrapper">
            <div class="page-content--bge5">
                <div class="container">
                    <div class="login-wrap">
                        <div class="login-content">
                            <div class="login-logo">
                                <a href="#">
                                    <img src="images/logo.png" alt=""/>
                                </a>
                            </div>
                            <span class="centrado" style="color:red;"> <c:out value="${sessionScope.MENSAJE}"/></span>
                           
                            <div class="login-form">
                                <form class="form-signin" method="post" 
                                      action="${pageContext.servletContext.contextPath}/usuarioLogin">
                                    <div class="form-group">
                                        <label>Usuario</label>
                                        <input class="au-input au-input--full" type="text" name="txtid" placeholder="Usuario">
                                    </div>
                                    <div class="form-group">
                                        <label>Contraseña</label>
                                        <input class="au-input au-input--full" type="password" name="txtclave" placeholder="Clave">
                                    </div>
                                    <hr class="line-seprate">
                                    <button class="au-btn au-btn--block au-btn--green m-b-20" type="submit">Iniciar Sesion</button>
                                </form>


                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>



        <!-- Main JS-->

    </body>

</html>
<!-- end document-->