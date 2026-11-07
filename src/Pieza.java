
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
public abstract class Pieza {
    //Atributos
    private String nombre;
    private int rango;
    private Bando bando;
    private boolean revelada;
    
    public String getNombre() {
        return nombre;
    }
    public int getRango() {
        return rango;
    }
    public Bando getBando() {
        return bando;
    }
    public boolean isRevelada() {
        return revelada;
    }
    public void revelar(){
        revelada=true;
    }
    public void ocultar(){
        revelada=false;
    }
    public abstract boolean puedeMoverse();
        
    
    public ResultadoCombate recibirAtaque(Pieza atacante){
        if (atacante.rango==1 && this.getRango()==10) {
            return ResultadoCombate.GANA_ATACANTE;
        }
        if (atacante.getRango()>this.rango) {
            return ResultadoCombate.GANA_ATACANTE;
        }else if (atacante.getRango()<this.rango) {
            return ResultadoCombate.GANA_DEFENSOR;
        }else{
            return ResultadoCombate.EMPATE;
        }
    }
    
    public ResultadoCombate atacar(Pieza enemiga){
        if (enemiga==null) {
            throw new IllegalArgumentException("El enemigo no puede ser nulo");
        }
        if (this.bando==enemiga.getBando()) {
            throw new IllegalArgumentException("No puedes atacar a tus compañeros");
        }
        return enemiga.recibirAtaque(this);
    }
    
    //Constructor
    public Pieza(String nombre, int rango, Bando bando) {
        this.nombre = nombre;
        this.rango = rango;
        this.bando = bando;
        this.revelada = false;
    } 
    
}
