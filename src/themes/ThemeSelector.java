/*
 * Selector de Temas - Permite cambiar temas dinámicamente
 */
package themes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class ThemeSelector extends JPanel {
    
    private final List<Component> componentsToUpdate = new ArrayList<>();
    private JComboBox<ThemeOption> themeComboBox;
    
    // Clase interna para las opciones de tema
    private static class ThemeOption {
        private final String displayName;
        private final ThemeManager.ThemeType themeType;
        private final String externalPath;
        
        public ThemeOption(String displayName, ThemeManager.ThemeType themeType) {
            this(displayName, themeType, null);
        }
        
        public ThemeOption(String displayName, ThemeManager.ThemeType themeType, String externalPath) {
            this.displayName = displayName;
            this.themeType = themeType;
            this.externalPath = externalPath;
        }
        
        @Override
        public String toString() {
            return displayName;
        }
        
        public ThemeManager.ThemeType getThemeType() {
            return themeType;
        }
        
        public String getExternalPath() {
            return externalPath;
        }
    }
    
    public ThemeSelector() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        // Crear opciones de tema
        ThemeOption[] themeOptions = {
            new ThemeOption("Oscuro Personalizado", ThemeManager.ThemeType.DARK_CUSTOM),
            new ThemeOption("Claro Personalizado", ThemeManager.ThemeType.LIGHT_CUSTOM),
            new ThemeOption("Oscuro por Defecto", ThemeManager.ThemeType.DARK_DEFAULT),
            new ThemeOption("Claro por Defecto", ThemeManager.ThemeType.LIGHT_DEFAULT),
            new ThemeOption("One Dark", ThemeManager.ThemeType.INTELLIJ_EXTERNAL, "/one_dark.theme.json"),
            new ThemeOption("Dracula", ThemeManager.ThemeType.INTELLIJ_EXTERNAL, "/dracula.theme.json"),
            new ThemeOption("Nord", ThemeManager.ThemeType.INTELLIJ_EXTERNAL, "/nord.theme.json"),
            new ThemeOption("Material Darker", ThemeManager.ThemeType.INTELLIJ_EXTERNAL, "/material_theme_darker.theme.json")
        };
        
        // Crear componentes
        add(new JLabel("Tema:"));
        
        themeComboBox = new JComboBox<>(themeOptions);
        themeComboBox.setSelectedIndex(0); // Seleccionar tema oscuro personalizado por defecto
        themeComboBox.addActionListener(this::onThemeChanged);
        add(themeComboBox);
        
        JButton refreshButton = new JButton("Actualizar UI");
        refreshButton.addActionListener(e -> ThemeManager.updateAllComponents());
        add(refreshButton);
    }
    
    private void onThemeChanged(ActionEvent e) {
        ThemeOption selectedTheme = (ThemeOption) themeComboBox.getSelectedItem();
        if (selectedTheme != null) {
            boolean success = ThemeManager.setupTheme(
                selectedTheme.getThemeType(), 
                selectedTheme.getExternalPath()
            );
            
            if (success) {
                // Actualizar todos los componentes registrados
                SwingUtilities.invokeLater(() -> {
                    ThemeManager.updateAllComponents();
                    updateRegisteredComponents();
                });
                
                JOptionPane.showMessageDialog(this, 
                    "Tema cambiado a: " + selectedTheme.toString(), 
                    "Tema Actualizado", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al cambiar al tema: " + selectedTheme.toString(), 
                    "Error de Tema", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Registra un componente para ser actualizado cuando cambie el tema
     */
    public void registerComponent(Component component) {
        componentsToUpdate.add(component);
    }
    
    /**
     * Actualiza todos los componentes registrados
     */
    private void updateRegisteredComponents() {
        for (Component component : componentsToUpdate) {
            SwingUtilities.updateComponentTreeUI(component);
        }
    }
    
    /**
     * Obtiene el tema actualmente seleccionado
     */
    public ThemeOption getSelectedTheme() {
        return (ThemeOption) themeComboBox.getSelectedItem();
    }
    
    /**
     * Establece el tema seleccionado por índice
     */
    public void setSelectedTheme(int index) {
        if (index >= 0 && index < themeComboBox.getItemCount()) {
            themeComboBox.setSelectedIndex(index);
        }
    }
    
    /**
     * Método estático para crear un diálogo de selección de tema
     */
    public static void showThemeDialog(Component parent) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Configuración de Temas");
        dialog.setModal(true);
        dialog.setSize(400, 150);
        dialog.setLocationRelativeTo(parent);
        
        ThemeSelector selector = new ThemeSelector();
        dialog.add(selector, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);
        
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}