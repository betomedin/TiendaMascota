<%@include file="template/header.jsp" %>
<%@include file="template/menu.jsp" %>

<!--La variable 'servicio' se inicializa en el Initial Context que está en header.jsp-->
<c:set var="productos" scope="page" value="<%=servicio.getProductos()%>"/>
<c:set var="categorias" scope="page" value="<%=servicio.getCategorias()%>"/>

<div class="container">
    <h3>Agregar productos</h3>
    <div class="row">
        <div class="col s12">
            <form method="post" action="control.do" enctype="multipart/form-data">
                <label>Nombre</label>
                <input type='text' name='nombre'/>
                <label>Precio</label>
                <input type='text' name='precio'/>
                <label>Unidad</label>
                <input type='text' name='unidad'/>
                <label>Descripcion</label>
                <textarea class='materialize-textarea' name='descripcion'></textarea>
                <label>Categoria</label>
                <select name='categoria'>
                    <c:forEach items="${pageScope.categorias}" var="c">
                        <option value='${c.idCategoria}'>${c.nombreCategoria}</option>
                    </c:forEach>
                </select>
                
                <div class="file-field input-field">
                    <div class="btn">
                        <span>foto</span>
                        <input type="file" name='foto'>
                    </div>
                    <div class="file-path-wrapper">
                        <input class="file-path validate" type="text">
                    </div>
                </div>
                
                <br><br><br>
                <button class="btn" name="boton" value="nuevoproducto">Crear</button>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col s12">
            ${requestScope.msg}
        </div>
    </div>
    <div class="row">
        <div class="col s12">
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
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${pageScope.productos}" var="p">
                        <tr>
                            <td>${p.idProducto}</td>
                            <td>${p.nombreProducto}</td>
                            <td>${p.precioProducto}</td>
                            <td>${p.unidadesProducto}</td>
                            <td>${p.descripcionProducto}</td>
                            <td>${p.categoria.nombreCategoria}</td>
                            <td><xx:TagImage array="${p.fotoProducto}" tam="50"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@include file="template/footer.jsp" %>
