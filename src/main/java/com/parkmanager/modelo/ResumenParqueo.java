package com.parkmanager.modelo;

import java.math.BigDecimal;

public class ResumenParqueo {

    private final int totalEspacios;
    private final int espaciosLibres;
    private final int espaciosOcupados;
    private final int estadiasActivas;
    private final int estadiasFinalizadas;
    private final BigDecimal ingresosTotales;

    public ResumenParqueo(int totalEspacios,
                          int espaciosLibres,
                          int espaciosOcupados,
                          int estadiasActivas,
                          int estadiasFinalizadas,
                          BigDecimal ingresosTotales) {

        if (totalEspacios < 0) {
            throw new IllegalArgumentException("El total de espacios no puede ser negativo");
        }
        if (espaciosLibres < 0) {
            throw new IllegalArgumentException("La cantidad de espacios libres no puede ser negativa");
        }
        if (espaciosOcupados < 0) {
            throw new IllegalArgumentException("La cantidad de espacios ocupados no puede ser negativa");
        }
        if (espaciosLibres + espaciosOcupados > totalEspacios) {
            throw new IllegalArgumentException("Los espacios libres y ocupados superan el total");
        }
        if (estadiasActivas < 0) {
            throw new IllegalArgumentException("La cantidad de estadías activas no puede ser negativa");
        }
        if (estadiasFinalizadas < 0) {
            throw new IllegalArgumentException("La cantidad de estadías finalizadas no puede ser negativa");
        }
        if (ingresosTotales == null) {
            throw new IllegalArgumentException("Los ingresos totales son obligatorios");
        }
        if (ingresosTotales.signum() < 0) {
            throw new IllegalArgumentException("Los ingresos totales no pueden ser negativos");
        }

        this.totalEspacios = totalEspacios;
        this.espaciosLibres = espaciosLibres;
        this.espaciosOcupados = espaciosOcupados;
        this.estadiasActivas = estadiasActivas;
        this.estadiasFinalizadas = estadiasFinalizadas;
        this.ingresosTotales = ingresosTotales;
    }

    public double getPorcentajeOcupacion() {
        if (totalEspacios == 0) {
            return 0.0;
        }
        return (espaciosOcupados * 100.0) / totalEspacios;
    }

    public int getEstadiasTotales() {
        return estadiasActivas + estadiasFinalizadas;
    }

    public int getTotalEspacios() {
        return totalEspacios;
    }

    public int getEspaciosLibres() {
        return espaciosLibres;
    }

    public int getEspaciosOcupados() {
        return espaciosOcupados;
    }

    public int getEstadiasActivas() {
        return estadiasActivas;
    }

    public int getEstadiasFinalizadas() {
        return estadiasFinalizadas;
    }

    public BigDecimal getIngresosTotales() {
        return ingresosTotales;
    }
}
