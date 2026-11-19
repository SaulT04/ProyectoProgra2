package com.parkmanager.web;

import com.parkmanager.modelo.Estadia;
import com.parkmanager.servicio.EstadiaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "SalidaServlet", urlPatterns = "/salida")
public class SalidaServlet extends HttpServlet {

    private final EstadiaService estadiaService;

    public SalidaServlet() {
        this(new EstadiaService());
    }

    SalidaServlet(EstadiaService estadiaService) {
        this.estadiaService = estadiaService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cargarVista(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            int idEstadia = Integer.parseInt(request.getParameter("idEstadia"));
            Estadia estadia = estadiaService.registrarSalida(idEstadia);

            request.setAttribute("salida", EstadiaVista.desde(estadia));
            request.setAttribute("mensaje", "Salida registrada correctamente.");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Seleccione una estadía válida.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            request.setAttribute("error", e.getMessage());
        } catch (SQLException e) {
            getServletContext().log("Error al registrar una salida", e);
            request.setAttribute("error", "No se pudo registrar la salida. Intente nuevamente.");
        }

        cargarVista(request, response);
    }

    private void cargarVista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<EstadiaVista> estadias = estadiaService.listarActivas()
                    .stream()
                    .map(EstadiaVista::desde)
                    .toList();

            request.setAttribute("estadias", estadias);
        } catch (SQLException e) {
            getServletContext().log("Error al listar las estadías activas", e);
            request.setAttribute("error", "No se pudieron cargar las estadías activas.");
            request.setAttribute("estadias", List.of());
        }

        request.getRequestDispatcher("/WEB-INF/vistas/salida.jsp")
                .forward(request, response);
    }
}
