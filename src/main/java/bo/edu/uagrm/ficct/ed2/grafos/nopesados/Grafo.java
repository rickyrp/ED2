package bo.edu.uagrm.ficct.ed2.grafos.nopesados;

import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionAristaNoExiste;
import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionAristaYaExiste;
import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionNroVerticesInvalido;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author  Ramirez Pineda Ricky Roy
 */
public class Grafo {
    protected  List<List<Integer>> listaDeAdyacencias;

    public Grafo() {
        this.listaDeAdyacencias = new LinkedList<>();
    }

    public Grafo(int nroIncialDeVertices) throws ExcepcionNroVerticesInvalido {
        if (nroIncialDeVertices <= 0) {
            throw new ExcepcionNroVerticesInvalido();
        }
        this.listaDeAdyacencias = new LinkedList<>();
        for (int i = 0; i < nroIncialDeVertices; i++) {
            this.insertarVertice();
        }
    }

    public void insertarVertice() {
        List<Integer> adyacentesDeNuevoVertice = new LinkedList<>();
        this.listaDeAdyacencias.add(adyacentesDeNuevoVertice);
    }

    public int cantidadDeVertices() {
        return listaDeAdyacencias.size();
    }

    public int gradoDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<Integer> adyacentesDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        return adyacentesDelVertice.size();
    }

    public void validarVertice(int posicionDeVertice) {
        if (posicionDeVertice < 0 || posicionDeVertice >= this.cantidadDeVertices()) {
            throw  new IllegalArgumentException("No existe vertice en la posicion " +
                    posicionDeVertice + " en este grafo");
        }
    }

    public void insertarArista(int posVerticeOrigen, int posVerticeDestino)
            throws ExcepcionAristaYaExiste {
        if (this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        adyacentesDelOrigen.add(posVerticeDestino);
        Collections.sort(adyacentesDelOrigen);
        if (posVerticeOrigen != posVerticeDestino) {
            List<Integer> adyacentesDelDestino = this.listaDeAdyacencias.get(posVerticeDestino);
            adyacentesDelDestino.add(posVerticeOrigen);
            Collections.sort(adyacentesDelDestino);
        }
    }

    public boolean existeAdyacencia(int posVerticeOrigen, int posVerticeDestino) {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        return adyacentesDelOrigen.contains(posVerticeDestino);
    }

    public Iterable<Integer> adyacentesDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<Integer> adyacentesDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        Iterable<Integer> iterableDeAdyacentes = adyacentesDelVertice;
        return  iterableDeAdyacentes;
    }

    public int cantidadDeAristas() {
        int cantArtistas = 0;
        int cantLazos = 0;
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            List<Integer> adyacentesDeUnVertice = this.listaDeAdyacencias.get(i);
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
        for (List<Integer> adyacenteDeUnVertice: this.listaDeAdyacencias) {
            int posicionDeVerticeAEliminarEnAdy = adyacenteDeUnVertice.indexOf(posVerticeAEliminar);
            if (posicionDeVerticeAEliminarEnAdy >= 0) {
                adyacenteDeUnVertice.remove(posicionDeVerticeAEliminarEnAdy);
            }

            for (int i = 0; i < adyacenteDeUnVertice.size(); i++) {
                int posicionDeAdyacente = adyacenteDeUnVertice.get(i);
                if (posicionDeAdyacente > posVerticeAEliminar) {
                    adyacenteDeUnVertice.set(i, posicionDeAdyacente - 1);
                }
            }
        }
    }

    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino)
            throws ExcepcionAristaNoExiste {
        if (!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw  new ExcepcionAristaNoExiste();
        }
        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        int posicionDelDestino = adyacentesDelOrigen.indexOf(posVerticeDestino);
        adyacentesDelOrigen.remove(posicionDelDestino);

        if (posVerticeOrigen != posVerticeDestino) {
            List<Integer> adyacentesDelDestino = this.listaDeAdyacencias.get(posVerticeDestino);
            int posicionDelOrigen = adyacentesDelDestino.indexOf(posVerticeOrigen);
            adyacentesDelDestino.remove(posicionDelOrigen);
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Lista de adyacencias\n");
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            s.append(i).append(" : ").append(this.adyacentesDeVertice(i)).append("\n");
        }
        return s.toString();
    }

    /*--------------------------------------------------------------------------
                             P R A C T I C O # 2
    --------------------------------------------------------------------------*/
    /*1. Para un grafo no dirigido implementar los métodos insertarVertice, insertarArista,
    eliminarVertice, eliminarArista, cantidadDeVertices, cantidadDeArista, gradoDeVertice*/

    /*9. Para un grafo no dirigido implementar un método o clase que permita encontrar
    si en dicho grafo hay ciclo.*/
    public boolean hayCiclo2daForma() throws ExcepcionNroVerticesInvalido,
            ExcepcionAristaYaExiste {
        Grafo grafoAux = new Grafo(this.cantidadDeVertices());
        RecorridoUtils marcados = new RecorridoUtils(this.cantidadDeVertices());
        boolean hayCiclo = hayCiclo2daForma(grafoAux, marcados, 0);

        while (!marcados.estanTodosMarcados() && !hayCiclo) {
            for (int i = 0; i < this.listaDeAdyacencias.size() && !hayCiclo; i++) {
                if (!marcados.estaVerticeMarcado(i)) {
                    hayCiclo = this.hayCiclo2daForma(grafoAux, marcados, i);
                }
            }
        }

        return hayCiclo;
    }

    private boolean hayCiclo2daForma(Grafo grafoAux, RecorridoUtils marcados,
                                     int posVertice) throws ExcepcionAristaYaExiste {
        marcados.marcarVertice(posVertice);
        Iterable<Integer> adyacentesDeVerticeEnTurno = this.adyacentesDeVertice(posVertice);
        for (Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno) {
            if (!marcados.estaVerticeMarcado(posVerticeAdyacente)) {
                grafoAux.insertarArista(posVertice, posVerticeAdyacente);
                if (this.hayCiclo2daForma(grafoAux, marcados, posVerticeAdyacente)) {
                    return true;
                }
            } else {
                if (!grafoAux.existeAdyacencia(posVertice, posVerticeAdyacente))
                    return true;
            }
        }

        return false;
    }

    /*10. Para un grafo no dirigido implementar método o clase para encontrar los componentes
    de las diferentes islas que hay en dicho grafo*/
    public List<List<Integer>> componentesDeLasIslas() {
        Isla islas = new Isla(this);
        return islas.getComponentesDeLaIsla();
    }

    /*--------------------------------------------------------------------------
                           A D I C I O N A L E S
    --------------------------------------------------------------------------*/

    //Hacer un recorrido (BFS O DFS) desde el primer vertice disponible y
    //si todos los vertices quedaron marcados entoces es conexo
    public boolean esConexo() {
        BFS recorrido = new BFS(this, 0);
        return recorrido.hayCaminosATodos();//si estan todos los vertices marcados
    }


    //Hacer un recorrido DFS y verificar si todos quedaron marcados, si no todos quedaron
    //marcados incremento +1 mi cantidad de islas, y busco el vertice que no quedo marcado
    //y lo proceso en mi DFS, asi sucesivamente hasta que todos los verticen queden marcados
    public int cantidadDeIslas() {
        if (this.cantidadDeVertices() == 0) {//si no hay ningun vertice en el grafo
            return 0;
        }
        DFS recorrido = new DFS(this, 0);
        int cant = 0;
        while (!recorrido.hayCaminosATodos()) {//si estan todos marcados
            cant++;
            for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
                if (!recorrido.hayCaminoAVertice(i)) {
                    recorrido.procesarDFS(i);
                    i = this.listaDeAdyacencias.size();
                }
            }
        }
        return cant + 1;
    }

}
