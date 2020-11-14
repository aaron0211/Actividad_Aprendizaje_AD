package com.aaron.actividad;

import com.aaron.actividad.domain.Usuarios;
import com.aaron.actividad.util.R;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UsuariosDAO {

    private Connection conexion;

    public void conectar() throws ClassNotFoundException, SQLException, IOException {
        Properties configuration = new Properties();
        String host = configuration.getProperty("host");
        String port = configuration.getProperty("port");
        String name = configuration.getProperty("name");
        String username = configuration.getProperty("username");
        String password = configuration.getProperty("password");
        configuration.load(R.getProperties("database.properties"));
        Class.forName("com.mysql.cj.jdbc.Driver");
        //conexion = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+name+"?serverTimezone=UTC",username,password);
        conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/registro?serverTimezone=UTC",
                "root", password);
    }

    public void desconectar() throws SQLException {
        conexion.close();
    }

    public void nuevoRegistro(Usuarios usuarios) throws SQLException{
        String sql = "INSERT INTO registro (nombre, apellidos, dni, telefono, subscripcion) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, usuarios.getNombre());
        sentencia.setString(2, usuarios.getApellidos());
        sentencia.setString(3, usuarios.getDni());
        sentencia.setString(4, usuarios.getTelefono());
        sentencia.setString(5, usuarios.getSubs());
        sentencia.executeUpdate();
    }

    public void borrarRegistro(Usuarios usuarios) throws SQLException{
        String sql = "DELETE FROM registro where dni = ?";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1,usuarios.getDni());
        sentencia.executeUpdate();
    }

    public void modificarRegistro(Usuarios usuarios) {
        String sql = "UPDATE registro set nombre = ?, apellidos = ?, telefono = ? ,subscripcion = ? WHERE dni = ?";
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
        String sql = "SELECT * FROM registro";
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
        String sql = "SELECT * FROM registro where nombre regexp ?";
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
