package com.parkmanager.servicio;

import com.parkmanager.dao.EstadisticaDAO;
import com.parkmanager.dao.impl.EstadisticaDAOImpl;
import com.parkmanager.modelo.ResumenParqueo;
import java.sql.SQLException;
import java.util.Objects;

public class EstadisticaService {

    private final EstadisticaDAO estadisticaDAO;

    public EstadisticaService() {
        this(new EstadisticaDAOImpl());
    }

    public EstadisticaService(EstadisticaDAO estadisticaDAO) {
        this.estadisticaDAO = Objects.requireNonNull(
                estadisticaDAO, "El DAO de estadísticas es obligatorio"
        );
    }

    public ResumenParqueo obtenerResumen() throws SQLException {
        return estadisticaDAO.obtenerResumen();
    }
}
