/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 * Implementación de un nodo para listas enlazadas simples.
 * Cada nodo contiene un dato genérico y una referencia al siguiente nodo en la secuencia
 *
 * @param <T> Tipo genérico del dato almacenado en el nodo
 * @author Luis Peña y Diego Linares
 */

public class NodoSimple<T> {
    private T data;
    private NodoSimple<T> next;
    
    
    /**
     * Constructor que crea un nuevo nodo con el dato especificado
     * El siguiente nodo es por defecto null.
     *
     * @param data Dato a almacenar en el nodo 
     */
    public NodoSimple(T data) {
        this.data = data;
        this.next = null;
    }

    /**
    * Getters y setters
    */
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public NodoSimple<T> getNext() {
        return next;
    }

    public void setNext(NodoSimple<T> next) {
        this.next = next;
    }

    
}