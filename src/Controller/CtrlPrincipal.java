package Controller;

import DAO.ConceptoDAO;
import DAO.FormaPagoDAO;
import DAO.MySQLConnection;
import DAO.TerceroDAO;
import DAO.UserDAO;
import Model.Concepto;
import Model.FormaPago;
import Model.Tercero;
import Model.User;
import View.FrmConcepto;
import View.FrmFormaPago;
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
        configurarEventosMenu();
    }

    private void configurarEventosMenu() {
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
            String textoBoton = vistaMenu.btnMenu1.getText();
            if (textoBoton.equals("Menu Principal")) {
                mostrarMenuPrincipal();
            } else {
                // Dashboard o cualquier otra acción del menú principal
            }
        }

        if (e.getSource() == vistaMenu.btnMenu2) {
            String textoBoton = vistaMenu.btnMenu2.getText();
            if (textoBoton.equals("Concepto de pago")) {
                cargarModuloConcepto();
            } else {
                // Mascotas o cualquier otra acción del menú principal
            }
        }

        if (e.getSource() == vistaMenu.btnMenu3) {
            String textoBoton = vistaMenu.btnMenu3.getText();
            if (textoBoton.equals("Forma de pago")) {
                cargarModuloFormaPago();
            } else {
                // Ordenes de servicio o cualquier otra acción del menú principal
            }
        }

        if (e.getSource() == vistaMenu.btnMenu4) {

        }

        if (e.getSource() == vistaMenu.btnMenu5) {

        }

        if (e.getSource() == vistaMenu.btnMenu6) {
            mostrarMenuTerceros();
        }
    }

    private void mostrarMenuTerceros() {
        configurarMenuTerceros();
        cargarModuloTerceros();
    }

    private void mostrarMenuPrincipal() {
        restaurarMenuCompleto();
    }

    private void configurarMenuTerceros() {
        vistaMenu.btnMenu1.setText("Menu Principal");
        vistaMenu.btnMenu1.setVisible(true);

        vistaMenu.btnMenu2.setText("Concepto de pago");
        vistaMenu.btnMenu2.setVisible(true);

        vistaMenu.btnMenu3.setText("Forma de pago");
        vistaMenu.btnMenu3.setVisible(true);

        vistaMenu.btnMenu4.setVisible(false);
        vistaMenu.btnMenu5.setVisible(false);
        vistaMenu.btnMenu6.setVisible(false);

        vistaMenu.repaint();
        vistaMenu.revalidate();
    }

    private void restaurarMenuCompleto() {
        vistaMenu.btnMenu1.setText("Dashboard");
        vistaMenu.btnMenu1.setVisible(true);

        vistaMenu.btnMenu2.setText("Mascotas");
        vistaMenu.btnMenu2.setVisible(true);

        vistaMenu.btnMenu3.setText("Ordenes de servicio");
        vistaMenu.btnMenu3.setVisible(true);

        vistaMenu.btnMenu4.setText("Examenes");
        vistaMenu.btnMenu4.setVisible(true);

        vistaMenu.btnMenu5.setText("Resultados");
        vistaMenu.btnMenu5.setVisible(true);

        vistaMenu.btnMenu6.setText("Comprobante de egreso");
        vistaMenu.btnMenu6.setVisible(true);

        limpiarPanelMostrar();

        vistaMenu.repaint();
        vistaMenu.revalidate();
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
        // Configurar animación del contenedor
        vistaMenu.panelMostrar.putClientProperty("FlatLaf.animation", "fade");
        vistaMenu.panelMostrar.putClientProperty("FlatLaf.animationDuration", 250);

        // Configurar el panel que se va a mostrar
        panel.setVisible(true);
        panel.setSize(1655, 992);
        panel.setLocation(0, 0);
        panel.setBackground(new Color(24, 26, 32));

        // Animación de salida del contenido anterior (opcional)
        if (vistaMenu.panelMostrar.getComponentCount() > 0) {
            vistaMenu.panelMostrar.putClientProperty("FlatLaf.animation", "slideLeft");
        }

        // Cambiar contenido
        vistaMenu.panelMostrar.removeAll();
        vistaMenu.panelMostrar.add(panel, BorderLayout.CENTER);

        // Animación de entrada del nuevo panel
        panel.putClientProperty("FlatLaf.animation", "slideUp");
        panel.putClientProperty("FlatLaf.animationDuration", 500);

        // Activar las animaciones
        vistaMenu.panelMostrar.revalidate();
        vistaMenu.panelMostrar.repaint();
    }

    private void cargarModuloConcepto() {
        try {

            Concepto objModel = new Concepto();
            FrmConcepto objView = new FrmConcepto();
            ConceptoDAO objDAO = new ConceptoDAO();
            CtrlConcepto objCtrl = new CtrlConcepto(objModel, objDAO, objView);

            objView.setControlador(objCtrl);
            objCtrl.iniciar();
            mostrarEnPanel(objView);

        } catch (Exception ex) {
            System.err.println("Error al cargar módulo de concepto: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void cargarModuloFormaPago() {
        try {
            // Aquí cargarías tu modelo FormaPago cuando lo tengas
            FormaPago objModel = new FormaPago();
            FrmFormaPago objView = new FrmFormaPago();
            FormaPagoDAO objDAO = new FormaPagoDAO();
            CtrlFormaPago objCtrl = new CtrlFormaPago(objModel, objDAO, objView);

            objView.setControlador(objCtrl);
            objCtrl.iniciar();
            mostrarEnPanel(objView);

        } catch (Exception ex) {
            System.err.println("Error al cargar módulo de forma de pago: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void limpiarPanelMostrar() {
        vistaMenu.panelMostrar.removeAll();
        vistaMenu.panelMostrar.revalidate();
        vistaMenu.panelMostrar.repaint();
    }
}
