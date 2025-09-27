package Controller;

import DAO.FormaPagoDAO;
import Model.FormaPago;
import Theme.Theme;
import View.FrmFormaPago;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CtrlFormaPago implements ActionListener {

    private final FormaPago model;
    private final FormaPagoDAO consult;
    private final FrmFormaPago vista;

    public CtrlFormaPago(FormaPago model, FormaPagoDAO consult, FrmFormaPago vista) {
        this.model = model;
        this.consult = consult;
        this.vista = vista;
    }

    public void iniciar() {
        vista.btn.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        configurarEstadoInicial();
        getJTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.btn) {
            String accionBoton = vista.btn.getText().toLowerCase().trim();
            System.out.println("Acción ejecutada: " + accionBoton);

            switch (accionBoton) {
                case "crear" ->
                    add();
                case "actualizar" ->
                    update();
                default -> {
                    JOptionPane.showMessageDialog(vista,
                            "Acción no reconocida: " + accionBoton,
                            "Error del Sistema",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        if (e.getSource() == vista.btnEliminar) {
            delete();
        }

        getJTable();
    }

    private void add() {
        try {
            if (!validarParaCreacion()) {
                return;
            }

            mapearDatosFormularioAModelo();
            boolean exito = consult.add(model);

            if (exito) {
                JOptionPane.showMessageDialog(vista,
                        "Forma de pago creada exitosamente",
                        "Operación Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);

                getJTable();
                limpiarFormulario();

            } else {
                JOptionPane.showMessageDialog(vista,
                        "No fue posible crear la forma de pago.\nVerifique los datos e intente nuevamente.",
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

    private void update() {
        try {
            if (vista.txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                        "Debe seleccionar una forma de pago de la tabla para actualizar",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!validarParaActualizacion()) {
                return;
            }

            mapearDatosFormularioAModelo();
            boolean exito = consult.update(model);

            if (exito) {
                JOptionPane.showMessageDialog(vista,
                        "Forma de pago actualizada exitosamente",
                        "Operación Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);

                getJTable();
                resetearFormularioParaCreacion();

            } else {
                JOptionPane.showMessageDialog(vista,
                        "No fue posible actualizar la forma de pago.\nVerifique los datos e intente nuevamente.",
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
            FormaPagoDAO fpDAO = new FormaPagoDAO();
            int proximoId = fpDAO.sugerirProximoId();
            vista.txtId.setText(String.valueOf(proximoId));
        }
    }

    private void delete() {
        try {
            if (vista.txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                        "Debe seleccionar una forma de pago de la tabla para eliminar",
                        "Selección requerida",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = Integer.parseInt(vista.txtId.getText().trim());
            String descripcion = vista.txtDescrip.getText();

            int confirmacion = JOptionPane.showConfirmDialog(vista,
                    "¿Confirma inactivar la forma de pago?\n\n"
                    + "ID: " + id + "\n"
                    + "Descripción: " + descripcion + "\n\n"
                    + "La forma de pago pasará a estado 'Inactivo'",
                    "Confirmar inactivación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean exito = consult.delete(id);

                if (exito) {
                    JOptionPane.showMessageDialog(vista,
                            "Forma de pago inactivada exitosamente",
                            "Operación exitosa",
                            JOptionPane.INFORMATION_MESSAGE);

                    getJTable();
                    resetearFormularioParaCreacion();
                } else {
                    JOptionPane.showMessageDialog(vista,
                            "No fue posible inactivar la forma de pago",
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
        if (vista.txtId.getText().trim().isEmpty()) {
            mostrarErrorValidacion("El ID es obligatorio");
            vista.txtId.requestFocus();
            return false;
        }

        if (vista.txtDescrip.getText().trim().isEmpty()) {
            mostrarErrorValidacion("La descripción es obligatoria");
            vista.txtDescrip.requestFocus();
            return false;
        }

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

        if (vista.txtDescrip.getText().trim().length() < 3) {
            mostrarErrorValidacion("La descripción debe tener al menos 3 caracteres");
            vista.txtDescrip.requestFocus();
            return false;
        }

        return true;
    }

    private boolean validarParaCreacion() {
        if (!validarDatosFormulario()) {
            return false;
        }

        int id = Integer.parseInt(vista.txtId.getText().trim());
        String descripcion = vista.txtDescrip.getText().trim();

        if (consult.existeId(id)) {
            mostrarErrorValidacion("Ya existe una forma de pago con el ID: " + id
                    + "\nPor favor, use un ID diferente.");
            vista.txtId.requestFocus();
            vista.txtId.selectAll();
            return false;
        }

        if (consult.existeDescripcion(descripcion)) {
            mostrarErrorValidacion("Ya existe una forma de pago con la descripción: " + descripcion
                    + "\nPor favor, use una descripción diferente.");
            vista.txtDescrip.requestFocus();
            vista.txtDescrip.selectAll();
            return false;
        }

        return true;
    }

    private boolean validarParaActualizacion() {
        return validarDatosFormulario();
    }

    private void mapearDatosFormularioAModelo() {
        model.setFpId(Integer.parseInt(vista.txtId.getText().trim()));
        model.setFpDescripcion(vista.txtDescrip.getText().trim());
        model.setFpEstado(vista.cmbEstado.getSelectedItem().toString());
    }

    public void getJTable() {
        try {
            List<FormaPago> formasPago = consult.getAllFormasPago();
            DefaultTableModel modeloTabla = (DefaultTableModel) vista.tableFormaPago.getModel();

            modeloTabla.setRowCount(0);

            for (FormaPago formaPagoItem : formasPago) {
                String estado = formaPagoItem.getFpEstado();

                modeloTabla.addRow(new Object[]{
                    formaPagoItem.getFpId(),
                    formaPagoItem.getFpDescripcion(),
                    estado
                });
            }

            System.out.println("Tabla actualizada con " + formasPago.size() + " registros");

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
        autoCompletarId();
        vista.cmbEstado.setSelectedIndex(1);
    }

    private void limpiarFormulario() {
        vista.txtId.setText("");
        vista.txtDescrip.setText("");
        vista.cmbEstado.setSelectedIndex(1);
    }

    private void resetearFormularioParaCreacion() {
        limpiarFormulario();
        vista.btn.setText("Crear");
        vista.btn.putClientProperty(FlatClientProperties.STYLE, Theme.btnPrinci + ";" + Theme.h2);
        vista.btnEliminar.setVisible(false);
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
