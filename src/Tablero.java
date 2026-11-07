
import enums.ResultadoCombate;
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
public class Tablero {
    private Pieza[][]casillas;
    private Bando bandoGanador;
    //Constructor
    public Tablero(){
        casillas=new Pieza[10][10];
        bandoGanador=null;
    }
    
    public void colocarPieza(Pieza pieza,int fila,int columna){
        if (fila<0||fila>10||columna<0||columna>10) {
            throw new IllegalArgumentException("Posicion invalida");
        }
        if (pieza==null) {
            throw new IllegalArgumentException("La pieza no puede ser nula");
        }
        if (casillas[fila][columna]!=null) {
            throw new IllegalArgumentException("La casilla ya esta ocupada");
        }
        casillas[fila][columna]=pieza;
    }
    
    public Pieza obtenerPieza(int fila,int columna){
        if (fila<0||fila>10||columna<0||columna>10) {
           throw new IllegalArgumentException("Posicion invalida");
        }
        return casillas[fila][columna];
    }
    
    public boolean casillaVacia(int fila, int columna){
       if (fila<0||fila>10||columna<0||columna>10) {
            throw new IllegalArgumentException("Posicion invalida");
        }
       return casillas[fila][columna]==null;
    }
    
    public Pieza retirarPieza(int fila, int columna){
       if (fila<0||fila>10||columna<0||columna>10) {
           throw new IllegalArgumentException("Posicion invalida");
        } 
        if (casillas[fila][columna]==null) {
            throw new IllegalStateException("No hay nada en esa casilla");
        }
        Pieza pieza=casillas[fila][columna];
        casillas[fila][columna]=null;
        return pieza;
    }
    
    public boolean movimientoValido(int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino){
        if (filaOrigen<0||filaOrigen>=10||columnaOrigen<0||columnaOrigen>=10||filaDestino<0||filaDestino>=10||columnaDestino<0||columnaDestino>=10) {
            return false;
        }
        int diferenciaFilas=Math.abs(filaDestino-filaOrigen);
        int diferenciaColumnas=Math.abs(columnaDestino-columnaOrigen);
        return(diferenciaFilas==1&&diferenciaColumnas==0)||(diferenciaFilas==0&&diferenciaColumnas==1);
    }
    
    public void moverPieza(int filaOrigen,int columnaOrigen, int filaDestino, int columnaDestino){
        if (!movimientoValido(filaOrigen,columnaOrigen,filaDestino,columnaDestino)) {
            throw new IllegalArgumentException("Movimiento invalido");
        }
        Pieza pieza=obtenerPieza(filaOrigen,columnaOrigen);
        if (pieza==null) {
            throw new IllegalStateException("No hay una pieza en el origen");
        }
        if (!pieza.puedeMoverse()) {
            throw new IllegalStateException("Esta pieza no puede moverse");
        }
        if (!casillaVacia(filaDestino,columnaDestino)) {
            throw new IllegalStateException("La casilla del destino esta ocupada");
        }
        retirarPieza(filaOrigen,columnaOrigen);
        colocarPieza(pieza,filaDestino,columnaDestino);
    }
    
    public ResultadoCombate atacarPieza(int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino){
        if (!movimientoValido(filaOrigen,columnaOrigen,filaDestino,columnaDestino)) {
            throw new IllegalArgumentException("Ataque invalido");
        }
        Pieza atacante=obtenerPieza(filaOrigen, columnaOrigen);
        Pieza defensor=obtenerPieza(filaDestino, columnaDestino);
        if (atacante==null||defensor==null) {
            throw new IllegalStateException("Deben existir dos piezas para atacar");
        }
        if (!atacante.puedeMoverse()) {
            throw new IllegalStateException("Esta pieza no puede atacar");
        }
        ResultadoCombate resultado=atacante.atacar(defensor);
        //Condicion para ganar la partida
        if (defensor instanceof Tierra&&resultado==ResultadoCombate.GANA_ATACANTE) {
            bandoGanador=atacante.getBando();
        }
        if (resultado==ResultadoCombate.GANA_ATACANTE) {
            retirarPieza(filaDestino, columnaDestino);
            retirarPieza(filaOrigen, columnaOrigen);
            colocarPieza(atacante, filaDestino, columnaDestino);
            
        }else if(resultado==ResultadoCombate.GANA_DEFENSOR){
            retirarPieza(filaOrigen, columnaOrigen);
        }else{
            retirarPieza(filaOrigen,columnaOrigen);
            retirarPieza(filaDestino,columnaDestino);
        }
        return resultado;
    }
    
    public Bando getBandoGanador(){
        return bandoGanador;
    }
}
