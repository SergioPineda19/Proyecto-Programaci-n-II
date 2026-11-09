package modelo;


import modelo.RegistroPartida;
import modelo.Partida;
import modelo.Jugador;
import enums.Bando;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author segio
 */

public class SistemaUsuarios {
    private Jugador[]jugadores;
    private int cantidadJugadores;
    private Jugador jugadorActual;
    private RegistroPartida[]historial=new RegistroPartida[1000];
    private int cantidadRegistros=0;
    private Partida partidaOriginal;
    private int totalUsuariosHistoricos = 0;
    
    public SistemaUsuarios(){
        jugadores=new Jugador[100];
        cantidadJugadores=0;
        jugadorActual=null;
    }
    
    public void registrarJugador(String usuario, String password){
        if (cantidadJugadores>=jugadores.length) {
            throw new IllegalStateException("No hay espacio para mas jugadores");
        }
        if (buscarJugador(usuario)!=null) {
            throw new IllegalArgumentException("El usuario ya existe");
        }
        Jugador nuevo=new Jugador(usuario,password);
        jugadores[cantidadJugadores]=nuevo;
        cantidadJugadores++;
        totalUsuariosHistoricos++;
    }
    
    public int getUsuariosActivos() {
        return cantidadJugadores;
    }

    public int getTotalUsuariosHistoricos() {
        return totalUsuariosHistoricos;
    }

    public Jugador buscarJugador(String usuario){
            for (int i = 0; i < cantidadJugadores; i++) {
                if (jugadores[i].getUsername().equals(usuario)) {
                    return jugadores[i];
                }
            }
            return null;
    }
    
    public boolean iniciarSesion(String usuario, String password){
        if (jugadorActual!= null) {
            throw new IllegalStateException("Ya existe una sesion iniciada");
        }
        Jugador encontrado=buscarJugador(usuario);
        if (encontrado==null) {
            return false;
        }
        if (encontrado.verificarPassword(password)) {
            jugadorActual=encontrado;
            return true;
        }
        return false;
    }
    
    public Jugador getJugadorActual(){
        return jugadorActual;
    }
     
    public void cerrarSesion(){
        if (jugadorActual==null) {
            throw new IllegalArgumentException("No hay una sesion iniciada");
        }
        jugadorActual=null;
    }
    
    public void registrarPartida(Partida partida){
        if (partida==null) {
            throw new IllegalArgumentException("La partida no puede ser nula");
        }
        if (!partida.isTerminada()) {
            throw new IllegalStateException("La partida no ha terminado");
        }
        for (int i = 0; i < cantidadRegistros; i++) {
            if (historial[i].getPartidaOriginal()==partida) {
                return;
            }
        }
        if (cantidadRegistros>=historial.length) {
            throw new IllegalStateException("El historial esta lleno");
        }
        RegistroPartida registro=new RegistroPartida(partida);
        historial[cantidadRegistros]=registro;
        cantidadRegistros++;
    }
    
    public RegistroPartida[]getHistorial(){
        return historial;
    }
    
    public int getCantidadRegistros(){
        return cantidadRegistros;
    }
    
    
    public RegistroPartida[] obtenerHistorialJugador(Jugador jugador) {
        if (jugador == null) {
            throw new IllegalArgumentException("El jugador no puede ser nulo");
        }
        int cantidad = 0;
        for (int i = 0; i < cantidadRegistros; i++) {
            if (historial[i].getGanador() == jugador ||
                historial[i].getPerdedor() == jugador) {
                cantidad++;
            }
        }
        RegistroPartida[] registros = new RegistroPartida[cantidad];
        int posicion = 0;
        for (int i = cantidadRegistros - 1; i >= 0; i--) {
            if (historial[i].getGanador() == jugador ||
                historial[i].getPerdedor() == jugador) {
                registros[posicion] = historial[i];
                posicion++;
            }
        }
        return registros;
    }
    
    public RegistroPartida[]obtenerMiHistorial(){
        if (jugadorActual==null) {
            throw new IllegalStateException("No hay una sesion iniciada");
        }
        return obtenerHistorialJugador(jugadorActual);
    }
    
    public Jugador[] obtenerRanking() {
        Jugador[] ranking = new Jugador[cantidadJugadores];
        for (int i = 0; i < cantidadJugadores; i++) {
            ranking[i] = jugadores[i];
        }
        for (int i = 0; i < ranking.length - 1; i++) {
            for (int j = 0; j < ranking.length - 1 - i; j++) {
                if (ranking[j].getPuntos() < ranking[j + 1].getPuntos()) {
                    Jugador temporal = ranking[j];
                    ranking[j] = ranking[j + 1];
                    ranking[j + 1] = temporal;
                }
            }
        }
        return ranking;
    }
    
    public int getCantidadJugadores() {
        return cantidadJugadores;
    }
    
    public int getTotalBatallas() {
        return cantidadRegistros;
    }
    
    public int contarVictorias(Bando bando) {
        if (bando == null) {
            throw new IllegalArgumentException("El bando no puede ser nulo");
        }
        int victorias = 0;
        for (int i = 0; i < cantidadRegistros; i++) {
            if (historial[i].getBandoGanador() == bando) {
                victorias++;
            }
        }
        return victorias;
    }
    
    public void cambiarPasswordActual(String actual, String nueva) {
        if (jugadorActual == null) {
            throw new IllegalStateException("No hay una sesión iniciada");
        }
        jugadorActual.cambiarPassword(actual, nueva);
    }
    
    
    public void eliminarCuenta(String password) {
        if (jugadorActual == null) {
            throw new IllegalStateException("No hay una sesión iniciada");
        }
        if (!jugadorActual.verificarPassword(password)) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }
        int posicion = -1;
        for (int i = 0; i < cantidadJugadores; i++) {
            if (jugadores[i] == jugadorActual) {
                posicion = i;
                break;
            }
        }
        if (posicion == -1) {
            throw new IllegalStateException("No se encontró el jugador");
        }
        for (int i = posicion; i < cantidadJugadores - 1; i++) {
            jugadores[i] = jugadores[i + 1];
        }
        cantidadJugadores--;
        jugadores[cantidadJugadores] = null;
        jugadorActual = null;
    }
    
    public void eliminarJugador(String usuario, String password) {
        Jugador jugador = buscarJugador(usuario);
        if (jugador == null || !jugador.verificarPassword(password)) {
            throw new IllegalArgumentException("Usuario o contraseña incorrectos.");
        }
        int posicion = -1;
        for (int i = 0; i < cantidadJugadores; i++) {
            if (jugadores[i] == jugador) {
                posicion = i;
                break;
            }
        }
        if (posicion == -1) {
            throw new IllegalStateException("No se encontró la cuenta.");
        }

        int nuevosRegistros = 0;
        for (int i = 0; i < cantidadRegistros; i++) {
            RegistroPartida registro = historial[i];
            if (registro.getGanador() != jugador&& registro.getPerdedor() != jugador) {
                historial[nuevosRegistros] = registro;
                nuevosRegistros++;
            }
        }

        for (int i = nuevosRegistros; i < cantidadRegistros; i++) {
            historial[i] = null;
        }
        cantidadRegistros = nuevosRegistros;

        for (int i = posicion; i < cantidadJugadores - 1; i++) {
            jugadores[i] = jugadores[i + 1];
        }
        cantidadJugadores--;
        jugadores[cantidadJugadores] = null;

        if (jugadorActual == jugador) {
            jugadorActual = null;
        }
    }
}
