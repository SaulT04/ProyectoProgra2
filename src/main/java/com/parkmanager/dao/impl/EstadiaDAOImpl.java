package com.parkmanager.dao.impl;

import com.parkmanager.config.ConexionDB;
import com.parkmanager.dao.EstadiaDAO;
import com.parkmanager.modelo.Estadia;
import com.parkmanager.modelo.Espacio;
import com.parkmanager.modelo.Vehiculo;
import com.parkmanager.modelo.enums.EstadoEspacio;
import com.parkmanager.modelo.enums.EstadoEstadia;
import com.parkmanager.modelo.enums.TipoVehiculo;
import com.parkmanager.util.VehiculoFactory;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EstadiaDAOImpl implements EstadiaDAO {

    private static final String SQL_SELECT_BASE =
            "SELECT e.id_estadia, e.placa, e.id_espacio, e.fecha_entrada, e.fecha_salida, "
                    + "e.monto, e.estado, v.tipo AS vehiculo_tipo, "
                    + "s.codigo AS espacio_codigo, s.tipo AS espacio_tipo, s.estado AS espacio_estado "
                    + "FROM estadia e "
                    + "JOIN vehiculo v ON v.placa = e.placa "
                    + "JOIN espacio s ON s.id_espacio = e.id_espacio ";

    private static final String SQL_BUSCAR_POR_ID =
            SQL_SELECT_BASE + "WHERE e.id_estadia = ?";

    private static final String SQL_BUSCAR_ACTIVA_POR_PLACA =
            SQL_SELECT_BASE + "WHERE e.placa = ? AND e.estado = 'ACTIVA'";

    private static final String SQL_LISTAR_ACTIVAS =
            SQL_SELECT_BASE + "WHERE e.estado = 'ACTIVA' ORDER BY e.fecha_entrada";

    private static final String SQL_LISTAR_POR_PLACA =
            SQL_SELECT_BASE + "WHERE e.placa = ? ORDER BY e.fecha_entrada DESC";

    private static final String SQL_INSERTAR =
            "INSERT INTO estadia (placa, id_espacio, fecha_entrada, estado) "
                    + "VALUES (?, ?, ?, 'ACTIVA') RETURNING id_estadia";

    private static final String SQL_FINALIZAR =
            "UPDATE estadia SET fecha_salida = ?, monto = ?, estado = 'FINALIZADA' "
                    + "WHERE id_estadia = ? AND estado = 'ACTIVA'";

    @Override
    public int insertar(Estadia estadia) throws SQLException {
        try (Connection conexion = ConexionDB.getConnection()) {
            return insertar(conexion, estadia);
        }
    }

    @Override
    public int insertar(Connection conexion, Estadia estadia) throws SQLException {
        if (conexion == null) {
            throw new IllegalArgumentException("La conexión es obligatoria");
        }
        if (estadia == null) {
            throw new IllegalArgumentException("La estadía es obligatoria");
        }

        try (PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR)) {
            sentencia.setString(1, estadia.getVehiculo().getPlaca());
            sentencia.setInt(2, estadia.getEspacio().getIdEspacio());
            sentencia.setTimestamp(3, Timestamp.valueOf(estadia.getFechaEntrada()));

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt("id_estadia");
                }
                throw new SQLException("No se pudo obtener el identificador de la estadía insertada");
            }
        }
    }

    @Override
    public Estadia buscarPorId(int idEstadia) throws SQLException {
        try (Connection conexion = ConexionDB.getConnection()) {
            return buscarPorId(conexion, idEstadia);
        }
    }

    @Override
    public Estadia buscarPorId(Connection conexion, int idEstadia) throws SQLException {
        if (conexion == null) {
            throw new IllegalArgumentException("La conexión es obligatoria");
        }

        try (PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR_POR_ID)) {
            sentencia.setInt(1, idEstadia);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return mapearFila(resultado);
                }
                return null;
            }
        }
    }

    @Override
    public Estadia buscarActivaPorPlaca(String placa) throws SQLException {
        try (Connection conexion = ConexionDB.getConnection()) {
            return buscarActivaPorPlaca(conexion, placa);
        }
    }

    @Override
    public Estadia buscarActivaPorPlaca(Connection conexion, String placa) throws SQLException {
        if (conexion == null) {
            throw new IllegalArgumentException("La conexión es obligatoria");
        }
        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException("La placa es obligatoria");
        }

        try (PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR_ACTIVA_POR_PLACA)) {
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
    public List<Estadia> listarActivas() throws SQLException {
        try (Connection conexion = ConexionDB.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR_ACTIVAS);
             ResultSet resultado = sentencia.executeQuery()) {

            return mapearLista(resultado);
        }
    }

    @Override
    public List<Estadia> listarPorPlaca(String placa) throws SQLException {
        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException("La placa es obligatoria");
        }

        try (Connection conexion = ConexionDB.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR_POR_PLACA)) {

            sentencia.setString(1, placa.trim().toUpperCase());

            try (ResultSet resultado = sentencia.executeQuery()) {
                return mapearLista(resultado);
            }
        }
    }

    @Override
    public boolean finalizar(int idEstadia, LocalDateTime fechaSalida, BigDecimal monto)
            throws SQLException {
        try (Connection conexion = ConexionDB.getConnection()) {
            return finalizar(conexion, idEstadia, fechaSalida, monto);
        }
    }

    @Override
    public boolean finalizar(Connection conexion, int idEstadia, LocalDateTime fechaSalida, BigDecimal monto)
            throws SQLException {
        if (conexion == null) {
            throw new IllegalArgumentException("La conexión es obligatoria");
        }
        if (fechaSalida == null) {
            throw new IllegalArgumentException("La fecha de salida es obligatoria");
        }
        if (monto == null) {
            throw new IllegalArgumentException("El monto es obligatorio");
        }

        try (PreparedStatement sentencia = conexion.prepareStatement(SQL_FINALIZAR)) {
            sentencia.setTimestamp(1, Timestamp.valueOf(fechaSalida));
            sentencia.setBigDecimal(2, monto);
            sentencia.setInt(3, idEstadia);

            return sentencia.executeUpdate() > 0;
        }
    }

    private List<Estadia> mapearLista(ResultSet resultado) throws SQLException {
        List<Estadia> estadias = new ArrayList<>();
        while (resultado.next()) {
            estadias.add(mapearFila(resultado));
        }
        return estadias;
    }

    private Estadia mapearFila(ResultSet resultado) throws SQLException {
        String placa = resultado.getString("placa");
        Vehiculo vehiculo = VehiculoFactory.crear(
                placa,
                TipoVehiculo.valueOf(resultado.getString("vehiculo_tipo"))
        );

        Espacio espacio = new Espacio(
                resultado.getInt("id_espacio"),
                resultado.getString("espacio_codigo"),
                TipoVehiculo.valueOf(resultado.getString("espacio_tipo")),
                EstadoEspacio.valueOf(resultado.getString("espacio_estado"))
        );

        Timestamp entrada = resultado.getTimestamp("fecha_entrada");
        Timestamp salida = resultado.getTimestamp("fecha_salida");

        return new Estadia(
                resultado.getInt("id_estadia"),
                vehiculo,
                espacio,
                entrada != null ? entrada.toLocalDateTime() : null,
                salida != null ? salida.toLocalDateTime() : null,
                resultado.getBigDecimal("monto"),
                EstadoEstadia.valueOf(resultado.getString("estado"))
        );
    }
}
