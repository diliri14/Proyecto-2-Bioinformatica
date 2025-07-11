/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 * Implementación de una hashtable para almacenar patrones de ADN.
 * Utiliza una estrategia de encadenamiento para resolver colisiones, donde cada "cubo" (bucket) de la tabla es una ListaSimple de nodos NodoArbol.
 * La clave hash se genera a partir de patrones de ADN de 3 caracteres (tripleta), asignando cada nucleótido (A, C, G, T) a un valor numérico para crear un índice.
 *
 * @author Diego Linares, Luis Mariano Lovera, Luis Peña
 */
public class Hashtable {
    
    private static final int tamaño = 64; // Tamaño fijo de la hashtable. Optimizado, 3 caracteres con 4 posibiles nucleótidos (4^3=64)
    private ListaSimple<NodoArbol>[] tabla; // Arreglo que representa los cubos del hashtable.
    private ListaSimple<String> listaColisiones;
    
    /**
     * Constructor de la clase Hashtable.
     * Inicializa el arreglo de ListaSimple y crea una nueva ListaSimple vacía para cada cubo de la tabla.
     */
    public Hashtable() {
        tabla = new ListaSimple[tamaño];
        for (int i = 0; i < tamaño; i++) {
            tabla[i] = new ListaSimple<>();
        }
        listaColisiones = new ListaSimple<>();
    }

    /**
     * Obtiene un valor numérico para un carácter de nucleótido de ADN dado.
     * Se utiliza para la función hash, mapeando A, C, G, T a valores de 0 a 3.
     *
     * @param c El carácter del nucleótido (A, C, G, T).
     * @return Un valor entero (0 para A, 1 para C, 2 para G, 3 para T), o -1 si el carácter no es válido.
     */
    private int getValor(char c) {
        switch (c) {
            case 'A': return 0;
            case 'C': return 1;
            case 'G': return 2;
            case 'T': return 3; 
            default: return -1;
        }
    }

    /**
     * Calcula el índice hash para un patrón de ADN de 3 caracteres.
     * Se le asigna cada patrón a un índice único entre 0 y 63, minimizando colisiones.
     * La fórmula se basa en un sistema de base 4: (valor_char1 * 4^2=16) + (valor_char2 * 4^1=4) + (valor_char3 * 4^0=1).
     *
     * @param patron El patrón de ADN de 3 caracteres.
     * @return El índice calculado para el patrón en la tabla hash.
     * @throws IllegalArgumentException Si el patrón es nulo o no tiene 3 caracteres de largo.
     */
    private int calcularIndice(String patron) {
        if (patron == null || patron.length() != 3) {
            throw new IllegalArgumentException("El patrón debe tener 3 caracteres de largo.");
        }
        int indice = 0;

        indice += getValor(patron.charAt(0)) * 16;
        indice += getValor(patron.charAt(1)) * 4;
        indice += getValor(patron.charAt(2)) * 1;
        return indice;
    }

    /**
     * Inserta un patrón de ADN y su posición en la tabla hash.
     * Si el patrón ya existe, agrega la nueva posición a la lista de posiciones del patrón.
     *
     * @param patron El patrón de ADN (String) a insertar.
     * @param posicion La posición (entero) asociada al patrón en la secuencia principal.
     */
    public void insertar(String patron, int posicion) {
        int indice = calcularIndice(patron);
        ListaSimple<NodoArbol> cubo = tabla[indice];

        // Buscar si el patrón ya existe en la ListaSimple de este cubo
        NodoSimple<NodoArbol> aux = cubo.getFirst();
        while (aux != null) {
            if (aux.getData().getPatron().equals(patron)) {
                aux.getData().insertarPosicion(posicion); 
                return;
            }
            aux = aux.getNext();
        }
        ListaSimple<Integer> lista = new ListaSimple<>();
        lista.insertarAlFinal(posicion);
        cubo.insertarAlFinal(new NodoArbol(patron, lista));
    }

    /**
     * Obtiene el NodoArbol asociado a un patrón de ADN específico.
     * Primero calcula su índice y luego busca el patrón dentro de la ListaSimple del cubo correspondiente.
     *
     * @param patron El patrón de ADN (String) a buscar.
     * @return El NodoArbol que contiene el patrón y sus posiciones (o null si el patrón no se encuentra).
     */
    public NodoArbol buscar(String patron) {
        int indice = calcularIndice(patron);
        ListaSimple<NodoArbol> cubo = tabla[indice];

        NodoSimple<NodoArbol> aux = cubo.getFirst();
        while (aux != null) {
            if (aux.getData().getPatron().equals(patron)) {
                return aux.getData();
            }
            aux = aux.getNext();
        }
        return null;
    }

    /**
     * Recupera todos los NodoArboles (patrones de ADN con sus posiciones) almacenados en la hashtable.
     * Itera sobre todos los cubos de la tabla y recopila todos los patrones presentes.
     *
     * @return Una ListaSimple que contiene todos los patrones almacenados.
     */
    public ListaSimple<NodoArbol> obtenerPatrones() {
        ListaSimple<NodoArbol> patrones = new ListaSimple<>();
        for (int i = 0; i < tamaño; i++) {
            ListaSimple<NodoArbol> cubo = tabla[i];
            NodoSimple<NodoArbol> aux = cubo.getFirst();
            while (aux != null) {
                patrones.insertarAlFinal(aux.getData());
                aux = aux.getNext();
            }
        }
        return patrones;
    }
    
    /**
     * Recupera todos los patrones almacenados en la tabla hash y los devuelve en una lista ordenada
     * por el primer carácter de cada patrón.
     *
     * @return Una ListaSimple con los patrones ordenados alfabéticamente por su primer carácter.
     */
    public ListaSimple<String> obtenerTodosPatronesOrdenados() {
        ListaSimple<String> patrones = new ListaSimple<>();

        for (int i = 0; i < tamaño; i++) {
            ListaSimple<NodoArbol> cubo = tabla[i];
            NodoSimple<NodoArbol> actual = cubo.getFirst();

            while (actual != null) {
                patrones.insertarAlFinal(actual.getData().getPatron());
                actual = actual.getNext();
            }
        }

        // Ordenar por primer carácter
        return ordenarPorPrimerCaracter(patrones);
    }
    
    /**
     * Ordena una lista de patrones por el primer carácter de cada uno usando el método de burbuja.
     * Este método es privado y solo se usa internamente.
     *
     * @param lista La lista de patrones a ordenar.
     * @return Una nueva ListaSimple con los patrones ordenados por su primer carácter.
     */
    private ListaSimple<String> ordenarPorPrimerCaracter(ListaSimple<String> lista) {
        if (lista.getFirst() == null || lista.getFirst().getNext() == null) {
            return lista;
        }

        // Convertir a array para ordenar
        int size = lista.getTamaño();
        String[] array = new String[size];
        NodoSimple<String> actual = lista.getFirst();

        for (int i = 0; i < size; i++) {
            array[i] = actual.getData();
            actual = actual.getNext();
        }

        // Ordenar por primer carácter (burbuja simple)
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (array[j].charAt(0) > array[j + 1].charAt(0)) {
                    String temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }

        // Reconstruir lista ordenada
        ListaSimple<String> ordenada = new ListaSimple<>();
        for (String s : array) {
            ordenada.insertarAlFinal(s);
        }

        return ordenada;
    }
    
    /**
     * Genera un reporte de colisiones encontradas en la tabla hash.
     * Recorre cada cubo y, si hay más de un patrón almacenado en el mismo índice, 
     * lo reporta como una colisión.
     *
     * @return Un String con el reporte de colisiones encontradas, o un mensaje indicando que no hubo colisiones.
     */
    public String reporteColisiones(){
        for (int i = 0; i < tamaño; i++) {
            ListaSimple<NodoArbol> cubo = tabla[i];
            if (cubo.getTamaño() > 1) {
                String cadena="Colisión en cubo N. " + i + ": ";
                NodoSimple<NodoArbol> aux = cubo.getFirst();
                while (aux!=null) {
                    cadena+=aux.getData().getPatron();
                    if (aux.getNext()!=null) {
                        cadena+= ", ";
                    }
                    aux = aux.getNext();
                }
                listaColisiones.insertarAlFinal(cadena);
            }
        }
        if (listaColisiones.esVacia()){
            return "No se generaron colisiones.";
        }
        return listaColisiones.mostrarLista();
    }

    
}
