package com.parkmanager.modelo;

import com.parkmanager.modelo.enums.EstadoEstadia;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class Estadia {

    private final int idEstadia;
    private final Vehiculo vehiculo;
    private final Espacio espacio;
    private final LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private BigDecimal monto;
    private EstadoEstadia estado;

    public Estadia(Vehiculo vehiculo, Espacio espacio, LocalDateTime fechaEntrada) {
        this(0, vehiculo, espacio, fechaEntrada, null, null, EstadoEstadia.ACTIVA);
    }

    public Estadia(int idEstadia,
                   Vehiculo vehiculo,
                   Espacio espacio,
                   LocalDateTime fechaEntrada,
                   LocalDateTime fechaSalida,
                   BigDecimal monto,
                   EstadoEstadia estado) {

        if (idEstadia < 0) {
            throw new IllegalArgumentException("El identificador de la estadía no puede ser negativo");
        }
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo es obligatorio");
        }
        if (espacio == null) {
            throw new IllegalArgumentException("El espacio es obligatorio");
        }
        if (fechaEntrada == null) {
            throw new IllegalArgumentException("La fecha de entrada es obligatoria");
        }
        if (estado == null) {
            throw new IllegalArgumentException("El estado de la estadía es obligatorio");
        }
        if (fechaSalida != null && fechaSalida.isBefore(fechaEntrada)) {
            throw new IllegalArgumentException("La fecha de salida no puede ser anterior a la entrada");
        }
        if (monto != null && monto.signum() < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo");
        }
        if (estado == EstadoEstadia.ACTIVA && (fechaSalida != null || monto != null)) {
            throw new IllegalArgumentException("Una estadía activa no puede tener salida ni monto");
        }
        if (estado == EstadoEstadia.FINALIZADA && (fechaSalida == null || monto == null)) {
            throw new IllegalArgumentException("Una estadía finalizada debe tener salida y monto");
        }

        this.idEstadia = idEstadia;
        this.vehiculo = vehiculo;
        this.espacio = espacio;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.monto = monto;
        this.estado = estado;
    }

    public void finalizar(LocalDateTime fechaSalida) {
        if (estado == EstadoEstadia.FINALIZADA) {
            throw new IllegalStateException("La estadía ya está finalizada");
        }
        if (fechaSalida == null) {
            throw new IllegalArgumentException("La fecha de salida es obligatoria");
        }
        if (fechaSalida.isBefore(fechaEntrada)) {
            throw new IllegalArgumentException("La fecha de salida no puede ser anterior a la entrada");
        }

        long minutos = Duration.between(fechaEntrada, fechaSalida).toMinutes();
        this.fechaSalida = fechaSalida;
        this.monto = vehiculo.calcularMonto(minutos);
        this.estado = EstadoEstadia.FINALIZADA;
    }

    public long getDuracionMinutos() {
        LocalDateTime fin = fechaSalida != null ? fechaSalida : LocalDateTime.now();
        return Math.max(0, Duration.between(fechaEntrada, fin).toMinutes());
    }

    public int getIdEstadia() {
        return idEstadia;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Espacio getEspacio() {
        return espacio;
    }

    public LocalDateTime getFechaEntrada() {
        return fechaEntrada;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public EstadoEstadia getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Estadia{" + vehiculo.getPlaca()
                + ", " + espacio.getCodigo()
                + ", " + estado
                + ", monto=" + monto + '}';
    }
}
