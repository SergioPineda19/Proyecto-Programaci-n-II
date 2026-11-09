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
import javax.swing.*;
import modelo.SistemaUsuarios;
import enums.Bando;

public class VentanaBatallas extends JFrame{
    private SistemaUsuarios sistema;

    public VentanaBatallas(SistemaUsuarios sistema) {
        this.sistema = sistema;
        setTitle("Marvel Stratego - Batallas");
        setSize(550, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        crearInterfaz();
        setVisible(true);
    }

    private void crearInterfaz() {

        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(new Color(25, 35, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titulo = new JLabel("ESTADISTICAS DE BATALLAS",SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 23));
        titulo.setForeground(Color.WHITE);
        panel.add(titulo, BorderLayout.NORTH);
        JPanel datos = new JPanel(new GridLayout(4, 1, 15, 15));
        datos.setOpaque(false);
        datos.add(crearEtiqueta("Jugadores registrados: "+ sistema.getCantidadJugadores()));
        datos.add(crearEtiqueta("Total de batallas: "+ sistema.getTotalBatallas()));
        datos.add(crearEtiqueta("Victorias de heroes: "+ sistema.contarVictorias(Bando.HEROE)));
        datos.add(crearEtiqueta("Victorias de villanos: "+ sistema.contarVictorias(Bando.VILLANOS)));

        panel.add(datos, BorderLayout.CENTER);
        JButton btnVolver = new JButton("VOLVER");
        btnVolver.addActionListener(e -> dispose());
        panel.add(btnVolver, BorderLayout.SOUTH);
        add(panel);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 18));
        etiqueta.setForeground(Color.WHITE);
        return etiqueta;
    }
}
