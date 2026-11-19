<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Estadísticas" />
<%@ include file="/WEB-INF/jspf/cabecera.jspf" %>

<section class="panel">
    <h2>Estadísticas del parqueo</h2>
    <p class="texto-suave">
        Resumen actual de ocupación, operaciones e ingresos registrados.
    </p>

    <%@ include file="/WEB-INF/jspf/mensajes.jspf" %>

    <c:choose>
        <c:when test="${empty resumen}">
            <p class="texto-vacio">No hay información disponible.</p>
        </c:when>
        <c:otherwise>
            <div class="resumen-grid">
                <article class="resumen-item">
                    <span class="resumen-etiqueta">Espacios totales</span>
                    <strong class="resumen-valor">${resumen.totalEspacios}</strong>
                </article>

                <article class="resumen-item">
                    <span class="resumen-etiqueta">Espacios libres</span>
                    <strong class="resumen-valor">${resumen.espaciosLibres}</strong>
                </article>

                <article class="resumen-item">
                    <span class="resumen-etiqueta">Espacios ocupados</span>
                    <strong class="resumen-valor">${resumen.espaciosOcupados}</strong>
                </article>

                <article class="resumen-item">
                    <span class="resumen-etiqueta">Ocupación</span>
                    <strong class="resumen-valor">
                        <c:out value="${porcentajeTexto}" />%
                    </strong>
                    <div class="barra-progreso" aria-hidden="true">
                        <span style="width: ${porcentajeTexto}%"></span>
                    </div>
                </article>

                <article class="resumen-item">
                    <span class="resumen-etiqueta">Estadías activas</span>
                    <strong class="resumen-valor">${resumen.estadiasActivas}</strong>
                </article>

                <article class="resumen-item">
                    <span class="resumen-etiqueta">Estadías finalizadas</span>
                    <strong class="resumen-valor">${resumen.estadiasFinalizadas}</strong>
                </article>

                <article class="resumen-item resumen-item-destacado">
                    <span class="resumen-etiqueta">Ingresos totales</span>
                    <strong class="resumen-valor">
                        <c:out value="${ingresosTexto}" />
                    </strong>
                </article>
            </div>

            <p class="texto-suave">
                Total de operaciones registradas: ${resumen.estadiasTotales}.
            </p>
        </c:otherwise>
    </c:choose>
</section>

<%@ include file="/WEB-INF/jspf/pie.jspf" %>
