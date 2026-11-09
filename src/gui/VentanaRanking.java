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
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.Jugador;
import modelo.SistemaUsuarios;

public class VentanaRanking extends JFrame{
    private SistemaUsuarios sistema;
    private JTable tabla;
    private DefaultTableModel modelo;

    public VentanaRanking(SistemaUsuarios sistema) {
        this.sistema = sistema;
        setTitle("Marvel Stratego - Ranking");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        crearInterfaz();
        cargarRanking();
        setVisible(true);
    }

    private void crearInterfaz() {

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(25, 35, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("RANKING DE JUGADORES", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        panel.add(titulo, BorderLayout.NORTH);
        String[] columnas = {
            "Posicion", "Jugador", "Puntos",
            "Partidas Heroes", "Partidas Villanos"
        };

        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.getTableHeader().setFont( new Font("Arial", Font.BOLD, 13));
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        JButton btnVolver = new JButton("VOLVER");
        btnVolver.addActionListener(e -> dispose());
        panel.add(btnVolver, BorderLayout.SOUTH);
        add(panel);
    }

    private void cargarRanking() {
        modelo.setRowCount(0);
        Jugador[] jugadores = sistema.obtenerRanking();
        if (jugadores == null) {
            return;
        }
        int posicion = 1;
        for (Jugador jugador : jugadores) {

            if (jugador != null) {
                modelo.addRow(new Object[]{posicion,jugador.getUsername(),jugador.getPuntos(),jugador.getPartidasHeroes(),jugador.getPartidasVillanos()
                });
                posicion++;
            }
        }
    }
}
