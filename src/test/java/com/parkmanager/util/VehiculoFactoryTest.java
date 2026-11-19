package com.parkmanager.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.parkmanager.modelo.Carro;
import com.parkmanager.modelo.Motocicleta;
import com.parkmanager.modelo.Vehiculo;
import com.parkmanager.modelo.enums.TipoVehiculo;
import org.junit.jupiter.api.Test;

class VehiculoFactoryTest {

    @Test
    void creaUnCarroCuandoElTipoEsCarro() {
        Vehiculo vehiculo = VehiculoFactory.crear("ABC-123", TipoVehiculo.CARRO);

        assertInstanceOf(Carro.class, vehiculo);
        assertEquals(TipoVehiculo.CARRO, vehiculo.getTipo());
    }

    @Test
    void creaUnaMotoCuandoElTipoEsMoto() {
        Vehiculo vehiculo = VehiculoFactory.crear("M-123", "moto");

        assertInstanceOf(Motocicleta.class, vehiculo);
        assertEquals(TipoVehiculo.MOTO, vehiculo.getTipo());
    }

    @Test
    void rechazaTiposInvalidos() {
        assertThrows(IllegalArgumentException.class,
                () -> VehiculoFactory.crear("ABC-123", (TipoVehiculo) null));
        assertThrows(IllegalArgumentException.class,
                () -> VehiculoFactory.crear("ABC-123", "camion"));
    }
}
