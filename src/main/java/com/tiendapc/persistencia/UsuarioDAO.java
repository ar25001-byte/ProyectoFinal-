package com.tiendapc.persistencia;

import com.tiendapc.modelo.Usuario;
import com.tiendapc.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private static final String SQL_VALIDAR = "SELECT Id, Usuario, Password FROM Usuarios WHERE Usuario = ? AND Password = ?";

    public Usuario validarCredenciales(String usuario, String password) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_VALIDAR)) {
             
            ps.setString(1, usuario);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(rs.getInt("Id"), rs.getString("Usuario"), rs.getString("Password"));
                }
            }
        }
        return null;
    }
}
