package com.parkmanager.web;

import com.parkmanager.servicio.EspacioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "EspacioServlet", urlPatterns = "/espacios")
public class EspacioServlet extends HttpServlet {

    private final EspacioService espacioService;

    public EspacioServlet() {
        this(new EspacioService());
    }

    EspacioServlet(EspacioService espacioService) {
        this.espacioService = espacioService;
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

        String codigo = request.getParameter("codigo");
        String tipo = request.getParameter("tipo");

        try {
            espacioService.registrarEspacio(codigo, tipo);
            request.setAttribute("mensaje", "Espacio registrado correctamente.");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
        } catch (SQLException e) {
            getServletContext().log("Error al registrar un espacio", e);
            request.setAttribute("error", "No se pudo registrar el espacio. Intente nuevamente.");
        }

        cargarVista(request, response);
    }

    private void cargarVista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("espacios", espacioService.listarEspacios());
        } catch (SQLException e) {
            getServletContext().log("Error al listar los espacios", e);
            request.setAttribute("error", "No se pudieron cargar los espacios.");
            request.setAttribute("espacios", List.of());
        }

        request.getRequestDispatcher("/WEB-INF/vistas/espacios.jsp")
                .forward(request, response);
    }
}
