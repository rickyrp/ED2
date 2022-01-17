package bo.edu.uagrm.ficct.ed2.grafos.nopesados;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author  Ramirez Pineda Ricky Roy
 */
public class DFS {
    private RecorridoUtils controlMarcados;
    private Grafo grafo;
    private List<Integer> recorrido;


    public DFS (Grafo unGrafo, int postVerticePartida) {
        this.grafo = unGrafo;
        grafo.validarVertice(postVerticePartida);
        recorrido = new LinkedList<>();
        controlMarcados = new RecorridoUtils(this.grafo.cantidadDeVertices()); // ya esta todo desmarcado
        procesarDFS(postVerticePartida);
    }

    public void procesarDFS(int posVertice) {
        controlMarcados.marcarVertice(posVertice);
        recorrido.add(posVertice);
        Iterable<Integer> adyacentesDeVerticeEnTurno = grafo.adyacentesDeVertice(posVertice);
        for (Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno) {
            if (!controlMarcados.estaVerticeMarcado(posVerticeAdyacente)) {
                procesarDFS(posVerticeAdyacente);
            }
        }
    }

    public boolean hayCaminoAVertice(int posVertice) {
        grafo.validarVertice(posVertice);
        return controlMarcados.estaVerticeMarcado(posVertice);
    }

    public Iterable<Integer> obtenerRecorrido() {
        return this.recorrido;
    }

    public boolean hayCaminosATodos() {
        return controlMarcados.estanTodosMarcados();
    }

}
