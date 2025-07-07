/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/*
 * Implementación de un nodo para el Árbol Binario de Búsqueda.
 * Cada nodo contiene un patrón de ADN (dato) y referencias a sus hijos izquierdo y derecho.
 * Además, almacena una lista de posiciones donde el patrón se encuentra en la secuencia principal de ADN.
 *
 * @author Diego Linares
 */

public class NodoArbol {
    private String patron;
    private ListaSimple<Integer> posiciones; 
    private NodoArbol hijoIzq;
    private NodoArbol hijoDer;
    private int altura;

    /*
     * Constructor para crear un nuevo nodo.
     * @param patron El patrón (secuencia de 3 caracteres de ADN) de este nodo.
     * @param posicion La primera posición (índice) donde se encontró este patrón.
     */
    public NodoArbol(String patron, ListaSimple<Integer> posiciones) {
        this.patron = patron;
        this.posiciones=posiciones;
        this.hijoIzq = null;
        this.hijoDer = null;
        this.altura = 1;
    }

    // Getters y setters
    public String getPatron() {
        return patron;
    }
    
    public void setPatron(String patron) {
        this.patron = patron;
    }

    public ListaSimple<Integer> getPosiciones() {
        return posiciones;
    }
    
    public int getFrecuencias(){
        return posiciones.getTamaño();
    }

    public void insertarPosicion(int posicion) {
        this.posiciones.insertarAlFinal(posicion);
    }

    public NodoArbol getHijoIzq() {
        return hijoIzq;
    }

    public void setHijoIzq(NodoArbol hijoIzq) {
        this.hijoIzq = hijoIzq;
    }

    public NodoArbol getHijoDer() {
        return hijoDer;
    }

    public void setHijoDer(NodoArbol hijoDer) {
        this.hijoDer = hijoDer;
    }
    
    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
    
    
}
