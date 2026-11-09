/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piezas;

/**
 *
 * @author segio
 */
import enums.Bando;
public class GeneradorPiezas {
    public static Pieza[] crearHeroes() {

        Pieza[] piezas = new Pieza[40];

        piezas[0] = new Personaje("Mr. Fantastic", 10, Bando.HEROE);
        piezas[1] = new Personaje("Captain America", 9, Bando.HEROE);
        piezas[2] = new Personaje("Professor X", 8, Bando.HEROE);
        piezas[3] = new Personaje("Nick Fury", 8, Bando.HEROE);
        piezas[4] = new Personaje("Spider-Man", 7, Bando.HEROE);
        piezas[5] = new Personaje("Wolverine", 7, Bando.HEROE);
        piezas[6] = new Personaje("Namor", 7, Bando.HEROE);
        piezas[7] = new Personaje("Daredevil", 6, Bando.HEROE);

        piezas[8] = new Personaje("Silver Surfer", 6, Bando.HEROE);
        piezas[9] = new Personaje("Hulk", 6, Bando.HEROE);
        piezas[10] = new Personaje("Iron Man", 6, Bando.HEROE);
        piezas[11] = new Personaje("Thor", 5, Bando.HEROE);
        piezas[12] = new Personaje("Human Torch", 5, Bando.HEROE);
        piezas[13] = new Personaje("Cyclops", 5, Bando.HEROE);
        piezas[14] = new Personaje("Invisible Woman", 5, Bando.HEROE);
        piezas[15] = new Personaje("Ghost Rider", 4, Bando.HEROE);

        piezas[16] = new Personaje("Punisher", 4, Bando.HEROE);
        piezas[17] = new Personaje("Blade", 4, Bando.HEROE);
        piezas[18] = new Personaje("Thing", 4, Bando.HEROE);
        piezas[19] = new Personaje("Emma Frost", 3, Bando.HEROE);
        piezas[20] = new Personaje("She-Hulk", 3, Bando.HEROE);
        piezas[21] = new Personaje("Giant Man", 3, Bando.HEROE);
        piezas[22] = new Personaje("Beast", 3, Bando.HEROE);
        piezas[23] = new Personaje("Colossus", 3, Bando.HEROE);

        piezas[24] = new Personaje("Gambit", 2, Bando.HEROE);
        piezas[25] = new Personaje("Spider-Girl", 2, Bando.HEROE);
        piezas[26] = new Personaje("Ice Man", 2, Bando.HEROE);
        piezas[27] = new Personaje("Storm", 2, Bando.HEROE);
        piezas[28] = new Personaje("Phoenix", 2, Bando.HEROE);
        piezas[29] = new Personaje("Dr. Strange", 2, Bando.HEROE);
        piezas[30] = new Personaje("Elektra", 2, Bando.HEROE);
        piezas[31] = new Personaje("Nightcrawler", 2, Bando.HEROE);

        piezas[32] = new Personaje("Black Widow", 1, Bando.HEROE);

        for (int i = 33; i <= 38; i++) {
            piezas[i] = new Bomba("Nova Blast", Bando.HEROE);
        }

        piezas[39] = new Tierra(Bando.HEROE);

        return piezas;
    }

    public static Pieza[] crearVillanos() {

        Pieza[] piezas = new Pieza[40];

        piezas[0] = new Personaje("Dr. Doom", 10, Bando.VILLANOS);
        piezas[1] = new Personaje("Galactus", 9, Bando.VILLANOS);
        piezas[2] = new Personaje("Kingpin", 8, Bando.VILLANOS);
        piezas[3] = new Personaje("Magneto", 8, Bando.VILLANOS);
        piezas[4] = new Personaje("Apocalypse", 7, Bando.VILLANOS);
        piezas[5] = new Personaje("Green Goblin", 7, Bando.VILLANOS);
        piezas[6] = new Personaje("Venom", 7, Bando.VILLANOS);
        piezas[7] = new Personaje("Bullseye", 6, Bando.VILLANOS);

        piezas[8] = new Personaje("Omega Red", 6, Bando.VILLANOS);
        piezas[9] = new Personaje("Onslaught", 6, Bando.VILLANOS);
        piezas[10] = new Personaje("Red Skull", 6, Bando.VILLANOS);
        piezas[11] = new Personaje("Mystique", 5, Bando.VILLANOS);
        piezas[12] = new Personaje("Mysterio", 5, Bando.VILLANOS);
        piezas[13] = new Personaje("Dr. Octopus", 5, Bando.VILLANOS);
        piezas[14] = new Personaje("Deadpool", 5, Bando.VILLANOS);
        piezas[15] = new Personaje("Abomination", 4, Bando.VILLANOS);

        piezas[16] = new Personaje("Thanos", 4, Bando.VILLANOS);
        piezas[17] = new Personaje("Black Cat", 4, Bando.VILLANOS);
        piezas[18] = new Personaje("Sabretooth", 4, Bando.VILLANOS);
        piezas[19] = new Personaje("Juggernaut", 3, Bando.VILLANOS);
        piezas[20] = new Personaje("Rhino", 3, Bando.VILLANOS);
        piezas[21] = new Personaje("Carnage", 3, Bando.VILLANOS);
        piezas[22] = new Personaje("Mole Man", 3, Bando.VILLANOS);
        piezas[23] = new Personaje("Lizard", 3, Bando.VILLANOS);

        piezas[24] = new Personaje("Mr. Sinister", 2, Bando.VILLANOS);
        piezas[25] = new Personaje("Sentinel 1", 2, Bando.VILLANOS);
        piezas[26] = new Personaje("Ultron", 2, Bando.VILLANOS);
        piezas[27] = new Personaje("Sandman", 2, Bando.VILLANOS);
        piezas[28] = new Personaje("Leader", 2, Bando.VILLANOS);
        piezas[29] = new Personaje("Viper", 2, Bando.VILLANOS);
        piezas[30] = new Personaje("Sentinel 2", 2, Bando.VILLANOS);
        piezas[31] = new Personaje("Electro", 2, Bando.VILLANOS);

        piezas[32] = new Personaje("Black Widow", 1, Bando.VILLANOS);

        for (int i = 33; i <= 38; i++) {
            piezas[i] = new Bomba("Pumpkin Bomb", Bando.VILLANOS);
        }

        piezas[39] = new Tierra(Bando.VILLANOS);

        return piezas;
    }

}
