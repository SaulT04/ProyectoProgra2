package com.parkmanager.util;

import com.parkmanager.modelo.Carro;
import com.parkmanager.modelo.Motocicleta;
import com.parkmanager.modelo.Vehiculo;
import com.parkmanager.modelo.enums.TipoVehiculo;

public final class VehiculoFactory {

    private VehiculoFactory() {
    }

    public static Vehiculo crear(String placa, TipoVehiculo tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de vehículo es obligatorio");
        }

        return switch (tipo) {
            case CARRO -> new Carro(placa);
            case MOTO -> new Motocicleta(placa);
        };
    }

    public static Vehiculo crear(String placa, String tipo) {
        return crear(placa, TipoVehiculo.desde(tipo));
    }
}
