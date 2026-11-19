<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Inicio" />
<%@ include file="/WEB-INF/jspf/cabecera.jspf" %>

<section class="bienvenida">
    <h2>Bienvenido</h2>
    <p>
        Proyecto de Programación II desarrollado con Java 17, Jakarta Servlets,
        JSP, JDBC y PostgreSQL.
    </p>
    <p class="estado">
        Proyecto completado: espacios, ingresos, salidas, historial y estadísticas.
    </p>
</section>

<section class="modulos">
    <article class="tarjeta">
        <h3>Espacios</h3>
        <p>Registro y control de disponibilidad de los espacios de parqueo.</p>
        <a class="boton" href="${pageContext.request.contextPath}/espacios">
            Administrar espacios
        </a>
    </article>

    <article class="tarjeta">
        <h3>Ingresos y salidas</h3>
        <p>Registro de vehículos y cálculo automático de la tarifa.</p>
        <div class="acciones-tarjeta">
            <a class="boton" href="${pageContext.request.contextPath}/ingreso">
                Registrar ingreso
            </a>
            <a class="boton boton-secundario" href="${pageContext.request.contextPath}/salida">
                Registrar salida
            </a>
        </div>
    </article>

    <article class="tarjeta">
        <h3>Historial</h3>
        <p>Consulta de estadías por placa, con fechas, tiempo y monto pagado.</p>
        <a class="boton" href="${pageContext.request.contextPath}/historial">
            Consultar historial
        </a>
    </article>

    <article class="tarjeta">
        <h3>Estadísticas</h3>
        <p>Resumen de ocupación, operaciones e ingresos del parqueo.</p>
        <a class="boton" href="${pageContext.request.contextPath}/estadisticas">
            Ver estadísticas
        </a>
    </article>
</section>

<%@ include file="/WEB-INF/jspf/pie.jspf" %>
