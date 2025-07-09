/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JOptionPane;

/**
 *
 * Clase encargada de leer, validar y procesar el archivo de ADN.
 * Permite cargar la secuencia, construir la tabla hash y el árbol AVL,
 * y generar reportes de aminoácidos.
 * 
 * @author Luis Mariano Lovera, Luis Peña
 */
public class ProcesadorArchivo {
    private String secuenciaADN;

    /**
     * Constructor que inicializa el procesador con la secuencia vacía.
     */
    public ProcesadorArchivo() {
        this.secuenciaADN = "";
    }
    
    /**
     * Lee el archivo seleccionado y guarda la secuencia de ADN.
     * Valida que solo contenga caracteres A, C, G, T.
     *
     * @param archivoSeleccionado Archivo a leer.
     * @return true si el archivo es válido y se pudo cargar, false en caso contrario.
     */
    public boolean leerArchivo(File archivoSeleccionado){
        secuenciaADN="";
        try{
            BufferedReader br = new BufferedReader(new FileReader(archivoSeleccionado));
            String linea;
            
            while ((linea=br.readLine())!=null){
                linea=linea.trim().toUpperCase();
           
                if (!linea.isEmpty()){
                    for (int i = 0; i < linea.length(); i++) {
                        char letra= linea.charAt(i);
                        if (letra!='A' && letra!='C' && letra!='G' && letra!='T') {
                            JOptionPane.showMessageDialog(null, "El archivo no se puede procesar. \nCaracter inválido detectado: "+letra,"Error",JOptionPane.ERROR_MESSAGE);
                            br.close();
                            return false;
                        }
                    }
                    secuenciaADN+=linea;
                }
            }
            br.close();
            return true;
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error leyendo el archivo: " +e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            return false;   
        }
    }
    
    /**
     * Procesa la secuencia y la divide en tripletas.
     * Cada tripleta se inserta en la tabla hash con su posición inicial.
     * Si la secuencia tiene caracteres sobrantes al final, los ignora y muestra advertencia.
     *
     * @param tabla Hashtable donde se almacenarán las tripletas y posiciones.
     */
    public void construirTabla(Hashtable tabla){
        for (int i = 0; i < secuenciaADN.length(); i += 3) {
            if (i+3 <=secuenciaADN.length()){
                String fragmento = secuenciaADN.substring(i, i + 3);
                tabla.insertar(fragmento, i);     
            }else{
                int sobrantes=secuenciaADN.length()-i;
                JOptionPane.showMessageDialog(null,"La secuencia tiene " + sobrantes +" caracter(es) al final que no forman una tripleta y serán ignorados.","Advertencia", JOptionPane.WARNING_MESSAGE);
                break;
            }
        }
    }
    
    /**
     * Llena el árbol AVL con los patrones y posiciones extraídos de la tabla hash.
     * Permite ordenar y buscar patrones por frecuencia de manera eficiente.
     *
     * @param tabla Hashtable con los patrones y posiciones.
     * @param arbol Árbol AVL que será llenado con los datos de la tabla.
     */
    public void construirArbol(Hashtable tabla, ArbolBB arbol) {
        ListaSimple<NodoArbol>patrones=tabla.obtenerPatrones();
        NodoSimple<NodoArbol>aux=patrones.getFirst();
        while (aux!=null) {
            String patron=aux.getData().getPatron();
            ListaSimple<Integer>posiciones=aux.getData().getPosiciones();
            arbol.insertar(patron, posiciones);
            aux=aux.getNext();
        }
    }
    
    /**
     * Genera un reporte con la información de los aminoácidos, mostrando
     * para cada uno las tripletas que lo forman y la frecuencia de cada tripleta.
     * Incluye una sección especial para tripletas de inicio, STOP e inválidas.
     *
     * @param tabla Hashtable con los patrones de ADN.
     * @return ListaSimple de Strings con el reporte por aminoácido.
     */
    public ListaSimple<String> generarReporteAminoacidos(Hashtable tabla) {
        ListaSimple<String> reporte = new ListaSimple<>();
        int totalSTOP = 0;
        int totalInicio = 0;
        int totalInvalidos = 0;

        // Mapa personalizado para aminoácidos
        ListaSimple<AminoAcidoInfo> aminoAcidosList = new ListaSimple<>();

        // Recorrer todos los patrones
        ListaSimple<NodoArbol> patrones = tabla.obtenerPatrones();
        NodoSimple<NodoArbol> actual = patrones.getFirst();

        while (actual != null) {
            String tripleta = actual.getData().getPatron();
            String aminoacido = TraductorAminoacidos.traducir(tripleta);
            int frecuencia = actual.getData().getFrecuencias();

            // Contar tipos especiales
            if (aminoacido.startsWith("STOP")) totalSTOP += frecuencia;
            else if (aminoacido.contains("Inicio")) totalInicio += frecuencia;
            else if (aminoacido.equals("Tripleta inválida")) totalInvalidos += frecuencia;

            // Buscar aminoácido en la lista
            AminoAcidoInfo info = buscarAminoAcidoInfo(aminoAcidosList, aminoacido);

            if (info == null) {
                info = new AminoAcidoInfo(aminoacido);
                aminoAcidosList.insertarAlFinal(info);
            }

            info.agregarTripleta(tripleta, frecuencia);
            actual = actual.getNext();
        }

        // Generar reporte principal
        NodoSimple<AminoAcidoInfo> aminoActual = aminoAcidosList.getFirst();
        while (aminoActual != null) {
            AminoAcidoInfo info = aminoActual.getData();
            if (!info.nombre.startsWith("STOP") && !info.nombre.contains("Inicio")) {
                reporte.insertarAlFinal(info.generarLineaReporte());
            }
            aminoActual = aminoActual.getNext();
        }

        // Agregar sección especial
        reporte.insertarAlFinal("\n--- TRIPLETAS ESPECIALES ---");
        reporte.insertarAlFinal("Metionina (Inicio): " + totalInicio + " ocurrencias");
        reporte.insertarAlFinal("STOP (Total): " + totalSTOP + " ocurrencias");
        reporte.insertarAlFinal("Tripletas inválidas: " + totalInvalidos + " ocurrencias");

        return reporte;
    }
    
    /**
     * Busca un aminoácido por su nombre en una lista de AminoAcidoInfo.
     *
     * @param lista Lista de objetos AminoAcidoInfo.
     * @param nombre Nombre del aminoácido a buscar.
     * @return Objeto AminoAcidoInfo correspondiente, o null si no está en la lista.
     */
    private AminoAcidoInfo buscarAminoAcidoInfo(ListaSimple<AminoAcidoInfo> lista, String nombre) {
        NodoSimple<AminoAcidoInfo> actual = lista.getFirst();
        while (actual != null) {
            if (actual.getData().nombre.equals(nombre)) {
                return actual.getData();
            }
            actual = actual.getNext();
        }
        return null;
    }
    
    public String getSecuenciaADN() {
        return secuenciaADN;
    }
           
}
