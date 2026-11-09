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
import enums.Bando;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import modelo.Tablero;
import piezas.Pieza;
import enums.ResultadoCombate;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import modelo.Partida;
import modelo.Jugador;
import modelo.SistemaUsuarios;


public class VentanaTablero extends JFrame{
    private Tablero tablero;
    private JButton[][] botones;
    private JLabel lblTurno;
    private Partida partida;
    private SistemaUsuarios sistemaUsuarios;
    private int filaSeleccionada = -1;
    private int columnaSeleccionada = -1;
    private JPanel panelDerrotadosHeroes;
    private JPanel panelDerrotadosVillanos;

    
    public VentanaTablero(Partida partida, Tablero tablero,
        SistemaUsuarios sistemaUsuarios) {
        this.partida = partida;
        this.tablero = tablero;
        this.sistemaUsuarios = sistemaUsuarios;
        botones = new JButton[10][10];
        setTitle("Marvel Stratego");
        setSize(950, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        crearEncabezado();
        crearTablero();
        crearPanelDerrotados();
        crearBotonRendirse();
        actualizarTurno();
        setVisible(true);
    }


    private void crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(25, 35, 55));
        JLabel titulo = new JLabel("MARVEL STRATEGO");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTurno = new JLabel("Turno: HEROES");
        lblTurno.setForeground(Color.YELLOW);
        lblTurno.setFont(new Font("Arial", Font.BOLD, 17));
        lblTurno.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo, BorderLayout.CENTER);
        panel.add(lblTurno, BorderLayout.SOUTH);
        add(panel, BorderLayout.NORTH);
    }

    private void crearTablero() {
        JPanel panelTablero = new PanelFondo();
        panelTablero.setLayout(new GridLayout(10, 10, 2, 2));
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                JButton boton = new JButton();
                boton.setPreferredSize(new Dimension(70, 70));
                boton.setFocusPainted(false);
                boton.setMargin(new java.awt.Insets(0, 0, 0, 0));
                botones[fila][columna] = boton;
                final int f = fila;
                final int c = columna;
                boton.addActionListener(e -> seleccionarCasilla(f, c));
                if (tablero.esZonaProhibida(fila, columna)) {
                    boton.setBackground(Color.DARK_GRAY);
                    boton.setEnabled(false);
                } else {
                    boton.setContentAreaFilled(false);
                    boton.setOpaque(false);
                    mostrarPieza(fila, columna);
                }
                panelTablero.add(boton);
            }
        }
        add(panelTablero, BorderLayout.CENTER);
    }

    private void mostrarPieza(int fila, int columna) {
        Pieza pieza = tablero.obtenerPieza(fila, columna);
        JButton boton = botones[fila][columna];
        boton.setIcon(null);
        boton.setText("");
        if (pieza == null) {
            return;
        }
        String rutaImagen;
        Bando bandoActual = partida.obtenerBandoTurno();
        if (pieza.getBando() != bandoActual) {
            if (pieza.getBando() == Bando.HEROE) {
                rutaImagen = "/imagenes/reversos/reverso_heroes.png";
            } else {
                rutaImagen = "/imagenes/reversos/reverso_villanos.png";
            }
        } else {
            String carpeta;
            if (pieza.getBando() == Bando.HEROE) {
                carpeta = "/imagenes/heroes/";
            } else {
                carpeta = "/imagenes/villanos/";
            }
            rutaImagen = carpeta+ obtenerNombreImagen(pieza, fila, columna);
        }
        java.net.URL ruta = getClass().getResource(rutaImagen);
        if (ruta != null) {
            ImageIcon imagen = new ImageIcon(ruta);
            Image ajustada = imagen.getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(ajustada));
        } else {
            if (pieza.getBando() == bandoActual) {
                boton.setText("" + pieza.getRango());
            } else {
                boton.setText("?");
            }
        }
    }

    private String obtenerNombreImagen(Pieza pieza, int fila, int columna) {
        String nombre = pieza.getNombre().toLowerCase();
        nombre = nombre.replace(".", "");
        nombre = nombre.replace("-", "_");
        nombre = nombre.replace(" ", "_");
        if (nombre.equals("planet_earth")) {
            return "40_planet_earth.png";
        }
        if (nombre.equals("nova_blast")) {
            return "34_nova_blast.png";
        }
        if (nombre.equals("pumpkin_bomb")) {
            return "34_pumpkin_bomb.png";
        }
        String carpeta = "heroes";
        if (pieza.getBando() == Bando.VILLANOS) {
            carpeta = "villanos";
        }
        String[] nombresHeroes = {
            "mr_fantastic", "captain_america", "professor_x",
            "nick_fury", "spider_man", "wolverine", "namor",
            "daredevil", "silver_surfer", "hulk", "iron_man",
            "thor", "human_torch", "cyclops", "invisible_woman",
            "ghost_rider", "punisher", "blade", "thing",
            "emma_frost", "she_hulk", "giant_man", "beast",
            "colossus", "gambit", "spider_girl", "ice_man",
            "storm", "phoenix", "dr_strange", "elektra",
            "nightcrawler", "black_widow"
        };
        String[] nombresVillanos = {
            "dr_doom", "galactus", "kingpin", "magneto",
            "apocalypse", "green_goblin", "venom", "bullseye",
            "omega_red", "onslaught", "red_skull", "mystique",
            "mysterio", "dr_octopus", "deadpool", "abomination",
            "thanos", "black_cat", "sabretooth", "juggernaut",
            "rhino", "carnage", "mole_man", "lizard",
            "mr_sinister", "sentinel_1", "ultron", "sandman",
            "leader", "viper", "sentinel_2", "electro",
            "black_widow"
        };
        String[] nombres;
        if (carpeta.equals("heroes")) {
            nombres = nombresHeroes;
        } else {
            nombres = nombresVillanos;
        }
        for (int i = 0; i < nombres.length; i++) {
            if (nombre.equals(nombres[i])) {
                int numero = i + 1;

                if (numero < 10) {
                    return "0" + numero + "_" + nombre + ".png";
                }
                return numero + "_" + nombre + ".png";
            }
        }
        return "";
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SistemaUsuarios sistema = new SistemaUsuarios();
                sistema.registrarJugador("Jugador1", "12345");
                sistema.registrarJugador("Jugador2", "54321");
                Jugador jugador1 = sistema.buscarJugador("Jugador1");
                Jugador jugador2 = sistema.buscarJugador("Jugador2");
                Partida partida = new Partida(jugador1, jugador2, sistema);
                Tablero tablero = new Tablero();
                tablero.colocarPiezasIniciales();
                new VentanaTablero(partida, tablero, sistema);
            }
        });
    }
    
    
    private void seleccionarCasilla(int fila, int columna) {
        if (partida.isTerminada()) {
            return;
        }
        Pieza pieza = tablero.obtenerPieza(fila, columna);
        Bando turnoActual = partida.obtenerBandoTurno();
        if (filaSeleccionada == -1) {
            if (pieza == null) {
                return;
            }
            if (pieza.getBando() != turnoActual) {
                JOptionPane.showMessageDialog(this,"No puedes seleccionar piezas del enemigo.");
                return;
            }
            if (!pieza.puedeMoverse()) {
                JOptionPane.showMessageDialog(this,"Esta pieza no puede moverse.");
                return;
            }
            filaSeleccionada = fila;
            columnaSeleccionada = columna;

            botones[fila][columna].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
            return;
        }
        if (filaSeleccionada == fila&& columnaSeleccionada == columna) {
            limpiarSeleccion();
            return;
        }
        if (pieza != null && pieza.getBando() == turnoActual) {
            limpiarSeleccion();
            JOptionPane.showMessageDialog(this,"No puedes mover sobre una pieza aliada.");
            return;
        }

        if (pieza != null) {
            realizarCombate(fila, columna);
            return;
        }
        try {
            partida.realizarMovimiento(tablero,filaSeleccionada,columnaSeleccionada, fila,columna);
            limpiarSeleccion();
            prepararSiguienteTurno();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            limpiarSeleccion();
            JOptionPane.showMessageDialog(this,ex.getMessage(),"Movimiento inválido",JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limpiarSeleccion() {
        if (filaSeleccionada != -1) {
            botones[filaSeleccionada][columnaSeleccionada].setBorder(UIManager.getBorder("Button.border"));
        }
        filaSeleccionada = -1;
        columnaSeleccionada = -1;
    }
    
    private void actualizarTablero() {
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                if (!tablero.esZonaProhibida(fila, columna)) {
                    mostrarPieza(fila, columna);
                }
            }
        }
    }
    
    private void realizarCombate(int filaDestino, int columnaDestino) {
        try {
            Pieza atacante = tablero.obtenerPieza(filaSeleccionada, columnaSeleccionada);
            Pieza defensor = tablero.obtenerPieza(filaDestino, columnaDestino);
            String nombreAtacante = atacante.getNombre();
            String nombreDefensor = defensor.getNombre();
            actualizarDerrotados();
            ResultadoCombate resultado = partida.realizarAtaque(tablero,filaSeleccionada,columnaSeleccionada,filaDestino,columnaDestino);
            actualizarDerrotados();
            String mensaje = nombreAtacante + " VS " + nombreDefensor + "\n\n";
            if (resultado == ResultadoCombate.GANA_ATACANTE) {
                mensaje += "Ganador: " + nombreAtacante;
            } else if (resultado == ResultadoCombate.GANA_DEFENSOR) {
                mensaje += "Ganador: " + nombreDefensor;
            } else {
                mensaje += "Empate: ambas piezas fueron eliminadas.";
            }
            limpiarSeleccion();
            ocultarTodasLasPiezas();
            JOptionPane.showMessageDialog(this,mensaje,"Resultado del combate",JOptionPane.INFORMATION_MESSAGE);
            if (partida.isTerminada()) {
                    JOptionPane.showMessageDialog(this,"¡Ganador: " + partida.getGanador().getUsername() + "!");
                    bloquearTablero();
                return;
            }
            prepararSiguienteTurno();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            limpiarSeleccion();
            JOptionPane.showMessageDialog(this,ex.getMessage(),"Ataque inválido",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void bloquearTablero() {
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                botones[fila][columna].setEnabled(false);
            }
        }
        lblTurno.setText("PARTIDA FINALIZADA");
    }
    
    private void actualizarTurno() {
        Jugador jugador = partida.getTurno();
        lblTurno.setText("Turno: " + jugador.getUsername() + " (" + partida.obtenerBandoTurno() + ")");
        actualizarTablero();
    }
    
    
    private void crearBotonRendirse() {
        JButton btnRendirse = new JButton("RENDIRSE");
        btnRendirse.setBackground(new Color(150, 30, 40));
        btnRendirse.setForeground(Color.WHITE);
        btnRendirse.setFont(new Font("Arial", Font.BOLD, 16));
        btnRendirse.addActionListener(e -> {
            if (partida.isTerminada()) {
                return;
            }
            int respuesta = JOptionPane.showConfirmDialog(this,"¿Seguro que deseas rendirte?","Confirmar rendición",JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                Jugador jugador = partida.getTurno();
                partida.rendirse(jugador);
                JOptionPane.showMessageDialog(this,"El jugador " + jugador.getUsername()+ " se ha rendido.\n"+ "Ganador: "+ partida.getGanador().getUsername());
                bloquearTablero();
            }
        });
        add(btnRendirse, BorderLayout.SOUTH);
    }
    
    
    private void prepararSiguienteTurno() {
        ocultarTodasLasPiezas();
        JOptionPane.showMessageDialog(this,"Turno de " + partida.getTurno().getUsername()+ "\n\nPresiona Aceptar cuando estés listo.","Cambio de turno",JOptionPane.INFORMATION_MESSAGE);
        actualizarTurno();
    }
    
    private void ocultarTodasLasPiezas() {
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                botones[fila][columna].setIcon(null);
                botones[fila][columna].setText("");
            }
        }
    }
    
    
    private void crearPanelDerrotados() {
        panelDerrotadosHeroes = new JPanel();
        panelDerrotadosVillanos = new JPanel();
        panelDerrotadosHeroes.setLayout(new BoxLayout(panelDerrotadosHeroes, BoxLayout.Y_AXIS));
        panelDerrotadosVillanos.setLayout(new BoxLayout(panelDerrotadosVillanos, BoxLayout.Y_AXIS));
        panelDerrotadosHeroes.setBackground(new Color(30, 45, 65));
        panelDerrotadosVillanos.setBackground(new Color(65, 30, 35));
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        JPanel panelDerecho = new JPanel(new BorderLayout());
        JLabel tituloHeroes = new JLabel("HEROES DERROTADOS",SwingConstants.CENTER);
        JLabel tituloVillanos = new JLabel("VILLANOS DERROTADOS",SwingConstants.CENTER);
        tituloHeroes.setForeground(Color.WHITE);
        tituloVillanos.setForeground(Color.WHITE);
        panelIzquierdo.setBackground(new Color(30, 45, 65));
        panelDerecho.setBackground(new Color(65, 30, 35));
        panelIzquierdo.add(tituloHeroes, BorderLayout.NORTH);
        panelDerecho.add(tituloVillanos, BorderLayout.NORTH);
        panelIzquierdo.add(new JScrollPane(panelDerrotadosHeroes),BorderLayout.CENTER);
        panelDerecho.add(new JScrollPane(panelDerrotadosVillanos),BorderLayout.CENTER);
        panelIzquierdo.setPreferredSize(new Dimension(120, 0));
        panelDerecho.setPreferredSize(new Dimension(120, 0));
        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.EAST);
    }
    
    
    private void actualizarDerrotados() {
        panelDerrotadosHeroes.removeAll();
        panelDerrotadosVillanos.removeAll();
        Pieza[] heroes = tablero.getPiezasVencidasHeroes();
        Pieza[] villanos = tablero.getPiezasVencidasVillanos();
        for (Pieza pieza : heroes) {
            if (pieza != null) {
                agregarPiezaDerrotada(panelDerrotadosHeroes, pieza);
            }
        }
        for (Pieza pieza : villanos) {
            if (pieza != null) {
                agregarPiezaDerrotada(panelDerrotadosVillanos, pieza);
            }
        }
        panelDerrotadosHeroes.revalidate();
        panelDerrotadosHeroes.repaint();
        panelDerrotadosVillanos.revalidate();
        panelDerrotadosVillanos.repaint();
    }
    
    
    private void agregarPiezaDerrotada(JPanel panel, Pieza pieza) {
        JLabel etiqueta = new JLabel();
        etiqueta.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
        String carpeta;
        if (pieza.getBando() == Bando.HEROE) {
            carpeta = "/imagenes/heroes/";
        } else {
            carpeta = "/imagenes/villanos/";
        }
        String nombreArchivo = obtenerNombreImagen(pieza, 0, 0);
        java.net.URL ruta = getClass().getResource(carpeta + nombreArchivo);
        if (ruta != null) {
            ImageIcon imagen = new ImageIcon(ruta);
            Image ajustada = imagen.getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH);
            etiqueta.setIcon(new ImageIcon(ajustada));
        } else {
            etiqueta.setText(pieza.getNombre());
            etiqueta.setForeground(Color.WHITE);
        }
        etiqueta.setToolTipText(pieza.getNombre() + " - Rango " + pieza.getRango());
        panel.add(etiqueta);
    }


}
