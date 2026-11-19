package com.parkmanager.modelo;

import com.parkmanager.modelo.enums.TipoVehiculo;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Objects;

public abstract class Vehiculo {

    private final String placa;

    protected Vehiculo(String placa) {
        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException("La placa es obligatoria");
        }
        this.placa = placa.trim().toUpperCase(Locale.ROOT);
    }

    public String getPlaca() {
        return placa;
    }

    public abstract TipoVehiculo getTipo();

    public abstract BigDecimal getTarifaPorHora();

    public BigDecimal calcularMonto(long minutos) {
        if (minutos < 0) {
            throw new IllegalArgumentException("El tiempo no puede ser negativo");
        }

        long horas = (minutos + 59) / 60;
        if (horas == 0) {
            horas = 1;
        }

        return getTarifaPorHora()
                .multiply(BigDecimal.valueOf(horas))
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (!(objeto instanceof Vehiculo)) {
            return false;
        }
        Vehiculo otro = (Vehiculo) objeto;
        return placa.equals(otro.placa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placa);
    }

    @Override
    public String toString() {
        return placa + " (" + getTipo() + ")";
    }
}
