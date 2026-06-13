package com.tiendapc.persistencia;

import com.tiendapc.modelo.Producto;
import com.tiendapc.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para la entidad Producto.
 *
 * Encapsula todas las operaciones CRUD contra la tabla 'Productos'
 * de la base de datos TiendaPCDB.
 *
 * Características:
 * - Uso exclusivo de PreparedStatement para prevenir SQL Injection.
 * - Manejo correcto de recursos con try-with-resources.
 * - Métodos independientes para cada operación.
 */
public class ProductoDAO {

    // ──────────────────────────────────────────────
    // Constantes SQL
    // ──────────────────────────────────────────────

    /** Insertar un nuevo producto */
    private static final String SQL_INSERT =
            "INSERT INTO Productos (Codigo, Nombre, Categoria, Precio, Stock) VALUES (?, ?, ?, ?, ?)";

    /** Obtener todos los productos ordenados por Id */
    private static final String SQL_SELECT_ALL =
            "SELECT Id, Codigo, Nombre, Categoria, Precio, Stock FROM Productos ORDER BY Id";

    /** Buscar productos por código o nombre (búsqueda parcial) */
    private static final String SQL_SEARCH =
            "SELECT Id, Codigo, Nombre, Categoria, Precio, Stock FROM Productos " +
            "WHERE Codigo LIKE ? OR Nombre LIKE ? ORDER BY Id";

    /** Actualizar un producto existente por su Id */
    private static final String SQL_UPDATE =
            "UPDATE Productos SET Codigo = ?, Nombre = ?, Categoria = ?, Precio = ?, Stock = ? WHERE Id = ?";

    /** Eliminar un producto por su Id */
    private static final String SQL_DELETE =
            "DELETE FROM Productos WHERE Id = ?";

    /** Buscar un producto por su código exacto */
    private static final String SQL_FIND_BY_CODE =
            "SELECT Id, Codigo, Nombre, Categoria, Precio, Stock FROM Productos WHERE Codigo = ?";

    // ──────────────────────────────────────────────
    // Método: Agregar Producto
    // ──────────────────────────────────────────────

    /**
     * Inserta un nuevo producto en la base de datos.
     *
     * @param producto Objeto Producto con los datos a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public boolean agregar(Producto producto) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {

            // Asignar los parámetros al PreparedStatement
            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getCategoria());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getStock());

            // Ejecutar la inserción y verificar filas afectadas
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    // ──────────────────────────────────────────────
    // Método: Listar Todos los Productos
    // ──────────────────────────────────────────────

    /**
     * Obtiene todos los productos de la base de datos.
     *
     * @return Lista de objetos Producto.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Producto> listarTodos() throws SQLException {
        List<Producto> productos = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            // Recorrer el ResultSet y mapear cada fila a un objeto Producto
            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        }

        return productos;
    }

    // ──────────────────────────────────────────────
    // Método: Buscar Productos
    // ──────────────────────────────────────────────

    /**
     * Busca productos cuyo código o nombre contenga el texto proporcionado.
     * Realiza una búsqueda parcial (LIKE %texto%).
     *
     * @param texto Texto a buscar en código o nombre.
     * @return Lista de productos que coinciden con la búsqueda.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Producto> buscar(String texto) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String patron = "%" + texto + "%"; // Patrón de búsqueda parcial

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SEARCH)) {

            ps.setString(1, patron); // Buscar en Codigo
            ps.setString(2, patron); // Buscar en Nombre

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productos.add(mapearProducto(rs));
                }
            }
        }

        return productos;
    }

    // ──────────────────────────────────────────────
    // Método: Actualizar Producto
    // ──────────────────────────────────────────────

    /**
     * Actualiza los datos de un producto existente en la base de datos.
     *
     * @param producto Objeto Producto con los datos actualizados (debe incluir Id).
     * @return true si la actualización fue exitosa, false en caso contrario.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public boolean actualizar(Producto producto) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getCategoria());
            ps.setDouble(4, producto.getPrecio());
            ps.setInt(5, producto.getStock());
            ps.setInt(6, producto.getId()); // WHERE Id = ?

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    // ──────────────────────────────────────────────
    // Método: Eliminar Producto
    // ──────────────────────────────────────────────

    /**
     * Elimina un producto de la base de datos por su Id.
     *
     * @param id Identificador del producto a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public boolean eliminar(int id) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    // ──────────────────────────────────────────────
    // Método: Buscar por Código Exacto
    // ──────────────────────────────────────────────

    /**
     * Busca un producto por su código exacto.
     *
     * @param codigo Código del producto.
     * @return Objeto Producto si se encuentra, null si no existe.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public Producto buscarPorCodigo(String codigo) throws SQLException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_CODE)) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
            }
        }
        return null; // No encontrado
    }

    // ──────────────────────────────────────────────
    // Método privado: Mapear ResultSet a Producto
    // ──────────────────────────────────────────────

    /**
     * Convierte una fila del ResultSet en un objeto Producto.
     * Método auxiliar para evitar duplicación de código.
     *
     * @param rs ResultSet posicionado en la fila actual.
     * @return Objeto Producto con los datos de la fila.
     * @throws SQLException Si ocurre un error al leer el ResultSet.
     */
    private Producto mapearProducto(ResultSet rs) throws SQLException {
        return new Producto(
                rs.getInt("Id"),
                rs.getString("Codigo"),
                rs.getString("Nombre"),
                rs.getString("Categoria"),
                rs.getDouble("Precio"),
                rs.getInt("Stock")
        );
    }
}
