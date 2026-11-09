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
import java.awt.*;
import javax.swing.*;
import modelo.Jugador;
import modelo.SistemaUsuarios;

public class VentanaPerfil extends JFrame{
    private SistemaUsuarios sistema;
    private JLabel lblUsuario;
    private JLabel lblPuntos;
    private JLabel lblHeroes;
    private JLabel lblVillanos;

    public VentanaPerfil(SistemaUsuarios sistema) {
        this.sistema = sistema;
        setTitle("Marvel Stratego - Mi Perfil");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        crearInterfaz();
        actualizarDatos();
        setVisible(true);
    }

    private void crearInterfaz() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(25, 35, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        JLabel titulo = new JLabel("MI PERFIL", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        panel.add(titulo, BorderLayout.NORTH);
        JPanel datos = new JPanel(new GridLayout(4, 1, 10, 10));
        datos.setOpaque(false);
        lblUsuario = crearEtiqueta();
        lblPuntos = crearEtiqueta();
        lblHeroes = crearEtiqueta();
        lblVillanos = crearEtiqueta();
        datos.add(lblUsuario);
        datos.add(lblPuntos);
        datos.add(lblHeroes);
        datos.add(lblVillanos);
        panel.add(datos, BorderLayout.CENTER);
        JPanel botones = new JPanel(new GridLayout(3, 1, 10, 10));
        botones.setOpaque(false);
        JButton btnPassword = new JButton("CAMBIAR PASSWORD");
        JButton btnEliminar = new JButton("ELIMINAR CUENTA");
        JButton btnCerrar = new JButton("VOLVER");
        botones.add(btnPassword);
        botones.add(btnEliminar);
        botones.add(btnCerrar);
        panel.add(botones, BorderLayout.SOUTH);
        add(panel);
        btnPassword.addActionListener(e -> cambiarPassword());
        btnEliminar.addActionListener(e -> eliminarCuenta());
        btnCerrar.addActionListener(e -> dispose());
    }

    private JLabel crearEtiqueta() {
        JLabel etiqueta = new JLabel();
        etiqueta.setForeground(Color.WHITE);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 17));
        return etiqueta;
    }

    private void actualizarDatos() {
        Jugador jugador = sistema.getJugadorActual();
        if (jugador == null) {
            return;
        }
        lblUsuario.setText("Usuario: " + jugador.getUsername());
        lblPuntos.setText("Puntos: " + jugador.getPuntos());
        lblHeroes.setText("Partidas como heroes: " + jugador.getPartidasHeroes());
        lblVillanos.setText("Partidas como villanos: " + jugador.getPartidasVillanos());
    }

    private void cambiarPassword() {
        Jugador jugador = sistema.getJugadorActual();
        JPasswordField actual = new JPasswordField(20);
        JPasswordField nueva = new JPasswordField(20);
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.add(new JLabel("Contraseña actual:"));
        panel.add(actual);
        panel.add(new JLabel("Nueva contraseña (5 caracteres):"));
        panel.add(nueva);
        int respuesta = JOptionPane.showConfirmDialog( this, panel, "Cambiar contraseña", JOptionPane.OK_CANCEL_OPTION);
        if (respuesta != JOptionPane.OK_OPTION) {
            return;
        }
        String passwordActual = new String(actual.getPassword());
        String passwordNueva = new String(nueva.getPassword());
        if (!jugador.verificarPassword(passwordActual)) {
            JOptionPane.showMessageDialog(this,"La contraseña actual es incorrecta.");
            return;
        }

        if (passwordNueva.length() != 5) {
            JOptionPane.showMessageDialog(this,"La nueva contraseña debe tener 5 caracteres.");
            return;
        }

        try {

            jugador.cambiarPassword(passwordActual, passwordNueva);
            JOptionPane.showMessageDialog(this,"Contraseña actualizada correctamente.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void eliminarCuenta() {
        Jugador jugador = sistema.getJugadorActual();
        JPasswordField campo = new JPasswordField(20);
        int respuesta = JOptionPane.showConfirmDialog(this, campo,"Confirma tu contraseña para eliminar la cuenta",JOptionPane.OK_CANCEL_OPTION);
        if (respuesta != JOptionPane.OK_OPTION) {
            return;
        }
        String password = new String(campo.getPassword());
        if (!jugador.verificarPassword(password)) {
            JOptionPane.showMessageDialog(this,"Contraseña incorrecta.");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,"¿Seguro que deseas eliminar tu cuenta?","Eliminar cuenta",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if (confirmar != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            sistema.eliminarJugador(jugador.getUsername(), password);
            JOptionPane.showMessageDialog(this,"Cuenta eliminada correctamente.");

            dispose();
            for (java.awt.Window ventana : java.awt.Window.getWindows()) {
                if (ventana instanceof VentanaMenuPrincipal) {
                    ventana.dispose();
                }
            }

            new VentanaInicio(sistema);

        } catch (IllegalArgumentException
                | IllegalStateException ex) {

            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    
}
