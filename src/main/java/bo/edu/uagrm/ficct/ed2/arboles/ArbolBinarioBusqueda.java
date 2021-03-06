/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.uagrm.ficct.ed2.arboles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Ramirez Pineda Ricky Roy
 * @param <K>
 * @param <V>
 */
public class ArbolBinarioBusqueda<K extends Comparable<K>, V> implements IArbolBusqueda<K, V> {

    protected NodoBinario<K, V> raiz;

    public ArbolBinarioBusqueda() {
    }

    public ArbolBinarioBusqueda(List<K> clavesInOrden, List<V> valoresInOrden,
            List<K> clavesNoInOrden, List<V> valoresNoInOrden, boolean esConPreOrden) {
        //pre-condicion se asume que los recorridos estan correctos
        if ((clavesInOrden.size() != clavesNoInOrden.size())
                || (valoresInOrden.size() != valoresNoInOrden.size())) {
            throw new RuntimeException("Los recorridos de las listas son incorrectos");
        }

        if (clavesInOrden.isEmpty() || valoresInOrden.isEmpty()
                || clavesNoInOrden.isEmpty() || valoresNoInOrden.isEmpty()) {
            throw new RuntimeException("Error, una de las listas esta vacia");
        }

        if (esConPreOrden) {
            this.raiz = this.reconstruirConPreOrden(clavesInOrden, valoresInOrden,
                    clavesNoInOrden, valoresNoInOrden);
        } else {
            this.raiz = this.reconstruirConPostOrden(clavesInOrden, valoresInOrden,
                    clavesNoInOrden, valoresNoInOrden);
        }
    }

    private NodoBinario<K, V> reconstruirConPreOrden(List<K> clavesInOrden, List<V> valoresInOrden,
            List<K> clavesNoInOrden, List<V> valoresNoInOrden) {
        if (clavesInOrden.isEmpty()) {
            return NodoBinario.nodoVacio();
        }

        K clave = clavesNoInOrden.get(0);
        V valor = valoresNoInOrden.get(0);
        NodoBinario<K, V> nodoRaiz = new NodoBinario<>(clave, valor);
        //posicion de la raiz en la lista inOrden
        int posicionRaiz = this.buscarPosicionRaiz(clavesInOrden, clave);

        //Hijo Izquierdo
        List<K> subListaIzqClavesInOrden = clavesInOrden.subList(0, posicionRaiz);
        List<V> subListaIzqValoresInOrden = valoresInOrden.subList(0, posicionRaiz);
        List<K> subListaIzqClavesNoInOrden = clavesNoInOrden.subList(1, posicionRaiz + 1);
        List<V> subListaIzqValoresNoInOrden = valoresNoInOrden.subList(1, posicionRaiz + 1);
        NodoBinario<K, V> hijoIzquierdo = this.reconstruirConPreOrden(subListaIzqClavesInOrden,
                subListaIzqValoresInOrden, subListaIzqClavesNoInOrden, subListaIzqValoresNoInOrden);

        //Hijo Derecho
        List<K> subListaDerClavesInOrden = clavesInOrden.subList(posicionRaiz + 1,
                clavesInOrden.size());
        List<V> subListaDerValoresInOrden = valoresInOrden.subList(posicionRaiz + 1,
                valoresInOrden.size());
        List<K> subListaDerClavesNoInOrden = clavesNoInOrden.subList(posicionRaiz + 1,
                clavesNoInOrden.size());
        List<V> subListaDerValoresNoInOrden = valoresNoInOrden.subList(posicionRaiz + 1,
                valoresNoInOrden.size());
        NodoBinario<K, V> hijoDerecho = this.reconstruirConPreOrden(subListaDerClavesInOrden,
                subListaDerValoresInOrden, subListaDerClavesNoInOrden, subListaDerValoresNoInOrden);

        nodoRaiz.setHijoIzquierdo(hijoIzquierdo);
        nodoRaiz.setHijoDerecho(hijoDerecho);
        return nodoRaiz;
    }

    private NodoBinario<K, V> reconstruirConPostOrden(List<K> clavesInOrden, List<V> valoresInOrden,
            List<K> clavesNoInOrden, List<V> valoresNoInOrden) {
        if (clavesInOrden.isEmpty()) {
            return NodoBinario.nodoVacio();
        }

        K clave = clavesNoInOrden.get(clavesNoInOrden.size() - 1);//ultimo elemento
        V valor = valoresNoInOrden.get(valoresNoInOrden.size() - 1);
        NodoBinario<K, V> nodoRaiz = new NodoBinario<>(clave, valor);
        //posicion de la raiz en la lista inOrden
        int posicionRaiz = this.buscarPosicionRaiz(clavesInOrden, clave);

        //Hijo Izquierdo
        List<K> subListaIzqClavesInOrden = clavesInOrden.subList(0, posicionRaiz);
        List<V> subListaIzqValoresInOrden = valoresInOrden.subList(0, posicionRaiz);
        List<K> subListaIzqClavesNoInOrden = clavesNoInOrden.subList(0, posicionRaiz);
        List<V> subListaIzqValoresNoInOrden = valoresNoInOrden.subList(0, posicionRaiz);
        NodoBinario<K, V> hijoIzquierdo = this.reconstruirConPostOrden(subListaIzqClavesInOrden,
                subListaIzqValoresInOrden, subListaIzqClavesNoInOrden, subListaIzqValoresNoInOrden);

        //Hijo Derecho
        List<K> subListaDerClavesInOrden = clavesInOrden.subList(posicionRaiz + 1,
                clavesInOrden.size());
        List<V> subListaDerValoresInOrden = valoresInOrden.subList(posicionRaiz + 1,
                valoresInOrden.size());
        List<K> subListaDerClavesNoInOrden = clavesNoInOrden.subList(posicionRaiz,
                clavesNoInOrden.size() - 1);
        List<V> subListaDerValoresNoInOrden = valoresNoInOrden.subList(posicionRaiz,
                valoresNoInOrden.size() - 1);
        NodoBinario<K, V> hijoDerecho = this.reconstruirConPostOrden(subListaDerClavesInOrden,
                subListaDerValoresInOrden, subListaDerClavesNoInOrden, subListaDerValoresNoInOrden);

        nodoRaiz.setHijoIzquierdo(hijoIzquierdo);
        nodoRaiz.setHijoDerecho(hijoDerecho);
        return nodoRaiz;
    }

    private int buscarPosicionRaiz(List<K> clavesInOrden, K claveABuscar) {
        for (int i = 0; i < clavesInOrden.size(); i++) {
            if (claveABuscar.compareTo(clavesInOrden.get(i)) == 0) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) throws NullPointerException {
        if (claveAInsertar == null) {
            throw new RuntimeException("No se permite insertar claves nulas");
        }
        if (valorAInsertar == null) {
            throw new RuntimeException("No se permite insertar valores nulos");
        }

        if (this.esArbolVacio()) {
            this.raiz = new NodoBinario<>(claveAInsertar, valorAInsertar);
            return;
        }

        NodoBinario<K, V> nodoAnterior = NodoBinario.nodoVacio();
        NodoBinario<K, V> nodoActual = this.raiz;
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            K claveActual = nodoActual.getClave();
            nodoAnterior = nodoActual;
            if (claveAInsertar.compareTo(claveActual) < 0) {//claveAInstertar < claveActual
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (claveAInsertar.compareTo(claveActual) > 0) {//claveAInstertar > claveActual
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                //el valorAInsertar es igual al que tiene el nodoActual (remplazar)
                nodoActual.setValor(valorAInsertar);
                return;
            }
        }

        //si llego a este punto quiere decir, que encontre donde insertar la clave y el valor
        NodoBinario<K, V> nodoNuevo = new NodoBinario<>(claveAInsertar, valorAInsertar);
        K claveAnterior = nodoAnterior.getClave();
        if (claveAInsertar.compareTo(claveAnterior) < 0) {
            nodoAnterior.setHijoIzquierdo(nodoNuevo);
        } else {
            nodoAnterior.setHijoDerecho(nodoNuevo);
        }
    }

    @Override
    public V eliminar(K claveAEliminar) throws ExcepcionClaveNoExiste {
        V valorAEliminar = this.buscar(claveAEliminar);
        if (valorAEliminar == null) {
            throw new ExcepcionClaveNoExiste();
        }

        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorAEliminar;
    }

    private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual, K claveAEliminar) {
        K claveActual = nodoActual.getClave();
        if (claveAEliminar.compareTo(claveActual) < 0) {
            NodoBinario<K, V> supuestoNuevoHijoIzq
                    = eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzq);
            return nodoActual;
        }
        if (claveAEliminar.compareTo(claveActual) > 0) {
            NodoBinario<K, V> supuestoNuevoHijoDer
                    = eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDer);
            return nodoActual;
        }

        //si llego aca, ya encontre el nodo con la clave a eliminar (revisar que caso es)
        //Caso 1
        if (nodoActual.esHoja()) {
            return NodoBinario.nodoVacio();
        }

        //Caso 2.1
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoIzquierdo();
        }

        //Caso 2.2
        if (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoDerecho();
        }

        //Caso 3
        NodoBinario<K, V> nodoDelSucesor = this.nodoSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K, V> supuestoNuevoHijo
                = eliminar(nodoActual.getHijoDerecho(), nodoDelSucesor.getClave());

        nodoActual.setHijoDerecho(supuestoNuevoHijo);
        nodoActual.setClave(nodoDelSucesor.getClave());
        nodoActual.setValor(nodoDelSucesor.getValor());
        return nodoActual;
    }

    protected NodoBinario<K, V> nodoSucesor(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoAnterior = NodoBinario.nodoVacio();
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoAnterior;
    }

    @Override
    public V buscar(K claveABuscar) {
        if (this.esArbolVacio()) {
            return null;
        }

        NodoBinario<K, V> nodoActual = this.raiz;
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            K claveActual = nodoActual.getClave();

            if (claveABuscar.compareTo(claveActual) < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (claveABuscar.compareTo(claveActual) > 0) {
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                return nodoActual.getValor();
            }
        }

        //si llego a este punto, quiere decir que la clave a buscar
        //no estan en el arbol, entoces debo retornar null
        return null;
    }

    @Override
    public boolean contiene(K claveABuscar) {
        return this.buscar(claveABuscar) != null;
    }

    @Override
    public int size() {
        if (this.esArbolVacio()) {
            return 0;
        }

        int cantidad = 0;
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        //llenamos la pila en PostOrden con los nodos del arbol
        meterEnPilaParaPostOrden(nodoActual, pilaDeNodos);

        while (!pilaDeNodos.isEmpty()) {
            nodoActual = pilaDeNodos.pop();
            cantidad++;

            if (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoDelTope = pilaDeNodos.peek();
                if (!nodoDelTope.esVacioHijoDerecho()
                        && (nodoDelTope.getHijoDerecho() != nodoActual)) {
                    meterEnPilaParaPostOrden(nodoDelTope.getHijoDerecho(), pilaDeNodos);
                }
            }
        }
        return cantidad;
    }

    public int sizeRec() {
        return sizeRec(this.raiz);
    }

    protected int sizeRec(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }

        int sizePorIzquierda = sizeRec(nodoActual.getHijoIzquierdo());
        int sizePorDerecha = sizeRec(nodoActual.getHijoDerecho());
        return sizePorIzquierda + sizePorDerecha + 1;
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }

    protected int altura(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }

        int alturaPorIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = altura(nodoActual.getHijoDerecho());
        return alturaPorIzquierda > alturaPorDerecha
                ? alturaPorIzquierda + 1 : alturaPorDerecha + 1;
    }

    public int alturaIT() {
        if (this.esArbolVacio()) {
            return 0;
        }

        int altura = 0;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);

        while (!colaDeNodos.isEmpty()) {
            int nodosDelNivel = colaDeNodos.size();
            int contador = 0;
            while (contador < nodosDelNivel) {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
                contador++;
            }
            altura++;
        }
        return altura;
    }

    @Override
    public int nivel() {
        if (this.esArbolVacio()) {
            return -1;
        }

        int nivel = -1;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty()) {
            int nodosDelNivel = colaDeNodos.size();
            int contador = 0;
            while (contador < nodosDelNivel) {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
                contador++;
            }
            nivel++;
        }
        return nivel;
    }

    @Override
    public void vaciar() {
        this.raiz = NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }

        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);

        while (!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            recorrido.add(nodoActual.getClave());

            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return recorrido;
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }

        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        pilaDeNodos.push(this.raiz);

        while (!pilaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());

            if (!nodoActual.esVacioHijoDerecho()) {
                pilaDeNodos.push(nodoActual.getHijoDerecho());
            }
            if (!nodoActual.esVacioHijoIzquierdo()) {
                pilaDeNodos.push(nodoActual.getHijoIzquierdo());
            }
        }
        return recorrido;
    }

    public List<K> recorridoEnPreOrdenRec() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnPreOrdenRec(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPreOrdenRec(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return;
        }

        recorrido.add(nodoActual.getClave());
        recorridoEnPreOrdenRec(nodoActual.getHijoIzquierdo(), recorrido);
        recorridoEnPreOrdenRec(nodoActual.getHijoDerecho(), recorrido);
    }

    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }

        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        //llenamos la pila en InOrden con los nodos del arbol
        this.meterEnPilaParaInOrden(nodoActual, pilaDeNodos);

        while (!pilaDeNodos.isEmpty()) {
            nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());

            if (!nodoActual.esVacioHijoDerecho()) {
                meterEnPilaParaInOrden(nodoActual.getHijoDerecho(), pilaDeNodos);
            }
        }
        return recorrido;
    }

    private void meterEnPilaParaInOrden(NodoBinario<K, V> nodoActual,
            Stack<NodoBinario<K, V>> pilaDeNodos) {
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            pilaDeNodos.push(nodoActual);
            nodoActual = nodoActual.getHijoIzquierdo();
        }
    }

    public List<K> recorridoEnInOrdenRec() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnInOrdenRec(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnInOrdenRec(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return;
        }

        recorridoEnInOrdenRec(nodoActual.getHijoIzquierdo(), recorrido);
        recorrido.add(nodoActual.getClave());
        recorridoEnInOrdenRec(nodoActual.getHijoDerecho(), recorrido);
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }

        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        //llenamos la pila en PostOrden con los nodos del arbol
        meterEnPilaParaPostOrden(nodoActual, pilaDeNodos);

        while (!pilaDeNodos.isEmpty()) {
            nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());

            if (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoDelTope = pilaDeNodos.peek();
                if (!nodoDelTope.esVacioHijoDerecho()
                        && (nodoDelTope.getHijoDerecho() != nodoActual)) {
                    meterEnPilaParaPostOrden(nodoDelTope.getHijoDerecho(), pilaDeNodos);
                }
            }
        }
        return recorrido;
    }

    private void meterEnPilaParaPostOrden(NodoBinario<K, V> nodoActual,
            Stack<NodoBinario<K, V>> pilaDeNodos) {
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            pilaDeNodos.push(nodoActual);

            if (!nodoActual.esVacioHijoIzquierdo()) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                nodoActual = nodoActual.getHijoDerecho();
            }
        }
    }

    public List<K> recorridoEnPostOrdenRec() {
        List<K> recorrido = new LinkedList<>();
        recorridoEnPostOrdenRec(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPostOrdenRec(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return;
        }

        recorridoEnPostOrdenRec(nodoActual.getHijoIzquierdo(), recorrido);
        recorridoEnPostOrdenRec(nodoActual.getHijoDerecho(), recorrido);
        recorrido.add(nodoActual.getClave());
    }

    @Override
    public String toString() {
        return generarCadenaDeArbol(raiz, "", true); //raiz, prefijo "", ponerCodo true
    }

    private String generarCadenaDeArbol(NodoBinario<K, V> nodoActual, String prefijo, boolean ponerCodo) {
        StringBuilder cadena = new StringBuilder();
        cadena.append(prefijo);
        if (prefijo.length() == 0) {
            cadena.append(ponerCodo ? "|__(R)" : "|--(R)");// ??? alt+192 +196 , ??? alt+195 +196
        } else {
            cadena.append(ponerCodo ? "|__(D)" : "|--(I)");// ??? alt+192 +196 , ??? alt+195 +196
        }
        if (NodoBinario.esNodoVacio(nodoActual)) {
            cadena.append("-||\n");// ??? alt+185
            return cadena.toString();
        }

        cadena.append(nodoActual.getClave().toString());
        cadena.append("\n");

        NodoBinario<K, V> nodoIzq = nodoActual.getHijoIzquierdo();
        String prefijoAux = prefijo + (ponerCodo ? "   " : "|   ");// ??? alt+179
        cadena.append(generarCadenaDeArbol(nodoIzq, prefijoAux, false));

        NodoBinario<K, V> nodoDer = nodoActual.getHijoDerecho();
        cadena.append(generarCadenaDeArbol(nodoDer, prefijoAux, true));

        return cadena.toString();
    }

    /*--------------------------------------------------------------------------
                                 T A R E A
    --------------------------------------------------------------------------*/
    /*Desarrollar un metodo que retorne la cantidad de nodos que tienen un 
    solo hijo diferente de vacio*/
    public int unSoloHijoIT() { // Modo Iterativo
        if (this.esArbolVacio()) {
            return 0;
        }

        int cant = 0;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);

        while (!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            if ((!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho())
                    || (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho())) {
                cant++;
            }
            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return cant;
    }
    
    public int unSoloHijoRec() { //Modo Recursivo
        if (this.esArbolVacio()) {
            return 0;
        }
        return unSoloHijoRec(this.raiz);
    }

    private int unSoloHijoRec(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        if (nodoActual.esHoja()) {
            return 0;
        }
        
        int cantPorIzq = unSoloHijoRec(nodoActual.getHijoIzquierdo());
        int cantPorDer = unSoloHijoRec(nodoActual.getHijoDerecho());
        if ((!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho())
            || (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho())) {
            return cantPorIzq + cantPorDer + 1;
        }
        
        return unSoloHijoRec(this.raiz);
    }
    

    /*Desarrollar un metodo que retorne verdadero si los nodos que no son hojas 
    en el arbol solo tienen un hijo. Falso en caso contrario*/
    public boolean noHojaUnSoloHijo() { //Modo Iterativo
        if (this.esArbolVacio()) {
            return false;
        }

        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);

        while (!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();

            if (!nodoActual.esHoja()) {
                if (!nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
                    return false;
                }
            }
            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }

        return true;
    }
    
    public boolean noHojaUnSoloHijoRec() { //Modo Recursivo
        if (this.esArbolVacio()) {
            return false;
        }
        return noHojaUnSoloHijoRec(this.raiz);
    }

    private boolean noHojaUnSoloHijoRec(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return false;
        }
        if (nodoActual.esHoja()) {
            return true;
        }
        
        if (!noHojaUnSoloHijoRec(nodoActual.getHijoIzquierdo())) {
            return false;
        }
        if (!noHojaUnSoloHijoRec(nodoActual.getHijoDerecho())) {
            return false;
        }
        if ((!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho())
            || (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho())) {
            return true;
        }
        
        return false;
    }   

    /*Desarrollar un metodo que retorne verdadero si los nodos que no son hojas 
    antes del nivel n en el arbol solo tienen un hijo. falso en caso contrario*/
    public boolean unSoloHijoAntesN(int nivelN) { //Modo iterativo
        if ((nivelN < 0) || (nivelN > this.nivel())) {
            throw new RuntimeException("Nivel fuera de rango");
        }
        if (this.esArbolVacio() || nivelN == 0) {
            return false;
        }

        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);

        while (!colaDeNodos.isEmpty() && nivelN > 0) {
            int cantDeNodosDelNivel = colaDeNodos.size();
            for (int i = 0; i < cantDeNodosDelNivel; i++) {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();

                if (!nodoActual.esHoja()) {
                    if (!nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
                        return false;
                    }
                }
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
            }
            nivelN--;
        }

        return true;
    }

    public boolean unSoloHijoAntesNRec(int nivelN) { //Modo Recursivo (Este lo copie xD)
        if ((nivelN < 0) || (nivelN > this.nivel())) {
            throw new RuntimeException("Nivel fuera de rango");
        }
        if (this.esArbolVacio()) {
            return false;
        }
        return unSoloHijoAntesNRec(this.raiz, nivelN, 0);
    }
    
    public boolean unSoloHijoAntesNRec(NodoBinario<K, V> nodoActual, int nivelN, int nivelActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return true;
        }
        if (nodoActual.esHoja()) {
            return true;
        }
        
        if (nivelN > nivelActual) {
            boolean verifIzq = unSoloHijoAntesNRec(nodoActual.getHijoIzquierdo(), 
                    nivelN, nivelActual + 1);
            boolean verifDer = unSoloHijoAntesNRec(nodoActual.getHijoDerecho(), nivelN, 
                    nivelActual + 1);
            boolean result = verifIzq || verifDer;
            if ( (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) 
                || (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho())) {
                result = result && true;
            } else {
                result = false;
            }
            return result;
        }
        
        return true;
    }
    
    /*--------------------------------------------------------------------------
                             P R A C T I C O # 1
    --------------------------------------------------------------------------*/
    /*7. Implemente un m??todo iterativo con el recorrido en inorden que retorne la 
    cantidad de nodos que tienen ambos hijos distintos de vac??o en un ??rbol binario*/
    public int ambosHijosInOrden() {
        if (this.esArbolVacio()) {
            return 0;
        }
        
        int cant = 0;
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        this.meterEnPilaParaInOrden(nodoActual, pilaDeNodos);
        
        while (!pilaDeNodos.isEmpty()) {            
            nodoActual = pilaDeNodos.pop();    
            if (!nodoActual.esVacioHijoDerecho()) {
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    cant++;
                }
                this.meterEnPilaParaInOrden(nodoActual.getHijoDerecho(), pilaDeNodos);
            }
        }
        
        return cant;
    }
    
    /*8. Implemente un m??todo recursivo que retorne la cantidad de nodos que tienen 
    un solo hijo no vaci??*/
    public int unSoloHijo() {
        if (this.esArbolVacio()) {
            return 0;
        }
        return unSoloHijoR(this.raiz);
    }

    private int unSoloHijoR(NodoBinario<K,V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        if (nodoActual.esHoja()) {
            return 0;
        }
        int cantPorIzq = unSoloHijoR(nodoActual.getHijoIzquierdo());
        int cantPorDer = unSoloHijoR(nodoActual.getHijoDerecho());
        if ((!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho())
            || (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho())) {
            return cantPorIzq + cantPorDer + 1;
        }
        return cantPorIzq + cantPorDer;
    }


    /*9. Implemente un m??todo iterativo con la l??gica de un recorrido en inOrden 
    que retorne el n??mero de hijos vacios que tiene un ??rbol binario.*/
    public int hijosVacioInOrden() {
        if (this.esArbolVacio()) {
            return 0;
        }
        
        int cant = 0;
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        this.meterEnPilaParaInOrden(nodoActual, pilaDeNodos);
        
        while (!pilaDeNodos.isEmpty()) {            
            nodoActual = pilaDeNodos.pop();    
            if (nodoActual.esVacioHijoIzquierdo()) {
                cant++;
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                this.meterEnPilaParaInOrden(nodoActual.getHijoDerecho(), pilaDeNodos);
            } else {
                cant++;
            }
        }
        
        return cant;
    }
    
    /*10. Implemente un m??todo que reciba en listas de par??metros las llaves y 
    valores de los recorridos en postorden e inorden respectivamente y que 
    reconstruya el ??rbol binario original. Su m??todo no debe usar el m??todo insertar*/
    
    //YA ESTA IMPLEMENTADO (LINEA DE CODIGO: 27)
    
    /*11. Implemente un m??todo privado que reciba un nodo binario de un ??rbol binario 
    y que retorne cu??l ser??a su predecesor inorden de la clave de dicho nodo.*/
    public K predecesor() {
        return predecesorInOrden(this.raiz);
    }

    private K predecesorInOrden(NodoBinario<K, V> nodoActual) {
        nodoActual = nodoActual.getHijoIzquierdo();
        K predecesor = (K) NodoBinario.nodoVacio();
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            predecesor = nodoActual.getClave();
            nodoActual = nodoActual.getHijoDerecho();
        }

        return predecesor;
    }
       
    /*13. Implemente una clase ArbolBinarioBusquedaEnteroCadena que usando como 
    base el ArbolBinarioBusqueda ya no sea un ??rbol gen??rico, si no un ??rbol binario 
    de b??squeda con claves enteras y valores cadena*/
    
    /*15. Para un ??rbol binario de b??squeda implemente un m??todo que reciba como 
    par??metro otro ??rbol y que retorne verdadero si los arboles son similares, 
    falso en caso contrario.*/
    public boolean sonSimilares(ArbolBinarioBusqueda<K, V> arbol2) {
        if (this.esArbolVacio() && arbol2.esArbolVacio()) {
            return true;
        }
        if ((!this.esArbolVacio() && arbol2.esArbolVacio())
            || (this.esArbolVacio() && !arbol2.esArbolVacio())) {
            return false;
        }

        Queue<NodoBinario<K, V>> colaDeNodos1 = new LinkedList<>();
        Queue<NodoBinario<K, V>> colaDeNodos2 = new LinkedList<>();
        colaDeNodos1.offer(this.raiz);
        colaDeNodos2.offer(arbol2.raiz);

        while (!colaDeNodos1.isEmpty() && !colaDeNodos2.isEmpty()) {
            NodoBinario<K, V> nodoActual1 = colaDeNodos1.poll();
            NodoBinario<K, V> nodoActual2 = colaDeNodos2.poll();

            if (!nodoActual1.esVacioHijoIzquierdo() && !nodoActual2.esVacioHijoIzquierdo()) {
                colaDeNodos1.offer(nodoActual1.getHijoIzquierdo());
                colaDeNodos2.offer(nodoActual2.getHijoIzquierdo());
            } else {
                if ((!nodoActual1.esVacioHijoIzquierdo() && nodoActual2.esVacioHijoIzquierdo())
                    || (nodoActual1.esVacioHijoIzquierdo() && !nodoActual2.esVacioHijoIzquierdo())) {
                    return false;
                }
            }

            if (!nodoActual1.esVacioHijoDerecho() && !nodoActual2.esVacioHijoDerecho()) {
                colaDeNodos1.offer(nodoActual1.getHijoDerecho());
                colaDeNodos2.offer(nodoActual2.getHijoDerecho());
            } else {
                if ((!nodoActual1.esVacioHijoDerecho() && nodoActual2.esVacioHijoDerecho())
                        || (nodoActual1.esVacioHijoDerecho() && !nodoActual2.esVacioHijoDerecho())) {
                    return false;
                }
            }

        }

        return true;
    }


    
    /*--------------------------------------------------------------------------
                             A D I C I O N A L E S
    --------------------------------------------------------------------------*/
    /*Implemente un m??todo que retorne la menor llave en un ??rbol binario de b??squeda*/
    public K llaveMenor() {     
        K llaveMenor = (K)NodoBinario.nodoVacio();
        NodoBinario<K, V> nodoActual = this.raiz;
        
        while (!NodoBinario.esNodoVacio(nodoActual)) {            
            llaveMenor = nodoActual.getClave();
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        
        return llaveMenor;
    }
    
    //Implemente un m??todo que retorne la mayor llave en un ??rbol binario de b??squeda
    public K llaveMayor() {     
        K llaveMayor = (K)NodoBinario.nodoVacio();
        NodoBinario<K, V> nodoActual = this.raiz;
        
        while (!NodoBinario.esNodoVacio(nodoActual)) {            
            llaveMayor = nodoActual.getClave();
            nodoActual = nodoActual.getHijoDerecho();
        }
        
        return llaveMayor;
    }

    /*Implemente un m??todo iterativo que retorne la cantidad de nodos que
    tienen ambos hijos distintos de vac??o en un ??rbol binario*/
    public int dosHijos() {
        int cant = 0;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);

        while (!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            if (!nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
                cant++;
            }
            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return cant;
    }

    /*Implemente un m??todo recursivo que retorne la cantidad de nodos que
        tienen ambos hijos distintos de vac??o en un ??rbol binario*/
    public int dosHijosRec() {
        if (this.esArbolVacio()) {
            return 0;
        }
        return dosHijosRec(this.raiz);
    }

    private int dosHijosRec(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        if (nodoActual.esHoja()) {
            return 0;
        }

        int cantPorIzq = dosHijosRec(nodoActual.getHijoIzquierdo());
        int cantPorDer = dosHijosRec(nodoActual.getHijoDerecho());
        if (!nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
            return cantPorIzq + cantPorDer + 1;
        }
        return cantPorIzq + cantPorDer;
    }

    /*Implemente un m??todo iterativo que retorne la cantidad de nodos que tienen
        ambos hijos distintos de vac??o en un ??rbol binario, pero solo en el nivel N*/
    public int dosHijosSoloEnN(int nivelN) {
        if ((nivelN < 0) || (nivelN > this.nivel())) {
            throw new RuntimeException("Nivel fuera de rango");
        }
        if (this.esArbolVacio()) {
            return 0;
        }

        int cant = 0;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        this.irAlNivelN(colaDeNodos, nivelN);

        while (!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            if (!nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
                cant++;
            }
        }

        return cant;
    }

    private void irAlNivelN(Queue<NodoBinario<K, V>> colaDeNodos, int nivelN){
        //la cola ya viene con la raiz osea con nivel 0
        while (!colaDeNodos.isEmpty() && nivelN > 0) {
            nivelN--;
            int cantDeNodosDelNivel = colaDeNodos.size();
            for (int i = 0; i < cantDeNodosDelNivel; i++) {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
            }
        }
    }

    /*Implemente un m??todo recursivo que retorne la cantidad de nodos que tienen
        ambos hijos distintos de vac??o en un ??rbol binario, pero solo en el nivel N*/


    /*Implemente un m??todo iterativo que retorne la cantidad nodos que tienen un
       solo hijo diferente de vac??o en un ??rbol binario, pero solo antes del nivel N*/
    public int unSoloHijoAntesDeN(int nivelN) {
        if ((nivelN < 0) || (nivelN > this.nivel())) {
            throw new RuntimeException("Nivel fuera de rango");
        }
        if (this.esArbolVacio() || nivelN == 0) {
            return 0;
        }

        int cant = 0;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);

        while (!colaDeNodos.isEmpty() && nivelN > 0) {
            int cantDeNodosDelNivel = colaDeNodos.size();
            for (int i = 0; i < cantDeNodosDelNivel; i++) {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();

                if ((!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho())
                        || (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho())) {
                    cant++;
                }
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
            }
            nivelN--;
        }

        return cant;
    }

    /*Implemente un m??todo iterativo con la l??gica de un recorrido en inOrden
        que retorne el n??mero de nodos que tiene un ??rbol binario.*/
    public int sizeInOrden() {
        if (this.esArbolVacio()) {
            return 0;
        }

        int cant = 0;
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        this.meterEnPilaParaInOrden(nodoActual, pilaDeNodos);

        while (!pilaDeNodos.isEmpty()) {
            cant++;
            nodoActual = pilaDeNodos.pop();
            if (!nodoActual.esVacioHijoDerecho()) {
                this.meterEnPilaParaInOrden(nodoActual.getHijoDerecho(), pilaDeNodos);
            }
        }

        return cant;
    }

    /*Implemente un m??todo privado que reciba un nodo binario de un ??rbol binario
        y que retorne cual ser??a su sucesor inorden de la clave de dicho nodo.*/
    public K sucesorInOrden() {
        return sucesorInOrden(this.raiz);
    }

    private  K sucesorInOrden(NodoBinario<K, V> nodoActual) {
        nodoActual = nodoActual.getHijoDerecho();
        K sucesor = (K) NodoBinario.nodoVacio();
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            sucesor = nodoActual.getClave();
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return sucesor;
    }


    /*Implemente un m??todo que retorne verdadero si un ??rbol binario esta lleno.
    Falso en caso contrario.*/
    /*Un ArbolBinario lleno es aquel que en el que todos sus nodos, excepto los del
        del ultimo nivel, tienen dos hijos*/
    /*El numero de nodos de un arbol binario lLeno = 2^h - 1 (donde h es la altura
        del arbol), ejemplo:  h=4 -> 15.
        (2^altura) - 1 = 1, 3, 7, 15, 31... */
    public boolean estaLLeno() {
        if (this.esArbolVacio()) {
            return false;
        }
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        int alturaActual = 0;
        while (!colaDeNodos.isEmpty()) {
            int cantQueDebeTener = ((int)Math.pow(2, alturaActual)) - 1;
            int cantDeNodos = colaDeNodos.size();
            if (cantQueDebeTener != cantDeNodos) {
                return false;
            }
            int i = 0;
            while (i < cantDeNodos) {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
                i++;
            }
            alturaActual++;
        }
        return true;
    }

    /*--------------------------------------------------------------------------
                    M O D E L O S   D E   E X A M E N E S
    --------------------------------------------------------------------------*/
    /*Para un ??rbol binario implemente un m??todo que retorne verdadero si el arbol
    es un monticulo, falso en caso contrario. Un ??rbol binario es monticulo si las
    clave almacenada en un nodo es menor que las claves almacenadas en sus hijos.*/
    public boolean esMonticulo() {
        if (this.esArbolVacio()) {
            return false;
        }
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            if (!nodoActual.esVacioHijoIzquierdo()) {
                NodoBinario<K, V> nodoHijo = nodoActual.getHijoIzquierdo();
                K claveHijo = nodoHijo.getClave();
                K claveActual = nodoActual.getClave();
                if  (claveActual.compareTo(claveHijo) >= 0)  {
                    return false;
                }
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                NodoBinario<K, V> nodoHijo = nodoActual.getHijoDerecho();
                K claveHijo = nodoHijo.getClave();
                K claveActual = nodoActual.getClave();
                if (claveActual.compareTo(claveHijo) >= 0) {
                    return false;
                }
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }

        return true;
    }

    /*Para un arbol binario de b??squeda implemente un metodo que retorne la mayor
    clave en dicho ??rbol. Su implementaci??n debe ser lo m??s eficiente posible aprovechandolas
    caracteristicas de un ??rbol binario de busqueda*/
    public K claveMayor() {
        K claveMayor = (K) NodoBinario.nodoVacio();
        NodoBinario<K, V> nodoActual = this.raiz;
        while (NodoBinario.esNodoVacio(nodoActual)) {
            claveMayor = nodoActual.getClave();
            nodoActual = nodoActual.getHijoDerecho();
        }

        return claveMayor;
    }

    /*Para un arbol binario de b??squeda implementar un m??todo que invierta el arbol, es decir,
    que para cualquier nodo del ??rbol en la rama izquierda haya solo llave mayores y para
    que en la rama derecha solo haya llaves menores. Su soluci??n debe ser recursiva.*/
    public void invertir() {
        this.raiz = invertir(this.raiz);
    }

    private NodoBinario<K, V> invertir(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return null;
        }
        if (nodoActual.esHoja()) {
            return nodoActual;
        }
        nodoActual.setHijoIzquierdo(invertir(nodoActual.getHijoIzquierdo()));
        nodoActual.setHijoDerecho(invertir(nodoActual.getHijoDerecho()));

        NodoBinario<K,V> nodoAuxIzq = nodoActual.getHijoIzquierdo();
        NodoBinario<K, V> nodoAuxDer = nodoActual.getHijoDerecho();
        nodoActual.setHijoIzquierdo(nodoAuxDer);
        nodoActual.setHijoDerecho(nodoAuxIzq);

        return nodoActual;
    }

    /*Retorna verdadero si los arboles son identicos  */
    public boolean sonIdenticos(ArbolBinarioBusqueda<K, V> arbol2) {
        if (this.esArbolVacio() && arbol2.esArbolVacio()) {
            return true;
        }
        if ((!this.esArbolVacio() && arbol2.esArbolVacio())
                || (this.esArbolVacio() && !arbol2.esArbolVacio())) {
            return false;
        }
        Queue<NodoBinario<K, V>> colaDeNodos1 = new LinkedList<>();
        Queue<NodoBinario<K, V>> colaDeNodos2 = new LinkedList<>();
        colaDeNodos1.offer(this.raiz);
        colaDeNodos2.offer(arbol2.raiz);

        while (!colaDeNodos1.isEmpty() && !colaDeNodos2.isEmpty()) {
            NodoBinario<K, V> nodoActual1 = colaDeNodos1.poll();
            NodoBinario<K, V> nodoActual2 = colaDeNodos2.poll();
            K claveActual1 = nodoActual1.getClave();
            K claveActual2 = nodoActual2.getClave();
            if(claveActual1.compareTo(claveActual2) != 0) {
                return false;
            }
            if (!nodoActual1.esVacioHijoIzquierdo() && !nodoActual2.esVacioHijoIzquierdo()) {
                colaDeNodos1.offer(nodoActual1.getHijoIzquierdo());
                colaDeNodos2.offer(nodoActual2.getHijoIzquierdo());
            } else {
                if ((!nodoActual1.esVacioHijoIzquierdo() && nodoActual2.esVacioHijoIzquierdo())
                        || (nodoActual1.esVacioHijoIzquierdo() && !nodoActual2.esVacioHijoIzquierdo())) {
                    return false;
                }
            }

            if (!nodoActual1.esVacioHijoDerecho() && !nodoActual2.esVacioHijoDerecho()) {
                colaDeNodos1.offer(nodoActual1.getHijoDerecho());
                colaDeNodos2.offer(nodoActual2.getHijoDerecho());
            } else {
                if ((!nodoActual1.esVacioHijoDerecho() && nodoActual2.esVacioHijoDerecho())
                        || (nodoActual1.esVacioHijoDerecho() && !nodoActual2.esVacioHijoDerecho())) {
                    return false;
                }
            }

        }
        return true;
    }

    /*Implementar el metodo buscar pero en forma recursiva*/
    public V buscarRec(K claveABuscar) {
        if (this.esArbolVacio()) {
            return null;
        }
        return buscarRec(this.raiz, claveABuscar);
    }

    private V buscarRec(NodoBinario<K, V> nodoActual, K claveABuscar) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return null;
        }

        V valor;
        if (claveABuscar.compareTo(nodoActual.getClave()) < 0) {
            valor = buscarRec(nodoActual.getHijoIzquierdo(), claveABuscar);
        } else if (claveABuscar.compareTo(nodoActual.getClave()) > 0) {
            valor = buscarRec(nodoActual.getHijoDerecho(), claveABuscar);
        } else {
            valor = nodoActual.getValor();
        }

        return valor;
    }

    /*Impelementar el metodo size pero con la logica de InOrden iterativo*/
    public int size2() {
        if (this.esArbolVacio()) {
            return  0;
        }
        int cant = 0;
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        this.meterEnPilaParaInOrden(nodoActual, pilaDeNodos);
        while (!pilaDeNodos.isEmpty()) {
            nodoActual = pilaDeNodos.pop();
            cant++;
            if (!nodoActual.esVacioHijoDerecho()) {
                this.meterEnPilaParaInOrden(nodoActual.getHijoDerecho(), pilaDeNodos);
            }
        }

        return cant;
    }

    /*Impelementar el metodo size pero con la logica de PostOrden iterativo*/
    public int size3() {
        if (this.esArbolVacio()) {
            return 0;
        }
        int cant = 0;
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        this.meterEnPila(pilaDeNodos, nodoActual);
        while (!pilaDeNodos.isEmpty()) {
            nodoActual = pilaDeNodos.pop();
            cant++;
            if (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoDelTope = pilaDeNodos.peek();
                if (!nodoDelTope.esVacioHijoDerecho() &&
                        (nodoDelTope.getHijoDerecho() != nodoActual)) {
                    this.meterEnPila(pilaDeNodos, nodoDelTope.getHijoDerecho());
                }
            }
        }

        return cant;
    }

    private void meterEnPila(Stack<NodoBinario<K,V>> pilaDeNodos, NodoBinario<K,V> nodoActual) {
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            pilaDeNodos.push(nodoActual);
            if (!nodoActual.esVacioHijoIzquierdo()) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                nodoActual = nodoActual.getHijoDerecho();
            }
        }
    }

    //----------------------------------------------------------------------
    public void invertir2() {
        this.raiz = invertir2(this.raiz);
    }

    private NodoBinario<K, V> invertir2(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return null;
        }
        if  (nodoActual.esHoja()) {
            return nodoActual;
        }

        nodoActual.setHijoIzquierdo(invertir2(nodoActual.getHijoIzquierdo()));
        nodoActual.setHijoDerecho(invertir2(nodoActual.getHijoDerecho()));

        NodoBinario<K, V> nodoAuxIzq = nodoActual.getHijoIzquierdo();
        NodoBinario<K, V> nodoAuxDer = nodoAuxIzq.getHijoDerecho();
        nodoActual.setHijoIzquierdo(nodoAuxDer);
        nodoActual.setHijoDerecho(nodoAuxIzq);

        return nodoActual;
    }

    public V buscar2(K claveABuscar) {
        if (this.esArbolVacio()) {
            return null;
        }

        return buscar2(this.raiz, claveABuscar);
    }

    private V buscar2(NodoBinario<K, V> nodoActual, K claveABuscar) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return null;
        }
        V valor;
        if  (claveABuscar.compareTo(nodoActual.getClave()) < 0) {
            valor = buscar2(nodoActual.getHijoIzquierdo(), claveABuscar);
        } else if (claveABuscar.compareTo(nodoActual.getClave()) > 0) {
            valor = buscar2(nodoActual.getHijoDerecho(), claveABuscar);
        } else {
            valor = nodoActual.getValor();
        }

        return valor;
    }


    public void invertir3() {
        this.raiz = invertir3(this.raiz);
    }

    private NodoBinario<K, V> invertir3(NodoBinario<K, V> nodoActual) {
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            return null;
        }

        if (nodoActual.esHoja()) {
            return nodoActual;
        }
        nodoActual.setHijoIzquierdo(invertir3(nodoActual.getHijoIzquierdo()));
        nodoActual.setHijoDerecho(invertir3(nodoActual.getHijoDerecho()));
        NodoBinario<K, V> nodoIzq = nodoActual.getHijoIzquierdo();
        NodoBinario<K, V> nodoDer = nodoActual.getHijoDerecho();
        nodoActual.setHijoIzquierdo(nodoDer);
        nodoIzq.setHijoDerecho(nodoIzq);
        return nodoActual;
    }
}
