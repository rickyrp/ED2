package bo.edu.uagrm.ficct.ed2.grafos.nopesados;

import java.util.List;

/**
 *
 * @author  Ramirez Pineda Ricky Roy
 */
public class AlgoritmoDeWarshall {
    private int[][] matriz;

    public AlgoritmoDeWarshall (Grafo unGrafo) {
        matriz = new int[unGrafo.listaDeAdyacencias.size()][unGrafo.listaDeAdyacencias.size()];
        for (int i = 0; i < matriz.length; i++) {//marca con cero toda la matriz
            for (int j = 0; j < matriz.length; j++) {
                matriz[i][j] = 0;
            }
        }
        ejecutarAlgoritmoWarshall(unGrafo);
    }

    private void ejecutarAlgoritmoWarshall(Grafo unGrafo) {
        this.matrizDeAdyacencia(unGrafo);
        for (int k = 0; k < this.matriz.length; k++) {
            for (int i = 0; i < this.matriz.length; i++) {
                for (int j = 0; j < this.matriz.length; j++) {
                    matriz[i][j] = matriz[i][j] | (matriz[i][k] & matriz[k][j]);
                }
            }
        }
    }

    private void matrizDeAdyacencia(Grafo unGrafo) {
        for (int i = 0; i < unGrafo.listaDeAdyacencias.size(); i++) {
            List<Integer> adyacenteEnTurno = unGrafo.listaDeAdyacencias.get(i);
            for (Integer adyacente : adyacenteEnTurno) {
                this.matriz[i][adyacente] = 1;
            }
        }
    }

    public boolean soloHayUnos() {//si hay puros 1 en la matriz
        for (int i = 0; i < this.matriz.length; i++) {
            for (int j = 0; j < this.matriz.length; j++) {
                if (this.matriz[i][j] != 1) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean diagonalHayAlgunUno() { //para saber si hay ciclo
        int j = 0;
        for (int i = 0; i < this.matriz.length; i++) {
            if (this.matriz[i][j] == 1) {
                return true;
            }
            j++;
        }

        return false;
    }

    public boolean hayCamino(int posVerticeOrigen, int posVerticeDestino) {
        return this.matriz[posVerticeOrigen][posVerticeDestino] == 1;
    }

    public int[][] getMatriz() {
        return this.matriz;
    }

    public String caminosAVertice() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < matriz.length; i++) {
            s.append(i).append(" : [");
            for (int j = 0; j < matriz.length; j++) {
                if (matriz[i][j] != 0) {
                    s.append(j).append(", ");
                }
            }
            s.append("]\n");
        }

        return s.toString();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[\n");
        for (int[] ints : this.matriz) {
            for (int j = 0; j < this.matriz.length; j++) {
                s.append(ints[j]).append("  ");
            }
            s.append("\n");
        }
        return s.toString() + ']';
    }
}
