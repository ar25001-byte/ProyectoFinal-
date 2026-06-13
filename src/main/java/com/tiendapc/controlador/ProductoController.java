package com.tiendapc.controlador;

import com.tiendapc.modelo.Producto;
import com.tiendapc.persistencia.ProductoDAO;

import java.sql.SQLException;
import java.util.List;

/**
 * Controlador para la entidad Producto.
 *
 * Actúa como intermediario entre la Vista (InventarioForm) y la
 * capa de Persistencia (ProductoDAO), siguiendo el patrón MVC.
 *
 * Responsabilidades:
 * - Delegar operaciones CRUD al DAO.
 * - Traducir excepciones SQL en mensajes comprensibles.
 * - Mantener la vista desacoplada de la persistencia.
 */
public class ProductoController {

    // Referencia al DAO (capa de persistencia)
    private final ProductoDAO productoDAO;

    /**
     * Constructor que inicializa el DAO.
     */
    public ProductoController() {
        this.productoDAO = new ProductoDAO();
    }

    // ──────────────────────────────────────────────
    // Operaciones CRUD delegadas al DAO
    // ──────────────────────────────────────────────

    /**
     * Agrega un nuevo producto al inventario.
     *
     * @param producto Producto a agregar.
     * @return true si se agregó correctamente.
     * @throws SQLException Si hay un error de base de datos.
     */
    public boolean agregarProducto(Producto producto) throws SQLException {
        return productoDAO.agregar(producto);
    }

    /**
     * Obtiene la lista completa de productos.
     *
     * @return Lista de todos los productos.
     * @throws SQLException Si hay un error de base de datos.
     */
    public List<Producto> listarProductos() throws SQLException {
        return productoDAO.listarTodos();
    }

    /**
     * Busca productos por código o nombre (búsqueda parcial).
     *
     * @param texto Texto de búsqueda.
     * @return Lista de productos coincidentes.
     * @throws SQLException Si hay un error de base de datos.
     */
    public List<Producto> buscarProductos(String texto) throws SQLException {
        return productoDAO.buscar(texto);
    }

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param producto Producto con los datos actualizados.
     * @return true si se actualizó correctamente.
     * @throws SQLException Si hay un error de base de datos.
     */
    public boolean actualizarProducto(Producto producto) throws SQLException {
        return productoDAO.actualizar(producto);
    }

    /**
     * Elimina un producto por su Id.
     *
     * @param id Identificador del producto a eliminar.
     * @return true si se eliminó correctamente.
     * @throws SQLException Si hay un error de base de datos.
     */
    public boolean eliminarProducto(int id) throws SQLException {
        return productoDAO.eliminar(id);
    }

    /**
     * Busca un producto por su código exacto.
     *
     * @param codigo Código del producto.
     * @return Producto encontrado o null si no existe.
     * @throws SQLException Si hay un error de base de datos.
     */
    public Producto buscarPorCodigo(String codigo) throws SQLException {
        return productoDAO.buscarPorCodigo(codigo);
    }
}
