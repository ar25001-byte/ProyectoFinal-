package com.tiendapc.vista;

import com.tiendapc.modelo.Usuario;
import com.tiendapc.persistencia.UsuarioDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginForm extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private UsuarioDAO usuarioDAO;

    // Paleta de colores
    private static final Color COLOR_FONDO = new Color(30, 30, 46);
    private static final Color COLOR_PANEL = new Color(40, 42, 58);
    private static final Color COLOR_TEXTO = new Color(205, 214, 244);
    private static final Color COLOR_TEXTO_SEC = new Color(166, 173, 200);
    private static final Color COLOR_ACENTO = new Color(137, 180, 250);
    private static final Color COLOR_INPUT = new Color(55, 58, 78);

    public LoginForm() {
        this.usuarioDAO = new UsuarioDAO();
        inicializarUI();
    }

    private void inicializarUI() {
        setTitle("💻 Ingreso - TiendaPC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(COLOR_FONDO);
        setLayout(new GridBagLayout());

        // Panel Principal
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(COLOR_PANEL);
        panelCentral.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(60, 63, 85), 1, true),
                new EmptyBorder(30, 40, 30, 40)
        ));

        // Título e Ícono
        JLabel lblIcono = new JLabel("🏪");
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblTitulo = new JLabel("Bienvenido a TiendaPC");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Inicie sesión para continuar");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(COLOR_TEXTO_SEC);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Campos de Texto
        txtUsuario = crearCampoTexto();
        txtPassword = crearCampoPassword();

        // Botón
        btnLogin = new JButton("Ingresar ➔");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(COLOR_ACENTO);
        btnLogin.setForeground(COLOR_FONDO);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        btnLogin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btnLogin.setBackground(COLOR_ACENTO.darker());
            }
            public void mouseExited(MouseEvent evt) {
                btnLogin.setBackground(COLOR_ACENTO);
            }
        });
        
        btnLogin.addActionListener(e -> validarLogin());

        // Ensamblar Panel
        panelCentral.add(lblIcono);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(lblTitulo);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 5)));
        panelCentral.add(lblSub);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 30)));
        
        panelCentral.add(crearFilaInput("👤 Usuario:", txtUsuario));
        panelCentral.add(Box.createRigidArea(new Dimension(0, 15)));
        panelCentral.add(crearFilaInput("🔑 Contraseña:", txtPassword));
        panelCentral.add(Box.createRigidArea(new Dimension(0, 30)));
        
        panelCentral.add(btnLogin);

        // Añadir panel al Frame
        add(panelCentral);
    }

    private JPanel crearFilaInput(String etiqueta, JTextField campo) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(COLOR_PANEL);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(COLOR_TEXTO_SEC);
        
        panel.add(lbl, BorderLayout.NORTH);
        panel.add(campo, BorderLayout.CENTER);
        return panel;
    }

    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBackground(COLOR_INPUT);
        campo.setForeground(COLOR_TEXTO);
        campo.setCaretColor(COLOR_TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_INPUT.darker(), 1),
                new EmptyBorder(8, 10, 8, 10)
        ));
        return campo;
    }

    private JPasswordField crearCampoPassword() {
        JPasswordField campo = new JPasswordField();
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBackground(COLOR_INPUT);
        campo.setForeground(COLOR_TEXTO);
        campo.setCaretColor(COLOR_TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_INPUT.darker(), 1),
                new EmptyBorder(8, 10, 8, 10)
        ));
        return campo;
    }

    private void validarLogin() {
        String usuarioStr = txtUsuario.getText().trim();
        String passwordStr = new String(txtPassword.getPassword());

        if (usuarioStr.isEmpty() || passwordStr.isEmpty()) {
            mostrarMensaje("Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Usuario u = usuarioDAO.validarCredenciales(usuarioStr, passwordStr);
            if (u != null) {
                InventarioForm inventario = new InventarioForm();
                inventario.setVisible(true);
                this.dispose();
            } else {
                mostrarMensaje("Usuario o contraseña incorrectos.", "Credenciales inválidas", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            mostrarMensaje("Error de conexión:\n" + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarMensaje(String msg, String titulo, int tipo) {
        UIManager.put("OptionPane.background", COLOR_PANEL);
        UIManager.put("Panel.background", COLOR_PANEL);
        UIManager.put("OptionPane.messageForeground", COLOR_TEXTO);
        JOptionPane.showMessageDialog(this, msg, titulo, tipo);
    }
}

