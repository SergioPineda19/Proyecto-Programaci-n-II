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
import modelo.SistemaUsuarios;
import modelo.Jugador;

public class VentanaMenuPrincipal extends JFrame{
    private SistemaUsuarios sistema;

    public VentanaMenuPrincipal(SistemaUsuarios sistema) {

        this.sistema = sistema;

        setTitle("Marvel Stratego - Menu Principal");
        setSize(600, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        crearInterfaz();

        setVisible(true);
    }
    
    private void crearInterfaz() {

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(25, 35, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        Jugador jugador = sistema.getJugadorActual();

        JLabel titulo = new JLabel("MARVEL STRATEGO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);

        JLabel bienvenida = new JLabel("Bienvenido: " + jugador.getUsername(),SwingConstants.CENTER);
        bienvenida.setForeground(Color.YELLOW);
        bienvenida.setFont(new Font("Arial", Font.BOLD, 17));

        JPanel encabezado = new JPanel(new GridLayout(2, 1, 5, 5));
        encabezado.setOpaque(false);
        encabezado.add(titulo);
        encabezado.add(bienvenida);

        panel.add(encabezado, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(7, 1, 10, 10));
        botones.setOpaque(false);

        JButton btnPartida = new JButton("PARTIDA NUEVA");
        JButton btnPerfil = new JButton("MI PERFIL");
        JButton btnHistorial = new JButton("LOG ULTIMOS JUEGOS");
        JButton btnRanking = new JButton("RANKING");
        JButton btnBatallas = new JButton("BATALLAS");
        JButton btnPassword = new JButton("CAMBIAR PASSWORD");
        JButton btnSalir = new JButton("CERRAR SESION");

        botones.add(btnPartida);
        botones.add(btnPerfil);
        botones.add(btnHistorial);
        botones.add(btnRanking);
        botones.add(btnBatallas);
        botones.add(btnPassword);
        botones.add(btnSalir);

        panel.add(botones, BorderLayout.CENTER);
        add(panel);
        btnPartida.addActionListener(e -> {new VentanaNuevaPartida(sistema);
        });
        btnPerfil.addActionListener(e -> {new VentanaPerfil(sistema);
        });
        btnHistorial.addActionListener(e -> {new VentanaHistorial(sistema);
        });
        btnRanking.addActionListener(e -> {JOptionPane.showMessageDialog(this,"");
        });
        btnBatallas.addActionListener(e -> {JOptionPane.showMessageDialog(this,"");
        });
        btnPassword.addActionListener(e -> {new VentanaPerfil(sistema);
        });
        btnSalir.addActionListener(e -> {sistema.cerrarSesion();new VentanaInicio(sistema);
        dispose();
        });
    }
}
