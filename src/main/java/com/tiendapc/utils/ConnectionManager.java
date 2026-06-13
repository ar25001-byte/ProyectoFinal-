package com.tiendapc.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria para gestionar la conexión JDBC con SQL Server.
 */
public class ConnectionManager {

    private static final String URL = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=TiendaPCDB;encrypt=true;trustServerCertificate=true";
    private static final String USER = "Ascencio";
    private static final String PASSWORD = "12345";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("[INFO] Driver JDBC de SQL Server cargado correctamente.");
        } catch (ClassNotFoundException e) {
            System.err.println("[ERROR FATAL] No se encontró el driver JDBC de SQL Server.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("[ERROR] No se pudo cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public static boolean testConnection() throws SQLException {
        try (Connection conn = getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("[INFO] Conexión exitosa a SQL Server.");
            System.out.println("  -> Servidor: " + meta.getDatabaseProductName());
            System.out.println("  -> Versión: " + meta.getDatabaseProductVersion());
            return true;
        }
    }
}

