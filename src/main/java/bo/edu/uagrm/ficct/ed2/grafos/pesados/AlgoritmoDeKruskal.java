package bo.edu.uagrm.ficct.ed2.grafos.pesados;

import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionAristaNoExiste;
import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionAristaYaExiste;
import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionNroVerticesInvalido;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  Ramirez Pineda Ricky Roy
 */
public class AlgoritmoDeKruskal {
    private GrafoPesado grafo;
    private GrafoPesado nuevoGrafo;
    private List<Integer> aristaOrigen;
    private List<Integer> aristaDestino;
    private List<Double> pesoDeLaArista;

    public AlgoritmoDeKruskal(GrafoPesado unGrafo) throws ExcepcionNroVerticesInvalido,
            ExcepcionAristaNoExiste, ExcepcionAristaYaExiste {
        this.grafo = unGrafo;
        this.nuevoGrafo = new GrafoPesado(this.grafo.cantidadDeVertices());
        aristaOrigen = new ArrayList<>();
        aristaDestino = new ArrayList<>();
        pesoDeLaArista = new ArrayList<>();

        //crea la estructura auxiliar con las aristas y peso (peso de menor a mayor)
        crearEstructuraAxiliar();
        ejecutarAlgoritmo();
    }

    private void ejecutarAlgoritmo() throws ExcepcionNroVerticesInvalido,
            ExcepcionAristaYaExiste, ExcepcionAristaNoExiste {
        for (int i = 0; i < aristaOrigen.size(); i++) {
            int posVerticeOrigen = aristaOrigen.get(i);
            int posVerticeDestino = aristaDestino.get(i);
            double peso = pesoDeLaArista.get(i);
            this.nuevoGrafo.insertarArista(posVerticeOrigen, posVerticeDestino, peso);
            //si en el nuevo grafo se crea un ciclo se debe eliminar esa arista
            if (this.nuevoGrafo.hayCiclo()) {
                this.nuevoGrafo.eliminarArista(posVerticeOrigen, posVerticeDestino);
            }
        }
    }

    private void crearEstructuraAxiliar() throws ExcepcionAristaNoExiste {
        for (int i = 0; i < this.grafo.cantidadDeAristas(); i++) {
            int posVerticeOrigen = -1;
            int posVerticeDestino = -1;
            double pesoMinimo = -1.0;
            for (int j = 0; j < this.grafo.cantidadDeVertices(); j++) {
                Iterable<Integer> adyacentesDeUnVertice = this.grafo.adyacentesDeVertice(j);
                for (Integer verticeDestino : adyacentesDeUnVertice) {
                    if (!existeArista(j, verticeDestino) ) {
                        if (pesoMinimo == -1.0 || (pesoMinimo > this.grafo.peso(j, verticeDestino))) {
                            pesoMinimo = this.grafo.peso(j, verticeDestino);
                            posVerticeOrigen = j;
                            posVerticeDestino = verticeDestino;
                        }
                    }
                }
            }
            aristaOrigen.add(posVerticeOrigen);
            aristaDestino.add(posVerticeDestino);
            pesoDeLaArista.add(pesoMinimo);
        }
    }

    private boolean existeArista(int posVerticeOrigen, int posVerticeDestino) {
        for (int i = 0; i < aristaOrigen.size(); i++) {
            int origen = aristaOrigen.get(i);
            int destino = aristaDestino.get(i);
            if ((origen == posVerticeOrigen && destino == posVerticeDestino)
                    || (origen == posVerticeDestino && destino == posVerticeOrigen)) {
                return true;
            }
        }
        return false;
    }


    public GrafoPesado getNuevoGrafo() {
        return this.nuevoGrafo;
    }

}
