/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 * Clase utilitaria para traducir tripletas de ADN a su respectivo aminoácido.
 * Usa una tabla estática para hacer la traducción rápida.
 * Ejemplo de uso: TraductorAminoacidos.traducir("ATG") devuelve "Metionina (Inicio)".
 *
 * @author Luis Peña
 */
public class TraductorAminoacidos {
    // Tabla de traducción: índice = (base1*16) + (base2*4) + base3
    private static final String[] AMINO_ACIDS = new String[64];
    
    static {
        // Inicializar tabla estática de traducción
        // Primera base: U (T en ADN)
        asignar("TTT", "Fenilalanina");
        asignar("TTC", "Fenilalanina");
        asignar("TTA", "Leucina");
        asignar("TTG", "Leucina");
        asignar("TCT", "Serina");
        asignar("TCC", "Serina");
        asignar("TCA", "Serina");
        asignar("TCG", "Serina");
        asignar("TAT", "Tirosina");
        asignar("TAC", "Tirosina");
        asignar("TAA", "STOP (Ocre)");
        asignar("TAG", "STOP (Ámbar)");
        asignar("TGT", "Cisteína");
        asignar("TGC", "Cisteína");
        asignar("TGA", "STOP (Ópalo)");
        asignar("TGG", "Triptófano");
        // Segunda base: C
        asignar("CTT", "Leucina");
        asignar("CTC", "Leucina");
        asignar("CTA", "Leucina");
        asignar("CTG", "Leucina");
        asignar("CCT", "Prolina");
        asignar("CCC", "Prolina");
        asignar("CCA", "Prolina");
        asignar("CCG", "Prolina");
        asignar("CAT", "Histidina");
        asignar("CAC", "Histidina");
        asignar("CAA", "Glutamina");
        asignar("CAG", "Glutamina");
        asignar("CGT", "Arginina");
        asignar("CGC", "Arginina");
        asignar("CGA", "Arginina");
        asignar("CGG", "Arginina");
        // Segunda base: A
        asignar("ATT", "Isoleucina");
        asignar("ATC", "Isoleucina");
        asignar("ATA", "Isoleucina");
        asignar("ATG", "Metionina (Inicio)");  // Inicio
        asignar("ACT", "Treonina");
        asignar("ACC", "Treonina");
        asignar("ACA", "Treonina");
        asignar("ACG", "Treonina");
        asignar("AAT", "Asparagina");
        asignar("AAC", "Asparagina");
        asignar("AAA", "Lisina");
        asignar("AAG", "Lisina");
        asignar("AGT", "Serina");
        asignar("AGC", "Serina");
        asignar("AGA", "Arginina");
        asignar("AGG", "Arginina");
        // Segunda base: G
        asignar("GTT", "Valina");
        asignar("GTC", "Valina");
        asignar("GTA", "Valina");
        asignar("GTG", "Valina");
        asignar("GCT", "Alanina");
        asignar("GCC", "Alanina");
        asignar("GCA", "Alanina");
        asignar("GCG", "Alanina");
        asignar("GAT", "Ácido Aspártico");
        asignar("GAC", "Ácido Aspártico");
        asignar("GAA", "Ácido Glutámico");
        asignar("GAG", "Ácido Glutámico");
        asignar("GGT", "Glicina");
        asignar("GGC", "Glicina");
        asignar("GGA", "Glicina");
        asignar("GGG", "Glicina");
    }
    
    /**
     * Asigna un aminoácido a una tripleta específica en la tabla.
     * 
     * @param adnTriplet Tripleta de ADN (String, por ejemplo "ATG").
     * @param aminoAcid Nombre del aminoácido correspondiente.
     */
    private static void asignar(String adnTriplet, String aminoAcid) {
        int index = calcularIndice(adnTriplet);
        if (index >= 0 && index < 64) {
            AMINO_ACIDS[index] = aminoAcid;
        }
    }
    
    /**
     * Calcula el índice para una tripleta de ADN en la tabla de aminoácidos.
     * Convierte cada base (A, C, G, T) a un número entre 0 y 3.
     * 
     * @param adnTriplet Tripleta de ADN a traducir.
     * @return Índice (0-63) correspondiente a esa tripleta, o -1 si es inválida.
     */
    private static int calcularIndice(String adnTriplet) {
        char base0 = adnTriplet.charAt(0);
        char base1 = adnTriplet.charAt(1);
        char base2 = adnTriplet.charAt(2);

        return baseToInt(base0) * 16 + 
               baseToInt(base1) * 4 + 
               baseToInt(base2);
    }
    
    /**
     * Convierte una base (A, C, G, T) en un valor numérico.
     * 
     * @param base Carácter de base.
     * @return 0 para A, 1 para C, 2 para G, 3 para T, -1 si la base es inválida.
     */
    private static int baseToInt(char base) {
        switch(base) {
            case 'A': return 0;
            case 'C': return 1;
            case 'G': return 2;
            case 'T': return 3;
            default: return -1;
        }
    }
    
    /**
     * Traduce una tripleta de ADN a su correspondiente aminoácido.
     * 
     * @param adnTriplet Tripleta de ADN (ejemplo: "TGG").
     * @return Nombre del aminoácido, o "Tripleta inválida" si la entrada no es válida.
     */
    public static String traducir(String adnTriplet) {
        int index = calcularIndice(adnTriplet);
        return index >= 0 && index < 64 ? AMINO_ACIDS[index] : "Tripleta inválida";
    }
}

