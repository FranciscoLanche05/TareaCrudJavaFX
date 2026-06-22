package com.crudjavafx.dao;

import com.crudjavafx.modelo.Paciente;

import java.sql.SQLException;
import java.util.List;

public interface PacienteDao {

    void insertar(Paciente paciente) throws SQLException;
    List<Paciente> listar() throws SQLException;
    void actualizar(Paciente paciente) throws SQLException;
    void eliminar(String cedula) throws SQLException;
    Paciente buscarPorCedula(String cedula) throws SQLException;

}
