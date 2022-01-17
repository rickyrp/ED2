package bo.edu.uagrm.ficct.ed2.grafos.pesados;

import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionAristaNoExiste;
import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionAristaYaExiste;
import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionNroVerticesInvalido;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author  Ramirez Pineda Ricky Roy
 */
public class DiGrafoPesado extends  GrafoPesado{

    public DiGrafoPesado() {
        super();
    }

    public DiGrafoPesado(int nroDeVertices) throws ExcepcionNroVerticesInvalido {
        super(nroDeVertices);
    }

    @Override
    public int cantidadDeAristas() {
        //suma todos los size de cada lista
        int cant = 0;
        for (List<AdyacenteConPeso> adyacentesDeUnVertice : this.listaDeAdyacencias) {
            cant = cant + adyacentesDeUnVertice.size();
        }
        return cant;
    }

    @Override
    public void insertarArista(int posVerticeOrigen, int posVerticeDestino, double peso)
            throws ExcepcionAristaYaExiste {
        if (this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<AdyacenteConPeso> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        AdyacenteConPeso verticeDestino = new AdyacenteConPeso(posVerticeDestino, peso);
        adyacentesDelOrigen.add(verticeDestino);
        Collections.sort(adyacentesDelOrigen);
    }


    @Override
    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino)
            throws ExcepcionAristaNoExiste {
        if (!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw  new ExcepcionAristaNoExiste();
        }
        List<AdyacenteConPeso> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        AdyacenteConPeso verticeDestino = adyacentesDelOrigen.get(posVerticeDestino);
        adyacentesDelOrigen.remove(verticeDestino);
    }

    @Override
    public int gradoDeVertice(int posDeVertice) {
        //return super.gradoDeVertice(posDeVertice);
        throw new UnsupportedOperationException("Metodo no soportado en digrafo");
    }

    public int gradoDeEntradaDeVertice(int posDeVertice) {
        super.validarVertice(posDeVertice);
        int entradaDeVertice = 0;
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            Iterable<Integer> adyacentesDeUnVertice = super.adyacentesDeVertice(i);
            for (Integer posDeAdyacente : adyacentesDeUnVertice) {
                if (posDeAdyacente == posDeVertice) {
                    entradaDeVertice++;
                }
            }
        }
        return entradaDeVertice;
    }

    public int gradoDeSalidaDeVertice(int posDeVertice) {
        return super.gradoDeVertice(posDeVertice);
    }

    /*--------------------------------------------------------------------------
                             P R A C T I C O # 2
    --------------------------------------------------------------------------*/

    /*13. Para un grafo dirigido pesado usando la implementación del algoritmo de Floyd-Wharsall
    encontrar los caminos de costo mínimo entre un vértice a y el resto. Mostrar los costos
    y cuáles son los caminos*/
    public String caminosDeCostoMinimoFloyd(int vertice) throws ExcepcionAristaNoExiste {
        AlgoritmoFloydWarshall algoritmo = new AlgoritmoFloydWarshall(this);
        return algoritmo.caminosDeCosteMinimoEntreElVertice(vertice);
    }

    /*15. Para un grafo dirigido pesado implementar el algoritmo de Dijkstra que muestre
    con que vértices hay caminos de costo mínimo partiendo desde un vértice v, con qué costo
    y cuáles son los caminos.*/
    public String caminoAVerticesYCostos(int verticeOrigen) throws ExcepcionAristaNoExiste {
        StringBuilder s = new StringBuilder();
        DFS dfsAux = new DFS(this, verticeOrigen);
        Iterable<Integer> recorrido = dfsAux.obtenerRecorrido();
        for (Integer integer : recorrido) {
            if (integer != verticeOrigen) {
                AlgoritmoDeDijkstra dijkstra = new AlgoritmoDeDijkstra(this, verticeOrigen, integer);
                s.append("Camino de costo minimo: ").append(dijkstra.elRecorrido());
                s.append(" | Costo: ").append(dijkstra.costoDelRecorrido()).append("\n");
            }
        }
        return s.toString();
    }

    //19. Para un grafo dirigido pesado implementar el algoritmo de Ford-Fulkerson
    public int flujoMaximo() throws ExcepcionAristaYaExiste, ExcepcionAristaNoExiste {
        AlgoritmoFordFulkerson fordFulkerson = new AlgoritmoFordFulkerson(this);
        return fordFulkerson.getFlujoMax();
    }

    /*--------------------------------------------------------------------------
                              A D I C I O N A L E S
    --------------------------------------------------------------------------*/


}
