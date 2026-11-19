package com.parkmanager.modelo;

import com.parkmanager.modelo.enums.TipoVehiculo;
import java.math.BigDecimal;

public class Carro extends Vehiculo {

    private static final BigDecimal TARIFA_POR_HORA = new BigDecimal("10.00");

    public Carro(String placa) {
        super(placa);
    }

    @Override
    public TipoVehiculo getTipo() {
        return TipoVehiculo.CARRO;
    }

    @Override
    public BigDecimal getTarifaPorHora() {
        return TARIFA_POR_HORA;
    }
}
