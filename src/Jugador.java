/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author segio
 */
public class Jugador {
    //Atributos
    private String username;
    private String password;
    private int puntos;
    private int partidasHeroes;
    private int partidasVillanos;

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getPuntos() {
        return puntos;
    }
    public int getPartidasHeroes() {
        return partidasHeroes;
    }
    public int getPartidasVillanos() {
        return partidasVillanos;
    }
    //Constructor
    public Jugador(String username, String password) {
        this.username = username;
        this.password = password;
        this.puntos = 0;
        this.partidasHeroes = 0;
        this.partidasVillanos = 0;
    }
    
    
    
    
    
}
