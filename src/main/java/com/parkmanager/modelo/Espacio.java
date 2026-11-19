package com.parkmanager.modelo;

import com.parkmanager.modelo.enums.EstadoEspacio;
import com.parkmanager.modelo.enums.TipoVehiculo;
import java.util.Locale;

public class Espacio {

    private final int idEspacio;
    private final String codigo;
    private final TipoVehiculo tipo;
    private EstadoEspacio estado;

    public Espacio(String codigo, TipoVehiculo tipo) {
        this(0, codigo, tipo, EstadoEspacio.LIBRE);
    }

    public Espacio(int idEspacio, String codigo, TipoVehiculo tipo, EstadoEspacio estado) {
        if (idEspacio < 0) {
            throw new IllegalArgumentException("El identificador del espacio no puede ser negativo");
        }
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código del espacio es obligatorio");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de vehículo del espacio es obligatorio");
        }
        if (estado == null) {
            throw new IllegalArgumentException("El estado del espacio es obligatorio");
        }

        this.idEspacio = idEspacio;
        this.codigo = codigo.trim().toUpperCase(Locale.ROOT);
        this.tipo = tipo;
        this.estado = estado;
    }

    public void ocupar() {
        if (estado == EstadoEspacio.OCUPADO) {
            throw new IllegalStateException("El espacio " + codigo + " ya está ocupado");
        }
        estado = EstadoEspacio.OCUPADO;
    }

    public void liberar() {
        if (estado == EstadoEspacio.LIBRE) {
            throw new IllegalStateException("El espacio " + codigo + " ya está libre");
        }
        estado = EstadoEspacio.LIBRE;
    }

    public boolean estaDisponible() {
        return estado == EstadoEspacio.LIBRE;
    }

    public int getIdEspacio() {
        return idEspacio;
    }

    public String getCodigo() {
        return codigo;
    }

    public TipoVehiculo getTipo() {
        return tipo;
    }

    public EstadoEspacio getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return codigo + " (" + tipo + ", " + estado + ")";
    }
}
