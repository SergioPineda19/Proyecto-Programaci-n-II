package piezas;


import piezas.Pieza;
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
public class Tierra extends Pieza {
    public Tierra(Bando bando){
        super("Tierra",0,bando);
    }
    @Override
    public boolean puedeMoverse(){
        return false;
    }
    @Override
    public ResultadoCombate recibirAtaque(Pieza atacante){
        return ResultadoCombate.GANA_ATACANTE;
    }
}
