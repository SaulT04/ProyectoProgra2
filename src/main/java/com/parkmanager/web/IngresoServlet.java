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

@WebServlet(name = "IngresoServlet", urlPatterns = "/ingreso")
public class IngresoServlet extends HttpServlet {

    private final EstadiaService estadiaService;

    public IngresoServlet() {
        this(new EstadiaService());
    }

    IngresoServlet(EstadiaService estadiaService) {
        this.estadiaService = estadiaService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        mostrarFormulario(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String placa = request.getParameter("placa");
        String tipo = request.getParameter("tipo");

        try {
            Estadia estadia = estadiaService.registrarIngreso(placa, tipo);
            request.setAttribute("estadia", EstadiaVista.desde(estadia));
            request.setAttribute("mensaje", "Ingreso registrado correctamente.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            request.setAttribute("error", e.getMessage());
        } catch (SQLException e) {
            getServletContext().log("Error al registrar un ingreso", e);
            request.setAttribute("error", "No se pudo registrar el ingreso. Intente nuevamente.");
        }

        mostrarFormulario(request, response);
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/vistas/ingreso.jsp")
                .forward(request, response);
    }
}
