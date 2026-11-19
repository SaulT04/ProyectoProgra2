package com.parkmanager.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConexionDB {

    private static final String ARCHIVO_CONFIGURACION = "db.properties";
    private static final Properties PROPIEDADES = cargarPropiedades();

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(
                    "No se encontró el controlador JDBC de PostgreSQL", e
            );
        }
    }

    private ConexionDB() {
    }

    private static Properties cargarPropiedades() {
        Properties propiedades = new Properties();

        try (InputStream entrada = ConexionDB.class
                .getClassLoader()
                .getResourceAsStream(ARCHIVO_CONFIGURACION)) {

            if (entrada == null) {
                throw new IllegalStateException(
                        "No se encontró el archivo " + ARCHIVO_CONFIGURACION
                                + " en src/main/resources. "
                                + "Copia db.properties.example y configura tus datos."
                );
            }

            propiedades.load(entrada);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "No se pudo leer el archivo " + ARCHIVO_CONFIGURACION, e
            );
        }

        validarPropiedad(propiedades, "db.url");
        validarPropiedad(propiedades, "db.user");
        validarPropiedad(propiedades, "db.password");

        return propiedades;
    }

    private static void validarPropiedad(Properties propiedades, String clave) {
        String valor = propiedades.getProperty(clave);
        if (valor == null || valor.isBlank()) {
            throw new IllegalStateException(
                    "La propiedad '" + clave + "' no está definida en "
                            + ARCHIVO_CONFIGURACION
            );
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PROPIEDADES.getProperty("db.url"),
                PROPIEDADES.getProperty("db.user"),
                PROPIEDADES.getProperty("db.password")
        );
    }
}
