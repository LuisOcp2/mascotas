package Controller;

import DAO.MySQLConnection;
import DAO.TerceroDAO;
import DAO.UserDAO;
import Model.Tercero;
import Model.User;
import View.FrmLogin;
import View.FrmPrincipal;
import View.FrmTerceros;
import View.PantallaInicial;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.IntelliJTheme;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mascotas.Mascotas;

public class CtrlPrincipal implements ActionListener {
    
    private final PantallaInicial vista;
    private final FrmPrincipal vistaMenu;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PantallaInicial.class.getName());
    private static FrmLogin loginForm;
    private final boolean UNDECORATED = !true;
    
    public CtrlPrincipal(PantallaInicial vista, FrmPrincipal vistaMenu) {
        this.vista = vista;
        this.vistaMenu = vistaMenu;
        this.vistaMenu.btnMenu1.addActionListener(this);
        this.vistaMenu.btnMenu2.addActionListener(this);
        this.vistaMenu.btnMenu3.addActionListener(this);
        this.vistaMenu.btnMenu4.addActionListener(this);
        this.vistaMenu.btnMenu5.addActionListener(this);
        this.vistaMenu.btnMenu6.addActionListener(this);
    }
    
    public void iniciar() {
        MySQLConnection cn = new MySQLConnection();
        IntelliJTheme.setup(Mascotas.class.getResourceAsStream("/dark.theme.json"));
        vista.setSize(new Dimension(1920, 1080));
        vista.setExtendedState(Frame.MAXIMIZED_BOTH);
        vista.setLocationRelativeTo(null);
        
        if (UNDECORATED) {
            vista.setUndecorated(UNDECORATED);
            vista.setBackground(new Color(0, 0, 0, 0));
        } else {
            vista.getRootPane().putClientProperty(FlatClientProperties.FULL_WINDOW_CONTENT, true);
        }
        
        loginForm = new FrmLogin();
        User objUserModel = new User();
        UserDAO objUserDAO = new UserDAO();
        CtrlUser objFactCtrl = new CtrlUser(objUserModel, objUserDAO, loginForm);
        vista.setContentPane(loginForm);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == vistaMenu.btnMenu1) {
            cargarModuloTerceros();
        }
        
        if (e.getSource() == vistaMenu.btnMenu2) {
            
        }
        
        if (e.getSource() == vistaMenu.btnMenu3) {
            
        }
        
        if (e.getSource() == vistaMenu.btnMenu4) {
            
        }
        
        if (e.getSource() == vistaMenu.btnMenu5) {
            
        }
        
        if (e.getSource() == vistaMenu.btnMenu6) {
            cargarModuloTerceros();
        }
    }
    
    private void cargarModuloTerceros() {
        try {
            Tercero objModel = new Tercero();
            FrmTerceros objView = new FrmTerceros();
            TerceroDAO objDAO = new TerceroDAO();
            CtrlTercero objCtrl = new CtrlTercero(objModel, objDAO, objView);
            
            objView.setControlador(objCtrl);
            objCtrl.iniciar();
            
            mostrarEnPanel(objView);
            
        } catch (Exception ex) {
            System.err.println("Error al cargar módulo de terceros: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void mostrarEnPanel(javax.swing.JPanel panel) {
        panel.setVisible(true);
        panel.setSize(1470, 885);
        panel.setLocation(0, 0);
        panel.setBackground(new Color(24, 26, 32));
        
        vistaMenu.panelMostrar.removeAll();
        vistaMenu.panelMostrar.add(panel, BorderLayout.CENTER);
        vistaMenu.panelMostrar.revalidate();
        vistaMenu.panelMostrar.repaint();
    }
}