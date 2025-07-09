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
 * @author Luis Lovera, Peña
 */
public class ProcesadorArchivo {
    private String secuenciaADN;

    public ProcesadorArchivo() {
        this.secuenciaADN = "";
    }
    
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
