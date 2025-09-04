/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mascotas;

import View.Login;
import java.util.logging.Logger;
import java.util.logging.Level;
import themes.ThemeManager;

public class Mascotas {
    
    private static final Logger logger = Logger.getLogger(Mascotas.class.getName());

    public static void main(String[] args) {
        
        // Configurar el tema usando ThemeManager
        boolean themeLoaded = false;
        
        // Opción 1: Usar tema personalizado oscuro con tus archivos .properties
        themeLoaded = ThemeManager.setupTheme(ThemeManager.ThemeType.DARK_CUSTOM, null);
        
        // Opción 2: Usar tema personalizado claro
        // themeLoaded = ThemeManager.setupTheme(ThemeManager.ThemeType.LIGHT_CUSTOM, null);
        
        // Opción 3: Usar tema por defecto oscuro
        // themeLoaded = ThemeManager.setupTheme(ThemeManager.ThemeType.DARK_DEFAULT, null);
        
        // Opción 4: Usar tema por defecto claro
        // themeLoaded = ThemeManager.setupTheme(ThemeManager.ThemeType.LIGHT_DEFAULT, null);
        
        // Opción 5: Usar tema externo de IntelliJ (como dracula, one_dark, etc.)
        // themeLoaded = ThemeManager.setupTheme(ThemeManager.ThemeType.INTELLIJ_EXTERNAL, "/one_dark.theme.json");
        
        if (themeLoaded) {
            logger.info("Tema configurado exitosamente: " + ThemeManager.getCurrentTheme());
        } else {
            logger.warning("No se pudo cargar el tema preferido, usando tema de respaldo");
        }
        
        // Crear y mostrar la ventana de login
        java.awt.EventQueue.invokeLater(() -> {
            try {
                Login inicio = new Login();
                inicio.setVisible(true);
                inicio.setLocationRelativeTo(null);
                
                logger.info("Aplicación iniciada correctamente");
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Error al iniciar la aplicación", ex);
            }
        });
    }
}