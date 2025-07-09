    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;
import gui.Interfaz1;

/**
 * Clase principal del proyecto de bioinformática.
 * Inicia la interfaz gráfica del sistema.
 * 
 * @author Diego Linares, Luis Mariano Lovera, Luis Peña
 */
public class Bioinformatica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Interfaz1 interfaz = new Interfaz1();
        interfaz.setTitle("Sistema de Identificación y Análisis de Secuencias");
        interfaz.setLocationRelativeTo(null);
        interfaz.setVisible(true);
    }
    
}
