/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author Peña
 */
public class AminoAcidoInfo {
    String nombre;
    ListaSimple<String> tripletas;
    int frecuenciaTotal;
    
    public AminoAcidoInfo(String nombre) {
        this.nombre = nombre;
        this.tripletas = new ListaSimple<>();
        this.frecuenciaTotal = 0;
    }
    
    public void agregarTripleta(String tripleta, int frecuencia) {
        tripletas.insertarAlFinal(tripleta);
        frecuenciaTotal += frecuencia;
    }
    
    public String generarLineaReporte() {
        String linea = nombre + ":\n";
        linea = linea + "  Tripleta(s): " + tripletas.mostrarLista(", ") + "\n";
        linea = linea + "  Frecuencia Total: " + frecuenciaTotal + "\n";
        return linea;
    } 
}
