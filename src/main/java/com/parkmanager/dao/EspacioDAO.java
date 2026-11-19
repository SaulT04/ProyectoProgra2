package com.parkmanager.dao;

import com.parkmanager.modelo.Espacio;
import com.parkmanager.modelo.enums.EstadoEspacio;
import com.parkmanager.modelo.enums.TipoVehiculo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface EspacioDAO {

    int insertar(Espacio espacio) throws SQLException;

    List<Espacio> listarTodos() throws SQLException;

    List<Espacio> listarDisponiblesPorTipo(TipoVehiculo tipo) throws SQLException;

    Espacio buscarPorId(int idEspacio) throws SQLException;

    Espacio buscarDisponiblePorTipoParaActualizar(Connection conexion, TipoVehiculo tipo) throws SQLException;

    boolean actualizarEstado(int idEspacio, EstadoEspacio estado) throws SQLException;

    boolean actualizarEstado(Connection conexion, int idEspacio, EstadoEspacio estado) throws SQLException;

    boolean existeCodigo(String codigo) throws SQLException;
}
