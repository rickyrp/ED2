package bo.edu.uagrm.ficct.ed2.arboles;

import java.util.*;

public class ArbolBinarioBusquedaEnteroCadena  {

    protected NodoBinario<Integer, String> raiz;

    public ArbolBinarioBusquedaEnteroCadena() {
    }

    /*13. Implemente una clase ArbolBinarioBusquedaEnteroCadena que usando como
    base el ArbolBinarioBusqueda ya no sea un árbol genérico, si no un árbol binario
    de búsqueda con claves enteras y valores cadena*/

    public void insertar(Integer claveAInsertar, String valorAInsertar) throws NullPointerException {
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

        NodoBinario<Integer, String> nodoAnterior = NodoBinario.nodoVacio();
        NodoBinario<Integer, String> nodoActual = this.raiz;
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            Integer claveActual = nodoActual.getClave();
            nodoAnterior = nodoActual;
            if (claveAInsertar.compareTo(claveActual) < 0) {//claveAInstertar < claveActual
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (claveAInsertar.compareTo(claveActual) > 0 ) {//claveAInstertar > claveActual
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                //el valorAInsertar es igual al que tiene el nodoActual (remplazar)
                nodoActual.setValor(valorAInsertar);
                return;
            }
        }

        //si llego a este punto quiere decir, que encontre donde insertar la clave y el valor
        NodoBinario<Integer, String> nodoNuevo = new NodoBinario<>(claveAInsertar, valorAInsertar);
        Integer claveAnterior = nodoAnterior.getClave();
        if (claveAInsertar.compareTo(claveAnterior) < 0) {
            nodoAnterior.setHijoIzquierdo(nodoNuevo);
        } else {
            nodoAnterior.setHijoDerecho(nodoNuevo);
        }
    }


    public String eliminar(Integer claveAEliminar) throws ExcepcionClaveNoExiste {
        String valorAEliminar = this.buscar(claveAEliminar);
        if (valorAEliminar == null) {
            throw new ExcepcionClaveNoExiste();
        }

        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorAEliminar;
    }

    private NodoBinario<Integer, String> eliminar(NodoBinario<Integer, String> nodoActual, Integer claveAEliminar) {
        Integer claveActual = nodoActual.getClave();
        if (claveAEliminar.compareTo(claveActual) < 0) {
            NodoBinario<Integer, String> supuestoNuevoHijoIzq
                    = eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzq);
            return nodoActual;
        }
        if (claveAEliminar.compareTo(claveActual) > 0) {
            NodoBinario<Integer, String> supuestoNuevoHijoDer
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
        NodoBinario<Integer, String> nodoDelSucesor = this.nodoSucesor(nodoActual.getHijoDerecho());
        NodoBinario<Integer, String> supuestoNuevoHijo
                = eliminar(nodoActual.getHijoDerecho(), nodoDelSucesor.getClave());

        nodoActual.setHijoDerecho(supuestoNuevoHijo);
        nodoActual.setClave(nodoDelSucesor.getClave());
        nodoActual.setValor(nodoDelSucesor.getValor());
        return nodoActual;
    }

    protected NodoBinario<Integer, String> nodoSucesor(NodoBinario<Integer, String> nodoActual) {
        NodoBinario<Integer, String> nodoAnterior = NodoBinario.nodoVacio();
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoAnterior;
    }


    public String buscar(Integer claveABuscar) {
        if (this.esArbolVacio()) {
            return "";
        }

        NodoBinario<Integer, String> nodoActual = this.raiz;
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            Integer claveActual = nodoActual.getClave();
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
        return "";
    }


    public boolean contiene(Integer claveABuscar) {
        return this.buscar(claveABuscar) != null;
    }


    public int size() {
        return sizeRec(this.raiz);
    }

    protected int sizeRec(NodoBinario<Integer, String> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }

        int sizePorIzquierda = sizeRec(nodoActual.getHijoIzquierdo());
        int sizePorDerecha = sizeRec(nodoActual.getHijoDerecho());
        return sizePorIzquierda + sizePorDerecha + 1;
    }


    public int altura() {
        if (this.esArbolVacio()) {
            return 0;
        }

        int altura = 0;
        Queue<NodoBinario<Integer, String>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);

        while (!colaDeNodos.isEmpty()) {
            int nodosDelNivel = colaDeNodos.size();
            int contador = 0;
            while (contador < nodosDelNivel) {
                NodoBinario<Integer, String> nodoActual = colaDeNodos.poll();
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


    public int nivel() {
        if (this.esArbolVacio()) {
            return -1;
        }

        int nivel = -1;
        Queue<NodoBinario<Integer, String>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty()) {
            int nodosDelNivel = colaDeNodos.size();
            int contador = 0;
            while (contador < nodosDelNivel) {
                NodoBinario<Integer, String> nodoActual = colaDeNodos.poll();
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


    public void vaciar() {
        this.raiz = NodoBinario.nodoVacio();
    }

    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }


    public List<Integer> recorridoPorNiveles() {
        List<Integer> recorrido = new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }

        Queue<NodoBinario<Integer, String>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);

        while (!colaDeNodos.isEmpty()) {
            NodoBinario<Integer, String> nodoActual = colaDeNodos.poll();
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


    public List<Integer> recorridoEnPreOrden() {
        List<Integer> recorrido = new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }

        Stack<NodoBinario<Integer, String>> pilaDeNodos = new Stack<>();
        pilaDeNodos.push(this.raiz);

        while (!pilaDeNodos.isEmpty()) {
            NodoBinario<Integer, String> nodoActual = pilaDeNodos.pop();
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


    public List<Integer> recorridoEnInOrden() {
        List<Integer> recorrido = new ArrayList<>();
        recorridoEnInOrdenRec(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnInOrdenRec(NodoBinario<Integer, String> nodoActual, List<Integer> recorrido) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return;
        }

        recorridoEnInOrdenRec(nodoActual.getHijoIzquierdo(), recorrido);
        recorrido.add(nodoActual.getClave());
        recorridoEnInOrdenRec(nodoActual.getHijoDerecho(), recorrido);
    }


    public List<Integer> recorridoEnPostOrden() {
        List<Integer> recorrido = new LinkedList<>();
        recorridoEnPostOrdenRec(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPostOrdenRec(NodoBinario<Integer, String> nodoActual, List<Integer> recorrido) {
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

    private String generarCadenaDeArbol(NodoBinario<Integer, String> nodoActual, String prefijo, boolean ponerCodo) {
        StringBuilder cadena = new StringBuilder();
        cadena.append(prefijo);
        if (prefijo.length() == 0) {
            cadena.append(ponerCodo ? "|__(R)" : "|--(R)");// └ alt+192 +196 , ├ alt+195 +196
        } else {
            cadena.append(ponerCodo ? "|__(D)" : "|--(I)");// └ alt+192 +196 , ├ alt+195 +196
        }
        if (NodoBinario.esNodoVacio(nodoActual)) {
            cadena.append("-||\n");// ╣ alt+185
            return cadena.toString();
        }

        cadena.append(nodoActual.getClave().toString());
        cadena.append("\n");

        NodoBinario<Integer, String> nodoIzq = nodoActual.getHijoIzquierdo();
        String prefijoAux = prefijo + (ponerCodo ? "   " : "|   ");// │ alt+179
        cadena.append(generarCadenaDeArbol(nodoIzq, prefijoAux, false));

        NodoBinario<Integer, String> nodoDer = nodoActual.getHijoDerecho();
        cadena.append(generarCadenaDeArbol(nodoDer, prefijoAux, true));

        return cadena.toString();
    }

}
