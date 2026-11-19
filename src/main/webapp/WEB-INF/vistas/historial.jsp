<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Historial" />
<%@ include file="/WEB-INF/jspf/cabecera.jspf" %>

<section class="panel">
    <h2>Historial por placa</h2>
    <p class="texto-suave">
        Consulte las estadías registradas para un vehículo.
    </p>

    <%@ include file="/WEB-INF/jspf/mensajes.jspf" %>

    <form class="formulario" method="get" action="${pageContext.request.contextPath}/historial">
        <div class="campo">
            <label for="placa">Placa</label>
            <input type="text"
                   id="placa"
                   name="placa"
                   maxlength="10"
                   pattern="[A-Za-z0-9-]{4,10}"
                   title="Entre 4 y 10 caracteres: letras, números o guiones"
                   placeholder="Ej. ABC-123"
                   value="<c:out value='${param.placa}' />"
                   required>
        </div>

        <div class="campo campo-accion">
            <button class="boton" type="submit">Buscar historial</button>
        </div>
    </form>
</section>

<c:if test="${not empty placaConsultada}">
    <section class="panel">
        <h2>Resultados para <c:out value="${placaConsultada}" /></h2>

        <c:choose>
            <c:when test="${empty estadias}">
                <p class="texto-vacio">No se encontraron estadías para esa placa.</p>
            </c:when>
            <c:otherwise>
                <div class="tabla-contenedor">
                    <table class="tabla">
                        <thead>
                        <tr>
                            <th>Placa</th>
                            <th>Tipo</th>
                            <th>Espacio</th>
                            <th>Entrada</th>
                            <th>Salida</th>
                            <th>Tiempo</th>
                            <th>Monto</th>
                            <th>Estado</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="estadia" items="${estadias}">
                            <tr>
                                <td><c:out value="${estadia.placa}" /></td>
                                <td><c:out value="${estadia.tipoVehiculo}" /></td>
                                <td><c:out value="${estadia.espacio}" /></td>
                                <td><c:out value="${estadia.fechaEntrada}" /></td>
                                <td><c:out value="${estadia.fechaSalida}" /></td>
                                <td><c:out value="${estadia.duracion}" /></td>
                                <td><c:out value="${estadia.monto}" /></td>
                                <td>
                                    <span class="etiqueta etiqueta-${estadia.estadoClase}">
                                        <c:out value="${estadia.estado}" />
                                    </span>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </section>
</c:if>

<%@ include file="/WEB-INF/jspf/pie.jspf" %>
