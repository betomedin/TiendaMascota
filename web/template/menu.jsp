<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="nav-title">
    <div class="nav-wrapper container">
        <!--<a href="" class="brand-logo left">Sistema</a>-->
        <c:if test="${not empty sessionScope.admin}">
            <ul id="nav-mobile" class=" hide-on-med-and-down">
                <li><a href="#">Inicio</a></li>                                  
                <li><a href="misdatos.jsp">Mis datos</a></li>                                  
                <li><a href="categoria.jsp">Módulo Categorías</a></li>                                  
                <li><a href="producto.jsp">Módulo Productos</a></li>                                  
                <li><a href="#">Módulo Venta</a></li>                                  
            </ul>
        </c:if>
        <c:if test="${not empty sessionScope.person}">
            <ul id="nav-mobile" class=" hide-on-med-and-down">                
                <li><a href="#">Mis datos</a></li>                                  
                <li><a href="catalogo.jsp">Catálogo</a></li>                                  
                <li><a href="carrito.jsp">Carrito</a></li>                                  
                <li><a href="#">Mis compras</a></li>                                                  
            </ul>
        </c:if>
        <ul id="nav-mobile" class="right hide-on-med-and-down">
            <c:if test="${not empty sessionScope.admin}">
                <li><b>Bienvenido ${sessionScope.person.nombreUser}</b></li>
                </c:if>
                <c:if test="${not empty sessionScope.person}">
                <li><b>Bienvenido ${sessionScope.person.nombreUser}</b></li>
                </c:if>                           
            <li><a href="salir.jsp"><i class="material-icons">power_settings_new</i></a></li>                 
        </ul>
    </div>
</nav>

