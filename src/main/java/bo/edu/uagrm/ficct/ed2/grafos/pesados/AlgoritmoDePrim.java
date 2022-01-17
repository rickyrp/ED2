package bo.edu.uagrm.ficct.ed2.grafos.pesados;

import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionAristaNoExiste;
import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionAristaYaExiste;
import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionNroVerticesInvalido;
import bo.edu.uagrm.ficct.ed2.grafos.nopesados.RecorridoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  Ramirez Pineda Ricky Roy
 */
public class AlgoritmoDePrim {
    private GrafoPesado grafo;
    private GrafoPesado nuevoGrafo;
    private RecorridoUtils controlMarcados;
    private static double INFINITO = 10000.0;//o tambien 2147483647.0 (max valor del int)

    public AlgoritmoDePrim (GrafoPesado unGrafo) throws ExcepcionNroVerticesInvalido,
            ExcepcionAristaYaExiste, ExcepcionAristaNoExiste {
        nuevoGrafo = new GrafoPesado(1);//inicializa con 1 solo vertice
        grafo = unGrafo;
        controlMarcados = new RecorridoUtils(grafo.cantidadDeVertices());
        ejecutarAlgoritmo();
    }

    private void ejecutarAlgoritmo() throws ExcepcionAristaNoExiste, ExcepcionAristaYaExiste {
        List<Integer> verticesMarcados = new ArrayList<>();
        verticesMarcados.add(0);
        controlMarcados.marcarVertice(0);
        int verticeDestino = -1;

        do {
            int verticeOrigen = -1;
            verticeDestino = -1;
            double pesoMinimo = INFINITO;
            for (int i = 0; i < verticesMarcados.size(); i++) {
                int verticeMarcadoEnTurno = verticesMarcados.get(i);
                Iterable<Integer> adyacentes = grafo.adyacentesDeVertice(verticeMarcadoEnTurno);
                int verticeAdicionar = verticeConCaminoMinimo(
                        verticeMarcadoEnTurno , adyacentes, pesoMinimo);
                if (verticeAdicionar != -1) {
                    pesoMinimo = this.grafo.peso(verticeMarcadoEnTurno , verticeAdicionar);
                    verticeOrigen = i;
                    verticeDestino = verticeAdicionar;
                } 
            }
            if (verticeDestino != -1) {
                controlMarcados.marcarVertice(verticeDestino);
                verticesMarcados.add(verticeDestino);
                this.nuevoGrafo.insertarVertice();
                //para el nuevo grafo, mi vertice de destino sera el
                // ultimo vertice que inserte en el nuevo grafo
                verticeDestino = nuevoGrafo.cantidadDeVertices() - 1;
                this.nuevoGrafo.insertarArista(verticeOrigen, verticeDestino, pesoMinimo);
            }
        } while (verticeDestino != -1);

    }

    private int verticeConCaminoMinimo(int posVerticeOrige, Iterable<Integer> adyacentesDelVertice,
                                       double pesoMinimo) throws ExcepcionAristaNoExiste {
        int verticeDestino = -1;
        for (Integer integer : adyacentesDelVertice) {
            if (!controlMarcados.estaVerticeMarcado(integer)) {
                if (this.grafo.peso(posVerticeOrige, integer) < pesoMinimo) {
                    pesoMinimo = this.grafo.peso(posVerticeOrige, integer);
                    verticeDestino = integer;
                }
            }
        }

        return verticeDestino;
    }

    public GrafoPesado getNuevoGrafo() {
        return this.nuevoGrafo;
    }


}
