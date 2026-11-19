<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Salida" />
<%@ include file="/WEB-INF/jspf/cabecera.jspf" %>

<section class="panel">
    <h2>Registrar salida</h2>
    <p class="texto-suave">
        Seleccione un vehículo para registrar su salida y calcular el monto a pagar.
    </p>

    <%@ include file="/WEB-INF/jspf/mensajes.jspf" %>

    <c:if test="${not empty salida}">
        <div class="recibo">
            <h3>Comprobante de salida</h3>
            <dl class="datos">
                <div>
                    <dt>Placa</dt>
                    <dd><c:out value="${salida.placa}" /></dd>
                </div>
                <div>
                    <dt>Espacio liberado</dt>
                    <dd><c:out value="${salida.espacio}" /></dd>
                </div>
                <div>
                    <dt>Fecha de entrada</dt>
                    <dd><c:out value="${salida.fechaEntrada}" /></dd>
                </div>
                <div>
                    <dt>Fecha de salida</dt>
                    <dd><c:out value="${salida.fechaSalida}" /></dd>
                </div>
                <div>
                    <dt>Tiempo de permanencia</dt>
                    <dd><c:out value="${salida.duracion}" /></dd>
                </div>
                <div>
                    <dt>Monto pagado</dt>
                    <dd class="monto"><c:out value="${salida.monto}" /></dd>
                </div>
            </dl>
        </div>
    </c:if>
</section>

<section class="panel">
    <h2>Vehículos dentro del parqueo</h2>

    <c:choose>
        <c:when test="${empty estadias}">
            <p class="texto-vacio">No hay vehículos con estadías activas.</p>
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
                        <th>Tiempo</th>
                        <th>Acción</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="estadia" items="${estadias}">
                        <tr>
                            <td><c:out value="${estadia.placa}" /></td>
                            <td><c:out value="${estadia.tipoVehiculo}" /></td>
                            <td><c:out value="${estadia.espacio}" /></td>
                            <td><c:out value="${estadia.fechaEntrada}" /></td>
                            <td><c:out value="${estadia.duracion}" /></td>
                            <td>
                                <form class="formulario-en-linea"
                                      method="post"
                                      action="${pageContext.request.contextPath}/salida">
                                    <input type="hidden" name="idEstadia" value="${estadia.idEstadia}">
                                    <button class="boton boton-pequeno" type="submit">
                                        Registrar salida
                                    </button>
                                </form>
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
