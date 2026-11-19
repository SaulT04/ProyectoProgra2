package com.parkmanager.servicio;

import com.parkmanager.config.ConexionDB;
import com.parkmanager.dao.EspacioDAO;
import com.parkmanager.dao.EstadiaDAO;
import com.parkmanager.dao.VehiculoDAO;
import com.parkmanager.dao.impl.EspacioDAOImpl;
import com.parkmanager.dao.impl.EstadiaDAOImpl;
import com.parkmanager.dao.impl.VehiculoDAOImpl;
import com.parkmanager.modelo.Estadia;
import com.parkmanager.modelo.Espacio;
import com.parkmanager.modelo.Vehiculo;
import com.parkmanager.modelo.enums.EstadoEspacio;
import com.parkmanager.modelo.enums.EstadoEstadia;
import com.parkmanager.modelo.enums.TipoVehiculo;
import com.parkmanager.util.Validador;
import com.parkmanager.util.VehiculoFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class EstadiaService {

    private final EstadiaDAO estadiaDAO;
    private final VehiculoDAO vehiculoDAO;
    private final EspacioDAO espacioDAO;

    public EstadiaService() {
        this(new EstadiaDAOImpl(), new VehiculoDAOImpl(), new EspacioDAOImpl());
    }

    public EstadiaService(EstadiaDAO estadiaDAO, VehiculoDAO vehiculoDAO, EspacioDAO espacioDAO) {
        this.estadiaDAO = Objects.requireNonNull(estadiaDAO, "El DAO de estadías es obligatorio");
        this.vehiculoDAO = Objects.requireNonNull(vehiculoDAO, "El DAO de vehículos es obligatorio");
        this.espacioDAO = Objects.requireNonNull(espacioDAO, "El DAO de espacios es obligatorio");
    }

    public Estadia registrarIngreso(String placa, TipoVehiculo tipo) throws SQLException {
        String placaNormalizada = Validador.validarPlaca(placa);

        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de vehículo es obligatorio");
        }

        try (Connection conexion = ConexionDB.getConnection()) {
            conexion.setAutoCommit(false);

            try {
                Vehiculo vehiculo = vehiculoDAO.buscarPorPlaca(conexion, placaNormalizada);

                if (vehiculo == null) {
                    vehiculo = VehiculoFactory.crear(placaNormalizada, tipo);
                    vehiculoDAO.insertar(conexion, vehiculo);
                } else if (vehiculo.getTipo() != tipo) {
                    throw new IllegalArgumentException(
                            "La placa " + placaNormalizada + " ya está registrada como " + vehiculo.getTipo()
                    );
                }

                Estadia activa = estadiaDAO.buscarActivaPorPlaca(conexion, placaNormalizada);
                if (activa != null) {
                    throw new IllegalStateException(
                            "El vehículo con placa " + placaNormalizada + " ya tiene una estadía activa"
                    );
                }

                Espacio espacio = espacioDAO.buscarDisponiblePorTipoParaActualizar(conexion, tipo);
                if (espacio == null) {
                    throw new IllegalStateException("No hay espacios disponibles para " + tipo);
                }

                espacio.ocupar();
                boolean espacioActualizado = espacioDAO.actualizarEstado(
                        conexion, espacio.getIdEspacio(), espacio.getEstado()
                );
                if (!espacioActualizado) {
                    throw new SQLException("No se pudo ocupar el espacio " + espacio.getCodigo());
                }

                LocalDateTime fechaEntrada = LocalDateTime.now();
                Estadia estadia = new Estadia(vehiculo, espacio, fechaEntrada);
                int idEstadia = estadiaDAO.insertar(conexion, estadia);

                Estadia registrada = new Estadia(
                        idEstadia, vehiculo, espacio, fechaEntrada, null, null, EstadoEstadia.ACTIVA
                );

                conexion.commit();
                return registrada;
            } catch (SQLException | RuntimeException e) {
                conexion.rollback();
                throw e;
            }
        }
    }

    public Estadia registrarIngreso(String placa, String tipo) throws SQLException {
        return registrarIngreso(placa, Validador.validarTipoVehiculo(tipo));
    }

    public Estadia registrarSalida(int idEstadia) throws SQLException {
        Validador.validarId(idEstadia);

        try (Connection conexion = ConexionDB.getConnection()) {
            conexion.setAutoCommit(false);

            try {
                Estadia estadia = estadiaDAO.buscarPorId(conexion, idEstadia);
                if (estadia == null) {
                    throw new IllegalArgumentException("No existe la estadía con id " + idEstadia);
                }
                if (estadia.getEstado() == EstadoEstadia.FINALIZADA) {
                    throw new IllegalStateException("La estadía ya está finalizada");
                }

                LocalDateTime fechaSalida = LocalDateTime.now();
                estadia.finalizar(fechaSalida);

                boolean estadiaActualizada = estadiaDAO.finalizar(
                        conexion, idEstadia, fechaSalida, estadia.getMonto()
                );
                if (!estadiaActualizada) {
                    throw new SQLException("No se pudo finalizar la estadía " + idEstadia);
                }

                boolean espacioActualizado = espacioDAO.actualizarEstado(
                        conexion, estadia.getEspacio().getIdEspacio(), EstadoEspacio.LIBRE
                );
                if (!espacioActualizado) {
                    throw new SQLException(
                            "No se pudo liberar el espacio " + estadia.getEspacio().getCodigo()
                    );
                }

                if (estadia.getEspacio().getEstado() == EstadoEspacio.OCUPADO) {
                    estadia.getEspacio().liberar();
                }

                conexion.commit();
                return estadia;
            } catch (SQLException | RuntimeException e) {
                conexion.rollback();
                throw e;
            }
        }
    }

    public List<Estadia> listarActivas() throws SQLException {
        return estadiaDAO.listarActivas();
    }

    public Estadia buscarPorId(int idEstadia) throws SQLException {
        Validador.validarId(idEstadia);
        return estadiaDAO.buscarPorId(idEstadia);
    }

    public Estadia buscarActivaPorPlaca(String placa) throws SQLException {
        String placaNormalizada = Validador.validarPlaca(placa);
        return estadiaDAO.buscarActivaPorPlaca(placaNormalizada);
    }

    public List<Estadia> buscarHistorial(String placa) throws SQLException {
        String placaNormalizada = Validador.validarPlaca(placa);
        return estadiaDAO.listarPorPlaca(placaNormalizada);
    }
}
