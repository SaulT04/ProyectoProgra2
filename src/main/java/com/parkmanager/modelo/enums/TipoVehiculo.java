package com.parkmanager.modelo.enums;

import java.util.Locale;

public enum TipoVehiculo {
    CARRO,
    MOTO;

    public static TipoVehiculo desde(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El tipo de vehículo es obligatorio");
        }

        try {
            return TipoVehiculo.valueOf(valor.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de vehículo inválido: " + valor);
        }
    }
}
