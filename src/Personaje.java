/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author segio
 */
public class Personaje extends Pieza{
    public Personaje(String nombre, int rango, Bando bando){
        super(nombre, rango, bando);
    }
    
    @Override
    public boolean puedeMoverse(){
        return true;
    }
}
