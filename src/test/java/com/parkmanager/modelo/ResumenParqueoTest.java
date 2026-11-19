package com.parkmanager.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ResumenParqueoTest {

    @Test
    void calculaPorcentajeYTotalDeEstadias() {
        ResumenParqueo resumen = new ResumenParqueo(
                10, 7, 3, 1, 2, new BigDecimal("25.50")
        );

        assertEquals(30.0, resumen.getPorcentajeOcupacion(), 0.0001);
        assertEquals(3, resumen.getEstadiasTotales());
        assertEquals(new BigDecimal("25.50"), resumen.getIngresosTotales());
    }

    @Test
    void porcentajeEsCeroCuandoNoHayEspacios() {
        ResumenParqueo resumen = new ResumenParqueo(
                0, 0, 0, 0, 0, BigDecimal.ZERO
        );

        assertEquals(0.0, resumen.getPorcentajeOcupacion(), 0.0001);
        assertEquals(0, resumen.getEstadiasTotales());
    }

    @Test
    void rechazaTotalNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> new ResumenParqueo(-1, 0, 0, 0, 0, BigDecimal.ZERO));
    }

    @Test
    void rechazaOcupacionMayorAlTotal() {
        assertThrows(IllegalArgumentException.class,
                () -> new ResumenParqueo(2, 1, 2, 0, 0, BigDecimal.ZERO));
    }

    @Test
    void rechazaIngresosNegativos() {
        assertThrows(IllegalArgumentException.class,
                () -> new ResumenParqueo(5, 5, 0, 0, 0, new BigDecimal("-0.01")));
    }

    @Test
    void rechazaIngresosNulos() {
        assertThrows(IllegalArgumentException.class,
                () -> new ResumenParqueo(5, 5, 0, 0, 0, null));
    }
}
