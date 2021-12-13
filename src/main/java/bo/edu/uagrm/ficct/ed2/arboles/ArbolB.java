/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.uagrm.ficct.ed2.arboles;

import java.util.Stack;

/**
 *
 * @author RYAN RAMIREZ PINEDA
 */
public class ArbolB<K extends Comparable<K>, V> extends ArbolMViasBusqueda<K, V> {

    private int nroMaximoDeDatos;
    private int nroMinimoDeDatos;
    private int nroMinimoDeHijos;

    public ArbolB() {
        this.nroMaximoDeDatos = 2;
        this.nroMinimoDeDatos = 1;
        this.nroMinimoDeHijos = 2;
    }

    public ArbolB(int orden) throws ExcepcionOrdenNoValido {
        super(orden);
        this.nroMaximoDeDatos = this.orden - 1;
        this.nroMinimoDeDatos = this.nroMaximoDeDatos / 2;
        this.nroMinimoDeHijos = this.nroMinimoDeDatos + 1;
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
            this.raiz = new NodoMVias<>(this.orden + 1, claveAInsertar, valorAInsertar);
            return;
        }

        Stack<NodoMVias<K, V>> pilaDePadres = new Stack<>();
        NodoMVias<K, V> nodoActual = this.raiz;
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            int posicionDeClaveAInsertar = super.getPosicionDeClave(nodoActual, claveAInsertar);
            if (posicionDeClaveAInsertar != ArbolMViasBusqueda.POSICION_NO_VALIDA) {
                //remplaza el valor de esa clave
                nodoActual.setValor(posicionDeClaveAInsertar, valorAInsertar);
                return;
            }
            //no hay clave en el nodo
            if (nodoActual.esHoja()) {
                super.insertarClaveYValorEnNodo(nodoActual, claveAInsertar, valorAInsertar);
                if (nodoActual.cantidadDeClavesNoVacias() > this.nroMaximoDeDatos) {
                    this.dividir(nodoActual, pilaDePadres);
                }
                return;
            }
            
            //no hay el nodo actual
            int posicionPorDondeBajar = super.getPosicionPorDondeBajar(nodoActual, claveAInsertar);
            pilaDePadres.push(nodoActual);
            nodoActual = nodoActual.getHijo(posicionPorDondeBajar);
        }
    }
    
    private void dividir(NodoMVias<K, V> nodoActual, Stack<NodoMVias<K, V>> pilaDeAncentros) {
        NodoMVias<K, V> nuevoNodoHijo = new NodoMVias<>(this.orden + 1);//Primer Hijo
        for (int i = 0; i < nroMinimoDeDatos; i++) {
            nuevoNodoHijo.setClave(i, nodoActual.getClave(i));
            nuevoNodoHijo.setValor(i, nodoActual.getValor(i));
            nuevoNodoHijo.setHijo(i, nodoActual.getHijo(i));
        }
        nuevoNodoHijo.setHijo(nroMinimoDeDatos, nodoActual.getHijo(nroMinimoDeDatos));

        int j = 0;
        NodoMVias<K, V> nuevoNodoHijo2 = new NodoMVias<>(this.orden + 1);//Segundo Hijo
        for (int i = nroMinimoDeDatos + 1; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            nuevoNodoHijo2.setClave(j, nodoActual.getClave(i));
            nuevoNodoHijo2.setValor(j, nodoActual.getValor(i));
            nuevoNodoHijo2.setHijo(j, nodoActual.getHijo(i));
            j++;
        }
        nuevoNodoHijo2.setHijo(j, nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));

        if (!pilaDeAncentros.isEmpty()) {
            NodoMVias<K, V> nodoAncestro = pilaDeAncentros.pop();
            if (!nodoAncestro.estanClavesLlenas()) {
                K claveInsertada = nodoActual.getClave(nroMinimoDeDatos);
                //con este metodo se inserta el dato en el lugar correcto
                super.insertarClaveYValorEnNodo(nodoAncestro, nodoActual.getClave(nroMinimoDeDatos),
                        nodoActual.getValor(nroMinimoDeDatos));
                int posicion = super.getPosicionDeClave(nodoAncestro, claveInsertada);
                nodoAncestro.setHijo(posicion, nuevoNodoHijo);
                nodoAncestro.setHijo(posicion + 1, nuevoNodoHijo2);
                if (nodoAncestro.cantidadDeClavesNoVacias() > this.nroMaximoDeDatos) {
                    this.dividir(nodoAncestro, pilaDeAncentros);
                }
            } else {
                nodoActual.setClave(0, nodoActual.getClave(nroMinimoDeDatos));
                nodoActual.setValor(0, nodoActual.getValor(nroMinimoDeDatos));
                int cantDeClavesNoVacias = nodoActual.cantidadDeClavesNoVacias();
                for (int i = 1; i < cantDeClavesNoVacias; i++) {
                    nodoActual.setClave(i, (K)NodoMVias.datoVacio());
                    nodoActual.setValor(i, (V)NodoMVias.datoVacio());
                    nodoActual.setHijo(i, NodoMVias.nodoVacio());
                }
                nodoActual.setHijo(0, nuevoNodoHijo);
                nodoActual.setHijo(1, nuevoNodoHijo2);
            }
        } else {//si la pila esta vacia quiere decir que no tiene ancestro el nodoActual, osea el nodoActual es la raiz
            nodoActual.setClave(0, nodoActual.getClave(nroMinimoDeDatos));
            nodoActual.setValor(0, nodoActual.getValor(nroMinimoDeDatos));
            int cantDeClavesNoVacias = nodoActual.cantidadDeClavesNoVacias();
            for (int i = 1; i < cantDeClavesNoVacias; i++) {
                nodoActual.setValor(i, (V)NodoMVias.datoVacio());
                nodoActual.setClave(i, (K)NodoMVias.datoVacio());
                nodoActual.setHijo(i, NodoMVias.nodoVacio());
            }
            nodoActual.setHijo(0, nuevoNodoHijo);
            nodoActual.setHijo(1, nuevoNodoHijo2);
        }
    }


    /*--------------------------------------------------------------------------
                             P R A C T I C O # 1
    --------------------------------------------------------------------------*/
    
    //3. Para el árbol B implemente el método insertar
        //esta implementado arriba
    
    //4. Para el árbol B implemente el método eliminar
        //No lo implemente :(
}
