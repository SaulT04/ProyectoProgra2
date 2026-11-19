package com.parkmanager.web;

import com.parkmanager.modelo.Estadia;
import com.parkmanager.modelo.enums.EstadoEstadia;
import com.parkmanager.modelo.enums.TipoVehiculo;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class EstadiaVista {

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final int idEstadia;
    private final String placa;
    private final String tipoVehiculo;
    private final String espacio;
    private final String fechaEntrada;
    private final String fechaSalida;
    private final String duracion;
    private final String monto;
    private final String estado;
    private final String estadoClase;

    private EstadiaVista(int idEstadia,
                         String placa,
                         String tipoVehiculo,
                         String espacio,
                         String fechaEntrada,
                         String fechaSalida,
                         String duracion,
                         String monto,
                         String estado,
                         String estadoClase) {
        this.idEstadia = idEstadia;
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.espacio = espacio;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.duracion = duracion;
        this.monto = monto;
        this.estado = estado;
        this.estadoClase = estadoClase;
    }

    public static EstadiaVista desde(Estadia estadia) {
        Objects.requireNonNull(estadia, "La estadía es obligatoria");

        boolean activa = estadia.getEstado() == EstadoEstadia.ACTIVA;

        return new EstadiaVista(
                estadia.getIdEstadia(),
                estadia.getVehiculo().getPlaca(),
                tipoTexto(estadia.getVehiculo().getTipo()),
                estadia.getEspacio().getCodigo(),
                estadia.getFechaEntrada().format(FORMATO_FECHA),
                estadia.getFechaSalida() != null
                        ? estadia.getFechaSalida().format(FORMATO_FECHA)
                        : "—",
                formatearDuracion(estadia.getDuracionMinutos()),
                estadia.getMonto() != null
                        ? "Bs " + estadia.getMonto().setScale(2, RoundingMode.HALF_UP).toPlainString()
                        : "—",
                activa ? "Activa" : "Finalizada",
                activa ? "activa" : "finalizada"
        );
    }

    private static String tipoTexto(TipoVehiculo tipo) {
        return switch (tipo) {
            case CARRO -> "Carro";
            case MOTO -> "Moto";
        };
    }

    private static String formatearDuracion(long minutos) {
        long horas = minutos / 60;
        long resto = minutos % 60;

        if (horas == 0) {
            return resto + " min";
        }
        return horas + " h " + resto + " min";
    }

    public int getIdEstadia() {
        return idEstadia;
    }

    public String getPlaca() {
        return placa;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public String getEspacio() {
        return espacio;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getMonto() {
        return monto;
    }

    public String getEstado() {
        return estado;
    }

    public String getEstadoClase() {
        return estadoClase;
    }
}
