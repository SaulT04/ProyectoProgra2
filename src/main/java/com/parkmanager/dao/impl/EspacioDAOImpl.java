package com.parkmanager.dao.impl;

import com.parkmanager.config.ConexionDB;
import com.parkmanager.dao.EspacioDAO;
import com.parkmanager.modelo.Espacio;
import com.parkmanager.modelo.enums.EstadoEspacio;
import com.parkmanager.modelo.enums.TipoVehiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EspacioDAOImpl implements EspacioDAO {

    private static final String SQL_INSERTAR =
            "INSERT INTO espacio (codigo, tipo, estado) VALUES (?, ?, ?) RETURNING id_espacio";

    private static final String SQL_LISTAR =
            "SELECT id_espacio, codigo, tipo, estado FROM espacio ORDER BY codigo";

    private static final String SQL_LISTAR_DISPONIBLES =
            "SELECT id_espacio, codigo, tipo, estado FROM espacio "
                    + "WHERE tipo = ? AND estado = 'LIBRE' ORDER BY codigo";

    private static final String SQL_BUSCAR_POR_ID =
            "SELECT id_espacio, codigo, tipo, estado FROM espacio WHERE id_espacio = ?";

    private static final String SQL_BUSCAR_DISPONIBLE_PARA_ACTUALIZAR =
            "SELECT id_espacio, codigo, tipo, estado FROM espacio "
                    + "WHERE tipo = ? AND estado = 'LIBRE' ORDER BY codigo LIMIT 1 FOR UPDATE";

    private static final String SQL_ACTUALIZAR_ESTADO =
            "UPDATE espacio SET estado = ? WHERE id_espacio = ?";

    private static final String SQL_EXISTE_CODIGO =
            "SELECT 1 FROM espacio WHERE codigo = ? LIMIT 1";

    @Override
    public int insertar(Espacio espacio) throws SQLException {
        if (espacio == null) {
            throw new IllegalArgumentException("El espacio es obligatorio");
        }

        try (Connection conexion = ConexionDB.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR)) {

            sentencia.setString(1, espacio.getCodigo());
            sentencia.setString(2, espacio.getTipo().name());
            sentencia.setString(3, espacio.getEstado().name());

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt("id_espacio");
                }
                throw new SQLException("No se pudo obtener el identificador del espacio insertado");
            }
        }
    }

    @Override
    public List<Espacio> listarTodos() throws SQLException {
        try (Connection conexion = ConexionDB.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR);
             ResultSet resultado = sentencia.executeQuery()) {

            return mapearLista(resultado);
        }
    }

    @Override
    public List<Espacio> listarDisponiblesPorTipo(TipoVehiculo tipo) throws SQLException {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de vehículo es obligatorio");
        }

        try (Connection conexion = ConexionDB.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR_DISPONIBLES)) {

            sentencia.setString(1, tipo.name());

            try (ResultSet resultado = sentencia.executeQuery()) {
                return mapearLista(resultado);
            }
        }
    }

    @Override
    public Espacio buscarPorId(int idEspacio) throws SQLException {
        try (Connection conexion = ConexionDB.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR_POR_ID)) {

            sentencia.setInt(1, idEspacio);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return mapearFila(resultado);
                }
                return null;
            }
        }
    }

    @Override
    public Espacio buscarDisponiblePorTipoParaActualizar(Connection conexion, TipoVehiculo tipo)
            throws SQLException {
        if (conexion == null) {
            throw new IllegalArgumentException("La conexión es obligatoria");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de vehículo es obligatorio");
        }

        try (PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR_DISPONIBLE_PARA_ACTUALIZAR)) {
            sentencia.setString(1, tipo.name());

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return mapearFila(resultado);
                }
                return null;
            }
        }
    }

    @Override
    public boolean actualizarEstado(int idEspacio, EstadoEspacio estado) throws SQLException {
        try (Connection conexion = ConexionDB.getConnection()) {
            return actualizarEstado(conexion, idEspacio, estado);
        }
    }

    @Override
    public boolean actualizarEstado(Connection conexion, int idEspacio, EstadoEspacio estado)
            throws SQLException {
        if (conexion == null) {
            throw new IllegalArgumentException("La conexión es obligatoria");
        }
        if (estado == null) {
            throw new IllegalArgumentException("El estado del espacio es obligatorio");
        }

        try (PreparedStatement sentencia = conexion.prepareStatement(SQL_ACTUALIZAR_ESTADO)) {
            sentencia.setString(1, estado.name());
            sentencia.setInt(2, idEspacio);

            return sentencia.executeUpdate() > 0;
        }
    }

    @Override
    public boolean existeCodigo(String codigo) throws SQLException {
        try (Connection conexion = ConexionDB.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_EXISTE_CODIGO)) {

            sentencia.setString(1, codigo);

            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next();
            }
        }
    }

    private List<Espacio> mapearLista(ResultSet resultado) throws SQLException {
        List<Espacio> espacios = new ArrayList<>();
        while (resultado.next()) {
            espacios.add(mapearFila(resultado));
        }
        return espacios;
    }

    private Espacio mapearFila(ResultSet resultado) throws SQLException {
        return new Espacio(
                resultado.getInt("id_espacio"),
                resultado.getString("codigo"),
                TipoVehiculo.valueOf(resultado.getString("tipo")),
                EstadoEspacio.valueOf(resultado.getString("estado"))
        );
    }
}
