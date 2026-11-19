package com.parkmanager.servicio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.parkmanager.config.ConexionDB;
import com.parkmanager.modelo.Estadia;
import com.parkmanager.modelo.enums.EstadoEspacio;
import com.parkmanager.modelo.enums.EstadoEstadia;
import com.parkmanager.modelo.enums.TipoVehiculo;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

@EnabledIfEnvironmentVariable(named = "PARKMANAGER_DB", matches = "true")
class EstadiaServiceTest {

    private final EstadiaService estadiaService = new EstadiaService();

    @Test
    void registraIngresoSalidaYConsultaHistorial() throws SQLException {
        String placa = String.format("TST%07d", System.currentTimeMillis() % 10_000_000L);
        int idEstadia = 0;
        int idEspacio = 0;

        try {
            Estadia ingreso = estadiaService.registrarIngreso(placa, TipoVehiculo.CARRO);
            idEstadia = ingreso.getIdEstadia();
            idEspacio = ingreso.getEspacio().getIdEspacio();

            final int idEstadiaGuardada = idEstadia;
            final int idEspacioGuardado = idEspacio;

            assertTrue(idEstadiaGuardada > 0);
            assertEquals(EstadoEstadia.ACTIVA, ingreso.getEstado());
            assertEquals(TipoVehiculo.CARRO, ingreso.getVehiculo().getTipo());
            assertEquals(TipoVehiculo.CARRO, ingreso.getEspacio().getTipo());
            assertEquals(EstadoEspacio.OCUPADO, consultarEstadoEspacio(idEspacioGuardado));

            assertTrue(estadiaService.listarActivas().stream()
                    .anyMatch(estadia -> estadia.getIdEstadia() == idEstadiaGuardada));

            assertThrows(IllegalStateException.class,
                    () -> estadiaService.registrarIngreso(placa, TipoVehiculo.CARRO));

            Estadia salida = estadiaService.registrarSalida(idEstadiaGuardada);
            assertEquals(EstadoEstadia.FINALIZADA, salida.getEstado());
            assertNotNull(salida.getFechaSalida());
            assertNotNull(salida.getMonto());
            assertTrue(salida.getMonto().compareTo(new BigDecimal("10.00")) >= 0);
            assertEquals(EstadoEspacio.LIBRE, consultarEstadoEspacio(idEspacioGuardado));

            List<Estadia> historial = estadiaService.buscarHistorial(placa);
            assertTrue(historial.stream()
                    .anyMatch(estadia -> estadia.getIdEstadia() == idEstadiaGuardada));

            assertThrows(IllegalStateException.class,
                    () -> estadiaService.registrarSalida(idEstadiaGuardada));

            assertThrows(IllegalArgumentException.class,
                    () -> estadiaService.registrarIngreso(placa, TipoVehiculo.MOTO));
        } finally {
            limpiar(placa, idEstadia, idEspacio);
        }
    }

    @Test
    void rechazaSalidaDeEstadiaInexistente() {
        assertThrows(IllegalArgumentException.class,
                () -> estadiaService.registrarSalida(999_999_999));
    }

    private EstadoEspacio consultarEstadoEspacio(int idEspacio) throws SQLException {
        String sql = "SELECT estado FROM espacio WHERE id_espacio = ?";

        try (Connection conexion = ConexionDB.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idEspacio);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return EstadoEspacio.valueOf(resultado.getString("estado"));
                }
                throw new SQLException("No existe el espacio con id " + idEspacio);
            }
        }
    }

    private void limpiar(String placa, int idEstadia, int idEspacio) throws SQLException {
        try (Connection conexion = ConexionDB.getConnection()) {
            try (PreparedStatement sentencia = conexion.prepareStatement(
                    "DELETE FROM estadia WHERE placa = ?")) {
                sentencia.setString(1, placa);
                sentencia.executeUpdate();
            }

            try (PreparedStatement sentencia = conexion.prepareStatement(
                    "DELETE FROM vehiculo WHERE placa = ?")) {
                sentencia.setString(1, placa);
                sentencia.executeUpdate();
            }

            if (idEspacio > 0) {
                try (PreparedStatement sentencia = conexion.prepareStatement(
                        "UPDATE espacio SET estado = 'LIBRE' WHERE id_espacio = ?")) {
                    sentencia.setInt(1, idEspacio);
                    sentencia.executeUpdate();
                }
            }
        }
    }
}
