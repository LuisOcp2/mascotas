/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package themes;

// Create a file named FlatLafConfig.java
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import java.awt.Color;
import java.awt.Font;
import javax.swing.UIManager;

public class FlatLafConfig {

    // Define main colors to match React version
    public static final Color ACCENT_BLUE = new Color(59, 130, 246);  // React blue-600
    public static final Color HOVER_COLOR = new Color(96, 165, 250);  // React blue-500
    public static final Color BACKGROUND = new Color(249, 250, 251);  // React gray-50
    public static final Color FOREGROUND = new Color(17, 24, 39);     // React gray-900

    public static void setup() {
        // Register Roboto font
        FlatRobotoFont.install();

        // Set main font
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));

        // Configure UI properties to match React styling
        UIManager.put("h1.font", new Font(FlatRobotoFont.FAMILY, Font.BOLD, 22));
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("Component.arc", 10);
        UIManager.put("Button.arc", 10);
        UIManager.put("Button.margin", new javax.swing.plaf.InsetsUIResource(4, 6, 4, 6));

        // Set accent color to match React blue
        UIManager.put("App.accent.blue", ACCENT_BLUE);
        UIManager.put("Button.background", ACCENT_BLUE);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.focusedBackground", Color.WHITE);
        UIManager.put("Button.focusedForeground", ACCENT_BLUE);

        // Text components styling
        UIManager.put("TextComponent.arc", 10);
        UIManager.put("TextField.margin", new javax.swing.plaf.InsetsUIResource(4, 6, 4, 6));
        UIManager.put("PasswordField.margin", new javax.swing.plaf.InsetsUIResource(4, 6, 4, 6));
        UIManager.put("ComboBox.padding", new javax.swing.plaf.InsetsUIResource(4, 6, 4, 6));

        // Menu styling
        UIManager.put("Menu.header.font", UIManager.getFont("h2.font"));
        UIManager.put("Menu.label.font", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 11));
        UIManager.put("Menu.scroll.width", 5);
        UIManager.put("Menu.scroll.trackInsets", new javax.swing.plaf.InsetsUIResource(2, 0, 2, 1));
        UIManager.put("Menu.scroll.thumbInsets", UIManager.get("Menu.scroll.trackInsets"));
        UIManager.put("Menu.icon.lightColor", new Color(240, 240, 240));
        UIManager.put("Menu.icon.darkColor", new Color(150, 150, 150));

        // Scrollbar styling for modern look
        UIManager.put("ScrollBar.trackArc", 999);
        UIManager.put("ScrollBar.thumbArc", 999);

        // Form components
        UIManager.put("FormattedTextField.margin", new javax.swing.plaf.InsetsUIResource(5, 5, 5, 5));
        UIManager.put("Popup.dropShadowInsets", new javax.swing.plaf.InsetsUIResource(2, 5, 8, 8));
        UIManager.put("ScrollPane.TextComponent.arc", 10);
        UIManager.put("TextArea.margin", new javax.swing.plaf.InsetsUIResource(3, 1, 3, 1));

        // Style for TabbedPane to match React styling
        UIManager.put("TabbedPane.selectedBackground", ACCENT_BLUE);
        UIManager.put("TabbedPane.selectedForeground", Color.WHITE);
        UIManager.put("TabbedPane.hoverColor", HOVER_COLOR);
        UIManager.put("TabbedPane.underlineColor", ACCENT_BLUE);
        UIManager.put("TabbedPane.tabHeight", 48);
        UIManager.put("TabbedPane.contentAreaColor", Color.WHITE);
        UIManager.put("TabbedPane.showTabSeparators", false);
        UIManager.put("TabbedPane.tabSeparatorsFullHeight", false);
        UIManager.put("TabbedPane.tabInsets", new javax.swing.plaf.InsetsUIResource(12, 20, 12, 20));
        UIManager.put("TabbedPane.contentBorderInsets", new javax.swing.plaf.InsetsUIResource(20, 20, 20, 20));

        // Toast and accent control
        UIManager.put("Toast.background", new Color(233, 236, 239));  // Lighten background
        UIManager.put("AccentControl.show", false);

        // Apply the theme
        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf: " + ex.getMessage());
        }
    }
}
