<%@include file="template/header.jsp" %>
<%@include file="template/menu.jsp" %>

<div class="container">
    <div class="row">
        <div class="col s12">
            <h3>Mis datos</h3>
        </div>
    </div>
    <div class="row">
        <div class="col s12">
            <form method="post" action="control.do">
                <label>Rut</label><input type="text" name="rut" readonly="" value="${(sessionScope.admin == null)?sessionScope.person.rutUser:sessionScope.admin.rutUser}"/>
                <label>Nombre</label><input type="text" name="nombre" readonly="" value="${(sessionScope.admin == null)?sessionScope.person.nombreUser:sessionScope.admin.nombreUser}"/>
                <label>Apellido</label><input type="text" name="apellido" readonly="" value="${(sessionScope.admin == null)?sessionScope.person.apellidoUser:sessionScope.admin.apellidoUser}"/>
                <label>Correo (actual : ${(sessionScope.admin == null)?sessionScope.person.emailUser:sessionScope.admin.emailUser}) <small></small></label><input type="text" name="email"/>
                <label>Clave</label><input type="text" name="clave1"/>
                <label>Confirmar clave</label><input type="text" name="clave2"/>
                <label><button class="btn" name="boton" value="actualizarusuario">Actualizar</button></label>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col s12">
            ${requestScope.msg}
        </div>
    </div>
</div>

<%@include file="template/footer.jsp" %>