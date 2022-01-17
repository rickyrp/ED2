package bo.edu.uagrm.ficct.ed2.grafos.pesados;

import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionAristaNoExiste;
import bo.edu.uagrm.ficct.ed2.grafos.nopesados.RecorridoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author  Ramirez Pineda Ricky Roy
 */
public class AlgoritmoDeDijkstra {
    private List<Double> costos;
    private RecorridoUtils controlMarcados;
    private List<Integer> predecesores;
    private GrafoPesado grafo;
    private static double INFINITO = 10000.0;// o tambien 2147483647.0 (max valor del int)
    private int posVerticeDestino;

    public  AlgoritmoDeDijkstra(GrafoPesado unGrafo, int posVerticeOrigen, int posVerticeDestino)
            throws ExcepcionAristaNoExiste {
        this.grafo = unGrafo;
        grafo.validarVertice(posVerticeOrigen);
        grafo.validarVertice(posVerticeDestino);
        this.posVerticeDestino = posVerticeDestino;
        //comprobar si existe camino entre los vertices
        DFS dfsAux = new DFS(this.grafo, posVerticeOrigen);
        if (!dfsAux.hayCaminoAVertice(posVerticeDestino)) {
            throw new  IllegalArgumentException("No exite camino entre los dos vertices");
        }
        costos = new ArrayList<>();
        ponerValorInfinitoACostos(posVerticeOrigen);//todos con INFINITO menos el posVerticeOrigen
        controlMarcados = new RecorridoUtils(grafo.cantidadDeVertices());
        predecesores = new ArrayList<>();
        ponerValorAPredesecores();//todos estan con -1

        ejecutarAlgoritmo(posVerticeOrigen,posVerticeDestino);
    }

    private void ejecutarAlgoritmo(int posVerticeOrigen, int posVerticeDestino)
            throws ExcepcionAristaNoExiste {
        int i = posVerticeOrigen;//el costo minimo
        while (!controlMarcados.estaVerticeMarcado(posVerticeDestino)) {
            controlMarcados.marcarVertice(i);
            Iterable<Integer> adyacentesDelVertice = this.grafo.adyacentesDeVertice(i);
            for (Integer vertice : adyacentesDelVertice) {
                if (!controlMarcados.estaVerticeMarcado(vertice)) {
                    if ( (costos.get(i) +  grafo.peso(i, vertice)) < costos.get(vertice)) {
                        //actualizo el valor del costo
                        costos.set(vertice, costos.get(i) +  grafo.peso(i, vertice));
                        predecesores.set(vertice, i);//actualizo el valor del predecesor
                    }
                }
            }
            i = posCosteMinimo();
        }
    }

    private int posCosteMinimo() {
        int posicion = 0;
        Double menor = INFINITO;
        for (int i = 0; i < costos.size(); i++) {
            if (!controlMarcados.estaVerticeMarcado(i) && menor > costos.get(i)) {
                menor = costos.get(i);
                posicion = i;
            }
        }

        return posicion;
    }

    private void ponerValorInfinitoACostos(int posVerticeOrigen) {
        for (int i = 0; i < this.grafo.cantidadDeVertices(); i++) {
            if (i != posVerticeOrigen) {
                costos.add(INFINITO);
            } else {
                costos.add(0.0);
            }
        }
    }

    private void ponerValorAPredesecores() {
        for (int i = 0; i < this.grafo.cantidadDeVertices(); i++) {
            predecesores.add(-1);
        }
    }

    public Iterable<Integer> elRecorrido() {
        List<Integer> recorrido = new ArrayList<>();
        Stack<Integer> pilaAux = new Stack<>();
        int posVertice = posVerticeDestino;
        pilaAux.push(posVertice);

        while (predecesores.get(posVertice) != -1) {
            pilaAux.push(predecesores.get(posVertice));
            posVertice = predecesores.get(posVertice);
        }
        while (!pilaAux.isEmpty()) {
            recorrido.add(pilaAux.pop());
        }

        return recorrido;
    }

    public double costoDelRecorrido() {
        return costos.get(this.posVerticeDestino);
    }


}
