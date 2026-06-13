package com.tiendapc;

import com.tiendapc.utils.ConnectionManager;
import com.tiendapc.vista.LoginForm;

import javax.swing.*;
import java.sql.SQLException;

/**
 * Clase principal de la aplicación.
 */
public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("[AVISO] No se pudo establecer el Look and Feel del sistema.");
        }

        UIManager.put("OptionPane.background", new java.awt.Color(40, 42, 58));
        UIManager.put("Panel.background", new java.awt.Color(40, 42, 58));
        UIManager.put("OptionPane.messageForeground", new java.awt.Color(205, 214, 244));

        SwingUtilities.invokeLater(() -> {
            try {
                // Prueba de conexión al iniciar
                ConnectionManager.testConnection();
                System.out.println("Conexión exitosa. Iniciando la aplicación...");

                // Si es exitosa, abrir login
                LoginForm login = new LoginForm();
                login.setVisible(true);

            } catch (SQLException ex) {
                // Mostrar el error exacto mediante JOptionPane si falla
                JOptionPane.showMessageDialog(null, 
                    "Error al conectar a SQL Server:\n\n" + ex.getMessage(), 
                    "Error de Conexión", 
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, 
                    "Error inesperado:\n\n" + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}
