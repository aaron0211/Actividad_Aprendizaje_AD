package com.aaron.actividad;

import com.aaron.actividad.domain.Usuarios;
import com.aaron.actividad.util.AlertUtils;
import com.aaron.actividad.util.Tarea;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

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
        try {
            usuariosDAO.conectar();
        }catch (SQLException sqle){
            AlertUtils.mostrarAlerta("Fallo al conectar");
        }catch (ClassNotFoundException cnfe){
            AlertUtils.mostrarAlerta("Fallo al conectar");
        }catch (IOException ioe){
            AlertUtils.mostrarAlerta("Fallo al conectar");
        }
    }

    public void exportar(){
        List<Usuarios> lista = lvRegistro.getItems();
        ListIterator li = lista.listIterator();

        try {
            FileWriter exportar = new FileWriter("C:\\Users\\aaron\\Desktop\\Prueba.txt");
            exportar.write("Nombre \t Apellidos \t DNI \t Teléfono \t Subscripción \n");
            while (li.hasNext()) {
                exportar.write(li.next()+"\n");
            }
            exportar.close();
            mensaje = "Lista exportada";
            Tarea tarea = new Tarea(lbMensaje,mensaje);
            tarea.start();
        } catch (IOException e) {
            AlertUtils.mostrarAlerta("Fallo al exportar");
        }
    }

    public void cargarLista(){
        lvRegistro.getItems().clear();
        List<Usuarios> lista = usuariosDAO.mostrarRegistro();
        lvRegistro.setItems(FXCollections.observableList(lista));

        String[] subs = new String[]{"<Selecciona>","Mensual","Trimestral","Anual"};
        cbSubs.setItems(FXCollections.observableArrayList(subs));
    }

    @FXML
    public void revert(Event event){
        cargarLista();
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
        try {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setContentText("¿Estás seguro?");
            confirmacion.setHeaderText("Eliminar usuario");
            Optional<ButtonType> respuesta = confirmacion.showAndWait();
            if(respuesta.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE)
                return;

            usuariosDAO.borrarRegistro(usuarios);
            cargarLista();
        }catch (SQLException sqlException){
            AlertUtils.mostrarAlerta("Fallo al eliminar usuario");
        }
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
    public void nuevo(Event event){
        tfNombre.setText("");
        tfApellidos.setText("");
        tfTelefono.setText("");
        tfDni.setText("");
        cbSubs.setValue("<Selecciona>");
        tfNombre.requestFocus();
    }

    @FXML
    public void nuevoRegistro(Event event) {
        String nombre = tfNombre.getText();
        if (nombre.equals("")) {
            mensaje = "Nombre OBLIGATORIO";
            Tarea tarea = new Tarea(lbMensaje,mensaje);
            tarea.start();
            return;
        }
        String apellidos = tfApellidos.getText();
        String dni = tfDni.getText();
        String telefono = tfTelefono.getText();
        String subs = cbSubs.getSelectionModel().getSelectedItem();
        Usuarios usuarios = new Usuarios(nombre,apellidos,dni,telefono,subs);

        try {
            usuariosDAO.nuevoRegistro(usuarios);
            cargarLista();
            mensaje = "Usuario añadido";
            Tarea tarea = new Tarea(lbMensaje,mensaje);
            tarea.start();
        }catch (SQLException sqlException){
            AlertUtils.mostrarAlerta("Fallo al añadir el usuario");
        }
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
