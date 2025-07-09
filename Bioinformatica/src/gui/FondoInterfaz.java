/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import javax.swing.*;
import java.awt.*;

/**
 * Panel personalizado que dibuja una imagen de fondo en la interfaz.
 * Carga la imagen desde la carpeta de recursos y la ajusta al tamaño del panel.
 *
 * @author Luis Mariano Lovera
 */
public class FondoInterfaz extends JPanel{
    private Image imagen;
    
    /**
     * Constructor. Carga la imagen de fondo desde los recursos del proyecto.
     */
    public FondoInterfaz() {
        this.imagen = new ImageIcon(getClass().getResource("/gui/Fondo de pantalla - Interfaz/Imagen, fondo de pantalla.jpg")).getImage();
    }
    
    /**
     * Dibuja la imagen de fondo escalada al tamaño del panel cada vez que se repinta.
     *
     * @param g Objeto Graphics usado para dibujar la imagen.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }
    
}
