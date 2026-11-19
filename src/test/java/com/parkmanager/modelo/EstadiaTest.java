package com.parkmanager.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.parkmanager.modelo.enums.EstadoEstadia;
import com.parkmanager.modelo.enums.TipoVehiculo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class EstadiaTest {

    private Estadia crearEstadia() {
        Vehiculo carro = new Carro("ABC-123");
        Espacio espacio = new Espacio("C-01", TipoVehiculo.CARRO);
        return new Estadia(carro, espacio, LocalDateTime.of(2026, 11, 19, 10, 0));
    }

    @Test
    void iniciaActiva() {
        Estadia estadia = crearEstadia();

        assertEquals(EstadoEstadia.ACTIVA, estadia.getEstado());
        assertNull(estadia.getMonto());
        assertNull(estadia.getFechaSalida());
    }

    @Test
    void alFinalizarCalculaElMonto() {
        Estadia estadia = crearEstadia();

        estadia.finalizar(LocalDateTime.of(2026, 11, 19, 11, 1));

        assertEquals(EstadoEstadia.FINALIZADA, estadia.getEstado());
        assertEquals(61, estadia.getDuracionMinutos());
        assertEquals(new BigDecimal("20.00"), estadia.getMonto());
    }

    @Test
    void cobraMinimoUnaHoraSiEntraYSaleEnElMismoMinuto() {
        Estadia estadia = crearEstadia();
        LocalDateTime mismaHora = estadia.getFechaEntrada();

        estadia.finalizar(mismaHora);

        assertEquals(new BigDecimal("10.00"), estadia.getMonto());
    }

    @Test
    void rechazaSalidaAnteriorALaEntrada() {
        Estadia estadia = crearEstadia();

        assertThrows(IllegalArgumentException.class,
                () -> estadia.finalizar(LocalDateTime.of(2026, 11, 19, 9, 0)));
    }

    @Test
    void noPuedeFinalizarseDosVeces() {
        Estadia estadia = crearEstadia();
        estadia.finalizar(LocalDateTime.of(2026, 11, 19, 11, 0));

        assertThrows(IllegalStateException.class,
                () -> estadia.finalizar(LocalDateTime.of(2026, 11, 19, 12, 0)));
    }
}
