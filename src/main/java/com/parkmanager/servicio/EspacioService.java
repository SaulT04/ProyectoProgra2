package com.parkmanager.servicio;

import com.parkmanager.dao.EspacioDAO;
import com.parkmanager.dao.impl.EspacioDAOImpl;
import com.parkmanager.modelo.Espacio;
import com.parkmanager.modelo.enums.TipoVehiculo;
import com.parkmanager.util.Validador;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class EspacioService {

    private final EspacioDAO espacioDAO;

    public EspacioService() {
        this(new EspacioDAOImpl());
    }

    public EspacioService(EspacioDAO espacioDAO) {
        this.espacioDAO = Objects.requireNonNull(
                espacioDAO, "El DAO de espacios es obligatorio"
        );
    }

    public int registrarEspacio(String codigo, TipoVehiculo tipo) throws SQLException {
        String codigoNormalizado = Validador.validarCodigoEspacio(codigo);

        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de vehículo es obligatorio");
        }
        if (espacioDAO.existeCodigo(codigoNormalizado)) {
            throw new IllegalArgumentException(
                    "Ya existe un espacio con el código " + codigoNormalizado
            );
        }

        Espacio espacio = new Espacio(codigoNormalizado, tipo);
        return espacioDAO.insertar(espacio);
    }

    public int registrarEspacio(String codigo, String tipo) throws SQLException {
        return registrarEspacio(codigo, Validador.validarTipoVehiculo(tipo));
    }

    public List<Espacio> listarEspacios() throws SQLException {
        return espacioDAO.listarTodos();
    }

    public List<Espacio> listarDisponibles(TipoVehiculo tipo) throws SQLException {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de vehículo es obligatorio");
        }
        return espacioDAO.listarDisponiblesPorTipo(tipo);
    }

    public Espacio buscarPorId(int idEspacio) throws SQLException {
        Validador.validarId(idEspacio);
        return espacioDAO.buscarPorId(idEspacio);
    }
}
