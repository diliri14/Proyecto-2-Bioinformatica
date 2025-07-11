/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

 /**
 * Implementación de una lista simplemente enlazada genérica.
 * Permite operaciones básicas como inserción al final, verificación de vacío,
 * vaciado de lista y representación en cadena de caracteres.
 * 
 * @param <T> Tipo genérico de los elementos almacenados en la lista
 * @author Luis Peña, Diego Linares, Luis Mariano Lovera
 */
public class ListaSimple<T> {
    private NodoSimple first;
    private NodoSimple last;
    private int tamaño;
    
    /**
     * Constructor que inicializa una lista vacía.
     */
    public ListaSimple() {
        this.first = null;
        this.last = null;
        this.tamaño = 0;
    }
    
    /**
     * Verifica si la lista está vacía.
     * 
     * @return true si la lista no contiene elementos, false en caso contrario
     */
    public boolean esVacia(){
        return first == null;
    }
    
    /**
     * Vacía la lista, eliminando todos sus elementos.
     */
    public void vaciar(){
        first=null;
        last=null;
        tamaño=0;
    }
    
    /**
     * Inserta un nuevo elemento al final de la lista.
     * 
     * @param newDato Elemento a insertar
     */
    public void insertarAlFinal(T newDato){
        NodoSimple aux = new NodoSimple(newDato);
        
        if(this.esVacia()){
            first = last = aux;
        }else{
            last.setNext(aux);
            last = aux;
        }
        tamaño++;        
    }
    
    /**
     * Genera una representación en cadena de todos los elementos de la lista.
     * Cada elemento se muestra en una línea separada.
     * 
     * @return Cadena con los elementos de la lista separados por saltos de línea
     */
    public String mostrarLista(){
        String cadena="";
        NodoSimple aux=first;
        
        while (aux!=null){
            cadena=cadena+aux.getData()+"\n";
            aux=aux.getNext();
        }
        return cadena;
    } 
    
    /**
     * Muestra la lista usando un separador personalizado entre los elementos.
     *
     * @param separador Cadena que se usa para separar los elementos.
     * @return Cadena con los elementos separados según el separador dado.
     */
    public String mostrarLista(String separador) {
        String cadena = "";
        NodoSimple<T> aux = first;
        while (aux != null) {
            cadena = cadena + aux.getData();
            if (aux.getNext() != null) {
                cadena = cadena + separador;
            }
            aux = aux.getNext();
        }
        return cadena;
    }
    
    /**
     * Muestra la lista de posiciones en formato visual, separando por comas y
     * agregando saltos de línea cada 22 posiciones para mejor legibilidad.
     *
     * @return Cadena con las posiciones separadas y alineadas.
     */
    public String mostrarListaPosiciones(){
        String cadena="";
        NodoSimple aux=first;
        int contador = 0;
        while (aux != null) {
            cadena = cadena + aux.getData();
            aux = aux.getNext();
            contador++;
            if (aux != null) {
                if (contador % 22==0) {
                    //Espacio para que se visualizen las ubicaciones de forma alineada
                    cadena = cadena + ",\n                     ";
                } else {
                    cadena = cadena + ", ";
                }
            }
        }
        return cadena;
    }
    
    /**
     * Muestra patrones y frecuencias en formato de reporte.
     * Cada nodo debe ser de tipo NodoArbol.
     *
     * @return Cadena con el patrón, frecuencia y ubicaciones para cada nodo.
     */
    public String mostrarPatronesFrecuencia(){
        String cadena="";
        NodoSimple aux=first;
        while (aux != null) {
            NodoArbol nodo = (NodoArbol) aux.getData();
            cadena += nodo.getPatron() + " | Frecuencia: " + nodo.getFrecuencias()+ "\nUbicaciones: ";
            NodoSimple auxPos = nodo.getPosiciones().getFirst();
            int contador = 0;
            while (auxPos != null) {
                cadena += auxPos.getData();
                auxPos = auxPos.getNext();
                contador++;
                if (auxPos != null) {
                    if (contador % 10 == 0) {
                        cadena += ",\n             ";
                    } else {
                        cadena += ", ";
                    }
                }
            }
            cadena += "\n"; 
            aux = aux.getNext();
        }
        return cadena;
    }
    
    /**
    * Verifica si un elemento específico existe en la lista.
    * La comparación se realiza usando el método equals() del elemento.
    * 
    * @param dato Elemento a buscar en la lista
    * @return true si el elemento se encuentra en la lista, false en caso contrario.
    */
    public boolean contieneDato(T dato) {
        NodoSimple actual=first;
    
        while (actual!=null) {
            if (actual.getData().equals(dato)) {
                return true;
            }
            actual=actual.getNext();
        }
    return false;
    }   


    /**
    * Getters y setters
    */
    public NodoSimple getFirst() {
        return first;
    }

    public void setFirst(NodoSimple first) {
        this.first = first;
    }

    public NodoSimple getLast() {
        return last;
    }

    public void setLast(NodoSimple last) {
        this.last = last;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }
    
    
}
