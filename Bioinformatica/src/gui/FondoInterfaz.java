/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Luis Mariano Lovera
 */
public class FondoInterfaz extends JPanel{
    private Image imagen;
    public FondoInterfaz() {
        this.imagen = new ImageIcon(getClass().getResource("/gui/Fondo de pantalla - Interfaz/Imagen, fondo de pantalla.jpg")).getImage();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }
    
}
