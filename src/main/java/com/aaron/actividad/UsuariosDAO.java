package com.aaron.actividad;

import com.aaron.actividad.domain.Usuarios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void borrarRegistro(Usuarios usuarios) {
        String sql = "DELETE FROM usuarios where dni = ?";

        try{
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1,usuarios.getDni());
            sentencia.executeUpdate();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    public void modificarRegistro(Usuarios usuarios) {
        String sql = "UPDATE usuarios set nombre = ?, apellidos = ?, telefono = ? ,subscripcion = ? WHERE dni = ?";
    /*    String sql2 = "UPDATE usuarios set nombre = ?, apellidos = ? WHERE dni = ?";
        String sql3 = "UPDATE usuarios set nombre = ? WHERE dni = ?";
        String sql4 = "UPDATE usuarios set apellidos = ? WHERE dni = ?";    */

        try{
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1,usuarios.getNombre());
            sentencia.setString(2,usuarios.getApellidos());
            sentencia.setString(3,usuarios.getTelefono());
            sentencia.setString(4,usuarios.getSubs());
            sentencia.setString(5,usuarios.getDni());
            sentencia.executeUpdate();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    public List<Usuarios> mostrarRegistro() {
        String sql = "SELECT * FROM usuarios";
        List<Usuarios> lista = new ArrayList<>();
        try{
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            ResultSet resultSet = sentencia.executeQuery();

            while(resultSet.next()){
                Usuarios usuarios = new Usuarios();
                usuarios.setNombre(resultSet.getString(2));
                usuarios.setApellidos(resultSet.getString(3));
                usuarios.setDni(resultSet.getString(4));
                usuarios.setTelefono(resultSet.getString(5));
                usuarios.setSubs(resultSet.getString(6));
                lista.add(usuarios);
            }
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return lista;
    }

    public List<Usuarios> buscarRegistro(Usuarios usuarios){
        String sql = "SELECT * FROM usuarios where nombre regexp ?";
        List<Usuarios> lista = new ArrayList<>();
        try{
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1,usuarios.getNombre());
            ResultSet resultSet = sentencia.executeQuery();

            while(resultSet.next()){
                Usuarios lUsuarios = new Usuarios();
                lUsuarios.setNombre(resultSet.getString(2));
                lUsuarios.setApellidos(resultSet.getString(3));
                lUsuarios.setDni(resultSet.getString(4));
                lUsuarios.setTelefono(resultSet.getString(5));
                lUsuarios.setSubs(resultSet.getString(6));
                lista.add(lUsuarios);
            }
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return lista;
    }
}
