package com.parkmanager.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class VehiculoTest {

    @Test
    void carroCobraPorHoraOFraccion() {
        Vehiculo carro = new Carro("ABC-123");

        assertEquals(new BigDecimal("10.00"), carro.calcularMonto(1));
        assertEquals(new BigDecimal("10.00"), carro.calcularMonto(60));
        assertEquals(new BigDecimal("20.00"), carro.calcularMonto(61));
        assertEquals(new BigDecimal("20.00"), carro.calcularMonto(120));
        assertEquals(new BigDecimal("30.00"), carro.calcularMonto(121));
    }

    @Test
    void motoCobraSuPropiaTarifa() {
        Vehiculo moto = new Motocicleta("M-123");

        assertEquals(new BigDecimal("5.00"), moto.calcularMonto(1));
        assertEquals(new BigDecimal("10.00"), moto.calcularMonto(61));
    }

    @Test
    void cobraMinimoUnaHora() {
        assertEquals(new BigDecimal("10.00"), new Carro("ABC-123").calcularMonto(0));
        assertEquals(new BigDecimal("5.00"), new Motocicleta("M-123").calcularMonto(0));
    }

    @Test
    void rechazaTiempoNegativo() {
        Vehiculo carro = new Carro("ABC-123");

        assertThrows(IllegalArgumentException.class, () -> carro.calcularMonto(-1));
    }

    @Test
    void normalizaLaPlaca() {
        assertEquals("ABC-123", new Carro(" abc-123 ").getPlaca());
        assertEquals("M-123", new Motocicleta("m-123").getPlaca());
    }
}
