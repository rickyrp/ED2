package bo.edu.uagrm.ficct.ed2.grafos.excepciones;

/**
 *
 * @author  Ramirez Pineda Ricky Roy
 */
public class ExcepcionAristaNoExiste extends Exception {

    /**
     * Creates a new instance of <code>ExcepcionAristaNoExiste</code> without
     * detail message.
     */
    public ExcepcionAristaNoExiste() {
        super("La arista NO existe en el grafo");
    }

    /**
     * Constructs an instance of <code>ExcepcionAristaNoExiste</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ExcepcionAristaNoExiste(String msg) {
        super(msg);
    }
}