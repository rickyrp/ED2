/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.uagrm.ficct.ed2.arboles;

import java.util.List;

/**
 *
 * @author Ramirez Pineda Ricky Roy
 * @param <K>
 * @param <V>
 */
public interface IArbolBusqueda<K extends Comparable<K>, V>{
    //No es necesario poner public porque toda interfaz es publica 
    void insertar(K claveAInsertar, V valorAInsertar) throws NullPointerException;
    V eliminar(K claveAEliminar) throws ExcepcionClaveNoExiste; 
    V buscar(K claveABuscar);
    boolean contiene(K claveABuscar);
    int size();
    int altura();
    int nivel();
    void vaciar();
    boolean esArbolVacio();
    List<K> recorridoPorNiveles();
    List<K> recorridoEnPreOrden();
    List<K> recorridoEnInOrden();
    List<K> recorridoEnPostOrden();
}
