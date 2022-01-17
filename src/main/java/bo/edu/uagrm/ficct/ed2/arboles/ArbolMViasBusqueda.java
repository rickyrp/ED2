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

/**
 *
 * @author Ramirez Pineda Ricky Roy
 * @param <K>
 * @param <V>
 */
public class ArbolMViasBusqueda<K extends Comparable<K>, V> implements IArbolBusqueda<K, V> {

    protected NodoMVias<K, V> raiz;
    protected int orden;
    protected static final int POSICION_NO_VALIDA = -1;
    protected static final int ORDEN_MINIMO = 3;

    public ArbolMViasBusqueda() {
        this.orden = ORDEN_MINIMO;
    }

    public ArbolMViasBusqueda(int ordenDelArbol) throws ExcepcionOrdenNoValido {
        if (ordenDelArbol < ORDEN_MINIMO) {
            throw new ExcepcionOrdenNoValido();
        }
        this.orden = ordenDelArbol;
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
            this.raiz = new NodoMVias<>(this.orden, claveAInsertar, valorAInsertar);
            return;
        }

        NodoMVias<K, V> nodoActual = this.raiz;
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            int posicionDeClaveAInsertar = this.getPosicionDeClave(nodoActual, claveAInsertar);
            if (posicionDeClaveAInsertar != POSICION_NO_VALIDA) {
                //remplaza el valor de esa clave
                nodoActual.setValor(posicionDeClaveAInsertar, valorAInsertar);
                return;
            }

            if (nodoActual.esHoja()) {
                if (nodoActual.estanClavesLlenas()) {
                    NodoMVias<K, V> nuevoHijo = 
                            new NodoMVias<>(this.orden, claveAInsertar, valorAInsertar);
                    int posicionDeEnlace = 
                            this.getPosicionPorDondeBajar(nodoActual, claveAInsertar);
                    nodoActual.setHijo(posicionDeEnlace, nuevoHijo);
                } else {
                    //se asume que es nodo hoja
                    this.insertarClaveYValorEnNodo(nodoActual, claveAInsertar, valorAInsertar);
                }
                return;
            }
            
            int posicionPorDondeBajar = 
                    this.getPosicionPorDondeBajar(nodoActual, claveAInsertar);
            if (nodoActual.esHijoVacio(posicionPorDondeBajar)) {
                NodoMVias<K, V> nuevoHijo = 
                        new NodoMVias<>(this.orden, claveAInsertar, valorAInsertar);
                nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
                return;
            } 
            nodoActual = nodoActual.getHijo(posicionPorDondeBajar);
        }
    }

    protected int getPosicionDeClave(NodoMVias<K, V> nodoActual, K claveAInsertar) {
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            K claveActual = nodoActual.getClave(i);
            if (claveActual.compareTo(claveAInsertar) == 0) {//si las claves son iguales
                return i;
            }
        }
        //como no encontre la clave retorno una posicion no valida
        return POSICION_NO_VALIDA;
    }

    protected int getPosicionPorDondeBajar(NodoMVias<K, V> nodoActual, K claveAInsertar) {
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            K claveActual = nodoActual.getClave(i);
            if (claveAInsertar.compareTo(claveActual) < 0) {
                return i;
            }
        } 
        
        //si llego hasta aqui quiere decir que la claveAInsertar es mayor 
        //que la ultima clave del nodo
        return (nodoActual.cantidadDeClavesNoVacias());
    }

     protected void insertarClaveYValorEnNodo(NodoMVias<K, V> nodoActual, 
             K claveAInsertar, V valorAInsertar) {      
         
         boolean cambioDePosicion = false;
         int posicion = nodoActual.cantidadDeClavesNoVacias();
         
         for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias() && !cambioDePosicion; i++) {
             K claveActual = nodoActual.getClave(i);
             if (claveAInsertar.compareTo(claveActual) < 0) {
                 posicion = i;
                 cambioDePosicion = true;
             }
         }
         
         for (int i = nodoActual.cantidadDeClavesNoVacias(); i > posicion; i--) {
             K claveAux = nodoActual.getClave(i - 1);
             V valorAux = nodoActual.getValor(i - 1);
             NodoMVias<K, V> nodoAux = nodoActual.getHijo(i);
             nodoActual.setClave(i, claveAux);
             nodoActual.setValor(i, valorAux);
             nodoActual.setHijo(i + 1, nodoAux);
         }
         
         nodoActual.setClave(posicion, claveAInsertar);
         nodoActual.setValor(posicion, valorAInsertar);
         nodoActual.setHijo(posicion, NodoMVias.nodoVacio());
         
//         //si no hubo cambio de posicion, entonces insertar en la ultima poscion del nodo
//         if (!cambioDePosicion) {
//             nodoActual.setClave(posicion, claveAInsertar);
//             nodoActual.setValor(posicion, valorAInsertar);
//             //nodoActual.setHijo(posicion + 1, NodoMVias.nodoVacio());
//         } else {
//             //recorro las claves y valores del nodo una poscion a la derecha
//             for (int i = nodoActual.cantidadDeClavesNoVacias(); i > posicion ; i--) {
//                 K claveAux = nodoActual.getClave(i - 1);
//                 V valorAux = nodoActual.getValor(i - 1);
//                 nodoActual.setClave(i, claveAux);
//                 nodoActual.setValor(i, valorAux);
//                 //nodoActual.setHijo(i + 1, NodoMVias.nodoVacio());
//             }
//             //inserto la clave y valor en la posicon correspondiente
//             nodoActual.setClave(posicion, claveAInsertar);
//             nodoActual.setValor(posicion, valorAInsertar);
//         }  
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
    
    private NodoMVias<K, V> eliminar(NodoMVias<K, V> nodoActual, K claveAEliminar) {
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            K claveEnTurno = nodoActual.getClave(i);
            if (claveAEliminar.compareTo(claveEnTurno) == 0) {
                if (nodoActual.esHoja()) {
                    this.eliminarClaveYValor(nodoActual, i);
                    if (nodoActual.cantidadDeClavesNoVacias() == 0) {
                        return NodoMVias.nodoVacio();
                    }
                    return nodoActual;
                }
                //caso que no sea hoja el nodo actual
                K claveDeRemplazo;
                if (this.tieneHijosMasAdelante(nodoActual, i)) {
                    claveDeRemplazo = 
                            this.buscarClaveSucesoraInOrden(nodoActual, i);
                } else {
                    claveDeRemplazo = 
                            this.buscarClavePredecesoraInOrden(nodoActual, i);
                }
                
                V valorAsociadoAClaveDeRemplazo = this.buscar(claveDeRemplazo);
                
                nodoActual = eliminar(nodoActual, claveDeRemplazo);
                nodoActual.setClave(i, claveDeRemplazo);
                nodoActual.setValor(i, valorAsociadoAClaveDeRemplazo);
                return nodoActual;
            } 
            //Y si no encuentro la clave a eliminar?
            if (claveAEliminar.compareTo(claveEnTurno) < 0) {
              NodoMVias<K, V> supuestoNuevoHijo = 
                      this.eliminar(nodoActual.getHijo(i), claveAEliminar);
              nodoActual.setHijo(i, supuestoNuevoHijo);
              return nodoActual;
            }
        } //fin del for
        
        NodoMVias<K, V> supuestoNuevoHijo = this.eliminar(
                nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()), claveAEliminar);
        nodoActual.setHijo(nodoActual.cantidadDeClavesNoVacias(), supuestoNuevoHijo);
        return nodoActual;
    }
    
    private void eliminarClaveYValor(NodoMVias<K, V> nodoActual, int posicion) {
        for (int i = posicion; i < nodoActual.cantidadDeClavesNoVacias() - 1; i++) {
            K claveAux = nodoActual.getClave(i + 1);
            V valorAux = nodoActual.getValor(i + 1);
            nodoActual.setClave(posicion, claveAux);
            nodoActual.setValor(posicion, valorAux);
        }
        //if (nodoActual.cantidadDeClavesNoVacias() > 1) {
            nodoActual.setValor(nodoActual.cantidadDeClavesNoVacias() - 1, (V) NodoMVias.datoVacio());
            nodoActual.setClave(nodoActual.cantidadDeClavesNoVacias() - 1, (K) NodoMVias.datoVacio());
        //} else {
            //nodoActual.setClave(0, (K) NodoMVias.datoVacio());
            //nodoActual.setValor(0, (V) NodoMVias.datoVacio());
        //}
    }
    
    private boolean tieneHijosMasAdelante(NodoMVias<K, V> nodoActual, int posicion) {
        /*for (int i = posicion + 1; i < this.orden; i++) {
            if (!nodoActual.esHijoVacio(i)) {
                return true;
            }
        }*/
        for (int i = posicion + 1; i < nodoActual.cantidadDeClavesNoVacias() + 1; i++) {
            if (!nodoActual.esHijoVacio(i)) {
                return true;
            }
        }
        return false;
    }
    
    private K buscarClaveSucesoraInOrden(NodoMVias<K, V> nodoActual, int posicion) {
       nodoActual = nodoActual.getHijo(posicion + 1);
       K claveSucesora = nodoActual.getClave(0);
       
        while (!NodoMVias.esNodoVacio(nodoActual)) {            
            claveSucesora = nodoActual.getClave(0);
            nodoActual = nodoActual.getHijo(0);
        }
        
        return claveSucesora;
    }
    
    private K buscarClavePredecesoraInOrden(NodoMVias<K, V> nodoActual, int posicion) {//K claveAEliminar
        nodoActual = nodoActual.getHijo(posicion);
        K clavePredecesora = nodoActual.getClave(nodoActual.cantidadDeClavesNoVacias() - 1);
        while (!NodoMVias.esNodoVacio(nodoActual)) {
                clavePredecesora = nodoActual.getClave(nodoActual.cantidadDeClavesNoVacias() - 1);
                nodoActual = nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias());
        }
        return clavePredecesora;
    }

    @Override
    public V buscar(K claveABuscar) {
        NodoMVias<K, V> nodoActual = this.raiz;
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            boolean huboCambioDeNodo = false;
            for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias()
                    && !huboCambioDeNodo; i++) {
                K claveActual = nodoActual.getClave(i);
                if (claveABuscar.compareTo(claveActual) == 0) {//claveABuscar == claveActual
                    return nodoActual.getValor(i);
                }
                if (claveABuscar.compareTo(claveActual) < 0) {
                    nodoActual = nodoActual.getHijo(i);
                    huboCambioDeNodo = true;
                }
            }

            if (!huboCambioDeNodo) {
                nodoActual = nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias());
            }
        }

        return (V) NodoMVias.datoVacio();
    }

    @Override
    public boolean contiene(K claveABuscar) {
        return this.buscar(claveABuscar) == NodoMVias.nodoVacio();
    }

    @Override
    public int size() {
        return size(this.raiz);
    }
   
    private int size(NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        
        int sizeAcumulado = 0;
        for (int i = 0; i < this.orden; i++) {
            int sizeDeHijo = size(nodoActual.getHijo(i));
            sizeAcumulado += sizeDeHijo;
        }
        
        /*otra forma de hacerlo
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            int sizeDeHijo = size(nodoActual.getHijo(i));
            sizeAcumulado += sizeDeHijo;
        }
        sizeAcumulado += size(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
        */
        return sizeAcumulado + 1;
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }
    
    private int altura(NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        
        int alturaMayorDeHijos = 0;
        
        for (int i = 0; i < this.orden; i++) {
            int alturaDeHijoEnTurno = altura(nodoActual.getHijo(i));
            if (alturaDeHijoEnTurno > alturaMayorDeHijos) {
                alturaMayorDeHijos = alturaDeHijoEnTurno;
            }
        }
        
        return alturaMayorDeHijos + 1;
    }

    @Override
    public int nivel() {
        return this.altura() - 1;
    }

    @Override
    public void vaciar() {
        this.raiz = NodoMVias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoMVias.esNodoVacio(this.raiz);
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }

        Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);

        while (!colaDeNodos.isEmpty()) {
            NodoMVias<K, V> nodoActual = colaDeNodos.poll();
            for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
                recorrido.add(nodoActual.getClave(i));
                if (!nodoActual.esHijoVacio(i)) {
                    colaDeNodos.offer(nodoActual.getHijo(i));
                }
            }
            if (!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())) {
                colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
            }
        }

        return recorrido;
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new ArrayList<>();
        this.recorridoEnPreOrden(this.raiz, recorrido);
        return recorrido;
    }
    
    private void recorridoEnPreOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            recorrido.add(nodoActual.getClave(i));
            recorridoEnPreOrden(nodoActual.getHijo(i), recorrido);
        }
        recorridoEnPreOrden(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()), recorrido);
    }

    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new ArrayList<>();
        this.recorridoEnInOrden(this.raiz, recorrido);
        return recorrido;
    }
    
    private void recorridoEnInOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        if (NodoMVias.esNodoVacio(nodoActual)) {//n==0
            return;
        }
        
        for (int i = 0; i < this.orden - 1; i++) {
            recorridoEnInOrden(nodoActual.getHijo(i), recorrido);
            if (!nodoActual.esClaveVacia(i)) {
                recorrido.add(nodoActual.getClave(i));
            }   
        }
        recorridoEnInOrden(nodoActual.getHijo(this.orden - 1), recorrido);
        
        //otra forma de hacerlo
        /*for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            recorridoEnInOrden(nodoActual.getHijo(i), recorrido);
            recorrido.add(nodoActual.getClave(i));
        }*/
        //recorridoEnInOrden(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()), recorrido);
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new ArrayList<>();
        this.recorridoEnPostOrden(this.raiz, recorrido);
        return recorrido;
    }
    
    private void recorridoEnPostOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        if (NodoMVias.esNodoVacio(nodoActual)) { //n==0
            return;
        }
        
        recorridoEnPostOrden(nodoActual.getHijo(0), recorrido);
        
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            recorridoEnPostOrden(nodoActual.getHijo(i + 1), recorrido);
            recorrido.add(nodoActual.getClave(i));
        }
    }
    
    @Override
    public String toString() {
        //raiz, prefijo "", ponerCodo true
        return generarCadenaDeArbol(this.raiz, "",  true, 0); 
    }
    
    private String generarCadenaDeArbol(NodoMVias<K, V> nodoActual, String prefijo, 
            boolean ponerCodo, int num) {
        StringBuilder cadena = new StringBuilder();
        cadena.append(prefijo);
        if (prefijo.length() == 0) {
            cadena.append("|__(R)");// └ alt+192 +196 , ├ alt+195 +196
        } else {
            cadena.append(ponerCodo ? "|__(" : "|--(");
            cadena.append(num);
            cadena.append(")");
        }
        if (NodoMVias.esNodoVacio(nodoActual)) {
            cadena.append("-||\n");// ╣ alt+185
            return cadena.toString();
        }
        
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            cadena.append(nodoActual.getClave(i).toString());
            cadena.append(" ");
        }
        cadena.append("\n");
        
        for (int i = 0; i < this.orden - 1; i++) {
            NodoMVias<K, V> nodoHijo = nodoActual.getHijo(i);
            String prefijoAux = prefijo + (ponerCodo ? "   " : "|   ");// │ alt+179
            cadena.append(generarCadenaDeArbol(nodoHijo, prefijoAux, false, i+1));
        }
        
        NodoMVias<K, V> nodoHijo = nodoActual.getHijo(this.orden-1);
        String prefijoAux = prefijo + (ponerCodo ? "   " : "|   ");// │ alt+179
        cadena.append(generarCadenaDeArbol(nodoHijo, prefijoAux, true, this.orden));
        
        return cadena.toString();
    }
    
    /*--------------------------------------------------------------------------
                                 T A R E A
    --------------------------------------------------------------------------*/
    //Implemente un metodo que devuelva la cantidad de claves no vacias en el arbol
    public int cantClavesNoVacias() { //Modo Iterativo
        if (this.esArbolVacio()) {
            return 0;
        }
        
        int cant = 0;
        Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);

        while (!colaDeNodos.isEmpty()) {
            NodoMVias<K, V> nodoActual = colaDeNodos.poll();
            cant += nodoActual.cantidadDeClavesNoVacias();
            if (!nodoActual.esHoja()) {
                for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
                    if (!nodoActual.esHijoVacio(i)) {
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                }
                if (!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())) {
                    colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
                }
            }
        }
        
        return cant;
    }
    
    public int cantClavesNoVaciasRec() { //Modo Recursivo
        return cantClavesNoVaciasRec(this.raiz);
    }

    private int cantClavesNoVaciasRec(NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        
        int cant = nodoActual.cantidadDeClavesNoVacias();
        if (!nodoActual.esHoja()) {
            for (int i = 0; i < this.orden; i++) {
                int cantDeHijoEnTurno = cantClavesNoVaciasRec(nodoActual.getHijo(i));
                cant += cantDeHijoEnTurno;
            }
        }
        
        return cant;
    }
    
    //Implemente un metodo que devuelva la cantidad de claves vacias en el arbol
    public int cantClavesVacias() { //Modo Iterativo
        if (this.esArbolVacio()) {
            return 0;
        }
        
        int cant = 0;
        Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);

        while (!colaDeNodos.isEmpty()) {
            NodoMVias<K, V> nodoActual = colaDeNodos.poll();
            cant += ((this.orden - 1) - nodoActual.cantidadDeClavesNoVacias());
            if (!nodoActual.esHoja()) {
                for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
                    if (!nodoActual.esHijoVacio(i)) {
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                }
                if (!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())) {
                    colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
                }
            }
        }
        
        return cant;
    }
    
    public int cantClavesVaciasRec() { //Modo Recursivo
        return cantClavesVaciasRec(this.raiz);
    }

    private int cantClavesVaciasRec(NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        
        int cant = ((this.orden - 1) - nodoActual.cantidadDeClavesNoVacias());
        if (!nodoActual.esHoja()) {
            for (int i = 0; i < this.orden; i++) {
                int cantDeHijoEnTurno = cantClavesVaciasRec(nodoActual.getHijo(i));
                cant += cantDeHijoEnTurno;
            }
        }
        
        return cant;
    }
     
    /*1. Para un arbol mvias crear un metodo que retorne la cantidad de hijos 
    vacios que hay en el arbol*/
    public int cantHijosVacios() {
        if (this.esArbolVacio()) {
            return 0;
        }
        
        int cant = 0;
        Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        
        while (!colaDeNodos.isEmpty()) {            
            NodoMVias<K, V> nodoActual = colaDeNodos.poll();            
            if (nodoActual.esHoja()) {
                cant += this.orden;
            } else {
                for (int i = 0; i < this.orden; i++) {
                    if (!nodoActual.esHijoVacio(i)) {
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    } else {
                        cant++;
                    }
                }
            }
        }
        
        return cant;
    }
    
    /*2. Para un arbol mvias crear un metodo que retorne la cantidad de hijos 
    vacios que hay en el nivel N del arbol*/
    public int cantHijosVaciosEnNivelN(int nivelN) {
        if (this.esArbolVacio()) {
            return 0;
        }
        
        int cant = 0;
        Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        this.irAlNivelN(colaDeNodos, nivelN);
        
        while (!colaDeNodos.isEmpty()) {            
            NodoMVias<K, V> nodoActual = colaDeNodos.poll();            
            if (nodoActual.esHoja()) {
                cant += this.orden;
            } else {
                for (int i = 0; i < this.orden; i++) {
                    if (nodoActual.esHijoVacio(i)) {
                        cant++;
                    }
                }
            }
        }
        
        return cant;
    }
    
    private void irAlNivelN(Queue<NodoMVias<K, V>> colaDeNodos, int nivelN) {
        while (!colaDeNodos.isEmpty() && nivelN > 0) {            
            int cantDeNodosDelNivel = colaDeNodos.size();
            int contador = 0;
            while (contador < cantDeNodosDelNivel) {
                NodoMVias<K, V> nodoActual = colaDeNodos.poll();
                if (!nodoActual.esHoja()) {
                    for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
                        if (!nodoActual.esHijoVacio(i)) {
                            colaDeNodos.offer(nodoActual.getHijo(i));
                        }
                    }
                    if (!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())) {
                        colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
                    }
                }
                contador++;
            }
            nivelN--;
        }
    }

    private void ejemplo(Queue<NodoMVias<K, V>> colaDeNodos, int nivelN) {
        while (!colaDeNodos.isEmpty() && nivelN > 0) {
            int cantNodos = colaDeNodos.size();
            int contador = 0;
            while (contador < cantNodos) {
                NodoMVias<K, V> nodoActual = colaDeNodos.poll();
                for (int i = 0; i < this.orden; i++) {
                    if (!nodoActual.esHijoVacio(i)) {
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                }
                contador++;
            }
            nivelN--;
        }
    }
        
    /*3. Para un arbol mvias crear un metodo que retorne la cantidad de hijos 
    vacios antes del nivel N del arbol*/
    public int cantHijosVaciosAntesDelNivelN(int nivelN) {
        if (this.esArbolVacio()) {
            return 0;
        }
        
        int cant = 0;
        Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        
        while (!colaDeNodos.isEmpty() && nivelN > 0) {
            int cantDeNodosDelNivel = colaDeNodos.size();
            int contador = 0;
            while (contador < cantDeNodosDelNivel) {
                NodoMVias<K, V> nodoActual = colaDeNodos.poll();
                if (nodoActual.esHoja()) {
                    cant += this.orden;
                } else {
                    for (int i = 0; i < this.orden; i++) {
                        if (!nodoActual.esHijoVacio(i)) {
                            colaDeNodos.offer(nodoActual.getHijo(i));
                        } else {
                            cant++;
                        }
                    }
                }
                contador++;
            }
            nivelN--;
        }
        
        return cant;
    }
    
    /*4. Para un arbol mvias crear un metodo que retorne la cantidad de hijos 
    vacios a partir del nivel N del arbol*/
    public int cantHijosVaciosAPartirDeNivelN(int nivelN) {
        if (this.esArbolVacio()) {
            return 0;
        }
        
        int cant = 0;
        Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        this.irAlNivelN(colaDeNodos, nivelN);
        
        while (!colaDeNodos.isEmpty()) {            
            NodoMVias<K, V> nodoActual = colaDeNodos.poll();            
            if (nodoActual.esHoja()) {
                cant += this.orden;
            } else {
                for (int i = 0; i < this.orden; i++) {
                    if (!nodoActual.esHijoVacio(i)) {
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    } else {
                        cant++;
                    }
                }
            }
        }
        
        return cant;
    }
    
    /*--------------------------------------------------------------------------
                             P R A C T I C O # 1
    --------------------------------------------------------------------------*/

    //5. Para el árbol mvias implemente el método insertar
        //esta implementado arriba
    
    //6. Para el árbol mvias implemente el método eliminar
        //esta implementado arriba
    
    /*12. Implemente un método que retorne verdadero si solo hay nodos completos 
    en el nivel n de un árbol m vias. Falso en caso contrario.*/
    public boolean nodosCompletosEnNivelN(int nivelN) {
        if ((nivelN < 0) || (nivelN > this.nivel())) {
            return false;
        }
        if (this.esArbolVacio()) {
            return false;
        }

        Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        this.irAlNivelN(colaDeNodos, nivelN);

        while (!colaDeNodos.isEmpty()) {
            NodoMVias<K, V> nodoActual = colaDeNodos.poll();
            if (nodoActual.cantidadDeClavesNoVacias() != this.orden - 1) {
                //if (nodoActual.cantidadDeHijosNoVacios() != this.orden) {
                    return false;
                //}
            }
        }

        return true;
    }

    /*14. Para un árbol m vías implementar un método que reciba otro árbol de parámetro 
    y que retorne verdadero si los arboles son similares. Falso en caso contrario.*/
    public boolean sonSimilares(ArbolMViasBusqueda<K, V> arbol2) {
        if (this.esArbolVacio() && arbol2.esArbolVacio()) {
            return true;
        }
        if ((!this.esArbolVacio() && arbol2.esArbolVacio())
                || (this.esArbolVacio() && !arbol2.esArbolVacio())) {
            return false;
        }
        if (this.orden != arbol2.orden) {
            return false;
        }

        Queue<NodoMVias<K, V>> colaDeNodos1 = new LinkedList<>();
        Queue<NodoMVias<K, V>> colaDeNodos2 = new LinkedList<>();
        colaDeNodos1.offer(this.raiz);
        colaDeNodos2.offer(arbol2.raiz);

        while (!colaDeNodos1.isEmpty() && !colaDeNodos2.isEmpty()) {
            NodoMVias<K, V> nodoActual1 = colaDeNodos1.poll();
            NodoMVias<K, V> nodoActual2 = colaDeNodos2.poll();
            if (nodoActual1.cantidadDeClavesNoVacias() != nodoActual2.cantidadDeClavesNoVacias()) {
                return false;
            }
            if (nodoActual1.cantidadDeHijosNoVacios() != nodoActual2.cantidadDeHijosNoVacios()) {
                return false;
            }

            if (!nodoActual1.esHoja()) {
                for (int i = 0; i < this.orden - 1; i++) {
                    if (!nodoActual1.esHijoVacio(i) && !nodoActual2.esHijoVacio(i)) {
                        colaDeNodos1.offer(nodoActual1.getHijo(i));
                        colaDeNodos2.offer(nodoActual2.getHijo(i));
                    } else if ((!nodoActual1.esHijoVacio(i) && nodoActual2.esHijoVacio(i))
                            ||(nodoActual1.esHijoVacio(i) && !nodoActual2.esHijoVacio(i))) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
    
    /*--------------------------------------------------------------------------
                             A D I C I O N A L E S
    --------------------------------------------------------------------------*/
    /*Implemente un método que retorne la mayor llave en un árbol m vias de búsqueda.*/
    public K llaveMenor() {
        K llaveMenor = (K) NodoMVias.datoVacio();
        NodoMVias<K, V> nodoActual = this.raiz;
        
        while (!NodoMVias.esNodoVacio(nodoActual)) {            
            llaveMenor = nodoActual.getClave(0);
            nodoActual = nodoActual.getHijo(0);
        }
        
        return llaveMenor;
    }

    /*Implemente un método que retorne la mayor llave en un árbol m vias de búsqueda.*/
    public K llaveMayor() {
        K llaveMayor = (K) NodoMVias.datoVacio();
        NodoMVias<K, V> nodoActual = this.raiz;

        while (!NodoMVias.esNodoVacio(nodoActual)) {
            llaveMayor = nodoActual.getClave(nodoActual.cantidadDeClavesNoVacias() - 1);
            nodoActual = nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias());
        }

        return llaveMayor;
    }

    /*Implemente un método que retorne verdadero si solo hay hojas en el último
    nivel de un árbol m-vias de búsqueda. Falso en caso contrario.*/
    public boolean soloHojasEnElUltimoNivel() { //PREGUNTAR
        if (this.esArbolVacio()) {
            return false;
        }

        return true;
    }

    /*Implemente un método que retorne verdadero si un árbol m vias tiene solo
    hojas o nodos con todos sus hijos distinto de vacío. Falso en caso contrario.*/
    public boolean soloHojasOTodosSusHijos() {
        if (this.esArbolVacio()) {
            return false;
        }

        Queue<NodoMVias<K,V >> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);

        while (!colaDeNodos.isEmpty()) {
            NodoMVias<K, V> nodoActual = colaDeNodos.poll();
            if (nodoActual.esHoja() || nodoActual.cantidadDeHijosNoVacios() == this.orden) {
                if (!nodoActual.esHoja()) {
                    for (int i = 0; i < this.orden - 1; i++) {
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                }
            } else {
                return false;
            }
        }

        return true;
    }

    /*--------------------------------------------------------------------------
                    M O D E L O S   D E   E X A M E N E S
    --------------------------------------------------------------------------*/
    /*Para un árbol mvias de búsqueda implemente un método que retorne cuantos hijos
    no vacios hay en el nivel n del árbol.*/
    public int hijosNoVaciosEnNivelN(int nivelN) {
        if  (this.esArbolVacio() || nivelN < 0) {
            return 0;
        }
        int cant = 0;
        Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        this.irAlNivelN(colaDeNodos, nivelN);

        while (!colaDeNodos.isEmpty()) {
            NodoMVias<K, V> nodoActual = colaDeNodos.poll();
            for (int i = 0; i < this.orden; i++) {
                if (!nodoActual.esHijoVacio(i)) {
                    cant++;
                }
            }
        }

        return cant;
    }

    private void irNivel(Queue<NodoMVias<K,V>> colaDeNodos, int nivelN) {
        while (!colaDeNodos.isEmpty() && nivelN > 0) {
            int cantNodos = colaDeNodos.size();
            int contador = 0;
            while (contador < cantNodos) {
                NodoMVias<K, V> nodoActual = colaDeNodos.poll();
                for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
                    if  (!nodoActual.esHijoVacio(i)) {
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                }
                if  (!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())) {
                    colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
                }
                contador++;
            }
            nivelN--;
        }
    }

}
