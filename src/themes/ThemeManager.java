/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package themes;


import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.util.UIScale;
import java.awt.Font;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ThemeManager {
    
    private static final Logger logger = Logger.getLogger(ThemeManager.class.getName());
    
    // Enum para los tipos de tema disponibles
    public enum ThemeType {
        DARK_CUSTOM("FlatDarkLaf.properties"),
        LIGHT_CUSTOM("FlatLightLaf.properties"),
        DARK_DEFAULT(""),
        LIGHT_DEFAULT(""),
        INTELLIJ_EXTERNAL("");
        
        private final String propertiesFile;
        
        ThemeType(String propertiesFile) {
            this.propertiesFile = propertiesFile;
        }
        
        public String getPropertiesFile() {
            return propertiesFile;
        }
    }
    
    /**
     * Configura el tema principal de la aplicación
     * @param themeType Tipo de tema a aplicar
     * @param externalThemePath Ruta del tema externo (solo para INTELLIJ_EXTERNAL)
     * @return true si se aplicó correctamente
     */
    public static boolean setupTheme(ThemeType themeType, String externalThemePath) {
        try {
            switch (themeType) {
                case DARK_CUSTOM:
                    return setupCustomTheme(themeType);
                    
                case LIGHT_CUSTOM:
                    return setupCustomTheme(themeType);
                    
                case DARK_DEFAULT:
                    FlatDarkLaf.setup();
                    logger.info("Tema oscuro por defecto aplicado");
                    return true;
                    
                case LIGHT_DEFAULT:
                    FlatLightLaf.setup();
                    logger.info("Tema claro por defecto aplicado");
                    return true;
                    
                case INTELLIJ_EXTERNAL:
                    return setupExternalIntelliJTheme(externalThemePath);
                    
                default:
                    return setupFallbackTheme();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al configurar el tema", ex);
            return setupFallbackTheme();
        }
    }
    
    /**
     * Configura un tema personalizado usando archivos .properties
     */
    private static boolean setupCustomTheme(ThemeType themeType) {
        try {
            // Primero configura el tema base
            if (themeType == ThemeType.DARK_CUSTOM) {
                FlatDarkLaf.setup();
            } else {
                FlatLightLaf.setup();
            }
            
            // Luego carga las propiedades personalizadas
            loadCustomProperties(themeType.getPropertiesFile());
            
            // Aplica configuraciones adicionales
            applyCommonConfiguration();
            applyFlatLafConfiguration();
            
            logger.info("Tema personalizado " + themeType.name() + " aplicado exitosamente");
            return true;
            
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Error al cargar tema personalizado: " + themeType.name(), ex);
            return false;
        }
    }
    
    /**
     * Carga propiedades personalizadas desde un archivo .properties
     */
    private static void loadCustomProperties(String propertiesFile) {
        try (InputStream stream = ThemeManager.class.getResourceAsStream("/" + propertiesFile)) {
            if (stream != null) {
                Properties props = new Properties();
                props.load(stream);
                
                // Aplicar cada propiedad al UIManager
                for (String key : props.stringPropertyNames()) {
                    String value = props.getProperty(key);
                    
                    // Procesar diferentes tipos de valores
                    Object processedValue = processPropertyValue(key, value);
                    UIManager.put(key, processedValue);
                }
                
                logger.info("Propiedades personalizadas cargadas desde: " + propertiesFile);
            } else {
                logger.warning("No se encontró el archivo de propiedades: " + propertiesFile);
            }
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Error al cargar propiedades desde " + propertiesFile, ex);
        }
    }
    
    /**
     * Procesa los valores de las propiedades para convertirlos al tipo correcto
     */
    private static Object processPropertyValue(String key, String value) {
        // Si es un color RGB
        if (value.startsWith("rgb(") && value.endsWith(")")) {
            try {
                String[] rgb = value.substring(4, value.length() - 1).split(",");
                int r = Integer.parseInt(rgb[0].trim());
                int g = Integer.parseInt(rgb[1].trim());
                int b = Integer.parseInt(rgb[2].trim());
                return new java.awt.Color(r, g, b);
            } catch (Exception ex) {
                logger.warning("Error al procesar color RGB: " + value);
            }
        }
        
        // Si es un color hexadecimal
        if (value.startsWith("#") && value.length() == 7) {
            try {
                return java.awt.Color.decode(value);
            } catch (Exception ex) {
                logger.warning("Error al procesar color hex: " + value);
            }
        }
        
        // Si es una fuente
        if (key.toLowerCase().contains("font")) {
            return processFont(value);
        }
        
        // Si es un Insets (margin, padding)
        if (key.toLowerCase().contains("margin") || key.toLowerCase().contains("padding") || 
            key.toLowerCase().contains("insets")) {
            return processInsets(value);
        }
        
        // Si es un número
        try {
            if (value.matches("^\\d+$")) {
                return Integer.parseInt(value);
            }
        } catch (NumberFormatException ex) {
            // Ignorar, no es un número
        }
        
        // Devolver como string por defecto
        return value;
    }
    
    /**
     * Procesa valores de fuente
     */
    private static Font processFont(String value) {
        try {
            if (value.startsWith("bold ")) {
                String sizeStr = value.substring(5).replace("+", "");
                int size = Integer.parseInt(sizeStr);
                Font defaultFont = UIManager.getFont("Label.font");
                return new Font(defaultFont.getName(), Font.BOLD, defaultFont.getSize() + size);
            }
            // Agregar más procesamiento de fuentes según sea necesario
        } catch (Exception ex) {
            logger.warning("Error al procesar fuente: " + value);
        }
        return null;
    }
    
    /**
     * Procesa valores de Insets
     */
    private static javax.swing.plaf.InsetsUIResource processInsets(String value) {
        try {
            String[] parts = value.split(",");
            if (parts.length == 4) {
                int top = Integer.parseInt(parts[0].trim());
                int left = Integer.parseInt(parts[1].trim());
                int bottom = Integer.parseInt(parts[2].trim());
                int right = Integer.parseInt(parts[3].trim());
                return new javax.swing.plaf.InsetsUIResource(top, left, bottom, right);
            }
        } catch (Exception ex) {
            logger.warning("Error al procesar insets: " + value);
        }
        return null;
    }
    
    /**
     * Configura un tema externo de IntelliJ
     */
    private static boolean setupExternalIntelliJTheme(String themePath) {
        try {
            InputStream themeStream = ThemeManager.class.getResourceAsStream(themePath);
            if (themeStream != null) {
                IntelliJTheme.setup(themeStream);
                applyCommonConfiguration();
                logger.info("Tema externo de IntelliJ aplicado: " + themePath);
                return true;
            } else {
                logger.warning("No se encontró el tema externo: " + themePath);
                return false;
            }
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Error al cargar tema externo de IntelliJ: " + themePath, ex);
            return false;
        }
    }
    
    /**
     * Aplica configuraciones comunes
     */
    private static void applyCommonConfiguration() {
        // Configuraciones comunes que se aplican a todos los temas
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("ScrollBar.trackArc", 999);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("Component.arc", 10);
    }
    
    /**
     * Carga configuraciones específicas de FlatLaf.properties
     */
    private static void applyFlatLafConfiguration() {
        try (InputStream stream = ThemeManager.class.getResourceAsStream("/FlatLaf.properties")) {
            if (stream != null) {
                Properties props = new Properties();
                props.load(stream);
                
                for (String key : props.stringPropertyNames()) {
                    String value = props.getProperty(key);
                    Object processedValue = processPropertyValue(key, value);
                    UIManager.put(key, processedValue);
                }
                
                logger.info("Configuración FlatLaf.properties aplicada");
            }
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Error al cargar FlatLaf.properties", ex);
        }
    }
    
    /**
     * Configura un tema de respaldo en caso de error
     */
    private static boolean setupFallbackTheme() {
       return false;
    }
    
    /**
     * Actualiza todos los componentes después de cambiar el tema
     */
    public static void updateAllComponents() {
        try {
            FlatLaf.updateUI();
            logger.info("Interfaz actualizada después del cambio de tema");
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Error al actualizar la interfaz", ex);
        }
    }
    
    /**
     * Obtiene el tema actual
     */
    public static String getCurrentTheme() {
        return UIManager.getLookAndFeel().getName();
    }
}
