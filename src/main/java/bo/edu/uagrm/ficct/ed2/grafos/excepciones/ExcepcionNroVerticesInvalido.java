package bo.edu.uagrm.ficct.ed2.grafos.excepciones;

/**
 *
 * @author  Ramirez Pineda Ricky Roy
 */
public class ExcepcionNroVerticesInvalido extends Exception {

    /**
     * Creates a new instance of <code>ExcepcionNroVerticesInvalido</code> without
     * detail message.
     */
    public ExcepcionNroVerticesInvalido() {
        super("Cantidad de vertices invalido");
    }

    /**
     * Constructs an instance of <code>ExcepcionNroVerticesInvalido</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ExcepcionNroVerticesInvalido(String msg) {
        super(msg);
    }
}