package com.parkmanager.dao;

import com.parkmanager.modelo.Vehiculo;
import java.sql.Connection;
import java.sql.SQLException;

public interface VehiculoDAO {

    Vehiculo buscarPorPlaca(String placa) throws SQLException;

    Vehiculo buscarPorPlaca(Connection conexion, String placa) throws SQLException;

    void insertar(Vehiculo vehiculo) throws SQLException;

    void insertar(Connection conexion, Vehiculo vehiculo) throws SQLException;
}
