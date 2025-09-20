/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mascotas;

import Controller.CtrlPrincipal;
import DAO.MySQLConnection;
import Theme.Theme;
import View.FrmPrincipal;
import View.PantallaInicial;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.IntelliJTheme;
import java.awt.Color;

public class Mascotas {

    public static void main(String[] args) {
        /*
         MySQLConnection cn = new MySQLConnection();
        IntelliJTheme.setup(Mascotas.class.getResourceAsStream("/dark.theme.json"));
        PantallaInicial pl = new PantallaInicial();
        pl.setVisible(true);
         */
        MySQLConnection cn = new MySQLConnection();
        IntelliJTheme.setup(Mascotas.class.getResourceAsStream("/dark.theme.json"));
        PantallaInicial objFactView = new PantallaInicial();
        FrmPrincipal objMenuView = new FrmPrincipal();
        CtrlPrincipal objPrinciCtrl = new CtrlPrincipal(objFactView,objMenuView);
        objPrinciCtrl.iniciar();
        objFactView.setVisible(true);

    }
}
