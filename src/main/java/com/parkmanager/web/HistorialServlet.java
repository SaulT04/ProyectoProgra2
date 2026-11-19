package com.parkmanager.web;

import com.parkmanager.servicio.EstadiaService;
import com.parkmanager.util.Validador;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "HistorialServlet", urlPatterns = "/historial")
public class HistorialServlet extends HttpServlet {

    private final EstadiaService estadiaService;

    public HistorialServlet() {
        this(new EstadiaService());
    }

    HistorialServlet(EstadiaService estadiaService) {
        this.estadiaService = estadiaService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String placa = request.getParameter("placa");

        if (placa != null && !placa.isBlank()) {
            try {
                String placaNormalizada = Validador.validarPlaca(placa);
                List<EstadiaVista> estadias = estadiaService.buscarHistorial(placaNormalizada)
                        .stream()
                        .map(EstadiaVista::desde)
                        .toList();

                request.setAttribute("placaConsultada", placaNormalizada);
                request.setAttribute("estadias", estadias);
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", e.getMessage());
            } catch (SQLException e) {
                getServletContext().log("Error al consultar el historial", e);
                request.setAttribute("error", "No se pudo consultar el historial. Intente nuevamente.");
            }
        }

        request.getRequestDispatcher("/WEB-INF/vistas/historial.jsp")
                .forward(request, response);
    }
}
