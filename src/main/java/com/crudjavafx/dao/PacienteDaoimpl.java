package com.crudjavafx.dao;

import com.crudjavafx.modelo.Paciente;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.List;
import com.crudjavafx.util.Conexion;

public class PacienteDaoimpl implements PacienteDao {


    public void insertar(Paciente paciente) throws SQLException {
        String sql = "INSERT INTO pacientes (cedula, nombres, apellidos, fecha_nacimiento, genero, email, tipo_sangre, alergias, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = Conexion.getConexion();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, paciente.getCedula());
        pstmt.setString(2, paciente.getNombres());
        pstmt.setString(3, paciente.getApellidos());
        pstmt.setDate(4, Date.valueOf(paciente.getFechaNacimiento()));
        pstmt.setString(5, paciente.getGenero());
        pstmt.setString(6, paciente.getEmail());
        pstmt.setString(7, paciente.getTipoSangre());
        pstmt.setString(8, paciente.getAlergias());
        pstmt.setBoolean(9, paciente.isActivo());
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    public List<Paciente> listar() throws SQLException {
        String sql = "SELECT * FROM pacientes ORDER BY apellidos";
        Connection conn = Conexion.getConexion();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        var rs = pstmt.executeQuery();
        List<Paciente> pacientes = new java.util.ArrayList<>();
        while (rs.next()) {
            Paciente paciente = new Paciente(
                    rs.getString("cedula"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("genero"),
                    rs.getString("email"),
                    rs.getString("tipo_sangre"),
                    rs.getString("alergias")
            );
            paciente.setActivo(rs.getBoolean("activo"));
            pacientes.add(paciente);
        }
        return pacientes;
    }

    public void actualizar(Paciente paciente) throws SQLException {
        String sql = "UPDATE pacientes SET nombres=?, apellidos=?, fecha_nacimiento=?, genero=?, email=?, tipo_sangre=?, alergias=?, activo=? WHERE cedula=?";
        Connection conn = Conexion.getConexion();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, paciente.getNombres());
        pstmt.setString(2, paciente.getApellidos());
        pstmt.setDate(3, Date.valueOf(paciente.getFechaNacimiento()));
        pstmt.setString(4, paciente.getGenero());
        pstmt.setString(6, paciente.getEmail());
        pstmt.setString(7, paciente.getTipoSangre());
        pstmt.setString(8, paciente.getAlergias());
        pstmt.setBoolean(9, paciente.isActivo());
        pstmt.setString(10, paciente.getCedula());
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    public void eliminar(String cedula) throws SQLException {
        String sql = "DELETE FROM pacientes WHERE cedula=?";
        Connection conn = Conexion.getConexion();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, cedula);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    public Paciente buscarPorCedula(String cedula) throws SQLException {
        String sql = "SELECT * FROM pacientes WHERE cedula=?";
        Connection conn = Conexion.getConexion();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, cedula);
        var rs = pstmt.executeQuery();
        Paciente paciente = null;
        if (rs.next()) {
            paciente = new Paciente(
                    rs.getString("cedula"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("genero"),
                    rs.getString("email"),
                    rs.getString("tipo_sangre"),
                    rs.getString("alergias")
            );
            paciente.setActivo(rs.getBoolean("activo"));
        }
        rs.close();
        pstmt.close();
        conn.close();
        return paciente;
    }
}
