package com.parkmanager.modelo;

import com.parkmanager.modelo.enums.TipoVehiculo;
import java.math.BigDecimal;

public class Motocicleta extends Vehiculo {

    private static final BigDecimal TARIFA_POR_HORA = new BigDecimal("5.00");

    public Motocicleta(String placa) {
        super(placa);
    }

    @Override
    public TipoVehiculo getTipo() {
        return TipoVehiculo.MOTO;
    }

    @Override
    public BigDecimal getTarifaPorHora() {
        return TARIFA_POR_HORA;
    }
}
