package com.parkmanager.servicio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.parkmanager.modelo.ResumenParqueo;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

@EnabledIfEnvironmentVariable(named = "PARKMANAGER_DB", matches = "true")
class EstadisticaServiceTest {

    private final EstadisticaService estadisticaService = new EstadisticaService();

    @Test
    void obtieneResumenConsistente() throws SQLException {
        ResumenParqueo resumen = estadisticaService.obtenerResumen();

        assertNotNull(resumen);
        assertEquals(
                resumen.getTotalEspacios(),
                resumen.getEspaciosLibres() + resumen.getEspaciosOcupados()
        );
        assertEquals(
                resumen.getEstadiasActivas() + resumen.getEstadiasFinalizadas(),
                resumen.getEstadiasTotales()
        );
        assertTrue(resumen.getEstadiasActivas() >= 0);
        assertTrue(resumen.getEstadiasFinalizadas() >= 0);
        assertTrue(resumen.getIngresosTotales().signum() >= 0);
        assertTrue(resumen.getPorcentajeOcupacion() >= 0.0);
        assertTrue(resumen.getPorcentajeOcupacion() <= 100.0);
    }
}
