<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Ingreso" />
<%@ include file="/WEB-INF/jspf/cabecera.jspf" %>

<section class="panel">
    <h2>Registrar ingreso</h2>
    <p class="texto-suave">
        El sistema asigna automáticamente un espacio libre según el tipo de vehículo.
    </p>

    <%@ include file="/WEB-INF/jspf/mensajes.jspf" %>

    <c:if test="${not empty estadia}">
        <div class="recibo">
            <h3>Datos del ingreso</h3>
            <dl class="datos">
                <div>
                    <dt>Placa</dt>
                    <dd><c:out value="${estadia.placa}" /></dd>
                </div>
                <div>
                    <dt>Tipo</dt>
                    <dd><c:out value="${estadia.tipoVehiculo}" /></dd>
                </div>
                <div>
                    <dt>Espacio asignado</dt>
                    <dd><c:out value="${estadia.espacio}" /></dd>
                </div>
                <div>
                    <dt>Fecha de entrada</dt>
                    <dd><c:out value="${estadia.fechaEntrada}" /></dd>
                </div>
            </dl>
        </div>
    </c:if>

    <form class="formulario" method="post" action="${pageContext.request.contextPath}/ingreso">
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

        <div class="campo">
            <label for="tipo">Tipo de vehículo</label>
            <select id="tipo" name="tipo" required>
                <option value="CARRO" ${param.tipo == 'CARRO' ? 'selected' : ''}>Carro</option>
                <option value="MOTO" ${param.tipo == 'MOTO' ? 'selected' : ''}>Moto</option>
            </select>
        </div>

        <div class="campo campo-accion">
            <button class="boton" type="submit">Registrar ingreso</button>
        </div>
    </form>
</section>

<%@ include file="/WEB-INF/jspf/pie.jspf" %>
