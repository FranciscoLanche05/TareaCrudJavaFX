package com.crudjavafx.controller;

import com.crudjavafx.dao.PacienteDao;
import com.crudjavafx.dao.PacienteDaoimpl;
import com.crudjavafx.modelo.Paciente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class PacienteController {


    @FXML private TextField txtCedula;
    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private DatePicker dpFechaNac;
    @FXML private ToggleGroup tgGenero;
    @FXML private RadioButton rbMasculino;
    @FXML private RadioButton rbFemenino;
    @FXML private TextField txtEmail;
    @FXML private ComboBox<String> cbTipoSangre;
    @FXML private TextArea txtAlergias;
    @FXML private CheckBox chkActivo;


    @FXML private TableView<Paciente> tablaPacientes;
    @FXML private TableColumn<Paciente, String> colCedula;
    @FXML private TableColumn<Paciente, String> colNombres;
    @FXML private TableColumn<Paciente, String> colApellidos;
    @FXML private TableColumn<Paciente, LocalDate> colFechaNac;
    @FXML private TableColumn<Paciente, String> colTipoSangre;

    private PacienteDao pacienteDao = new PacienteDaoimpl();

    @FXML
    public void initialize() {

        cbTipoSangre.setItems(FXCollections.observableArrayList(
                "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
        ));


        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colFechaNac.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colTipoSangre.setCellValueFactory(new PropertyValueFactory<>("tipoSangre"));

        cargarPacientes();


        tablaPacientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cargarDatosEnFormulario(newSelection);
            }
        });
    }

    private void cargarPacientes() {
        try {
            ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList(pacienteDao.listar());
            tablaPacientes.setItems(listaPacientes);
        } catch (SQLException e) {
            mostrarAlerta("Error de Base de Datos", "No se pudieron cargar los pacientes.", Alert.AlertType.ERROR);
        }
    }

    private void cargarDatosEnFormulario(Paciente p) {
        txtCedula.setText(p.getCedula());
        txtCedula.setDisable(true);
        txtNombres.setText(p.getNombres());
        txtApellidos.setText(p.getApellidos());
        dpFechaNac.setValue(p.getFechaNacimiento());

        if (p.getGenero().equals("Masculino")) {
            rbMasculino.setSelected(true);
        } else if (p.getGenero().equals("Femenino")) {
            rbFemenino.setSelected(true);
        }

        txtEmail.setText(p.getEmail() != null ? p.getEmail() : "");
        cbTipoSangre.setValue(p.getTipoSangre());
        txtAlergias.setText(p.getAlergias() != null ? p.getAlergias() : "");
        chkActivo.setSelected(p.isActivo());
    }

    @FXML
    public void limpiarFormulario(ActionEvent event) {
        txtCedula.clear();
        txtCedula.setDisable(false);
        txtNombres.clear();
        txtApellidos.clear();
        dpFechaNac.setValue(null);
        tgGenero.selectToggle(null);
        txtEmail.clear();
        cbTipoSangre.getSelectionModel().clearSelection();
        txtAlergias.clear();
        chkActivo.setSelected(true);
        tablaPacientes.getSelectionModel().clearSelection();
    }

    @FXML
    public void guardarPaciente(ActionEvent event) {
        if (!validarCampos()) {
            return;
        }

        try {

            String cedula = txtCedula.getText().trim();
            String nombres = txtNombres.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            LocalDate fechaNac = dpFechaNac.getValue();
            RadioButton rbSeleccionado = (RadioButton) tgGenero.getSelectedToggle();
            String genero = rbSeleccionado.getText();
            String email = txtEmail.getText().trim();
            String tipoSangre = cbTipoSangre.getValue();
            String alergias = txtAlergias.getText().trim();
            boolean activo = chkActivo.isSelected();

            Paciente paciente = new Paciente(cedula, nombres, apellidos, fechaNac, genero, email, tipoSangre, alergias);
            paciente.setActivo(activo);


            if (txtCedula.isDisabled()) {
                pacienteDao.actualizar(paciente);
                mostrarAlerta("Éxito", "Paciente actualizado correctamente.", Alert.AlertType.INFORMATION);
            } else {

                if (pacienteDao.buscarPorCedula(cedula) != null) {
                    mostrarAlerta("Advertencia", "Ya existe un paciente con esa cédula.", Alert.AlertType.WARNING);
                    return;
                }
                pacienteDao.insertar(paciente);
                mostrarAlerta("Éxito", "Paciente registrado correctamente.", Alert.AlertType.INFORMATION);
            }

            cargarPacientes();
            limpiarFormulario(null);

        } catch (SQLException e) {
            mostrarAlerta("Error", "Ocurrió un error en la base de datos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void eliminarPaciente(ActionEvent event) {
        Paciente pacienteSeleccionado = tablaPacientes.getSelectionModel().getSelectedItem();

        if (pacienteSeleccionado == null) {
            mostrarAlerta("Advertencia", "Debe seleccionar un paciente de la tabla para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Eliminar al paciente " + pacienteSeleccionado.getNombres() + " " + pacienteSeleccionado.getApellidos() + "?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                pacienteDao.eliminar(pacienteSeleccionado.getCedula());
                mostrarAlerta("Éxito", "Paciente eliminado correctamente.", Alert.AlertType.INFORMATION);
                cargarPacientes();
                limpiarFormulario(null);
            } catch (SQLException e) {
                mostrarAlerta("Error", "No se pudo eliminar el paciente: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private boolean validarCampos() {
        if (!txtCedula.getText().matches("\\d{10}")) {
            mostrarAlerta("Error de Validación", "La cédula debe tener exactamente 10 dígitos numéricos.", Alert.AlertType.ERROR);
            return false;
        }
        if (!txtNombres.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            mostrarAlerta("Error de Validación", "El nombre no puede estar vacío ni contener números.", Alert.AlertType.ERROR);
            return false;
        }
        if (!txtApellidos.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            mostrarAlerta("Error de Validación", "El apellido no puede estar vacío ni contener números.", Alert.AlertType.ERROR);
            return false;
        }
        if (dpFechaNac.getValue() == null || dpFechaNac.getValue().isAfter(LocalDate.now())) {
            mostrarAlerta("Error de Validación", "La fecha de nacimiento es inválida o es futura.", Alert.AlertType.ERROR);
            return false;
        }
        if (tgGenero.getSelectedToggle() == null) {
            mostrarAlerta("Error de Validación", "Debe seleccionar un género.", Alert.AlertType.ERROR);
            return false;
        }

        if (!txtEmail.getText().isEmpty() && !txtEmail.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            mostrarAlerta("Error de Validación", "El correo electrónico no tiene un formato válido.", Alert.AlertType.ERROR);
            return false;
        }
        if (cbTipoSangre.getValue() == null) {
            mostrarAlerta("Error de Validación", "Debe seleccionar el tipo de sangre.", Alert.AlertType.ERROR);
            return false;
        }
        if (txtAlergias.getText().length() > 300) {
            mostrarAlerta("Error de Validación", "El campo alergias no puede superar 300 caracteres.", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}