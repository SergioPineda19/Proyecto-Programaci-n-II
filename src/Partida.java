/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author segio
 */
public class Partida {
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorHeroes;
    private Jugador jugadorVillanos;
    private Jugador turno;
    private Jugador ganador;
    private EstadoPartida estado;

    public Partida(Jugador jugador1, Jugador jugador2) {
        if (jugador1==null||jugador2==null) {
            throw new IllegalArgumentException("Los jugadores no pueden ser nulos");
        }
        if (jugador1==jugador2) {
            throw new IllegalArgumentException("Un jugador no puede jugar contra si mismo");
        }
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.jugadorHeroes = jugador1;
        this.jugadorVillanos = jugador2;
        this.turno = jugador1; 
        this.ganador = null;
        this.estado = EstadoPartida.EN_CURSO;
        jugadorHeroes.registrarPartida(Bando.HEROE);
        jugadorVillanos.registrarPartida(Bando.VILLANOS);
    }
    
    public void cambiarTurno(){
        if (estado==EstadoPartida.FINALIZADA) {
            throw new IllegalStateException("No se puede cambiar el turno debido a que la partida ya finalizo");
        }
        if (estado==EstadoPartida.EN_CURSO) {
            if (turno==jugador1) {
                turno=jugador2;
            }else{
                turno=jugador1;
            }
        }
    }
    
    public Jugador getTurno(){
        return turno;
    }
    private void finalizarPartida(Jugador ganador){
        if (estado==EstadoPartida.FINALIZADA) {
            throw new IllegalStateException("La partida ya ha finalizado");
        }
        if (ganador!=jugador1&&ganador!=jugador2) {
            throw new IllegalArgumentException("El ganador no pertenece a esta partida");
        }
        this.ganador=ganador;
        estado=EstadoPartida.FINALIZADA;
        ganador.sumarPuntos(100);
    }
    public Jugador getGanador(){
        return ganador;
    }
    public boolean isTerminada(){
        return estado==EstadoPartida.FINALIZADA;
    }
    public Jugador obtenerJugadorBando(Bando bando){
        if (bando==bando.HEROE) {
            return jugadorHeroes;
        }else if(bando==bando.VILLANOS){
            return jugadorVillanos;
        }else{
            return null;
        }
    }
    
    public void capturarTierra(Pieza atacante){
        if (estado==EstadoPartida.EN_CURSO) {
            Jugador ganador = obtenerJugadorBando(atacante.getBando());
        }
        if (ganador!=null) {
            finalizarPartida(ganador);
        }
    }
     
}
