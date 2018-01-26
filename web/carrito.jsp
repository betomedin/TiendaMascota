<%@include file="template/header.jsp" %>
<%@include file="template/menu.jsp" %>

<div class="container">
    <div class="row">
        <div class="col s12">
            <h3>Carrito de compras</h3>
        </div>
    </div>
    <div class="row">
        <div class="col s12">
            <c:if test="${empty sessionScope.carrito}">
                <div class="card-panel teal lighten-2">No se han agregado productos al carrito</div>
            </c:if>
            <c:if test="${not empty sessionScope.carrito}">
                <table class="bordered">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Precio</th>
                            <th>Unidad</th>
                            <th>Descripcion</th>
                            <th>Categoria</th>
                            <th>Foto</th>
                            <th>Stock</th>
                            <th>Compra</th>
                            <th>Accion</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${sessionScope.carrito}" var="p">
                            <tr>
                                <td>${p.idProducto}</td>
                                <td>${p.nombreProducto}</td>
                                <td>${p.precioProducto}</td>
                                <td>${servicio.getProducto(p.idProducto).unidadesProducto}</td>
                                <td>${p.descripcionProducto}</td>
                                <td>${p.categoria.nombreCategoria}</td>
                                <td><xx:TagImage array="${p.fotoProducto}" tam="50"/></td>
                                <td>${p.unidadesProducto}</td>
                                <td><input style="width: 50px;" value="${p.unidadesProducto}" type="number" min="0" max="${p.unidadesProducto}" ${(servicio.getProducto(p.idProducto).unidadesProducto < 1)?"readonly":""} /></td>
                                <td>
                                    <form method="post" action="control.do">
                                        <input type="hidden" name="id" value="${p.idProducto}"/>
                                        <button class="btn-floating" name="boton" value="eliminarcarrito">
                                            <i class="material-icons">delete</i>
                                        </button>
                                    </form>                                    
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
    <div class="row center">
        <div class="col s12">
            <form method="post" action="control.do">
                <button class="btn btn-large" name="boton" value="comprar">
                    <i class="material-icons">attach_money</i>
                    Comprar 
                    ${sessionScope.total}
                </button>
            </form>
        </div>
    </div>
</div>

<%@include file="template/footer.jsp" %>