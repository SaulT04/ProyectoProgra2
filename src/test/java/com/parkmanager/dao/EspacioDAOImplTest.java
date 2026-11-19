package com.parkmanager.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.parkmanager.config.ConexionDB;
import com.parkmanager.dao.impl.EspacioDAOImpl;
import com.parkmanager.modelo.Espacio;
import com.parkmanager.modelo.enums.EstadoEspacio;
import com.parkmanager.modelo.enums.TipoVehiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

@EnabledIfEnvironmentVariable(named = "PARKMANAGER_DB", matches = "true")
class EspacioDAOImplTest {

    private final EspacioDAO espacioDAO = new EspacioDAOImpl();

    @Test
    void insertaBuscaListaYActualizaEstado() throws SQLException {
        String codigo = "T-" + (System.currentTimeMillis() % 1_000_000);
        int id = 0;

        try {
            id = espacioDAO.insertar(new Espacio(codigo, TipoVehiculo.CARRO));
            assertTrue(id > 0);

            Espacio guardado = espacioDAO.buscarPorId(id);
            assertNotNull(guardado);
            assertEquals(codigo, guardado.getCodigo());
            assertEquals(TipoVehiculo.CARRO, guardado.getTipo());
            assertEquals(EstadoEspacio.LIBRE, guardado.getEstado());
            assertTrue(espacioDAO.existeCodigo(codigo));
            assertTrue(contieneEspacio(
                    espacioDAO.listarDisponiblesPorTipo(TipoVehiculo.CARRO), id));

            assertTrue(espacioDAO.actualizarEstado(id, EstadoEspacio.OCUPADO));
            assertEquals(EstadoEspacio.OCUPADO, espacioDAO.buscarPorId(id).getEstado());
            assertFalse(contieneEspacio(
                    espacioDAO.listarDisponiblesPorTipo(TipoVehiculo.CARRO), id));
        } finally {
            eliminarEspacio(id);
        }
    }

    private boolean contieneEspacio(List<Espacio> espacios, int id) {
        return espacios.stream().anyMatch(espacio -> espacio.getIdEspacio() == id);
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
