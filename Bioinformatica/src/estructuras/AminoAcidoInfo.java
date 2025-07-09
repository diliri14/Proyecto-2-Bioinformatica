/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 * Clase para almacenar información sobre un aminoácido.
 * Guarda el nombre del aminoácido, una lista con las tripletas que lo forman
 * y la frecuencia total de aparición de esas tripletas en la secuencia principal.
 * Se usa para armar el reporte de aminoácidos al final del análisis.
 *
 * @author Luis Peña
 */
public class AminoAcidoInfo {
    String nombre;
    ListaSimple<String> tripletas;
    int frecuenciaTotal;
    
    /**
     * Constructor. Crea un nuevo objeto AminoAcidoInfo con el nombre del aminoácido.
     * La lista de tripletas empieza vacía y la frecuencia total en cero.
     *
     * @param nombre Nombre del aminoácido (por ejemplo, "Fenilalanina").
     */
    public AminoAcidoInfo(String nombre) {
        this.nombre = nombre;
        this.tripletas = new ListaSimple<>();
        this.frecuenciaTotal = 0;
    }
    
     /**
     * Agrega una tripleta al aminoácido y suma su frecuencia.
     *
     * @param tripleta Tripleta que genera este aminoácido.
     * @param frecuencia Cuántas veces aparece esa tripleta.
     */
    public void agregarTripleta(String tripleta, int frecuencia) {
        tripletas.insertarAlFinal(tripleta);
        frecuenciaTotal += frecuencia;
    }
    
    /**
     * Genera líneas de texto con el resumen del aminoácido para el reporte.
     * Incluye el nombre, las tripletas y la frecuencia total.
     *
     * @return Línea formateada para mostrar en el reporte.
     */
    public String generarLineaReporte() {
        String linea = nombre + ":\n";
        linea = linea + "  Tripleta(s): " + tripletas.mostrarLista(", ") + "\n";
        linea = linea + "  Frecuencia Total: " + frecuenciaTotal + "\n";
        return linea;
    } 
}
