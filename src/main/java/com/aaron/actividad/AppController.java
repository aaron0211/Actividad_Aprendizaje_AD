package com.aaron.actividad;

import com.aaron.actividad.domain.Usuarios;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AppController {

    public TextField tfNombre;
    public TextField tfApellidos;
    public TextField tfDni;
    public TextField tfTelefono;
    public ComboBox<String> cdSubs;

    private UsuariosDAO usuariosDAO;

    public AppController() {
        usuariosDAO = new UsuariosDAO();
        usuariosDAO.conectar();
    }

    @FXML
    public void buscarRegistro(Event event) {
    }

    @FXML
    public void modificarRegistro(Event event) {

    }

    @FXML
    public void borrarRegistro(Event event) {

    }

    @FXML
    public void mostrarRegistro(Event event){

    }

    @FXML
    public void nuevoRegistro(Event event) {
        String nombre = tfNombre.getText();
        if (nombre.equals("")) {
            // TODO Error de que falta indicar la matricula como campo obligatorio
        }
        String apellidos = tfApellidos.getText();
        String dni = tfDni.getText();
        String telefono = tfTelefono.getText();
        String subs = "anual";
                //cdSubs.getSelectionModel().getSelectedItem();

        Usuarios usuarios = new Usuarios(nombre,apellidos,dni,telefono,subs);
        usuariosDAO.nuevoRegistro(usuarios);
    }
}
