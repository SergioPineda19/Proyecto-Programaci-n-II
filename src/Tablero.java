
import piezas.Tierra;
import piezas.Pieza;
import enums.ResultadoCombate;
import enums.Bando;
import enums.Bando;
import piezas.Pieza;
import piezas.GeneradorPiezas;
import java.util.Random;

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
    private Pieza[]piezasVencidasHeroes;
    private Pieza[]piezasVencidasVillanos;
    private int cantidadVencidasHeroes;
    private int cantidadVencidasVillanos;
    private Bando bandoGanador;
    //Constructor
    public Tablero(){
        casillas=new Pieza[10][10];
        piezasVencidasHeroes=new Pieza[40];
        piezasVencidasVillanos=new Pieza[40];
        cantidadVencidasHeroes=0;
        cantidadVencidasVillanos=0;
        bandoGanador=null;
    }
    
    public void colocarPieza(Pieza pieza,int fila,int columna){
        if (fila<0||fila>10||columna<0||columna>10) {
            throw new IllegalArgumentException("Posicion invalida");
        }
        if (esZonaProhibida(fila,columna)) {
            throw new IllegalArgumentException("No se puede colocar una pieza en las zonas prohibidas");
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
        if (esZonaProhibida(filaOrigen,columnaOrigen)||(esZonaProhibida(filaDestino,columnaDestino))) {
            return false;
        }
        int diferenciaFilas=Math.abs(filaDestino-filaOrigen);
        int diferenciaColumnas=Math.abs(columnaDestino-columnaOrigen);
        boolean unaCasilla=(diferenciaFilas==1&&diferenciaColumnas==0)||(diferenciaFilas==0&&diferenciaColumnas==1);
        if (unaCasilla) {
            return true;
        }
        Pieza pieza=casillas[filaOrigen][columnaOrigen];
        if (pieza==null||pieza.getRango()!=2||!pieza.puedeMoverse()) {
            return false;
        }
        boolean lineaRecta=(diferenciaFilas>0&&diferenciaColumnas==0)||(diferenciaFilas==0&&diferenciaColumnas>0);
        if (!lineaRecta) {
            return false;
        }
        return caminoDespejado(filaOrigen,columnaOrigen,filaDestino,columnaDestino);
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
        int diferenciaFilas=Math.abs(filaDestino-filaOrigen);
        int diferenciaColumnas=Math.abs(columnaDestino-columnaOrigen);
        if (pieza.getRango()==2&&(diferenciaFilas>1||diferenciaColumnas>1)) {
            pieza.revelar();
        }
        retirarPieza(filaOrigen,columnaOrigen);
        colocarPieza(pieza,filaDestino,columnaDestino);
    }
    
    public ResultadoCombate atacarPieza(int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino){
        if (!movimientoValido(filaOrigen, columnaOrigen,filaDestino, columnaDestino)) {
            throw new IllegalArgumentException("Ataque inválido");
        }
        int diferenciaFilas = Math.abs(filaDestino - filaOrigen);
        int diferenciaColumnas = Math.abs(columnaDestino - columnaOrigen);
        if (diferenciaFilas + diferenciaColumnas != 1) {
            throw new IllegalArgumentException("Solo se puede atacar a una casilla adyacente");
        }
        Pieza atacante = obtenerPieza(filaOrigen, columnaOrigen);
        Pieza defensor = obtenerPieza(filaDestino, columnaDestino);
        if (atacante == null || defensor == null) {
            throw new IllegalStateException("Deben existir dos piezas para atacar");
        }
        if (!atacante.puedeMoverse()) {
            throw new IllegalStateException("Esta pieza no puede atacar");
        }
        ResultadoCombate resultado = atacante.atacar(defensor);
        if (defensor instanceof Tierra &&
                resultado == ResultadoCombate.GANA_ATACANTE) {
            bandoGanador = atacante.getBando();
        }
        if (resultado == ResultadoCombate.GANA_ATACANTE) {
            registrarPiezaVencida(defensor);
            retirarPieza(filaDestino, columnaDestino);
            retirarPieza(filaOrigen, columnaOrigen);
            colocarPieza(atacante, filaDestino, columnaDestino);
        } else if (resultado == ResultadoCombate.GANA_DEFENSOR) {
            registrarPiezaVencida(atacante);
            retirarPieza(filaOrigen, columnaOrigen);
        } else {
            registrarPiezaVencida(atacante);
            registrarPiezaVencida(defensor);
            retirarPieza(filaOrigen, columnaOrigen);
            retirarPieza(filaDestino, columnaDestino);
        }
        return resultado;
    }
    
    public Bando getBandoGanador(){
        return bandoGanador;
    }
    
    public boolean esZonaProhibida(int fila, int columna){
        return(fila==4||fila==5)&&((columna==2||columna==3)||(columna==6||columna==7));
    }
    //Piezas de rango 2
    public boolean caminoDespejado(int filaOrigen,int columnaOrigen,int filaDestino,int columnaDestino){
        int pasoFila=Integer.compare(filaDestino, filaOrigen);
        int pasoColumna=Integer.compare(columnaDestino, columnaOrigen);
        int fila=filaOrigen+pasoFila;
        int columna=columnaOrigen+pasoColumna;
        while(fila!=filaDestino||columna!=columnaDestino){
            if (!casillaVacia(fila,columna)||esZonaProhibida(fila,columna)) {
                return false;
            }
            fila+=pasoFila;
            columna+=pasoColumna;
        }
        return true;
    }
    
    //Piezas Vencidas
    public void registrarPiezaVencida(Pieza pieza){
        if (pieza==null) {
            throw new IllegalArgumentException("La pieza no puede ser nula");
        }
        if (pieza.getBando()==Bando.HEROE) {
            piezasVencidasHeroes[cantidadVencidasHeroes]=pieza;
            cantidadVencidasHeroes++;
        }else{
            piezasVencidasVillanos[cantidadVencidasVillanos]=pieza;
            cantidadVencidasVillanos++;
        }
    }
    
    //Recursivo
    private int contarPiezasMoviles(Bando bando,int posicion){
        if (posicion==100) {
            return 0;
        }
        int fila=posicion/10;
        int columna=posicion%10;
        Pieza pieza=casillas[fila][columna];
        int cantidad=0;
        if (pieza!=null&&pieza.getBando()==bando&&pieza.puedeMoverse()) {
            cantidad=1;
        }
        return cantidad+contarPiezasMoviles(bando,posicion+1);
    }
    
    public int contarPiezasMoviles(Bando bando){
        if (bando==null) {
            throw new IllegalArgumentException("El bando no puede ser nulo");
        }
        return contarPiezasMoviles(bando,0);
    }
    
    public Bando verificarVictoriaPorEliminacion(){
        int heroes=contarPiezasMoviles(Bando.HEROE);
        int villanos=contarPiezasMoviles(Bando.VILLANOS);
        if (heroes==0&&villanos>0) {
            return Bando.VILLANOS;
        }
        if (villanos==0&&heroes>0) {
            return Bando.HEROE;
        }
        return null;
    }
    
    public void colocarPiezasIniciales() {
        Pieza[] heroes = GeneradorPiezas.crearHeroes();
        Pieza[] villanos = GeneradorPiezas.crearVillanos();
        colocarBando(heroes, Bando.HEROE);
        colocarBando(villanos, Bando.VILLANOS);
    }
    
    
    private void colocarBando(Pieza[] piezas, Bando bando) {
        Random random = new Random();
        int filaFinal;
        if (bando == Bando.HEROE) {
            filaFinal = 9;
        } else {
            filaFinal = 0;
        }
        int columnaTierra = random.nextInt(8) + 1;
        colocarPieza(piezas[39], filaFinal, columnaTierra);
        int filaInterior;
        if (bando == Bando.HEROE) {
            filaInterior = 8;
        } else {
            filaInterior = 1;
        }
        int bombasColocadas = 0;
        int[][] posiciones = {
            {filaFinal, columnaTierra - 1},
            {filaFinal, columnaTierra + 1},
            {filaInterior, columnaTierra},
            {filaInterior, columnaTierra - 1},
            {filaInterior, columnaTierra + 1}
        };

        for (int i = 0; i < posiciones.length; i++) {
            int fila = posiciones[i][0];
            int columna = posiciones[i][1];
            if (columna >= 0 && columna < 10
                    && casillaVacia(fila, columna)) {
                colocarPieza(piezas[33 + bombasColocadas], fila, columna);
                bombasColocadas++;
            }
        }
        while (bombasColocadas < 6) {
            int fila = filaFinal;
            if (random.nextBoolean()) {
                fila = filaInterior;
            }
            int columna = random.nextInt(10);
            if (casillaVacia(fila, columna)) {
                colocarPieza(piezas[33 + bombasColocadas],
                        fila, columna);
                bombasColocadas++;
            }
        }
        int colocadas = 24;
        while (colocadas <= 31) {
            int fila;
            if (bando == Bando.HEROE) {
                fila = random.nextInt(2) + 6;
            } else {
                fila = random.nextInt(2) + 2;
            }
            int columna = random.nextInt(10);
            if (casillaVacia(fila, columna)) {
                colocarPieza(piezas[colocadas], fila, columna);
                colocadas++;
            }
        }
        for (int i = 0; i <= 32; i++) {
            if (i >= 24 && i <= 31) {
                continue;
            }
            boolean colocado = false;
            while (!colocado) {
                int fila;
                if (bando == Bando.HEROE) {
                    fila = random.nextInt(4) + 6;
                } else {
                    fila = random.nextInt(4);
                }
                int columna = random.nextInt(10);
                if (casillaVacia(fila, columna)) {
                    colocarPieza(piezas[i], fila, columna);
                    colocado = true;
                }
            }
        }
    }

    
    


}
