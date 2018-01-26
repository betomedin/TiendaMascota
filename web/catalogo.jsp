<%@include file="template/header.jsp" %>
<%@include file="template/menu.jsp" %>

<c:set var="productos" scope="page" value="<%=servicio.getProductos()%>"/>

<div class="container">
    <div class="row">
        <div class="col s12">
            <h3>Catálogo de productos</h3>
        </div>
    </div>
    <div class="row">
        <c:forEach items="${pageScope.productos}" var="p">
            <div class="">
                <div class="col s12 m3">
                    <div class="card" style="height: 350px;">
                        <div class="card-image">
                            <xx:TagImage array="${p.fotoProducto}" tam="50"/>
                            <span class="card-title">${p.nombreProducto}</span>
                            <form method="post" action="control.do">
                                <input type="hidden" name="id" value="${p.idProducto}"/>
                                <button name="boton" value="agregarcarrito" class="btn-floating halfway-fab waves-effect waves-light red">
                                    <i class="material-icons">add</i>
                                </button>
                            </form>
                        </div>
                        <div class="card-content">
                            <p>${p.descripcionProducto}</p>
                            <b>Valor $ ${p.precioProducto}</b>
                            <p><small>Stock : ${p.unidadesProducto}</small></p>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%@include file="template/footer.jsp" %>