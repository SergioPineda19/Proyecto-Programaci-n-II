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
    public Tablero(){
        casillas=new Pieza[10][10];
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
}
