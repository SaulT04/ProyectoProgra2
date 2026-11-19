package com.parkmanager.dao;

import com.parkmanager.modelo.ResumenParqueo;
import java.sql.SQLException;

public interface EstadisticaDAO {

    ResumenParqueo obtenerResumen() throws SQLException;
}
