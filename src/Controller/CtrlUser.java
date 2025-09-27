package Controller;

import DAO.UserDAO;
import Model.User;
import View.FrmLogin;
import View.FrmPrincipal;
import View.PantallaInicial;
import Theme.Theme;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class CtrlUser implements ActionListener {

    private final User modelUser;
    private final UserDAO consultUser;
    private final FrmLogin vistaUser;

    public CtrlUser(User modelUser, UserDAO consultUser, FrmLogin vistaUser) {
        this.modelUser = modelUser;
        this.consultUser = consultUser;
        this.vistaUser = vistaUser;
        this.vistaUser.btnLogin.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaUser.btnLogin) {
            String userL = vistaUser.txtUser.getText();
            String passL = vistaUser.txtPass.getText();

            limpiarEstilosCampos();

            if (userL.trim().isEmpty() || passL.trim().isEmpty()) {
                mostrarCamposVacios(userL, passL);
                return;
            }

            procesarLogin(userL, passL);
        }
    }

    private void limpiarEstilosCampos() {
        vistaUser.txtUser.putClientProperty(FlatClientProperties.STYLE, Theme.getNormalField());
        vistaUser.txtPass.putClientProperty(FlatClientProperties.STYLE, Theme.getNormalField());
    }

    private void mostrarCamposVacios(String userL, String passL) {
        if (userL.trim().isEmpty()) {
            vistaUser.txtUser.putClientProperty(FlatClientProperties.STYLE, Theme.getErrorField());
        }
        if (passL.trim().isEmpty()) {
            vistaUser.txtPass.putClientProperty(FlatClientProperties.STYLE, Theme.getErrorField());
        }
        JOptionPane.showMessageDialog(vistaUser, "Por favor complete todos los campos",
                "Campos requeridos", JOptionPane.WARNING_MESSAGE);
    }

    private void procesarLogin(String userL, String passL) {
        vistaUser.btnLogin.setEnabled(false);
        String textoOriginal = vistaUser.btnLogin.getText();
        vistaUser.btnLogin.setText("Verificando...");

        try {
            User usuarioValido = consultUser.validarLogin(userL, passL);

            if (usuarioValido != null) {
                loginExitoso(usuarioValido);
            } else {
                loginFallido();
            }
        } catch (Exception ex) {
            manejarErrorLogin(ex);
        } finally {
            vistaUser.btnLogin.setText(textoOriginal);
            vistaUser.btnLogin.setEnabled(true);
        }
    }

    private void loginExitoso(User usuario) {

        abrirMenuPrincipal();
        cerrarVentanaLogin();
    }

    private void loginFallido() {
        JOptionPane.showMessageDialog(vistaUser, "Usuario o contraseña incorrectos\nIntente de nuevo",
                "Error de autenticación", JOptionPane.ERROR_MESSAGE);

        vistaUser.txtUser.putClientProperty(FlatClientProperties.STYLE, Theme.getErrorField());
        vistaUser.txtPass.putClientProperty(FlatClientProperties.STYLE, Theme.getErrorField());

        vistaUser.txtPass.setText("");
        vistaUser.txtUser.requestFocus();
    }

    private void manejarErrorLogin(Exception ex) {
        System.err.println("Error durante el login: " + ex.getMessage());
        JOptionPane.showMessageDialog(vistaUser, "Error de conexión\nIntente nuevamente",
                "Error del sistema", JOptionPane.ERROR_MESSAGE);
    }

private void abrirMenuPrincipal() {
    SwingUtilities.invokeLater(() -> {
        FrmPrincipal menuPrincipal = new FrmPrincipal();
        PantallaInicial pantallaInicial = new PantallaInicial();
        CtrlPrincipal controladorPrincipal = new CtrlPrincipal(pantallaInicial, menuPrincipal);
        
        // Configurar posición y tamaño
        menuPrincipal.setLocationRelativeTo(null);
        menuPrincipal.setSize(new Dimension(1920, 1080));
        menuPrincipal.setTitle("Sistema de veterinaria - Menu");
        
        // Mostrar y maximizar
        menuPrincipal.setVisible(true);
        
        // Pequeño delay para suavizar la transición
        Timer delayTimer = new Timer(50, e -> {
            menuPrincipal.setExtendedState(Frame.MAXIMIZED_BOTH);
            ((Timer)e.getSource()).stop();
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    });
}
    private void cerrarVentanaLogin() {
        SwingUtilities.invokeLater(() -> {
            // Obtener la ventana padre del componente login
            Window ventanaPadre = SwingUtilities.getWindowAncestor(vistaUser);

            if (ventanaPadre != null) {
                ventanaPadre.dispose();
                System.out.println("Ventana de login cerrada correctamente");
            } else {
                // Si no hay ventana padre, cerrar el panel directamente
                vistaUser.setVisible(false);
                System.out.println("Panel de login ocultado");
            }
        });
    }
}
