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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import modelo.Jugador;
import modelo.Partida;
import modelo.SistemaUsuarios;
import modelo.Tablero;

public class VentanaNuevaPartida extends JFrame{
    private SistemaUsuarios sistema;
    private JComboBox<String> comboJugadores;

    public VentanaNuevaPartida(SistemaUsuarios sistema) {
        this.sistema = sistema;
        setTitle("Marvel Stratego - Nueva Partida");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        crearInterfaz();
        setVisible(true);
    }

    private void crearInterfaz() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(25, 35, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
        JLabel titulo = new JLabel("NUEVA PARTIDA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        panel.add(titulo, BorderLayout.NORTH);
        JPanel centro = new JPanel(new GridLayout(4, 1, 10, 10));
        centro.setOpaque(false);
        Jugador jugadorActual = sistema.getJugadorActual();
        JLabel lblJugador = new JLabel("Jugador actual: " + jugadorActual.getUsername());
        lblJugador.setForeground(Color.YELLOW);
        lblJugador.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel lblOponente = new JLabel("Selecciona tu oponente:");
        lblOponente.setForeground(Color.WHITE);
        comboJugadores = new JComboBox<String>();
        Jugador[] ranking = sistema.obtenerRanking();
        for (Jugador jugador : ranking) {

            if (jugador != null
                    && jugador != jugadorActual) {

                comboJugadores.addItem(jugador.getUsername());
            }
        }

        centro.add(lblJugador);
        centro.add(lblOponente);
        centro.add(comboJugadores);
        panel.add(centro, BorderLayout.CENTER);
        JPanel botones = new JPanel(new GridLayout(1, 2, 15, 0));
        botones.setOpaque(false);
        JButton btnIniciar = new JButton("INICIAR PARTIDA");
        JButton btnCancelar = new JButton("CANCELAR");
        botones.add(btnIniciar);
        botones.add(btnCancelar);
        panel.add(botones, BorderLayout.SOUTH);
        add(panel);
        btnIniciar.addActionListener(e -> iniciarPartida());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void iniciarPartida() {

        if (comboJugadores.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,"Debes tener otro jugador registrado para jugar.");
            return;
        }

        Jugador jugador1 = sistema.getJugadorActual();
        String nombreOponente =comboJugadores.getSelectedItem().toString();
        Jugador jugador2 = sistema.buscarJugador(nombreOponente);
        if (jugador2 == null || jugador1 == jugador2) {
            JOptionPane.showMessageDialog(this,"El oponente seleccionado no es válido.");
            return;
        }

        try {

            Partida partida = new Partida(jugador1, jugador2, sistema);
            Tablero tablero = new Tablero();
            tablero.colocarPiezasIniciales();
            new VentanaTablero(partida, tablero, sistema);
            dispose();

        } catch (IllegalArgumentException
                | IllegalStateException ex) {

            JOptionPane.showMessageDialog(this,ex.getMessage(),"Error al iniciar partida",JOptionPane.ERROR_MESSAGE);
        }
    }
}
