/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.uagrm.ficct.ed2.arboles;

/**
 *
 * @author Ramirez Pineda Ricky Roy
 */
public class ExcepcionOrdenNoValido extends Exception {

    /**
     * Creates a new instance of <code>ExcepcionOrdenNoValido</code> without
     * detail message.
     */
    public ExcepcionOrdenNoValido() {
        super("Orden del arbol debe ser mayor o igual a 3");
    }

    /**
     * Constructs an instance of <code>ExcepcionOrdenNoValido</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ExcepcionOrdenNoValido(String msg) {
        super(msg);
    }
}
