package bo.edu.uagrm.ficct.ed2.grafos.excepciones;

/**
 *
 * @author  Ramirez Pineda Ricky Roy
 */
public class ExcepcionAristaYaExiste extends Exception {

    /**
     * Creates a new instance of <code>ExcepcionAristaYaExiste</code> without
     * detail message.
     */
    public ExcepcionAristaYaExiste() {
        super("La arista ya existe en el grafo");
    }

    /**
     * Constructs an instance of <code>ExcepcionAristaYaExisteo</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ExcepcionAristaYaExiste(String msg) {
        super(msg);
    }
}