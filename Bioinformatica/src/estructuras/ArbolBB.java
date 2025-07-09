package estructuras;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Implementación de un Árbol Binario de Búsqueda que se auto-balancea, es decir un Árbol AVL.
 * El árbol almacena, en cada nodo, un patrón de ADN (String) junto a una lista de posiciones (ListaSimple<Integer>) 
 * donde ese patrón aparece en la secuencia principal.
 * 
 * El criterio de orden del árbol es la frecuencia (cantidad de posiciones) del patrón. 
 * En caso de empate de frecuencias, el patrón se compara alfabéticamente.
 *
 * Proporciona métodos para insertar patrones, buscar patrones con mayor o menor frecuencia,
 * y recorrer el árbol en orden (inorden), entre otros.
 *
 * @author Diego Linares, Luis Mariano Lovera
 */
public class ArbolBB {
    private NodoArbol raiz;
    
    /**
     * Constructor que inicializa un árbol vacío.
     */
    public ArbolBB() {
        this.raiz = null;
    }
    
    /**
     * Verifica si el árbol está vacío.
     * 
     * @return true si el árbol no contiene raíz, false en caso contrario
     */
    public boolean esVacio() {
        return raiz == null;
    }
    
    /**
     * Obtiene la altura de un nodo específico.
     * Si es nulo se considera como 0.
     *
     * @param nodo El nodo del cual se desea obtener la altura.
     * @return La altura del nodo especificado, o 0 si el nodo es nulo.
     */
    private int altura(NodoArbol nodo) {
        if (nodo == null) {
            return 0;
        }
        else {
            return nodo.getAltura();
        }
    }
    
    /**
     * Actualiza la altura de un nodo basándose en la altura de sus hijos.
     * Esto con el objetivo de mantener el árbol con estructura AVL.
     * Llamado después de que se cambie la estrucutra del árbol a través de inserciones o rotaciones. 
     *
     * @param true El nodo que debe actualizar su altura.
     */
    private void actualizarAltura(NodoArbol nodo) {
        if (nodo != null) {
            nodo.setAltura(1 + Math.max(altura(nodo.getHijoIzq()), altura(nodo.getHijoDer())));
        }
    }
    
    /**
     * Calcula la diferencia entre la altura de ambos hijos de un nodo.
     * Un nodo está balanceado si Balance() se encuentra entre -1 y 1.
     *
     * @param nodo El nodo de cual se desea conocer su balance.
     * @return el factor de balanceo del nodo (entero).
     */
    private int Balance(NodoArbol nodo) {
        if (nodo == null) {
            return 0;
        }
        return altura(nodo.getHijoIzq()) - altura(nodo.getHijoDer());
    }
    
    /**
     * Realiza una rotación simple a la derecha.
     * Esta rotación se aplica cuando hay un desbalance en el subárbol izquierdo del hijo izquierdo (caso LL).
     * El nodo 'y' es la raíz del subárbol desbalanceado.
     *
     * @param y El nodo raíz del subárbol a rotar (el nodo desbalanceado).
     * @return La nueva raíz del subárbol después de la rotación.
     */
    private NodoArbol rotarDerecha(NodoArbol y) {
        if (y==null || y.getHijoIzq()==null) {
            return y;
        }
        
        NodoArbol x = y.getHijoIzq();
        NodoArbol T2 = x.getHijoDer();

        x.setHijoDer(y);
        y.setHijoIzq(T2);

        actualizarAltura(y);
        actualizarAltura(x);

        return x;
    }

    /**
     * Realiza una rotación simple a la izquierda.
     * Esta rotación se aplica cuando hay un desbalance en el subárbol derecho del hijo derecho (caso RR).
     * El nodo 'x' es la raíz del subárbol desbalanceado.
     *
     * @param x El nodo raíz del subárbol a rotar (el nodo desbalanceado).
     * @return La nueva raíz del subárbol después de la rotación.
     */
    private NodoArbol rotarIzquierda(NodoArbol x) {
        if (x==null || x.getHijoDer()==null) {
            return x;
        }
        
        NodoArbol y = x.getHijoDer();
        NodoArbol T2 = y.getHijoIzq();

        y.setHijoIzq(x);
        x.setHijoDer(T2);

        actualizarAltura(x);
        actualizarAltura(y);

        return y;
    }
    
    /**
     * Inserta un patrón de ADN y su lista de posiciones en el árbol AVL.
     * El árbol se auto-balancea después de la inserción.
     * @param patron El patrón de ADN a insertar.
     * @param posiciones La lista de posiciones asociadas al patrón en la secuencia principal.
     */
    public void insertar(String patron, ListaSimple<Integer> posiciones) {
        raiz = metodoInsertar(raiz, patron, posiciones);
    }
    
    /**
    * Método auxiliar recursivo para insertar un patrón y su lista de posiciones en el árbol AVL.
    * También actualiza alturas y realiza rotaciones para mantener el balanceo AVL.
    * @param aux El nodo actual en el subárbol.
    * @param patron El patrón de ADN a insertar.
    * @param posiciones La lista de posiciones asociadas al patrón.
    * @return La nueva raíz del subárbol después de la inserción/balanceo.
    */
    private NodoArbol metodoInsertar(NodoArbol aux, String patron,  ListaSimple<Integer> posiciones) {
        if (aux==null) {
            return new NodoArbol(patron, posiciones); 
        }else{
            
            int freqPatron = posiciones.getTamaño();
            int freqActual = aux.getFrecuencias(); 
            
            if (freqPatron < freqActual) {
                aux.setHijoIzq(metodoInsertar(aux.getHijoIzq(), patron, posiciones));
            } else if (freqPatron > freqActual) {
                aux.setHijoDer(metodoInsertar(aux.getHijoDer(), patron, posiciones));
            }else{
                
                //Si la frecuencia es igual, se compara alfabéticamente el patrón
                int comparacion = patron.compareTo(aux.getPatron());

                if (comparacion < 0) {
                    aux.setHijoIzq(metodoInsertar(aux.getHijoIzq(), patron, posiciones));
                } else if (comparacion > 0) {
                     aux.setHijoDer(metodoInsertar(aux.getHijoDer(), patron, posiciones));
                } else {
                    return aux;
                }
            }
        
            actualizarAltura(aux);

            int balance = Balance(aux);

            if (balance > 1 && patron.compareTo(aux.getHijoIzq().getPatron()) < 0) {
                return rotarDerecha(aux);
            }

            if (balance < -1 && patron.compareTo(aux.getHijoDer().getPatron()) > 0) {
                return rotarIzquierda(aux);
            }

            if (balance > 1 && patron.compareTo(aux.getHijoIzq().getPatron()) > 0) {
                aux.setHijoIzq(rotarIzquierda(aux.getHijoIzq()));
                return rotarDerecha(aux);
            }

            if (balance < -1 && patron.compareTo(aux.getHijoDer().getPatron()) < 0) {
                aux.setHijoDer(rotarDerecha(aux.getHijoDer()));
                return rotarIzquierda(aux);
            }
        
            return aux;
            
        }
    }
      
    /**
     * Busca y retorna el nodo con la mayor frecuencia (más a la derecha).
     * @return NodoArbol con mayor frecuencia, o null si el árbol está vacío.
     */
    public NodoArbol buscarNodoMayorFrecuencia(){
        NodoArbol actual=raiz;
        if (actual==null){
            return null;
        }else{
            while (actual.getHijoDer()!=null){
                actual=actual.getHijoDer();
            }
        }
        return actual;
    }

    /**
     * Busca y retorna el nodo con la menor frecuencia (más a la izquierda).
     * @return NodoArbol con menor frecuencia, o null si el árbol está vacío.
     */
    public NodoArbol buscarNodoMenorFrecuencia(){
    NodoArbol actual=raiz;
        if (actual==null){
            return null;
        }else{
            while (actual.getHijoIzq()!=null){
                actual=actual.getHijoIzq();
            }
        }
        return actual;
    }
    
    /**
     * Devuelve una lista con todos los patrones de mayor frecuencia.
     * @return ListaSimple con los nodos de mayor frecuencia.
     */
    public ListaSimple<NodoArbol> buscarMayorFrecuencia() {
        ListaSimple<NodoArbol> listaMayorFrec = new ListaSimple<>();
        NodoArbol nodoMax = buscarNodoMayorFrecuencia();
        if (nodoMax!=null) {
            int freqMax=nodoMax.getFrecuencias();
            recolectarPorFrecuencia(raiz, freqMax, listaMayorFrec);
        }
        return listaMayorFrec;
    }

    /**
     * Devuelve una lista con todos los patrones de menor frecuencia.
     * @return ListaSimple con los nodos de menor frecuencia.
     */
    public ListaSimple<NodoArbol> buscarMenorFrecuencia() {
        ListaSimple<NodoArbol> listaMenorFrec = new ListaSimple<>();
        NodoArbol nodoMin = buscarNodoMenorFrecuencia();
        if (nodoMin!=null) {
            int freqMin=nodoMin.getFrecuencias();
            recolectarPorFrecuencia(raiz, freqMin, listaMenorFrec);
        }
        return listaMenorFrec;
    }   
    
    /**
     * Recolecta en una lista todos los nodos cuya frecuencia coincide con la buscada.
     * @param nodo Nodo actual del árbol.
     * @param frecuenciaObjetivo Frecuencia a buscar.
     * @param lista Lista donde se agregan los nodos encontrados.
     */
    public void recolectarPorFrecuencia(NodoArbol nodo, int frecuenciaObjetivo, ListaSimple<NodoArbol> lista){
        if (nodo==null){
            return;
        }else{
            int freqNodo=nodo.getFrecuencias();

            if (freqNodo>frecuenciaObjetivo) {
                //Solo busca a la izquierda
                recolectarPorFrecuencia(nodo.getHijoIzq(), frecuenciaObjetivo, lista);
            } else if (freqNodo<frecuenciaObjetivo) {
                //Solo busca a la derecha
                recolectarPorFrecuencia(nodo.getHijoDer(), frecuenciaObjetivo, lista);
            }else{
                //Frecuencia igual: busca ambos lados y agrega (busqueda basada en inorden)
                recolectarPorFrecuencia(nodo.getHijoIzq(), frecuenciaObjetivo, lista);
                lista.insertarAlFinal(nodo);
                recolectarPorFrecuencia(nodo.getHijoDer(), frecuenciaObjetivo, lista);
            }
        }
    }
    
    /**
     * Realiza un recorrido Inorden del árbol AVL.
     * Los elementos se visitan en orden: hijo izquierdo, nodo actual, hijo derecho.
     * Esto resulta en una lista de patrones ordenados alfabéticamente.
     *
     * @return Una ListaSimple de Strings con los patrones en orden inorden.
     */
    public ListaSimple<String> inorden() {
        ListaSimple<String> orden = new ListaSimple<>();
        inorden(raiz, orden); // Llama al método recursivo auxiliar
        return orden;
    }
    
    /**
     * Método auxiliar recursivo para realizar el recorrido Inorden.
     * Este método acumula los patrones en la lista proporcionada.
     *
     * @param nodo El nodo actual en el subárbol que se está procesando en la recursión.
     * @param lista La lista donde se acumularán los patrones visitados.
     */
    private void inorden(NodoArbol nodo, ListaSimple<String> lista) {
        if (nodo != null) {
 
            inorden(nodo.getHijoIzq(), lista);
            String patron = nodo.getPatron();
            int frecuencia = nodo.getFrecuencias();
            String listadoPosiciones = nodo.getPosiciones().mostrarListaPosiciones();
            lista.insertarAlFinal(patron + " | Frecuencia: "+frecuencia+"\nUbicaciones: " + listadoPosiciones+"\n");
            inorden(nodo.getHijoDer(), lista);
        }
    }
    
}

