/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author segio
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import modelo.SistemaUsuarios;

public class VentanaInicio extends JFrame{
    private SistemaUsuarios sistema;
    public VentanaInicio(SistemaUsuarios sistema) {
        this.sistema = sistema;
        setTitle("Marvel Stratego - Inicio");
        setSize(550, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        crearInterfaz();
        setVisible(true);
    }
    private void crearInterfaz() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(new Color(25, 35, 55));
        JLabel titulo = new JLabel( "MARVEL STRATEGO",SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);
        panel.add(titulo, BorderLayout.NORTH);
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 15, 15));
        panelBotones.setBackground(new Color(25, 35, 55));
        JButton btnLogin = new JButton("INICIAR SESION");
        JButton btnRegistro = new JButton("CREAR PLAYER");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 18));
        btnRegistro.setFont(new Font("Arial", Font.BOLD, 18));
        panelBotones.add(btnLogin);
        panelBotones.add(btnRegistro);
        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(new Color(25, 35, 55));
        centro.add(panelBotones, BorderLayout.CENTER);
        centro.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 80, 40, 80));
        panel.add(centro, BorderLayout.CENTER);
        add(panel);
        btnLogin.addActionListener(e -> iniciarSesion());
        btnRegistro.addActionListener(e -> registrarJugador());
    }

    private void registrarJugador() {
        String usuario = javax.swing.JOptionPane.showInputDialog(
            this, "Ingrese su nombre de usuario:");
        if (usuario == null) {
            return;
        }
        usuario = usuario.trim();
        if (usuario.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "El usuario no puede estar vacío.");
            return;
        }
        javax.swing.JPasswordField campoPassword =new javax.swing.JPasswordField(20);
        campoPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        campoPassword.setPreferredSize(new java.awt.Dimension(300, 40));
        int respuesta = javax.swing.JOptionPane.showConfirmDialog(this, campoPassword, "Ingrese una contraseña de 5 caracteres",javax.swing.JOptionPane.OK_CANCEL_OPTION);
        if (respuesta != javax.swing.JOptionPane.OK_OPTION) {
            return;
        }
        String password = new String(campoPassword.getPassword());
        if (password.length() != 5) {
            javax.swing.JOptionPane.showMessageDialog(this, "La contraseña debe tener exactamente 5 caracteres.");
            return;
        }
        try {
            sistema.registrarJugador(usuario, password);
            javax.swing.JOptionPane.showMessageDialog(this, "Jugador registrado correctamente.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void iniciarSesion() {
        String usuario = javax.swing.JOptionPane.showInputDialog(this, "Ingrese su usuario:");
        if (usuario == null) {
            return;
        }
        javax.swing.JPasswordField campoPassword =new javax.swing.JPasswordField(20);
        int respuesta = javax.swing.JOptionPane.showConfirmDialog(this, campoPassword, "Ingrese su contraseña",javax.swing.JOptionPane.OK_CANCEL_OPTION);
        if (respuesta != javax.swing.JOptionPane.OK_OPTION) {
            return;
        }
        String password = new String(campoPassword.getPassword());
        if (sistema.iniciarSesion(usuario.trim(), password)) {
            javax.swing.JOptionPane.showMessageDialog(this, "Bienvenido, " + usuario + ".");
            new VentanaMenuPrincipal(sistema);
            dispose();
        } else {

            javax.swing.JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaUsuarios sistema = new SistemaUsuarios();
            new VentanaInicio(sistema);
        });
    }
}
