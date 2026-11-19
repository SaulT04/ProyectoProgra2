package com.parkmanager.dao.impl;

import com.parkmanager.config.ConexionDB;
import com.parkmanager.dao.VehiculoDAO;
import com.parkmanager.modelo.Vehiculo;
import com.parkmanager.modelo.enums.TipoVehiculo;
import com.parkmanager.util.VehiculoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehiculoDAOImpl implements VehiculoDAO {

    private static final String SQL_BUSCAR_POR_PLACA =
            "SELECT placa, tipo FROM vehiculo WHERE placa = ?";

    private static final String SQL_INSERTAR =
            "INSERT INTO vehiculo (placa, tipo) VALUES (?, ?)";

    @Override
    public Vehiculo buscarPorPlaca(String placa) throws SQLException {
        try (Connection conexion = ConexionDB.getConnection()) {
            return buscarPorPlaca(conexion, placa);
        }
    }

    @Override
    public Vehiculo buscarPorPlaca(Connection conexion, String placa) throws SQLException {
        if (conexion == null) {
            throw new IllegalArgumentException("La conexión es obligatoria");
        }
        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException("La placa es obligatoria");
        }

        try (PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR_POR_PLACA)) {
            sentencia.setString(1, placa.trim().toUpperCase());

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return mapearFila(resultado);
                }
                return null;
            }
        }
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws SQLException {
        try (Connection conexion = ConexionDB.getConnection()) {
            insertar(conexion, vehiculo);
        }
    }

    @Override
    public void insertar(Connection conexion, Vehiculo vehiculo) throws SQLException {
        if (conexion == null) {
            throw new IllegalArgumentException("La conexión es obligatoria");
        }
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo es obligatorio");
        }

        try (PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR)) {
            sentencia.setString(1, vehiculo.getPlaca());
            sentencia.setString(2, vehiculo.getTipo().name());
            sentencia.executeUpdate();
        }
    }

    private Vehiculo mapearFila(ResultSet resultado) throws SQLException {
        return VehiculoFactory.crear(
                resultado.getString("placa"),
                TipoVehiculo.valueOf(resultado.getString("tipo"))
        );
    }
}
