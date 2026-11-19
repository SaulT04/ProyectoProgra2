package com.parkmanager.util;

import com.parkmanager.modelo.enums.TipoVehiculo;
import java.util.Locale;
import java.util.regex.Pattern;

public final class Validador {

    private static final Pattern PATRON_PLACA = Pattern.compile("^[A-Z0-9-]{4,10}$");
    private static final Pattern PATRON_CODIGO_ESPACIO = Pattern.compile("^[A-Z0-9-]{2,10}$");

    private Validador() {
    }

    public static String validarPlaca(String placa) {
        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException("La placa es obligatoria");
        }

        String normalizada = placa.trim().toUpperCase(Locale.ROOT);
        if (!PATRON_PLACA.matcher(normalizada).matches()) {
            throw new IllegalArgumentException(
                    "La placa debe tener entre 4 y 10 caracteres: letras, números o guiones"
            );
        }
        return normalizada;
    }

    public static String validarCodigoEspacio(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código del espacio es obligatorio");
        }

        String normalizado = codigo.trim().toUpperCase(Locale.ROOT);
        if (!PATRON_CODIGO_ESPACIO.matcher(normalizado).matches()) {
            throw new IllegalArgumentException(
                    "El código del espacio debe tener entre 2 y 10 caracteres: letras, números o guiones"
            );
        }
        return normalizado;
    }

    public static TipoVehiculo validarTipoVehiculo(String tipo) {
        return TipoVehiculo.desde(tipo);
    }

    public static int validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El identificador debe ser mayor a cero");
        }
        return id;
    }
}
