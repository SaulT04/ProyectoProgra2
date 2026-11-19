package com.parkmanager.dao;

import com.parkmanager.modelo.Estadia;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface EstadiaDAO {

    int insertar(Estadia estadia) throws SQLException;

    int insertar(Connection conexion, Estadia estadia) throws SQLException;

    Estadia buscarPorId(int idEstadia) throws SQLException;

    Estadia buscarPorId(Connection conexion, int idEstadia) throws SQLException;

    Estadia buscarActivaPorPlaca(String placa) throws SQLException;

    Estadia buscarActivaPorPlaca(Connection conexion, String placa) throws SQLException;

    List<Estadia> listarActivas() throws SQLException;

    List<Estadia> listarPorPlaca(String placa) throws SQLException;

    boolean finalizar(int idEstadia, LocalDateTime fechaSalida, BigDecimal monto) throws SQLException;

    boolean finalizar(Connection conexion, int idEstadia, LocalDateTime fechaSalida, BigDecimal monto)
            throws SQLException;
}
