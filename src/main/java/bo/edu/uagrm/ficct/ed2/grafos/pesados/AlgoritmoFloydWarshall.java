package bo.edu.uagrm.ficct.ed2.grafos.pesados;

import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionAristaNoExiste;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  Ramirez Pineda Ricky Roy
 */
public class AlgoritmoFloydWarshall {
    //private GrafoPesado grafo;
    private Double[][] matrizP;//matriz donde almaceno el peso
    private int[][] matrizPred;
    private static double INFINITO = 10000.0;//o tambien 2147483647.0 (max valor del int)

    public AlgoritmoFloydWarshall(GrafoPesado unGrafo) throws ExcepcionAristaNoExiste {
        //no debe de haber ciclo en el grafo
        matrizP = new Double[unGrafo.cantidadDeVertices()][unGrafo.cantidadDeVertices()];
        matrizPred = new int[unGrafo.cantidadDeVertices()][unGrafo.cantidadDeVertices()];
        for (int i = 0; i < matrizPred.length; i++) {
            for (int j = 0; j < matrizPred.length; j++) {
                this.matrizPred[i][j] = -1;
            }
        }
        ejecutarAlgoritmo(unGrafo);
    }

    private void ejecutarAlgoritmo(GrafoPesado unGrafo) throws ExcepcionAristaNoExiste {
        matrizDeAdyacencia(unGrafo);
        for (int k = 0; k < this.matrizP.length; k++) {
            for (int i = 0; i < this.matrizP.length; i++) {
                for (int j = 0; j < this.matrizP.length; j++) {
                    if (matrizP[i][j] > (matrizP[i][k] + matrizP[k][j]) ) {
                        matrizPred[i][j] = k;
                        matrizP[i][j] = matrizP[i][k] + matrizP[k][j];
                    }
                }
            }
        }

    }

    private void matrizDeAdyacencia(GrafoPesado unGrafo) throws ExcepcionAristaNoExiste {
        int k = 0;
        for (int i = 0; i < unGrafo.cantidadDeVertices(); i++) {
            for (int j = 0; j < unGrafo.cantidadDeVertices(); j++) {
                if (unGrafo.existeAdyacencia(i, j)) {
                    this.matrizP[i][j] = unGrafo.peso(i, j);//ingreso el peso del adyacente
                } else {
                    this.matrizP[i][j] = INFINITO;
                }
                //this.matrizP[i][j] = INFINITO;
            }
            /*List<AdyacenteConPeso> adyacenteEnTurno = unGrafo.listaDeAdyacencias.get(i);
            for (AdyacenteConPeso adyacente: adyacenteEnTurno) {
                int vertice = adyacente.getIndiceDeVertice();
                this.matrizP[i][vertice] = unGrafo.peso(i, vertice);//ingreso el peso del adyacente
            }*/
            this.matrizP[i][k] = 0.0;//para la diagonal
            k++;
        }
    }

    public String caminosDeCosteMinimoEntreElVertice(int posVertice) {
        StringBuilder s = new StringBuilder();
        List<Integer> vertices = new ArrayList<>();
        for (int i = 0; i < this.matrizP.length; i++) {
            if (matrizP[posVertice][i] != 0 && matrizP[posVertice][i] != INFINITO) {
                vertices.add(i);
                s.append("Vertice(").append(posVertice).append("): ");
                s.append("Camino: ").append(vertices);
                s.append(" Costo: ").append(matrizP[posVertice][i]).append("\n");
            }
        }
        return s.toString();
    }

    public String matrizP() {
        StringBuilder s = new StringBuilder("[\n");
        for (Double[] doubles : this.matrizP) {
            for (int j = 0; j < this.matrizP.length; j++) {
                s.append(doubles[j]).append("  ");
            }
            s.append("\n");
        }
        return s +"]";
    }

    public String matrizPred() {
        StringBuilder s = new StringBuilder("[\n");
        for (int[] integers : this.matrizPred) {
            for (int j = 0; j < this.matrizPred.length; j++) {
                s.append(integers[j]).append("  ");
            }
            s.append("\n");
        }
        return s +"]";
    }
}
