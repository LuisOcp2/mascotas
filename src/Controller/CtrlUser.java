package Controller;

import DAO.UserDAO;
import Model.User;
import View.FrmLogin;
import View.FrmPrincipal;
import Theme.Theme;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

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
        if(e.getSource()== vistaUser.btnLogin){
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
        String mensaje = "Bienvenido " + usuario.getUsuNombre() + " " + usuario.getUsuApellido();
        JOptionPane.showMessageDialog(vistaUser, mensaje, "Login exitoso", 
                JOptionPane.INFORMATION_MESSAGE);
        
        abrirMenuPrincipal();
        vistaUser.setVisible(false);
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
        FrmPrincipal menuPrincipal = new FrmPrincipal();
        menuPrincipal.setVisible(true);
        menuPrincipal.setLocationRelativeTo(null);
        menuPrincipal.setSize(new Dimension(1920, 1080));
        menuPrincipal.setExtendedState(Frame.MAXIMIZED_BOTH);
        menuPrincipal.setTitle("Sistema de veterinaria - Menu");
        
    }
}