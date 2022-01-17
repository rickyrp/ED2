package bo.edu.uagrm.ficct.ed2.grafos.nopesados;

import java.util.ArrayList;
import java.util.List;

public class Isla {
    List<List<Integer>> componentesDeLaIsla;
    int cantidadDeIslas;

    public Isla(Grafo unGrafo) {
        componentesDeLaIsla = new ArrayList<>();
        cantidadDeIslas = cantidadDeIslasGrafoNoDirigido(unGrafo);
    }
    public Isla(DiGrafo unGrafo) {
        componentesDeLaIsla = new ArrayList<>();
        crearListas(unGrafo);
        cantidadDeIslas = cantidadDeIslasGrafoDirigido(unGrafo);
    }

    private void crearListas(Grafo unGrafo) {
        for (int i = 0; i < unGrafo.listaDeAdyacencias.size(); i++) {
            List<Integer> aux = new ArrayList<>();
            componentesDeLaIsla.add(aux);
        }
    }

    private List<Integer> islaNueva(Iterable<Integer> elRecorrido) {
        List<Integer> islaNueva = new ArrayList<>();
        for (Integer aux : elRecorrido) {
            boolean estaEnUnaIsla = false;
            for (List<Integer> integers : componentesDeLaIsla) {
                if (integers.contains(aux)) {
                    estaEnUnaIsla = true;
                    break;
                }
            }
            if (!estaEnUnaIsla) {
                islaNueva.add(aux);
            }
        }

        return islaNueva;
    }

    private int cantidadDeIslasGrafoDirigido(DiGrafo unGrafo) {
        if (unGrafo.cantidadDeVertices() == 0) {//si no hay ningun vertice en el grafo
            return 0;
        }
        cantidadDeIslas = 0;
        DFS dfsAux = new DFS(unGrafo, 0);

        while (!dfsAux.hayCaminosATodos()) {
            Iterable<Integer> recorrido = dfsAux.obtenerRecorrido();//aqui estan los vertices marcados
            int adyacenteNoMarcado = adyacenteNoMarcadoDeUnVerticeMarcado(unGrafo, dfsAux, recorrido);
            if (adyacenteNoMarcado == -1) {
                componentesDeLaIsla.add(cantidadDeIslas, islaNueva(dfsAux.obtenerRecorrido()));
                cantidadDeIslas++;
                for (int i = 0; i < unGrafo.listaDeAdyacencias.size(); i++) {
                    if (!dfsAux.hayCaminoAVertice(i)) {//pregunto cual vertice no esta marcado
                        dfsAux.procesarDFS(i);
                        i = unGrafo.listaDeAdyacencias.size();
                    }
                }
            } else {
                dfsAux.procesarDFS(adyacenteNoMarcado);
            }
        }

        componentesDeLaIsla.add(cantidadDeIslas, islaNueva(dfsAux.obtenerRecorrido()));
        return cantidadDeIslas + 1;
    }

    private int adyacenteNoMarcadoDeUnVerticeMarcado(DiGrafo unGrafo, DFS dfAux,
                                                     Iterable<Integer> recorridoDelDFS) {
        for (Integer posVertice : recorridoDelDFS) {//aqui estan todos los vertices marcados
            for (int i = 0; i < unGrafo.listaDeAdyacencias.size(); i++) {
                if (unGrafo.existeAdyacencia(i, posVertice)) {
                    if (!dfAux.hayCaminoAVertice(i)) {
                        return i;
                    }
                }
            }
        }

        return -1; //retorna -1 si no encuentra un adyacente no marcado, de los vertices marcados
    }

    private int cantidadDeIslasGrafoNoDirigido(Grafo unGrafo) {
        if (unGrafo.cantidadDeVertices() == 0) {//si no hay ningun vertice en el grafo
            return 0;
        }
        cantidadDeIslas = 0;
        DFS dfsAuxiliar = new DFS(unGrafo, 0);

        while (!dfsAuxiliar.hayCaminosATodos()) {
            componentesDeLaIsla.add(cantidadDeIslas, islaNueva(dfsAuxiliar.obtenerRecorrido()));
            cantidadDeIslas++;
            for (int i = 0; i < unGrafo.listaDeAdyacencias.size(); i++) {
                if (!dfsAuxiliar.hayCaminoAVertice(i)) {//pregunta si ese vertice no esta marcado
                    dfsAuxiliar.procesarDFS(i);
                    i = unGrafo.listaDeAdyacencias.size();
                }
            }
        }

        componentesDeLaIsla.add(cantidadDeIslas, islaNueva(dfsAuxiliar.obtenerRecorrido()));
        return cantidadDeIslas + 1;
    }

    public int getCantidadDeIslas() {
        return cantidadDeIslas;
    }

    public List<List<Integer>> getComponentesDeLaIsla() {
        return this.componentesDeLaIsla;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < cantidadDeIslas; i++) {
            s.append(componentesDeLaIsla.get(i));
            s.append("\n");
        }
        return s.toString();
    }
}
