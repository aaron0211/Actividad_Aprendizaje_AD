package com.aaron.actividad;

import com.aaron.actividad.domain.Usuarios;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuariosDAO {

    private Connection conexion;

    public void conectar() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/registro?serverTimezone=UTC",
                    "root", "");
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void desconectar() {

    }

    public void nuevoRegistro(Usuarios usuarios) {
        String sql = "INSERT INTO usuarios (nombre, apellidos, dni, telefono, subscripcion) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, usuarios.getNombre());
            sentencia.setString(2, usuarios.getApellidos());
            sentencia.setString(3, usuarios.getDni());
            sentencia.setString(4, usuarios.getTelefono());
            sentencia.setString(5, usuarios.getSubs());
            sentencia.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Usuario registrado");
            alert.setContentText("El usuario se ha guardado con éxito");
            alert.show();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void eliminarCoche() {

    }

    public void modificarCoche() {

    }

    public void obtenerCoches() {

    }
}
