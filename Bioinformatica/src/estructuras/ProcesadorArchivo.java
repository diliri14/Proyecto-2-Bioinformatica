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
 * @author Luis Lovera
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
    
    public String getSecuenciaADN() {
        return secuenciaADN;
    }
           
}
