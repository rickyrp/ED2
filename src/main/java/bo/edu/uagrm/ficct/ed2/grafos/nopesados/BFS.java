package bo.edu.uagrm.ficct.ed2.grafos.nopesados;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author  Ramirez Pineda Ricky Roy
 */
public class BFS {
    private RecorridoUtils controlDeMarcados;
    private Grafo grafo;
    private List<Integer> recorrido;

    public BFS (Grafo unGrafo, int posVerticePartida) {
        this.grafo = unGrafo;
        grafo.validarVertice(posVerticePartida);
        recorrido = new LinkedList<>();
        controlDeMarcados = new RecorridoUtils(this.grafo.cantidadDeVertices()); // ya esta todo desmarcado
        ejecutarBFS(posVerticePartida);
    }

    private void ejecutarBFS(int posVertice) {
        Queue<Integer> cola = new LinkedList<>();
        cola.offer(posVertice);
        controlDeMarcados.marcarVertice(posVertice);

        do {
            int posVerticeEnTurno = cola.poll();
            recorrido.add(posVerticeEnTurno);
            Iterable<Integer> adyacentesDeVerticeEnTurno = grafo.adyacentesDeVertice(posVerticeEnTurno);
            for (Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno) {
                if (!controlDeMarcados.estaVerticeMarcado(posVerticeAdyacente)) {
                    cola.offer(posVerticeAdyacente);
                    controlDeMarcados.marcarVertice(posVerticeAdyacente);
                }
            }

        } while(!cola.isEmpty());
    }

    public boolean hayCaminoAVertice(int posVertice) {
        grafo.validarVertice(posVertice);
        return controlDeMarcados.estaVerticeMarcado(posVertice);
    }

    public Iterable<Integer> obtenerRecorrido() {
        return this.recorrido;
    }

    public boolean hayCaminosATodos() {
        return controlDeMarcados.estanTodosMarcados();
    }

}
