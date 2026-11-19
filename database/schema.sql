CREATE TABLE IF NOT EXISTS espacio (
    id_espacio SERIAL PRIMARY KEY,
    codigo     VARCHAR(10) NOT NULL UNIQUE,
    tipo       VARCHAR(10) NOT NULL CHECK (tipo IN ('CARRO', 'MOTO')),
    estado     VARCHAR(10) NOT NULL DEFAULT 'LIBRE'
                            CHECK (estado IN ('LIBRE', 'OCUPADO'))
);

CREATE TABLE IF NOT EXISTS vehiculo (
    placa VARCHAR(10) PRIMARY KEY,
    tipo  VARCHAR(10) NOT NULL CHECK (tipo IN ('CARRO', 'MOTO'))
);

CREATE TABLE IF NOT EXISTS estadia (
    id_estadia    SERIAL PRIMARY KEY,
    placa         VARCHAR(10) NOT NULL REFERENCES vehiculo(placa),
    id_espacio    INTEGER NOT NULL REFERENCES espacio(id_espacio),
    fecha_entrada TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_salida  TIMESTAMP,
    monto         NUMERIC(10,2),
    estado        VARCHAR(10) NOT NULL DEFAULT 'ACTIVA'
                            CHECK (estado IN ('ACTIVA', 'FINALIZADA')),
    CONSTRAINT chk_fechas CHECK (fecha_salida IS NULL OR fecha_salida >= fecha_entrada),
    CONSTRAINT chk_monto CHECK (monto IS NULL OR monto >= 0)
);

CREATE UNIQUE INDEX IF NOT EXISTS ux_estadia_espacio_activa
    ON estadia(id_espacio)
    WHERE estado = 'ACTIVA';

CREATE UNIQUE INDEX IF NOT EXISTS ux_estadia_placa_activa
    ON estadia(placa)
    WHERE estado = 'ACTIVA';

CREATE INDEX IF NOT EXISTS ix_estadia_placa ON estadia(placa);
CREATE INDEX IF NOT EXISTS ix_espacio_tipo_estado ON espacio(tipo, estado);
