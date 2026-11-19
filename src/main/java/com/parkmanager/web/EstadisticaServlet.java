package com.parkmanager.web;

import com.parkmanager.modelo.ResumenParqueo;
import com.parkmanager.servicio.EstadisticaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Locale;

@WebServlet(name = "EstadisticaServlet", urlPatterns = "/estadisticas")
public class EstadisticaServlet extends HttpServlet {

    private final EstadisticaService estadisticaService;

    public EstadisticaServlet() {
        this(new EstadisticaService());
    }

    EstadisticaServlet(EstadisticaService estadisticaService) {
        this.estadisticaService = estadisticaService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ResumenParqueo resumen = estadisticaService.obtenerResumen();

            request.setAttribute("resumen", resumen);
            request.setAttribute(
                    "ingresosTexto",
                    "Bs " + resumen.getIngresosTotales()
                            .setScale(2, RoundingMode.HALF_UP)
                            .toPlainString()
            );
            request.setAttribute(
                    "porcentajeTexto",
                    String.format(Locale.ROOT, "%.1f", resumen.getPorcentajeOcupacion())
            );
        } catch (SQLException e) {
            getServletContext().log("Error al obtener las estadísticas", e);
            request.setAttribute("error", "No se pudieron cargar las estadísticas.");
        } catch (IllegalArgumentException e) {
            getServletContext().log("Resumen de parqueo inválido", e);
            request.setAttribute("error", "No se pudieron cargar las estadísticas.");
        }

        request.getRequestDispatcher("/WEB-INF/vistas/estadisticas.jsp")
                .forward(request, response);
    }
}
