package bo.edu.uagrm.ficct.ed2.grafos.nopesados;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author  Ramirez Pineda Ricky Roy
 */
public class AlgoritmoOrdenamientoTopologico {
    private DiGrafo grafo;
    private List<Integer> recorrido;

    public  AlgoritmoOrdenamientoTopologico(DiGrafo unGrafo) {
        this.grafo = unGrafo;
        if (grafo.hayCiclo() || !grafo.esDebilmenteConexo()) {
            throw new RuntimeException("Error, No se puede implementar el algoritmo de " +
                    "ordenamiento topologico por que hay ciclo o no es conexo");
        }
        recorrido = new LinkedList<>();
        ejecutarAlgoritmo();
    }

    private void ejecutarAlgoritmo() {
        Queue<Integer> colaDeVertices = new LinkedList<>();
        for (int i = 0; i < this.grafo.listaDeAdyacencias.size(); i++) {
            if (this.grafo.gradoDeEntradaDeVertice(i) == 0) {
                colaDeVertices.offer(i);
            }
        }

        while (!colaDeVertices.isEmpty()) {
            /*elimina todos su adyacentes de ese vertice, asi sus adyacentes
            reducen -1 su grado de entrada*/
            this.grafo.listaDeAdyacencias.get(colaDeVertices.peek()).clear();
            recorrido.add(colaDeVertices.poll());
            for (int i = 0; i < this.grafo.listaDeAdyacencias.size(); i++) {
                if (this.grafo.gradoDeEntradaDeVertice(i) == 0 &&
                        !recorrido.contains(i) && !colaDeVertices.contains(i)) {
                    colaDeVertices.offer(i);
                }
            }
        }
    }

    public Iterable<Integer> elRecorrido(){
        return recorrido;
    }

}
