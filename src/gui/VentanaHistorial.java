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
import modelo.RegistroPartida;
import modelo.SistemaUsuarios;

public class VentanaHistorial extends JFrame{
    private SistemaUsuarios sistema;
    private JTable tabla;
    private DefaultTableModel modelo;

    public VentanaHistorial(SistemaUsuarios sistema) {
        this.sistema = sistema;
        setTitle("Marvel Stratego - Historial");
        setSize(850, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        crearInterfaz();
        cargarHistorial();
        setVisible(true);
    }

    private void crearInterfaz() {

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(25, 35, 55));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titulo = new JLabel("LOG ULTIMOS JUEGOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        titulo.setForeground(Color.WHITE);
        panel.add(titulo, BorderLayout.NORTH);
        String[] columnas = {
            "Ganador", "Perdedor", "Motivo", "Fecha"
        };

        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        JButton btnVolver = new JButton("VOLVER");
        btnVolver.addActionListener(e -> dispose());
        panel.add(btnVolver, BorderLayout.SOUTH);
        add(panel);
    }

    private void cargarHistorial() {
        modelo.setRowCount(0);
        Jugador actual = sistema.getJugadorActual();
        RegistroPartida[] registros =sistema.obtenerHistorialJugador(actual);
        if (registros == null) {
            return;
        }
        for (RegistroPartida registro : registros) {
            if (registro != null) {
                modelo.addRow(new Object[]{
                    registro.getGanador().getUsername(),
                    registro.getPerdedor().getUsername(),
                    registro.getMotivo(),
                    registro.getFecha()
                });
            }
        }
    }
}
