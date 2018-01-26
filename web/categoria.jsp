<%@include file="template/header.jsp" %>
<%@include file="template/menu.jsp" %>

<!--La variable 'servicio' se inicializa en el Initial Context que está en header.jsp-->
<c:set var="categorias" scope="page" value="<%=servicio.getCategorias()%>"/>

<div class="container">
    <h3>Agregar categoría</h3>
    <div class="row">
        <div class="col s12">
            <form method="post" action="control.do">
                <label>Nueva Categoría</label>
                <input type="text" name="nombre"/>
                <button class="btn" name="boton" value="nuevacategoria">Crear</button>
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
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${pageScope.categorias}" var="c">
                        <tr>
                            <td>${c.idCategoria}</td>
                            <td>${c.nombreCategoria}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@include file="template/footer.jsp" %>
