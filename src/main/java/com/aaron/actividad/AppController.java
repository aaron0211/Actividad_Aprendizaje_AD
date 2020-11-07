package com.aaron.actividad;

import com.aaron.actividad.domain.Usuarios;
import com.aaron.actividad.util.Tarea;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

public class AppController {

    public TextField tfNombre;
    public TextField tfApellidos;
    public TextField tfDni;
    public TextField tfTelefono;
    public ComboBox<String> cbSubs;
    public ListView<Usuarios> lvRegistro;
    public Label lbMensaje;

    private UsuariosDAO usuariosDAO;
    private String mensaje;

    public AppController() {
        usuariosDAO = new UsuariosDAO();
        usuariosDAO.conectar();
    }

    public void exportar(){
        List<Usuarios> lista = usuariosDAO.mostrarRegistro();
        ListIterator li = lista.listIterator();

        try {
            FileWriter exportar = new FileWriter("C:\\Users\\aaron\\Desktop\\Prueba.txt");
            exportar.write("Nombre \t Apellidos \t DNI \t Teléfono \t Subscripción \n");
            while (li.hasNext()) {
                exportar.write(li.next()+"\n");
            }
            exportar.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mensaje = "Lista exportada";
        Tarea tarea = new Tarea(lbMensaje,mensaje);
        tarea.start();
    }

    public void cargarLista(){
        lvRegistro.getItems().clear();
        List<Usuarios> lista = usuariosDAO.mostrarRegistro();
        lvRegistro.setItems(FXCollections.observableList(lista));

        String[] subs = new String[]{"Mensual","Trimestral","Anual"};
        cbSubs.setItems(FXCollections.observableArrayList(subs));
    }

    @FXML
    public void buscarRegistro(Event event) {
        String dni = tfDni.getText();
        String apellidos = tfApellidos.getText();
        String nombre = tfNombre.getText();
        String telefono = tfTelefono.getText();
        String subs = cbSubs.getSelectionModel().getSelectedItem();

        Usuarios usuarios = new Usuarios(nombre,apellidos,dni,telefono,subs);
        List<Usuarios> lista = usuariosDAO.buscarRegistro(usuarios);
        lvRegistro.getItems().clear();
        lvRegistro.setItems(FXCollections.observableList(lista));
    }

    @FXML
    public void modificarRegistro(Event event) {
        String dni = tfDni.getText();
        if (dni.equals("")) return;
        String apellidos = tfApellidos.getText();
        String nombre = tfNombre.getText();
        String telefono = tfTelefono.getText();
        String subs = cbSubs.getSelectionModel().getSelectedItem();

        Usuarios usuarios = new Usuarios(nombre,apellidos,dni,telefono,subs);
        usuariosDAO.modificarRegistro(usuarios);
        cargarLista();
        mensaje = "Registro modificado";
        Tarea tarea = new Tarea(lbMensaje,mensaje);
        tarea.start();
    }

    @FXML
    public void borrarRegistro(Event event) {
        Usuarios usuarios = lvRegistro.getSelectionModel().getSelectedItem();
        if(usuarios == null) return;
        usuariosDAO.borrarRegistro(usuarios);
        cargarLista();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Usuario borrado");
        alert.setContentText("El usuario se ha borrado con éxito");
        alert.show();
    }

    @FXML
    public void mostrarRegistro(Event event){
    /*    String nombre = tfNombre.getText();
        String apellidos = tfApellidos.getText();
        String dni = tfDni.getText();
        String telefono = tfTelefono.getText();
        String subs = "anual";
        Usuarios usuarios = new Usuarios(nombre,apellidos,dni,telefono,subs);
        usuariosDAO.mostrarRegistro();*/

        exportar();

    }

    @FXML
    public void nuevoRegistro(Event event) {
        String nombre = tfNombre.getText();
        if (nombre.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nombre");
            alert.setContentText("Nombre obligatorio");
            alert.show();
            return;
        }
        String apellidos = tfApellidos.getText();
        String dni = tfDni.getText();
        String telefono = tfTelefono.getText();
        String subs = cbSubs.getSelectionModel().getSelectedItem();

        Usuarios usuarios = new Usuarios(nombre,apellidos,dni,telefono,subs);
        usuariosDAO.nuevoRegistro(usuarios);
        cargarLista();
        mensaje = "Usuario añadido";
        Tarea tarea = new Tarea(lbMensaje,mensaje);
        tarea.start();
    }

    private void cargarRegistro(Usuarios usuarios){
        tfNombre.setText(usuarios.getNombre());
        tfApellidos.setText(usuarios.getApellidos());
        tfDni.setText(usuarios.getDni());
        tfTelefono.setText(usuarios.getTelefono());
        cbSubs.setValue(usuarios.getSubs());
    }

    @FXML
    public void seleccionarRegistro(Event event){
        Usuarios usuarios = lvRegistro.getSelectionModel().getSelectedItem();
        cargarRegistro(usuarios);
    }
}
