<%-- 
    Document   : index
    Created on : 25-01-2018, 9:53:33
    Author     : betomedin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="template/header.jsp" %>
<div class="container ">
    <div class="row">
        <div class="col s6 center offset-l3">
            <h3>Login de Usuario</h3>
            <form class="" method="post" action="control.do">
                <label>
                    Usuario<br>
                    <input type="text" name="rut" value=""/>
                </label>
                <br>
                <label>
                    Password<br>
                    <input type="password" name="clave" value=""/>
                </label>
                <br><br>
                <label>
                    ${requestScope.msg}
                </label>
                <br><br>
                <label>
                    <button class="btn" name="boton" value="login">Ingresar</button>
                </label>
                <label>
                    <button class="btn btn-flat" name="boton" value="formRegistro">Registro</button>
                </label>
            </form>    
        </div>
    </div>
</div>

<%@include file="template/footer.jsp" %>

