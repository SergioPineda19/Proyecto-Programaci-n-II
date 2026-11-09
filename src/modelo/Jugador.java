package modelo;


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
        
        if (username==null||username.trim().isEmpty()) {
            throw new IllegalArgumentException("El usuario no puede estar vacio");
        }
        if (password==null||password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacia");
        }
    }
    
    public boolean verificarPassword(String password){
        return this.password.equals(password);   
    }
    
    public void sumarPuntos(int cantidad){
        if (cantidad<=0) {
            throw new IllegalArgumentException("Los puntos deben ser mayores que cero");
        }
        puntos+=cantidad;
    }
    
    public void registrarPartida(Bando bando){
        if (bando==null) {
            throw new IllegalArgumentException("El bando no puede estar vacio");
        }
        if (bando==Bando.HEROE) {
            partidasHeroes++;
        }else{
            partidasVillanos++;
        }
    }
        
    public void cambiarPassword(String passwordActual, String passwordNueva) {
        if (!verificarPassword(passwordActual)) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta");
        }
        if (passwordNueva == null || passwordNueva.length() != 5) {
            throw new IllegalArgumentException("La nueva contraseña debe tener 5 caracteres");
        }
        this.password = passwordNueva;
    }

   
}
