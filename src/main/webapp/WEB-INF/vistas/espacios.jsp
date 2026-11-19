<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Espacios" />
<%@ include file="/WEB-INF/jspf/cabecera.jspf" %>

<section class="panel">
    <h2>Espacios de parqueo</h2>
    <p class="texto-suave">
        Registre un nuevo espacio y consulte cuáles están libres u ocupados.
    </p>

    <%@ include file="/WEB-INF/jspf/mensajes.jspf" %>

    <form class="formulario" method="post" action="${pageContext.request.contextPath}/espacios">
        <div class="campo">
            <label for="codigo">Código</label>
            <input type="text"
                   id="codigo"
                   name="codigo"
                   maxlength="10"
                   pattern="[A-Za-z0-9-]{2,10}"
                   title="Entre 2 y 10 caracteres: letras, números o guiones"
                   placeholder="Ej. C-04"
                   required>
        </div>

        <div class="campo">
            <label for="tipo">Tipo de vehículo</label>
            <select id="tipo" name="tipo" required>
                <option value="CARRO">Carro</option>
                <option value="MOTO">Moto</option>
            </select>
        </div>

        <div class="campo campo-accion">
            <button class="boton" type="submit">Registrar espacio</button>
        </div>
    </form>
</section>

<section class="panel">
    <h2>Listado de espacios</h2>

    <c:choose>
        <c:when test="${empty espacios}">
            <p class="texto-vacio">No hay espacios registrados.</p>
        </c:when>
        <c:otherwise>
            <div class="tabla-contenedor">
                <table class="tabla">
                    <thead>
                    <tr>
                        <th>Código</th>
                        <th>Tipo</th>
                        <th>Estado</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="espacio" items="${espacios}">
                        <tr>
                            <td><c:out value="${espacio.codigo}" /></td>
                            <td><c:out value="${espacio.tipo}" /></td>
                            <td>
                                <c:choose>
                                    <c:when test="${espacio.estado == 'LIBRE'}">
                                        <span class="etiqueta etiqueta-libre">Libre</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="etiqueta etiqueta-ocupado">Ocupado</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</section>

<%@ include file="/WEB-INF/jspf/pie.jspf" %>
