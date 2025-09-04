/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mascotas;

import View.Login;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.IntelliJTheme;
import com.sun.tools.javac.Main;
import java.io.InputStream;

public class Mascotas {

    public static void main(String[] args) {
        
                IntelliJTheme.setup(Main.class.getResourceAsStream("/skyblue.theme.json"));
        Login inicio = new Login();
        inicio.setVisible(true);
        inicio.setLocationRelativeTo(null);
    }

}
