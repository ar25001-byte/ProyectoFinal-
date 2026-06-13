package com.tiendapc.modelo;

/**
 * Clase modelo que representa un Producto en el inventario.
 * Encapsula los atributos correspondientes a la tabla 'Productos'
 * de la base de datos TiendaPCDB.
 *
 * Principios aplicados:
 * - Encapsulamiento: atributos privados con getters/setters.
 * - Sobrescritura de toString() para depuración.
 */
public class Producto {

    // ──────────────────────────────────────────────
    // Atributos privados (mapeados a columnas de BD)
    // ──────────────────────────────────────────────

    private int id;            // Clave primaria autoincremental
    private String codigo;     // Código único del producto (ej: "CPU-001")
    private String nombre;     // Nombre descriptivo del producto
    private String categoria;  // Categoría (Procesador, RAM, GPU, etc.)
    private double precio;     // Precio unitario con dos decimales
    private int stock;         // Cantidad disponible en inventario

    // ──────────────────────────────────────────────
    // Constructores
    // ──────────────────────────────────────────────

    /**
     * Constructor vacío.
     * Útil para crear instancias que se poblarán después con setters.
     */
    public Producto() {
    }

    /**
     * Constructor completo (sin id, para inserciones nuevas).
     *
     * @param codigo    Código único del producto
     * @param nombre    Nombre del producto
     * @param categoria Categoría del producto
     * @param precio    Precio unitario
     * @param stock     Cantidad en inventario
     */
    public Producto(String codigo, String nombre, String categoria, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    /**
     * Constructor completo (con id, para representar registros existentes).
     *
     * @param id        Identificador en la base de datos
     * @param codigo    Código único del producto
     * @param nombre    Nombre del producto
     * @param categoria Categoría del producto
     * @param precio    Precio unitario
     * @param stock     Cantidad en inventario
     */
    public Producto(int id, String codigo, String nombre, String categoria, double precio, int stock) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    // ──────────────────────────────────────────────
    // Getters y Setters
    // ──────────────────────────────────────────────

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // ──────────────────────────────────────────────
    // toString para depuración
    // ──────────────────────────────────────────────

    /**
     * Representación legible del producto.
     */
    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}
