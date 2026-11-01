/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author segio
 */
public class Bomba extends Pieza{
    public Bomba(String nombre, Bando bando){
        super(nombre, 0, bando);
    }
    @Override
    public boolean puedeMoverse(){
        return false;
    }
    
    @Override
    public String recibirAtaque(Pieza atacante){
        if (atacante.getRango()==3) {
            return atacante.getNombre()+"Elimina a "+this.getNombre();
        }else{
            return this.getNombre()+"Elimina a "+atacante.getNombre();
        }
    }
}
