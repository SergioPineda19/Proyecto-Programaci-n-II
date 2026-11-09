/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author segio
 */
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
public class PanelFondo extends JPanel{
     private Image imagen;
    public PanelFondo() {
        java.net.URL ruta = getClass().getResource(
                "/imagenes/fondos/ciudad.png");
        if (ruta != null) {
            imagen = new ImageIcon(ruta).getImage();
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imagen != null) {
            g.drawImage(imagen, 0, 0,
                    getWidth(), getHeight(), this);
        }
    }
}
