package com.parkmanager.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.parkmanager.modelo.enums.TipoVehiculo;
import org.junit.jupiter.api.Test;

class ValidadorTest {

    @Test
    void aceptaYNormalizaPlacasValidas() {
        assertEquals("ABC-123", Validador.validarPlaca(" abc-123 "));
        assertEquals("1234ABC", Validador.validarPlaca("1234ABC"));
        assertEquals("123-ABC", Validador.validarPlaca("123-abc"));
    }

    @Test
    void rechazaPlacasInvalidas() {
        assertThrows(IllegalArgumentException.class, () -> Validador.validarPlaca(null));
        assertThrows(IllegalArgumentException.class, () -> Validador.validarPlaca("   "));
        assertThrows(IllegalArgumentException.class, () -> Validador.validarPlaca("A1"));
        assertThrows(IllegalArgumentException.class, () -> Validador.validarPlaca("ABC 123"));
        assertThrows(IllegalArgumentException.class, () -> Validador.validarPlaca("12345678901"));
    }

    @Test
    void aceptaYNormalizaCodigosDeEspacio() {
        assertEquals("C-01", Validador.validarCodigoEspacio(" c-01 "));
        assertEquals("M-02", Validador.validarCodigoEspacio("M-02"));
    }

    @Test
    void rechazaCodigosDeEspacioInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> Validador.validarCodigoEspacio(null));
        assertThrows(IllegalArgumentException.class, () -> Validador.validarCodigoEspacio("C"));
        assertThrows(IllegalArgumentException.class, () -> Validador.validarCodigoEspacio("C 01"));
    }

    @Test
    void convierteElTipoDeVehiculo() {
        assertEquals(TipoVehiculo.CARRO, Validador.validarTipoVehiculo("carro"));
        assertEquals(TipoVehiculo.MOTO, Validador.validarTipoVehiculo("MOTO"));
        assertThrows(IllegalArgumentException.class,
                () -> Validador.validarTipoVehiculo("camion"));
        assertThrows(IllegalArgumentException.class,
                () -> Validador.validarTipoVehiculo(null));
    }

    @Test
    void validaIdentificadoresPositivos() {
        assertEquals(1, Validador.validarId(1));
        assertThrows(IllegalArgumentException.class, () -> Validador.validarId(0));
        assertThrows(IllegalArgumentException.class, () -> Validador.validarId(-5));
    }
}
