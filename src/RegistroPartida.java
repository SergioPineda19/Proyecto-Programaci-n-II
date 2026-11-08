
import enums.Bando;
import enums.MotivoVictoria;
import java.util.Calendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author segio
 */
public class RegistroPartida {
    private Jugador perdedor;
    private Jugador ganador;
    private Bando bandoGanador;
    private Bando bandoPerdedor;
    private MotivoVictoria motivo;
    private Calendar fecha;
    private Partida partidaOriginal;
    
    public RegistroPartida(Partida partida){
        if (partida==null) {
            throw new IllegalArgumentException("La partida no puede ser nula");
        }
        if (!partida.isTerminada()) {
            throw new IllegalStateException("La partida todavia no ha terminado");
        }
        ganador=partida.getGanador();
        perdedor=partida.getPerdedor();
        if (ganador==partida.obtenerJugadorBando(Bando.HEROE)) {
            bandoGanador=Bando.HEROE;
            bandoPerdedor=Bando.VILLANOS;
        }else{
            bandoGanador=Bando.VILLANOS;
            bandoPerdedor=Bando.HEROE;
        }
        motivo=partida.getMotivoVictoria();
        fecha=partida.getFechaFinalizacion();
        this.partidaOriginal = partida;
    }
    
    
    public Jugador getGanador() {
        return ganador;
    }

    public Jugador getPerdedor() {
        return perdedor;
    }

    public Bando getBandoGanador() {
        return bandoGanador;
    }

    public Bando getBandoPerdedor() {
        return bandoPerdedor;
    }

    public MotivoVictoria getMotivo() {
        return motivo;
    }

    public Calendar getFecha() {
        return (Calendar) fecha.clone();
    }
    
    public Partida getPartidaOriginal() {
        return partidaOriginal;
    }
    
    public boolean ganoJugador(Jugador jugador){
        if (jugador==null) {
            throw new IllegalArgumentException("El jugador no puede ser nulo");
        }
        return ganador==jugador;
    }
    
    public String obtenerDescripcion() {
        String descripcion = "Ganador: " + ganador.getUsername();
        descripcion += " | Perdedor: " + perdedor.getUsername();
        descripcion += " | Bando ganador: " + bandoGanador;
        descripcion += " | Motivo: " + motivo;
        return descripcion;
    }



}
