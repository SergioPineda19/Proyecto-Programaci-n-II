/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author segio
 */
public class Pieza {
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
    public boolean puedeMoverse(){
        return true;
    }
    
    public String recibirAtaque(Pieza atacante){
        if (this.rango==1 && atacante.getRango()==10) {
            return this.getNombre()+" Elimina a "+atacante.getNombre();
        }
        if (this.rango>atacante.getRango()) {
            return this.getNombre()+" Elimina a "+atacante.getNombre();
        }else if (this.rango<atacante.getRango()) {
            return atacante.getNombre()+" Elimina a "+this.getNombre();
        }else{
            return "Empate, ambas piezas son eliminadas";
        }
    }
    
    public String atacar(Pieza enemiga){
        if (this.bando==enemiga.getBando()) {
            return "No puedes atacar a tus compañeros.";
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
