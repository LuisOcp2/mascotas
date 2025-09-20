/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.TerceroDAO;
import Model.Tercero;
import Theme.Theme;
import View.FrmTerceros;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lmog2
 */
public class CtrlTercero implements ActionListener {

    private final Tercero model;
    private final TerceroDAO consult;
    private final FrmTerceros vista;

    public CtrlTercero(Tercero model, TerceroDAO consult, FrmTerceros vista) {
        this.model = model;
        this.consult = consult;
        this.vista = vista;
    }

    public void iniciar() {
        vista.btn.addActionListener(this); // ← ESTO FALTABA
        vista.btnEliminar.addActionListener(this); // ← ESTO FALTABA
        configurarEstadoInicial();
        getJTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.btn) {

            // Obtener la acción actual del botón (crear o actualizar)
            String accionBoton = vista.btn.getText().toLowerCase().trim();

            // Log para debugging - útil durante desarrollo
            System.out.println("Acción ejecutada: " + accionBoton);

            // Ejecutar acción correspondiente usando switch moderno
            switch (accionBoton) {
                case "crear" ->
                    ejecutarCreacion();
                case "actualizar" ->
                    ejecutarActualizacion();
                default -> {
                    JOptionPane.showMessageDialog(vista,
                            "Acción no reconocida: " + accionBoton,
                            "Error del Sistema",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        if (e.getSource() == vista.btnEliminar) {

            ejecutarEliminacion();

        }
        iniciar();
    }

    private void ejecutarCreacion() {
        try {
            // Usar validación específica para creación
            if (!validarParaCreacion()) {
                return;
            }

            mapearDatosFormularioAModelo();
            boolean exito = consult.add(model);

            if (exito) {
                JOptionPane.showMessageDialog(vista,
                        "Tercero creado exitosamente",
                        "Operación Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);

                getJTable();
                limpiarFormulario();

            } else {
                JOptionPane.showMessageDialog(vista,
                        "No fue posible crear el tercero.\nVerifique los datos e intente nuevamente.",
                        "Error de Operación",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista,
                    "Error en el formato del ID. Debe ser un número válido.",
                    "Error de Validación",
                    JOptionPane.WARNING_MESSAGE);
            vista.txtId.requestFocus();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista,
                    "Error inesperado: " + ex.getMessage(),
                    "Error del Sistema",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        
    }

    /**
     * Método de actualización modificado con validación específica
     */
    private void ejecutarActualizacion() {
        try {
            if (vista.txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                        "Debe seleccionar un tercero de la tabla para actualizar",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Usar validación específica para actualización
            if (!validarParaActualizacion()) {
                return;
            }

            mapearDatosFormularioAModelo();
            boolean exito = consult.update(model);

            if (exito) {
                JOptionPane.showMessageDialog(vista,
                        "Tercero actualizado exitosamente",
                        "Operación Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);

                getJTable();
                resetearFormularioParaCreacion();

            } else {
                JOptionPane.showMessageDialog(vista,
                        "No fue posible actualizar el tercero.\nVerifique los datos e intente nuevamente.",
                        "Error de Operación",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista,
                    "Error durante la actualización: " + ex.getMessage(),
                    "Error del Sistema",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void autoCompletarId() {
        if (vista.txtId.getText().trim().isEmpty()) {
            TerceroDAO td = new TerceroDAO();
            int proximoId = td.sugerirProximoId();
            vista.txtId.setText(String.valueOf(proximoId));
        }
    }

    private void ejecutarEliminacion() {
        try {
            // Validar que hay un tercero seleccionado
            if (vista.txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                        "Debe seleccionar un tercero de la tabla para eliminar",
                        "Selección requerida",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = Integer.parseInt(vista.txtId.getText().trim());
            String razonSocial = vista.txtRazonSocial.getText();

            // Confirmación simple
            int confirmacion = JOptionPane.showConfirmDialog(vista,
                    "¿Confirma inactivar el tercero?\n\n"
                    + "ID: " + id + "\n"
                    + "Razón Social: " + razonSocial + "\n\n"
                    + "El tercero pasará a estado 'Inactivo'",
                    "Confirmar inactivación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean exito = consult.delete(id);

                if (exito) {
                    JOptionPane.showMessageDialog(vista,
                            "Tercero inactivado exitosamente",
                            "Operación exitosa",
                            JOptionPane.INFORMATION_MESSAGE);

                    getJTable();
                    resetearFormularioParaCreacion();
                } else {
                    JOptionPane.showMessageDialog(vista,
                            "No fue posible inactivar el tercero",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista,
                    "ID inválido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarDatosFormulario() {
        // Validar campos obligatorios
        if (vista.txtId.getText().trim().isEmpty()) {
            mostrarErrorValidacion("El ID es obligatorio");
            vista.txtId.requestFocus();
            return false;
        }

        if (vista.txtIdentifi.getText().trim().isEmpty()) {
            mostrarErrorValidacion("La identificación es obligatoria");
            vista.txtIdentifi.requestFocus();
            return false;
        }

        if (vista.txtRazonSocial.getText().trim().isEmpty()) {
            mostrarErrorValidacion("La razón social es obligatoria");
            vista.txtRazonSocial.requestFocus();
            return false;
        }

        // Validar formato del ID
        int id;
        try {
            id = Integer.parseInt(vista.txtId.getText().trim());
            if (id <= 0) {
                mostrarErrorValidacion("El ID debe ser un número positivo");
                vista.txtId.requestFocus();
                return false;
            }
        } catch (NumberFormatException ex) {
            mostrarErrorValidacion("El ID debe ser un número válido");
            vista.txtId.requestFocus();
            return false;
        }

        // Validar longitud de identificación
        if (vista.txtIdentifi.getText().trim().length() < 3) {
            mostrarErrorValidacion("La identificación debe tener al menos 3 caracteres");
            vista.txtIdentifi.requestFocus();
            return false;
        }

        // Validar combos
        if (vista.cmbTi.getSelectedIndex() == 4) {
            mostrarErrorValidacion("Debe seleccionar un tipo de identificación válido");
            vista.cmbTi.requestFocus();
            return false;
        }

        if (vista.cmbEstado.getSelectedIndex() == 2) {
            mostrarErrorValidacion("Debe seleccionar un estado válido");
            vista.cmbEstado.requestFocus();
            return false;
        }

        return true;
    }

    private boolean validarParaCreacion() {
        // Primero validar datos básicos
        if (!validarDatosFormulario()) {
            return false;
        }

        int id = Integer.parseInt(vista.txtId.getText().trim());
        String identificacion = vista.txtIdentifi.getText().trim();

        // VALIDACIÓN CRÍTICA: Verificar ID duplicado
        if (consult.existeId(id)) {
            mostrarErrorValidacion("Ya existe un tercero con el ID: " + id
                    + "\nPor favor, use un ID diferente.");
            vista.txtId.requestFocus();
            vista.txtId.selectAll(); // Seleccionar todo el texto para facilitar reemplazo
            return false;
        }

        // VALIDACIÓN ADICIONAL: Verificar identificación duplicada
        if (consult.existeIdentificacion(identificacion)) {
            mostrarErrorValidacion("Ya existe un tercero con la identificación: " + identificacion
                    + "\nPor favor, verifique el número de identificación.");
            vista.txtIdentifi.requestFocus();
            vista.txtIdentifi.selectAll();
            return false;
        }

        return true;
    }

    private boolean validarParaActualizacion() {
        // Para actualización, no validar duplicados porque es el mismo registro
        return validarDatosFormulario();
    }

    private void mapearDatosFormularioAModelo() {
        // Mapeo con validaciones y conversiones necesarias
        model.setId(Integer.parseInt(vista.txtId.getText().trim()));
        model.setTi(vista.cmbTi.getSelectedItem().toString());
        model.setIdentificacion(vista.txtIdentifi.getText().trim());
        model.setRepresentante_legal(vista.txtRepresentante.getText().trim());
        model.setRazon_social(vista.txtRazonSocial.getText().trim());
        model.setDireccion(vista.txtDir.getText().trim());
        model.setTel1(vista.txtNum1.getText().trim());
        model.setTel2(vista.txtNum2.getText().trim());

        // CORRECCIÓN CRÍTICA: Usar los campos correctos
        // ANTES (INCORRECTO): model.setObservaciones(vista.txtNum2.getText());
        // DESPUÉS (CORRECTO):
        model.setObservaciones(vista.txtAObs.getText().trim());

        // ANTES (INCORRECTO): model.setEstado(vista.txtNum2.getText());
        // DESPUÉS (CORRECTO):
        model.setEstado(vista.cmbEstado.getSelectedItem().toString());
    }

    public void getJTable() {
        try {
            // Obtener datos desde el DAO usando tu método existente
            List<Tercero> terceros = consult.getAllTerceros();
            DefaultTableModel modeloTabla = (DefaultTableModel) vista.tableTerceros.getModel();

            // Limpiar tabla antes de cargar nuevos datos
            modeloTabla.setRowCount(0);

            // Iterar y agregar cada tercero a la tabla
            for (Tercero terceroItem : terceros) {
                // Determinar color de estado (mantienes tu lógica existente)
                String color = terceroItem.getEstado().toLowerCase().equals("activo")
                        ? "#4CAF50" // Verde para activo
                        : "#F44336"; // Rojo para inactivo

                String estado = terceroItem.getEstado();

                // Agregar fila con todos los datos del tercero
                modeloTabla.addRow(new Object[]{
                    terceroItem.getId(),
                    terceroItem.getTi(),
                    terceroItem.getIdentificacion(),
                    terceroItem.getRepresentante_legal(),
                    terceroItem.getRazon_social(),
                    terceroItem.getDireccion(),
                    terceroItem.getTel1(),
                    terceroItem.getTel2(),
                    terceroItem.getObservaciones(),
                    estado
                });
            }

            // Log para debugging
            System.out.println("Tabla actualizada con " + terceros.size() + " registros");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista,
                    "Error al cargar los datos: " + ex.getMessage(),
                    "Error de Carga",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void configurarEstadoInicial() {
        vista.btn.setText("Crear");
        vista.btn.putClientProperty(FlatClientProperties.STYLE, Theme.btnPrinci + ";" + Theme.h2);
        limpiarFormulario();

        // AUTO-COMPLETAR ID DISPONIBLE
        autoCompletarId(); // ← AGREGAR ESTA LÍNEA

        vista.cmbTi.setSelectedIndex(0);
        vista.cmbEstado.setSelectedIndex(1);
    }

    private void limpiarFormulario() {
        vista.txtId.setText("");
        vista.txtIdentifi.setText("");
        vista.txtRepresentante.setText("");
        vista.txtRazonSocial.setText("");
        vista.txtDir.setText("");
        vista.txtNum1.setText("");
        vista.txtNum2.setText("");
        vista.txtAObs.setText("");

        // Resetear combos a valores por defecto
        vista.cmbTi.setSelectedIndex(0);
        vista.cmbEstado.setSelectedIndex(1);
    }

    private void resetearFormularioParaCreacion() {
        limpiarFormulario();
        vista.btn.setText("Crear");
        vista.btn.putClientProperty(FlatClientProperties.STYLE, Theme.btnPrinci + ";" + Theme.h2);
    }

    private void mostrarErrorValidacion(String mensaje) {
        JOptionPane.showMessageDialog(vista,
                mensaje,
                "Error de Validación",
                JOptionPane.WARNING_MESSAGE);
    }

    public void configurarModoActualizacion() {
        vista.btn.setText("Actualizar");
        vista.btn.putClientProperty(FlatClientProperties.STYLE, Theme.btnSecun + ";" + Theme.h2);
    }

}
