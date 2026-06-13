package com.tiendapc.vista;

import com.tiendapc.controlador.ProductoController;
import com.tiendapc.modelo.Producto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class InventarioForm extends JFrame {

    // ──────────────────────────────────────────────
    // Componentes de la interfaz
    // ──────────────────────────────────────────────

    private JTextField txtId, txtCodigo, txtNombre, txtPrecio, txtStock, txtBuscar;
    private JComboBox<String> cmbCategoria;
    private JButton btnBuscar, btnMostrarTodos;
    private JButton btnAgregar, btnEditar, btnEliminar, btnLimpiar;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JLabel lblReloj, lblTotalProductos, lblBajoStock, lblCategorias;

    private final ProductoController controller;

    // Paleta de colores profesional
    private static final Color COLOR_FONDO = new Color(30, 30, 46);
    private static final Color COLOR_PANEL = new Color(40, 42, 58);
    private static final Color COLOR_CAMPO = new Color(55, 58, 78);
    private static final Color COLOR_TEXTO = new Color(205, 214, 244);
    private static final Color COLOR_TEXTO_SEC = new Color(166, 173, 200);
    private static final Color COLOR_ACENTO = new Color(137, 180, 250);
    private static final Color COLOR_VERDE = new Color(166, 227, 161);
    private static final Color COLOR_ROJO = new Color(243, 139, 168);
    private static final Color COLOR_AMARILLO = new Color(249, 226, 175);
    private static final Color COLOR_TABLA_HEADER = new Color(49, 50, 68);
    private static final Color COLOR_TABLA_ALT = new Color(45, 47, 63);
    private static final Color COLOR_SELECCION = new Color(88, 91, 112);
    private static final Color COLOR_SIDEBAR = new Color(24, 24, 37);

    public InventarioForm() {
        this.controller = new ProductoController();
        inicializarUI();
        iniciarReloj();
        cargarProductos();
    }

    private void inicializarUI() {
        setTitle("🖥️ TiendaPC Dashboard v2.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 800);
        setMinimumSize(new Dimension(1100, 700));
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
        setLayout(new BorderLayout());

        // Header Superior
        add(crearHeader(), BorderLayout.NORTH);

        // Sidebar Izquierdo
        add(crearSidebar(), BorderLayout.WEST);

        // Panel Principal Central
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setBackground(COLOR_FONDO);
        panelCentral.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Top: Dashboard Cards
        panelCentral.add(crearTarjetasDashboard(), BorderLayout.NORTH);

        // Center: Formulario y Tabla divididos
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(crearPanelTabla());
        splitPane.setRightComponent(crearPanelFormulario());
        splitPane.setDividerLocation(750);
        splitPane.setDividerSize(6);
        splitPane.setBorder(null);
        splitPane.setBackground(COLOR_FONDO);

        panelCentral.add(splitPane, BorderLayout.CENTER);

        // Footer Inferior
        panelCentral.add(crearFooter(), BorderLayout.SOUTH);

        add(panelCentral, BorderLayout.CENTER);

        configurarEventos();
    }

    // ──────────────────────────────────────────────
    // Constructores de Paneles UI
    // ──────────────────────────────────────────────

    private JPanel crearHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(60, 63, 85)),
                new EmptyBorder(15, 25, 15, 25)));

        JLabel lblTitulo = new JLabel("🏪 Panel Administrativo TiendaPC");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_TEXTO);
        panel.add(lblTitulo, BorderLayout.WEST);

        lblReloj = new JLabel("Cargando fecha...");
        lblReloj.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblReloj.setForeground(COLOR_ACENTO);
        panel.add(lblReloj, BorderLayout.EAST);

        return panel;
    }

    private JPanel crearSidebar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_SIDEBAR);
        panel.setPreferredSize(new Dimension(220, 0));
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(60, 63, 85)));

        JLabel lblMenu = new JLabel("MENÚ PRINCIPAL");
        lblMenu.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMenu.setForeground(COLOR_TEXTO_SEC);
        lblMenu.setBorder(new EmptyBorder(20, 20, 10, 20));

        panel.add(lblMenu);
        panel.add(crearBotonNavegacion("🏠  Inicio", true));
        panel.add(Box.createVerticalGlue());

        JButton btnSalir = crearBotonNavegacion("🚪  Cerrar Sesión", false);
        btnSalir.setForeground(COLOR_ROJO);
        btnSalir.addActionListener(e -> {
            new LoginForm().setVisible(true);
            this.dispose();
        });
        panel.add(btnSalir);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        return panel;
    }

    private JButton crearBotonNavegacion(String texto, boolean activo) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(activo ? COLOR_ACENTO : COLOR_TEXTO);
        btn.setBackground(activo ? COLOR_PANEL : COLOR_SIDEBAR);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(0, 20, 0, 0));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                if (!activo)
                    btn.setBackground(COLOR_PANEL);
            }

            public void mouseExited(MouseEvent evt) {
                if (!activo)
                    btn.setBackground(COLOR_SIDEBAR);
            }
        });
        return btn;
    }

    private JPanel crearTarjetasDashboard() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 15, 0));
        panel.setBackground(COLOR_FONDO);

        lblTotalProductos = new JLabel("0");
        lblBajoStock = new JLabel("0");
        lblCategorias = new JLabel("0");

        panel.add(crearTarjeta("Total Productos", "📦", lblTotalProductos, COLOR_ACENTO));
        panel.add(crearTarjeta("Poco Stock (<5)", "⚠️", lblBajoStock, COLOR_AMARILLO));
        panel.add(crearTarjeta("Categorías Activas", "🏷️", lblCategorias, COLOR_VERDE));

        return panel;
    }

    private JPanel crearTarjeta(String titulo, String icono, JLabel lblValor, Color colorAcento) {
        JPanel tarjeta = new JPanel(new BorderLayout(10, 10));
        tarjeta.setBackground(COLOR_PANEL);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(4, 0, 0, 0, colorAcento),
                new EmptyBorder(15, 20, 15, 20)));

        JLabel lblTitle = new JLabel(titulo);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(COLOR_TEXTO_SEC);

        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblValor.setForeground(COLOR_TEXTO);

        JLabel lblIcon = new JLabel(icono);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));

        tarjeta.add(lblTitle, BorderLayout.NORTH);
        tarjeta.add(lblValor, BorderLayout.CENTER);
        tarjeta.add(lblIcon, BorderLayout.EAST);

        return tarjeta;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(COLOR_FONDO);

        // Buscador superior
        JPanel panelBusqueda = new JPanel(new BorderLayout(10, 0));
        panelBusqueda.setBackground(COLOR_PANEL);
        panelBusqueda.setBorder(new EmptyBorder(10, 15, 10, 15));

        txtBuscar = crearCampoTexto(20);
        txtBuscar.setText(" Buscar por código o nombre...");
        txtBuscar.setForeground(COLOR_TEXTO_SEC);

        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtBuscar.getText().trim().equals("Buscar por código o nombre...")) {
                    txtBuscar.setText("");
                    txtBuscar.setForeground(COLOR_TEXTO);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtBuscar.getText().trim().isEmpty()) {
                    txtBuscar.setText(" Buscar por código o nombre...");
                    txtBuscar.setForeground(COLOR_TEXTO_SEC);
                }
            }
        });

        panelBusqueda.add(txtBuscar, BorderLayout.CENTER);

        JPanel panelBtnsBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBtnsBusqueda.setBackground(COLOR_PANEL);
        btnBuscar = crearBotonAccion("🔍 Buscar", COLOR_ACENTO);
        btnMostrarTodos = crearBotonAccion("📋 Todos", COLOR_TEXTO_SEC);
        panelBtnsBusqueda.add(btnBuscar);
        panelBtnsBusqueda.add(btnMostrarTodos);
        panelBusqueda.add(panelBtnsBusqueda, BorderLayout.EAST);

        panel.add(panelBusqueda, BorderLayout.NORTH);

        // Tabla
        String[] columnas = { "ID", "Código", "Nombre", "Categoría", "Precio", "Stock" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaProductos = new JTable(modeloTabla);
        personalizarTabla();

        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.getViewport().setBackground(COLOR_PANEL);
        scrollPane.setBorder(new LineBorder(new Color(60, 63, 85), 1));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelFormulario() {
        JPanel panelWrapper = new JPanel(new BorderLayout(0, 15));
        panelWrapper.setBackground(COLOR_FONDO);
        panelWrapper.setBorder(new EmptyBorder(0, 10, 0, 0));

        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBackground(COLOR_PANEL);
        panelCampos.setBorder(crearBordeConTitulo("Detalles del Producto"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        txtId = crearCampoTexto(10);
        txtId.setEditable(false);
        txtId.setBackground(new Color(35, 37, 50));
        txtCodigo = crearCampoTexto(15);
        txtNombre = crearCampoTexto(30);
        cmbCategoria = new JComboBox<>(new String[] {
                "Seleccionar...", "Procesador", "Memoria RAM", "Tarjeta Gráfica",
                "Almacenamiento", "Tarjeta Madre", "Fuente de Poder", "Gabinete", "Monitor", "Periférico", "Otro"
        });
        cmbCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbCategoria.setBackground(COLOR_CAMPO);
        cmbCategoria.setForeground(COLOR_TEXTO);
        txtPrecio = crearCampoTexto(10);
        txtStock = crearCampoTexto(10);

        agregarCampoFormulario(panelCampos, gbc, 0, "ID (Auto):", txtId);
        agregarCampoFormulario(panelCampos, gbc, 1, "Código:", txtCodigo);
        agregarCampoFormulario(panelCampos, gbc, 2, "Nombre:", txtNombre);
        agregarCampoFormulario(panelCampos, gbc, 3, "Categoría:", cmbCategoria);
        agregarCampoFormulario(panelCampos, gbc, 4, "Precio ($):", txtPrecio);
        agregarCampoFormulario(panelCampos, gbc, 5, "Stock:", txtStock);

        panelWrapper.add(panelCampos, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 10, 10));
        panelBotones.setBackground(COLOR_FONDO);

        btnAgregar = crearBotonAccion("➕ Agregar", COLOR_VERDE);
        btnEditar = crearBotonAccion("✏️ Guardar", COLOR_AMARILLO);
        btnEliminar = crearBotonAccion("🗑️ Eliminar", COLOR_ROJO);
        btnLimpiar = crearBotonAccion("🧹 Limpiar", COLOR_ACENTO);

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        panelWrapper.add(panelBotones, BorderLayout.SOUTH);

        return panelWrapper;
    }

    private JPanel crearFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(COLOR_FONDO);
        JLabel lbl = new JLabel("TiendaPC Sistema de Inventario v2.0 © 2026");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(COLOR_TEXTO_SEC);
        footer.add(lbl);
        return footer;
    }

    // ──────────────────────────────────────────────
    // Personalización UI (Tabla, Inputs, etc.)
    // ──────────────────────────────────────────────

    private void personalizarTabla() {
        tablaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaProductos.setRowHeight(35);
        tablaProductos.setBackground(COLOR_PANEL);
        tablaProductos.setForeground(COLOR_TEXTO);
        tablaProductos.setSelectionBackground(COLOR_SELECCION);
        tablaProductos.setSelectionForeground(COLOR_TEXTO);
        tablaProductos.setGridColor(new Color(55, 58, 78));
        tablaProductos.setShowHorizontalLines(true);
        tablaProductos.setShowVerticalLines(false);

        JTableHeader header = tablaProductos.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(COLOR_TABLA_HEADER);
        header.setForeground(COLOR_ACENTO);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_ACENTO));

        tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(250);

        tablaProductos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(COLOR_SELECCION);
                } else {
                    c.setBackground(row % 2 == 0 ? COLOR_PANEL : COLOR_TABLA_ALT);
                }
                if (column == 0 || column == 4 || column == 5) {
                    ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    ((DefaultTableCellRenderer) c).setHorizontalAlignment(SwingConstants.LEFT);
                }
                setBorder(new EmptyBorder(0, 10, 0, 10));
                return c;
            }
        });
    }

    private JTextField crearCampoTexto(int cols) {
        JTextField campo = new JTextField(cols);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBackground(COLOR_CAMPO);
        campo.setForeground(COLOR_TEXTO);
        campo.setCaretColor(COLOR_TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_CAMPO.darker(), 1),
                new EmptyBorder(8, 10, 8, 10)));
        return campo;
    }

    private void agregarCampoFormulario(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta,
            JComponent comp) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0;
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(COLOR_TEXTO_SEC);
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(comp, gbc);
    }

    private TitledBorder crearBordeConTitulo(String titulo) {
        TitledBorder border = BorderFactory.createTitledBorder(
                new LineBorder(new Color(60, 63, 85), 1), " " + titulo + " ");
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        border.setTitleColor(COLOR_ACENTO);
        return border;
    }

    private JButton crearBotonAccion(String texto, Color bg) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(new Color(20, 20, 30));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 40));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bg.darker());
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(bg);
            }
        });
        return btn;
    }

    // ──────────────────────────────────────────────
    // Lógica y Eventos
    // ──────────────────────────────────────────────

    private void iniciarReloj() {
        Timer timer = new Timer(1000, e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy | hh:mm:ss a");
            lblReloj.setText("🕒 " + sdf.format(new Date()));
        });
        timer.start();
    }

    private void configurarEventos() {
        btnAgregar.addActionListener(e -> accionAgregar());
        btnEditar.addActionListener(e -> accionEditar());
        btnEliminar.addActionListener(e -> accionEliminar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnBuscar.addActionListener(e -> accionBuscar());
        btnMostrarTodos.addActionListener(e -> cargarProductos());
        txtBuscar.addActionListener(e -> accionBuscar());

        tablaProductos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tablaProductos.getSelectedRow();
                if (fila >= 0)
                    poblarFormularioDesdeTabla(fila);
            }
        });
    }

    private void cargarProductos() {
        try {
            List<Producto> productos = controller.listarProductos();
            actualizarTabla(productos);
            actualizarEstadisticas(productos);
        } catch (SQLException ex) {
            mostrarError("Error al cargar productos", ex);
        }
    }

    private void actualizarEstadisticas(List<Producto> productos) {
        lblTotalProductos.setText(String.valueOf(productos.size()));

        long bajoStock = productos.stream().filter(p -> p.getStock() < 5).count();
        lblBajoStock.setText(String.valueOf(bajoStock));

        int categorias = productos.stream().map(Producto::getCategoria).collect(Collectors.toSet()).size();
        lblCategorias.setText(String.valueOf(categorias));
    }

    private void actualizarTabla(List<Producto> productos) {
        modeloTabla.setRowCount(0);
        for (Producto p : productos) {
            modeloTabla.addRow(new Object[] {
                    p.getId(), p.getCodigo(), p.getNombre(), p.getCategoria(),
                    String.format("$%.2f", p.getPrecio()), p.getStock()
            });
        }
    }

    private void poblarFormularioDesdeTabla(int fila) {
        txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
        txtCodigo.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtNombre.setText(modeloTabla.getValueAt(fila, 2).toString());
        cmbCategoria.setSelectedItem(modeloTabla.getValueAt(fila, 3).toString());
        txtPrecio.setText(modeloTabla.getValueAt(fila, 4).toString().replace("$", "").replace(",", "."));
        txtStock.setText(modeloTabla.getValueAt(fila, 5).toString());
    }

    private Producto crearProductoDesdeFormulario() {
        return new Producto(
                txtCodigo.getText().trim(),
                txtNombre.getText().trim(),
                cmbCategoria.getSelectedItem().toString(),
                Double.parseDouble(txtPrecio.getText().trim()),
                Integer.parseInt(txtStock.getText().trim()));
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtCodigo.setText("");
        txtNombre.setText("");
        cmbCategoria.setSelectedIndex(0);
        txtPrecio.setText("");
        txtStock.setText("");
        tablaProductos.clearSelection();
    }

    private void accionAgregar() {
        if (!validarCamposVacios() || !validarDatosNumericos())
            return;
        try {
            if (controller.agregarProducto(crearProductoDesdeFormulario())) {
                mostrarMensaje("✅ Producto agregado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarProductos();
            } else {
                mostrarMensaje("❌ Error al agregar producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            if (ex.getMessage().contains("UNIQUE"))
                mostrarMensaje("⚠️ El código ya existe.", "Duplicado", JOptionPane.WARNING_MESSAGE);
            else
                mostrarError("Error de BD", ex);
        }
    }

    private void accionEditar() {
        if (txtId.getText().isEmpty()) {
            mostrarMensaje("⚠️ Seleccione un producto para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarCamposVacios() || !validarDatosNumericos())
            return;

        if (JOptionPane.showConfirmDialog(this, "¿Actualizar producto?", "Confirmar",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
            return;

        try {
            Producto p = crearProductoDesdeFormulario();
            p.setId(Integer.parseInt(txtId.getText().trim()));
            if (controller.actualizarProducto(p)) {
                mostrarMensaje("✅ Producto actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarProductos();
            }
        } catch (SQLException ex) {
            mostrarError("Error al actualizar", ex);
        }
    }

    private void accionEliminar() {
        if (txtId.getText().isEmpty()) {
            mostrarMensaje("⚠️ Seleccione un producto para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "⚠️ ¿Eliminar producto permanentemente?", "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION)
            return;

        try {
            if (controller.eliminarProducto(Integer.parseInt(txtId.getText().trim()))) {
                mostrarMensaje("✅ Producto eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarProductos();
            }
        } catch (SQLException ex) {
            mostrarError("Error al eliminar", ex);
        }
    }

    private void accionBuscar() {
        String texto = txtBuscar.getText().trim().replace(" Buscar por código o nombre...", "");
        if (texto.isEmpty()) {
            cargarProductos();
            return;
        }
        try {
            List<Producto> res = controller.buscarProductos(texto);
            actualizarTabla(res);
            if (res.isEmpty())
                mostrarMensaje("ℹ️ No se encontraron resultados.", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            mostrarError("Error en búsqueda", ex);
        }
    }

    private boolean validarCamposVacios() {
        if (txtCodigo.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty() ||
                cmbCategoria.getSelectedIndex() == 0 || txtPrecio.getText().trim().isEmpty() ||
                txtStock.getText().trim().isEmpty()) {
            mostrarMensaje("⚠️ Complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validarDatosNumericos() {
        try {
            if (Double.parseDouble(txtPrecio.getText().trim()) <= 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            mostrarMensaje("⚠️ Precio inválido.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            if (Integer.parseInt(txtStock.getText().trim()) < 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            mostrarMensaje("⚠️ Stock inválido.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private void mostrarMensaje(String msg, String titulo, int tipo) {
        UIManager.put("OptionPane.background", COLOR_PANEL);
        UIManager.put("Panel.background", COLOR_PANEL);
        UIManager.put("OptionPane.messageForeground", COLOR_TEXTO);
        JOptionPane.showMessageDialog(this, msg, titulo, tipo);
    }

    private void mostrarError(String ctx, SQLException ex) {
        mostrarMensaje(ctx + ":\n" + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
    }
}
