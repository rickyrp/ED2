package bo.edu.uagrm.ficct.ed2.grafos.nopesados;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author  Ramirez Pineda Ricky Roy
 */
public class RecorridoUtils {
    private List<Boolean> marcados;

    public RecorridoUtils(int nroVertices) {
        marcados = new LinkedList<>();
        for (int i = 0; i < nroVertices; i++) {
            marcados.add(Boolean.FALSE);
        }
    }

    public void desmarcarTodos() {
        for (int i = 0; i < marcados.size(); i++) {
            marcados.set(i, Boolean.FALSE);
        }
    }

    /**
     * Retorna verdadero para indicar que un vertice esta marcado. Falso en caso contrario
     * PRE: se asume que la posicion de vertice es una posicion valida
     * @param posVertice
     * @return
     */
    public boolean estaVerticeMarcado(int posVertice) {
        return marcados.get(posVertice);
    }

    public boolean estanTodosMarcados() {
        for (Boolean marcado : marcados) {
            if (!marcado) {
                return false;
            }
        }

        return true;
    }

    /**
     * PRE-Condicion: La posicion de vertice, es una posicion valida
     * @param posVertice
     */
    public void marcarVertice(int posVertice) {
        marcados.set(posVertice, Boolean.TRUE);
    }


}
