package com.parkmanager.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.parkmanager.modelo.enums.EstadoEspacio;
import com.parkmanager.modelo.enums.TipoVehiculo;
import org.junit.jupiter.api.Test;

class EspacioTest {

    @Test
    void iniciaLibre() {
        Espacio espacio = new Espacio("C-01", TipoVehiculo.CARRO);

        assertTrue(espacio.estaDisponible());
        assertEquals(EstadoEspacio.LIBRE, espacio.getEstado());
    }

    @Test
    void puedeOcuparseYLiberarse() {
        Espacio espacio = new Espacio("C-01", TipoVehiculo.CARRO);

        espacio.ocupar();
        assertFalse(espacio.estaDisponible());
        assertEquals(EstadoEspacio.OCUPADO, espacio.getEstado());

        espacio.liberar();
        assertTrue(espacio.estaDisponible());
        assertEquals(EstadoEspacio.LIBRE, espacio.getEstado());
    }

    @Test
    void noPuedeOcuparseDosVeces() {
        Espacio espacio = new Espacio("C-01", TipoVehiculo.CARRO);
        espacio.ocupar();

        assertThrows(IllegalStateException.class, espacio::ocupar);
    }

    @Test
    void noPuedeLiberarseSiYaEstaLibre() {
        Espacio espacio = new Espacio("C-01", TipoVehiculo.CARRO);

        assertThrows(IllegalStateException.class, espacio::liberar);
    }

    @Test
    void validaSusDatosObligatorios() {
        assertThrows(IllegalArgumentException.class,
                () -> new Espacio(null, TipoVehiculo.CARRO));
        assertThrows(IllegalArgumentException.class,
                () -> new Espacio("C-01", null));
    }
}
