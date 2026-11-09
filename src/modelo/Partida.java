package modelo;
import modelo.Jugador;
import modelo.Tablero;
import piezas.Pieza;
import enums.EstadoPartida;
import enums.Bando;
import enums.MotivoVictoria;
import enums.ResultadoCombate;
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
public class Partida {
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorHeroes;
    private Jugador jugadorVillanos;
    private Jugador turno;
    private Jugador ganador;
    private EstadoPartida estado;
    private MotivoVictoria motivoVictoria;
    private Calendar fechaFinalizacion;
    private SistemaUsuarios sistemaUsuarios;

    public Partida(Jugador jugador1, Jugador jugador2,SistemaUsuarios sistemaUsuarios) {
        this(jugador1, jugador2);
        if (sistemaUsuarios == null) {
            throw new IllegalArgumentException("El sistema de usuarios no puede ser nulo");
        }
        this.sistemaUsuarios = sistemaUsuarios;
    }

    public Partida(Jugador jugador1, Jugador jugador2) {
        if (jugador1 == null || jugador2 == null) {
            throw new IllegalArgumentException("Los jugadores no pueden ser nulos");
        }
        if (jugador1 == jugador2) {
            throw new IllegalArgumentException("Un jugador no puede jugar contra si mismo");
        }

        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        jugadorHeroes = jugador1;
        jugadorVillanos = jugador2;
        turno = jugadorHeroes;
        ganador = null;
        estado = EstadoPartida.EN_CURSO;
        motivoVictoria = null;
        fechaFinalizacion = null;
        sistemaUsuarios = null;
        jugadorHeroes.registrarPartida(Bando.HEROE);
        jugadorVillanos.registrarPartida(Bando.VILLANOS);
    }

    public void cambiarTurno() {
        if (estado == EstadoPartida.FINALIZADA) {
            throw new IllegalStateException("La partida ya finalizo");
        }

        if (turno == jugador1) {
            turno = jugador2;
        } else {
            turno = jugador1;
        }
    }

    public Jugador getTurno() {
        return turno;
    }

    public Jugador getGanador() {
        return ganador;
    }

    public Jugador getPerdedor() {
        if (ganador == null) {
            return null;
        }
        if (ganador == jugadorHeroes) {
            return jugadorVillanos;
        }
        return jugadorHeroes;
    }

    public boolean isTerminada() {
        return estado == EstadoPartida.FINALIZADA;
    }

    public boolean estaEnCurso() {
        return estado == EstadoPartida.EN_CURSO;
    }

    public Jugador obtenerJugadorBando(Bando bando) {
        if (bando == Bando.HEROE) {
            return jugadorHeroes;
        }
        if (bando == Bando.VILLANOS) {
            return jugadorVillanos;
        }
        return null;
    }

    public Bando obtenerBandoTurno() {
        if (turno == jugadorHeroes) {
            return Bando.HEROE;
        }
        return Bando.VILLANOS;
    }

    public void validarTurno(Pieza pieza) {
        if (estado == EstadoPartida.FINALIZADA) {
            throw new IllegalStateException("La partida ya termino");
        }
        if (pieza == null) {
            throw new IllegalArgumentException("No hay ninguna pieza seleccionada");
        }
        if (pieza.getBando() != obtenerBandoTurno()) {
            throw new IllegalArgumentException("No es el turno de esta pieza");
        }
    }

    private void finalizarPartida(Jugador jugadorGanador) {
        if (estado == EstadoPartida.FINALIZADA) {
            throw new IllegalStateException("La partida ya ha finalizado");
        }

        if (jugadorGanador != jugador1&& jugadorGanador != jugador2) {
            throw new IllegalArgumentException("El ganador no pertenece a esta partida");
        }

        if (motivoVictoria != MotivoVictoria.CAPTURAR_TIERRA&& motivoVictoria != MotivoVictoria.ELIMINACION) {
            throw new IllegalStateException("No se ha establecido un motivo valido");
        }
        ganador = jugadorGanador;
        estado = EstadoPartida.FINALIZADA;
        fechaFinalizacion = Calendar.getInstance();
        ganador.sumarPuntos(100);
        registrarResultado();
    }

    private void registrarResultado() {
        if (sistemaUsuarios != null) {
            sistemaUsuarios.registrarPartida(this);
        }
    }

    public void capturarTierra(Pieza atacante) {
        if (estado == EstadoPartida.FINALIZADA) {
            return;
        }
        if (atacante == null) {
            throw new IllegalArgumentException(
                    "El atacante no puede ser nulo");
        }
        Jugador jugadorGanador =obtenerJugadorBando(atacante.getBando());
        if (jugadorGanador != null) {
            motivoVictoria = MotivoVictoria.CAPTURAR_TIERRA;
            finalizarPartida(jugadorGanador);
        }
    }

    public void verificarGanador(Tablero tablero) {
        if (tablero == null) {
            throw new IllegalArgumentException("El tablero no puede ser nulo");
        }
        if (estado == EstadoPartida.FINALIZADA) {
            return;
        }
        Bando bandoGanador = tablero.getBandoGanador();
        if (bandoGanador != null) {
            motivoVictoria = MotivoVictoria.CAPTURAR_TIERRA;
        } else {
            bandoGanador=tablero.verificarVictoriaPorEliminacion();
            if (bandoGanador != null) {
                motivoVictoria = MotivoVictoria.ELIMINACION;
            }
        }
        if (bandoGanador != null) {
            Jugador jugadorGanador =obtenerJugadorBando(bandoGanador);
            finalizarPartida(jugadorGanador);
        }
    }

    public void realizarMovimiento(Tablero tablero,
            int filaOrigen, int columnaOrigen,
            int filaDestino, int columnaDestino) {

        if (tablero == null) {
            throw new IllegalArgumentException("El tablero no puede ser nulo");
        }

        Pieza pieza = tablero.obtenerPieza(filaOrigen, columnaOrigen);
        validarTurno(pieza);
        tablero.moverPieza(filaOrigen, columnaOrigen,filaDestino, columnaDestino);
        cambiarTurno();
    }

    public ResultadoCombate realizarAtaque(Tablero tablero,
            int filaOrigen, int columnaOrigen,
            int filaDestino, int columnaDestino) {
        if (tablero == null) {
            throw new IllegalArgumentException("El tablero no puede ser nulo");
        }

        Pieza atacante = tablero.obtenerPieza(filaOrigen, columnaOrigen);
        validarTurno(atacante);
        ResultadoCombate resultado = tablero.atacarPieza(filaOrigen, columnaOrigen,filaDestino, columnaDestino);
        verificarGanador(tablero);
        if (estado == EstadoPartida.EN_CURSO) {
            cambiarTurno();
        }
        return resultado;
    }

    public void realizarJugada(Tablero tablero,
            int filaOrigen, int columnaOrigen,
            int filaDestino, int columnaDestino) {

        if (tablero == null) {
            throw new IllegalArgumentException("El tablero no puede ser nulo");
        }

        if (tablero.casillaVacia(filaDestino, columnaDestino)) {
            realizarMovimiento(tablero,filaOrigen, columnaOrigen,filaDestino, columnaDestino);
        } else {
            realizarAtaque(tablero,filaOrigen, columnaOrigen,filaDestino, columnaDestino);
        }
    }

    public void rendirse(Jugador jugador) {
        if (estado == EstadoPartida.FINALIZADA) {
            throw new IllegalStateException("La partida ya termino");
        }
        if (jugador != jugadorHeroes&& jugador != jugadorVillanos) {
            throw new IllegalArgumentException("El jugador no existe en esta partida");
        }
        if (jugador == jugadorHeroes) {
            ganador = jugadorVillanos;
        } else {
            ganador = jugadorHeroes;
        }
        motivoVictoria = MotivoVictoria.RENDICION;
        estado = EstadoPartida.FINALIZADA;
        fechaFinalizacion = Calendar.getInstance();
        ganador.sumarPuntos(3);
        registrarResultado();
    }

    public MotivoVictoria getMotivoVictoria() {
        return motivoVictoria;
    }
    public Calendar getFechaFinalizacion() {
        if (fechaFinalizacion == null) {
            return null;
        }
        return (Calendar) fechaFinalizacion.clone();
    }
}
