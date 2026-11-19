package com.parkmanager.dao.impl;

import com.parkmanager.config.ConexionDB;
import com.parkmanager.dao.EstadisticaDAO;
import com.parkmanager.modelo.ResumenParqueo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstadisticaDAOImpl implements EstadisticaDAO {

    private static final String SQL_RESUMEN =
            "SELECT "
                    + "(SELECT COUNT(*) FROM espacio) AS total_espacios, "
                    + "(SELECT COUNT(*) FROM espacio WHERE estado = 'LIBRE') AS espacios_libres, "
                    + "(SELECT COUNT(*) FROM espacio WHERE estado = 'OCUPADO') AS espacios_ocupados, "
                    + "(SELECT COUNT(*) FROM estadia WHERE estado = 'ACTIVA') AS estadias_activas, "
                    + "(SELECT COUNT(*) FROM estadia WHERE estado = 'FINALIZADA') AS estadias_finalizadas, "
                    + "(SELECT COALESCE(SUM(monto), 0) FROM estadia WHERE estado = 'FINALIZADA') "
                    + "AS ingresos_totales";

    @Override
    public ResumenParqueo obtenerResumen() throws SQLException {
        try (Connection conexion = ConexionDB.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_RESUMEN);
             ResultSet resultado = sentencia.executeQuery()) {

            if (resultado.next()) {
                return new ResumenParqueo(
                        resultado.getInt("total_espacios"),
                        resultado.getInt("espacios_libres"),
                        resultado.getInt("espacios_ocupados"),
                        resultado.getInt("estadias_activas"),
                        resultado.getInt("estadias_finalizadas"),
                        resultado.getBigDecimal("ingresos_totales")
                );
            }

            throw new SQLException("No se pudo obtener el resumen del parqueo");
        }
    }
}
