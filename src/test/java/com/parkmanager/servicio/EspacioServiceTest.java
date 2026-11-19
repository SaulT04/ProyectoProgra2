package com.parkmanager.servicio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.parkmanager.config.ConexionDB;
import com.parkmanager.modelo.Espacio;
import com.parkmanager.modelo.enums.TipoVehiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

@EnabledIfEnvironmentVariable(named = "PARKMANAGER_DB", matches = "true")
class EspacioServiceTest {

    private final EspacioService espacioService = new EspacioService();

    @Test
    void registraEspacioYRechazaCodigoDuplicado() throws SQLException {
        String codigo = "S-" + (System.currentTimeMillis() % 1_000_000);
        int id = 0;

        try {
            id = espacioService.registrarEspacio(" " + codigo.toLowerCase() + " ", TipoVehiculo.MOTO);
            assertTrue(id > 0);

            Espacio guardado = espacioService.buscarPorId(id);
            assertNotNull(guardado);
            assertEquals(codigo, guardado.getCodigo());
            assertEquals(TipoVehiculo.MOTO, guardado.getTipo());

            final int idGuardado = id;
            List<Espacio> disponibles = espacioService.listarDisponibles(TipoVehiculo.MOTO);
            assertFalse(disponibles.isEmpty());
            assertTrue(disponibles.stream()
                    .anyMatch(espacio -> espacio.getIdEspacio() == idGuardado));

            assertThrows(IllegalArgumentException.class,
                    () -> espacioService.registrarEspacio(codigo, TipoVehiculo.MOTO));
        } finally {
            eliminarEspacio(id);
        }
    }

    private void eliminarEspacio(int id) throws SQLException {
        if (id <= 0) {
            return;
        }

        String sql = "DELETE FROM espacio WHERE id_espacio = ?";
        try (Connection conexion = ConexionDB.getConnection();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            sentencia.executeUpdate();
        }
    }
}
