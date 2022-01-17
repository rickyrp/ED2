package bo.edu.uagrm.ficct.ed2.grafos.pesados;

import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionAristaNoExiste;
import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionAristaYaExiste;
import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionNroVerticesInvalido;
import bo.edu.uagrm.ficct.ed2.grafos.nopesados.RecorridoUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author  Ramirez Pineda Ricky Roy
 */
public class GrafoPesado {
    protected  List<List<AdyacenteConPeso>> listaDeAdyacencias;

    public GrafoPesado() {
        this.listaDeAdyacencias = new LinkedList<>();
    }

    public GrafoPesado(int nroIncialDeVertices) throws ExcepcionNroVerticesInvalido {
        if (nroIncialDeVertices <= 0) {
            throw new ExcepcionNroVerticesInvalido();
        }
        this.listaDeAdyacencias = new LinkedList<>();
        for (int i = 0; i < nroIncialDeVertices; i++) {
            this.insertarVertice();
        }
    }

    public void insertarVertice() {
        List<AdyacenteConPeso> adyacentesDeNuevoVertice = new LinkedList<>();
        this.listaDeAdyacencias.add(adyacentesDeNuevoVertice);
    }

    public int cantidadDeVertices() {
        return listaDeAdyacencias.size();
    }

    public int gradoDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<AdyacenteConPeso> adyacentesDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        return adyacentesDelVertice.size();
    }

    public void validarVertice(int posicionDeVertice) {
        if (posicionDeVertice < 0 || posicionDeVertice >= this.cantidadDeVertices()) {
            throw  new IllegalArgumentException("No existe vertice en la posicion " +
                    posicionDeVertice + " en este grafo");
        }
    }

    public void insertarArista(int posVerticeOrigen, int posVerticeDestino, double peso)
            throws ExcepcionAristaYaExiste {
        if (this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<AdyacenteConPeso> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        AdyacenteConPeso adyacenteDelOrigen =  new AdyacenteConPeso(posVerticeDestino, peso);
        adyacentesDelOrigen.add(adyacenteDelOrigen);
        Collections.sort(adyacentesDelOrigen);
        if (posVerticeOrigen != posVerticeDestino) {
            List<AdyacenteConPeso> adyacentesDelDestino = this.listaDeAdyacencias.get(posVerticeDestino);
            AdyacenteConPeso adyacenteDelDestino =  new AdyacenteConPeso(posVerticeOrigen, peso);
            adyacentesDelDestino.add(adyacenteDelDestino);
            Collections.sort(adyacentesDelDestino);
        }
    }

    public boolean existeAdyacencia(int posVerticeOrigen, int posVerticeDestino) {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        List<AdyacenteConPeso> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        AdyacenteConPeso adyacenteDelOrigen = new AdyacenteConPeso(posVerticeDestino);
        return adyacentesDelOrigen.contains(adyacenteDelOrigen);
    }

    public Iterable<Integer> adyacentesDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<AdyacenteConPeso> adyacentesDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        List<Integer> soloVertices = new ArrayList<>();
        for (AdyacenteConPeso adyacenteConPeso: adyacentesDelVertice) {
            soloVertices.add(adyacenteConPeso.getIndiceDeVertice());
        }
        Iterable<Integer> iterableDeAdyacentes = soloVertices;
        return  iterableDeAdyacentes;
    }

    public int cantidadDeAristas() {
        int cantArtistas = 0;
        int cantLazos = 0;
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            Iterable<Integer> adyacentesDeUnVertice = this.adyacentesDeVertice(i);
            for (Integer posDeAdyacente : adyacentesDeUnVertice) {
                if (i == posDeAdyacente) {
                    cantLazos++;
                } else {
                    cantArtistas++;
                }
            }
        }
        return cantLazos + (cantArtistas / 2);
    }

    public void eliminarVertice(int posVerticeAEliminar) {
        validarVertice(posVerticeAEliminar);
        this.listaDeAdyacencias.remove(posVerticeAEliminar);
        for (List<AdyacenteConPeso> adyacenteDeUnVertice: this.listaDeAdyacencias) {
            AdyacenteConPeso verticeAEliminar = new AdyacenteConPeso(posVerticeAEliminar);
            int posicionDeVerticeAEliminarEnAdy = adyacenteDeUnVertice.indexOf(verticeAEliminar);
            if (posicionDeVerticeAEliminarEnAdy >= 0) {
                adyacenteDeUnVertice.remove(posicionDeVerticeAEliminarEnAdy);
            }

            for (int i = 0; i < adyacenteDeUnVertice.size(); i++) {
                AdyacenteConPeso adyacente = adyacenteDeUnVertice.get(i);
                if (adyacente.getIndiceDeVertice() > posVerticeAEliminar) {
                    adyacente.setIndiceDeVertice(adyacente.getIndiceDeVertice() - 1);
                }
            }
        }
    }

    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino)
            throws ExcepcionAristaNoExiste {
        if (!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw  new ExcepcionAristaNoExiste();
        }
        List<AdyacenteConPeso> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        AdyacenteConPeso verticeDestino = new AdyacenteConPeso(posVerticeDestino);
        adyacentesDelOrigen.remove(verticeDestino);

        if (posVerticeOrigen != posVerticeDestino) {
            List<AdyacenteConPeso> adyacentesDelDestino = this.listaDeAdyacencias.get(posVerticeDestino);
            AdyacenteConPeso verticeOrigen = new AdyacenteConPeso(posVerticeOrigen);
            adyacentesDelDestino.remove(verticeOrigen);
        }
    }

    public double peso(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        if (!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        List<AdyacenteConPeso> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        AdyacenteConPeso unAdyacenteDelOrigen = new AdyacenteConPeso(posVerticeDestino);
        int posicionDelVertice = adyacentesDelOrigen.indexOf(unAdyacenteDelOrigen);
        AdyacenteConPeso adyacenteDelOrigenReal = adyacentesDelOrigen.get(posicionDelVertice);
        return adyacenteDelOrigenReal.getPeso();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Lista de adyacencias\n");
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            s.append(i).append(" : [");
            List<AdyacenteConPeso> adyacentes = this.listaDeAdyacencias.get(i);
            for (AdyacenteConPeso vertice: adyacentes) {
                s.append(" {").append(vertice.getIndiceDeVertice()).append(", ")
                        .append(vertice.getPeso()).append("}").append(",");
            }
            s.deleteCharAt(s.length() - 1);
            s.append(" ]\n");
        }
        return s.toString();
    }

    /*--------------------------------------------------------------------------
                             P R A C T I C O # 2
    --------------------------------------------------------------------------*/
    /*16. Para un grafo no dirigido pesado implementar el algoritmo de Kruskal que
    muestre cual es el grafo encontrado por el algoritmo*/
    public GrafoPesado algoritmoKruskal() throws ExcepcionNroVerticesInvalido,
            ExcepcionAristaYaExiste, ExcepcionAristaNoExiste {
        AlgoritmoDeKruskal kruskal = new AlgoritmoDeKruskal(this);
        return  kruskal.getNuevoGrafo();
    }

    /*17. Para un grafo no dirigido pesado implementar el algoritmo de Prim que
    muestre cual es el grafo encontrado por el algoritmo*/
    public GrafoPesado algoritmoPrim() throws ExcepcionNroVerticesInvalido,
            ExcepcionAristaYaExiste, ExcepcionAristaNoExiste {
        AlgoritmoDePrim prim = new AlgoritmoDePrim(this);
        return prim.getNuevoGrafo();
    }

    /*--------------------------------------------------------------------------
                              A D I C I O N A L E S
    --------------------------------------------------------------------------*/

    public boolean esConexo() {
        BFS recorrido = new BFS(this, 0);
        return recorrido.hayCaminosATodos();
    }

    public boolean hayCiclo() throws ExcepcionNroVerticesInvalido, ExcepcionAristaYaExiste {
        GrafoPesado grafoAux = new GrafoPesado(this.cantidadDeVertices());
        RecorridoUtils marcados = new RecorridoUtils(this.cantidadDeVertices());
        boolean ciclo = hayCiclo(grafoAux, marcados, 0);

        while (!marcados.estanTodosMarcados() && !ciclo) {
            for (int i = 0; i < this.cantidadDeVertices() && !ciclo; i++) {
                if (!marcados.estaVerticeMarcado(i)) {
                    ciclo = hayCiclo(grafoAux, marcados, i);
                }
            }
        }

        return ciclo;
    }

    private boolean hayCiclo(GrafoPesado grafoAux, RecorridoUtils marcados, int posVertice)
            throws ExcepcionAristaYaExiste {
        marcados.marcarVertice(posVertice);
        Iterable<Integer> adyacentesDeVerticeEnTurno = this.adyacentesDeVertice(posVertice);
        for (Integer verticeAdyacente:  adyacentesDeVerticeEnTurno) {
            if (!marcados.estaVerticeMarcado(verticeAdyacente)) {
                grafoAux.insertarArista(posVertice, verticeAdyacente, 0.0);
                if (this.hayCiclo(grafoAux, marcados, verticeAdyacente)) {
                    return true;
                }
            } else {
                if (!grafoAux.existeAdyacencia(posVertice, verticeAdyacente))
                    return true;
            }
        }

        return false;
    }

    public int cantidadDeIslas() {
        if (this.cantidadDeVertices() == 0) {
            return 0;
        }
        DFS dfsAux = new DFS(this, 0);
        int cant = 0;

        while (!dfsAux.hayCaminosATodos()) {
            cant++;
            for (int i = 0; i < this.cantidadDeVertices(); i++) {
                if (!dfsAux.hayCaminoAVertice(i)) {
                    dfsAux.procesarDFS(i);
                    i = this.cantidadDeVertices();
                }
            }
        }

        return cant + 1;
    }

}
