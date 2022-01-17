package bo.edu.uagrm.ficct.ed2.grafos.pesados;

import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionAristaNoExiste;
import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionAristaYaExiste;
import bo.edu.uagrm.ficct.ed2.grafos.nopesados.RecorridoUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class AlgoritmoFordFulkerson {
    private int fuente;
    private int sumidero;
    private int flujoMax;
    private DiGrafoPesado grafo;
    private RecorridoUtils marcados;


    public AlgoritmoFordFulkerson(DiGrafoPesado grafo) throws ExcepcionAristaYaExiste, ExcepcionAristaNoExiste {
        this.flujoMax = 0;
        this.grafo = grafo;
        List<Integer> listaDeFuentes = new LinkedList<>();
        List<Integer> listaDeSumideros = new LinkedList<>();
        for (int i = 0; i < grafo.cantidadDeVertices(); i++) {
            if (grafo.gradoDeEntradaDeVertice(i) == 0)
                listaDeFuentes.add(i);
            if (grafo.gradoDeSalidaDeVertice(i) == 0)
                listaDeSumideros.add(i);
        }
        this.fuente = listaDeFuentes.get(0);
        if (listaDeFuentes.size() > 1) {
            this.grafo.insertarVertice();
            this.fuente = this.grafo.cantidadDeVertices() - 1;//el ultimo vertice que inserte
            crearFuente(listaDeFuentes, fuente);
        }
        this.sumidero = listaDeSumideros.get(0);
        if (listaDeSumideros.size() > 1) {
            this.grafo.insertarVertice();
            this.sumidero = this.grafo.cantidadDeVertices() - 1;//el ultimo vertice que inserte
            crearSumidero(listaDeSumideros, sumidero);
        }

        this.marcados = new RecorridoUtils(this.grafo.cantidadDeVertices());
        for (int i = 0; i < this.grafo.cantidadDeVertices(); i++) {
            Iterable<Integer> adyacentes = grafo.adyacentesDeVertice(i);
            for (Integer vertice: adyacentes) {
                if (!grafo.existeAdyacencia(vertice, i)) {
                    grafo.insertarArista(vertice, i, 0.0);
                }
            }
        }

        ejecutarAlgoritmo();
    }

    private void crearSumidero(List<Integer> sumideros, int nuevoSumidero) throws ExcepcionAristaYaExiste {
        for (int i = 0; i < sumideros.size(); i++) {
            int sumideroEnTurno = sumideros.get(i);
            this.grafo.insertarArista(sumideroEnTurno, nuevoSumidero, 10000.0);
        }
    }

    private void crearFuente(List<Integer> fuentes, int nuevaFuente) throws ExcepcionAristaYaExiste {
        for (int i = 0; i < fuentes.size(); i++) {
            int fuenteEnTurno = fuentes.get(i);
            this.grafo.insertarArista(nuevaFuente, fuenteEnTurno, 10000.0);
        }
    }

    public AlgoritmoFordFulkerson(int fuente, int sumidero, DiGrafoPesado grafo)
            throws ExcepcionAristaYaExiste, ExcepcionAristaNoExiste {
        this.fuente = fuente;
        this.sumidero = sumidero;
        this.flujoMax = 0;
        this.grafo = grafo;
        this.marcados = new RecorridoUtils(this.grafo.cantidadDeVertices());
        for (int i = 0; i < this.grafo.cantidadDeVertices(); i++) {
            Iterable<Integer> adyacentes = grafo.adyacentesDeVertice(i);
            for (Integer vertice: adyacentes) {
                if (!grafo.existeAdyacencia(vertice, i)) {
                    grafo.insertarArista(vertice, i, 0.0);
                }
            }
        }

        ejecutarAlgoritmo();
    }

    private void ejecutarAlgoritmo() throws ExcepcionAristaNoExiste {
        double flujoMinimo = 0.0;
        int verticeEnTurno  = 0;
        while (!marcados.estaVerticeMarcado(fuente)) {
            boolean puedeSeguir = true;
            List<Integer> flujo = new ArrayList<>();
            Stack<Integer> pilaDeVertices = new Stack<>();
            pilaDeVertices.push(fuente);
            do {
                verticeEnTurno = pilaDeVertices.peek();
                flujo.add(verticeEnTurno);

                List<AdyacenteConPeso> adyacentesEnTurno = grafo.listaDeAdyacencias.get(verticeEnTurno);
                verticeEnTurno = getVerticeConMayorPesoDeEnvio(adyacentesEnTurno, verticeEnTurno, pilaDeVertices);
                if (verticeEnTurno == - 1) {
                    if (pilaDeVertices.peek() == fuente) {
                        puedeSeguir = false;
                    }
                    marcados.marcarVertice(pilaDeVertices.peek());
                    pilaDeVertices.pop();
                } else {
                    pilaDeVertices.add(verticeEnTurno);
                }

                if (verticeEnTurno == sumidero) {
                    puedeSeguir = false;
                    flujo.add(verticeEnTurno);
                    flujoMinimo = flujoMinimo(flujo);
                    flujoMax += flujoMinimo;
                    reducirYAumentarPesos(flujo, flujoMinimo);
                }

            } while (puedeSeguir);

        }
    }

    private void reducirYAumentarPesos(List<Integer> flujo, double flujoMinimo) {
        for (int i = 0; i < flujo.size() - 1; i++) {
            int verticeOrigen = flujo.get(i);
            int verticeDestino = flujo.get(i + 1);

            //Reduzco el peso
            List<AdyacenteConPeso> adyacentesDelOrigen = grafo.listaDeAdyacencias.get(verticeOrigen);
            AdyacenteConPeso unAdyacenteDelOrigen = new AdyacenteConPeso(verticeDestino);
            int posicionDelVertice = adyacentesDelOrigen.indexOf(unAdyacenteDelOrigen);
            AdyacenteConPeso adyacenteACambiarDeOrigen = adyacentesDelOrigen.get(posicionDelVertice);
            adyacenteACambiarDeOrigen.setPeso(adyacenteACambiarDeOrigen.getPeso() - flujoMinimo);

            //Aumento el peso
            List<AdyacenteConPeso> adyacentesDelDestino = grafo.listaDeAdyacencias.get(verticeDestino);
            AdyacenteConPeso unAdyacenteDelDestino = new AdyacenteConPeso(verticeOrigen);
            posicionDelVertice = adyacentesDelDestino.indexOf(unAdyacenteDelDestino);
            AdyacenteConPeso adyacenteACambiarDeDestino = adyacentesDelDestino.get(posicionDelVertice);
            adyacenteACambiarDeDestino.setPeso(adyacenteACambiarDeDestino.getPeso() + flujoMinimo);
        }
    }

    private double flujoMinimo(List<Integer> flujo) throws ExcepcionAristaNoExiste {
        double minimo = grafo.peso(flujo.get(0), flujo.get(1));
        for (int i = 1; i < flujo.size() - 1; i++) {
            int verticeOrigen = flujo.get(i);
            int verticeDestino = flujo.get(i + 1);
            if (minimo > grafo.peso(verticeOrigen, verticeDestino)) {
                minimo = grafo.peso(verticeOrigen, verticeDestino);
            }
        }

        return minimo;
    }

    private int getVerticeConMayorPesoDeEnvio(List<AdyacenteConPeso> adyacentes, int vertice,
                                              Stack<Integer> pilaDeVertices) throws ExcepcionAristaNoExiste {
        int verticeAntecesor = fuente;
        int verticeAux = -1;
        if (pilaDeVertices.size() >= 2) {
            verticeAux = pilaDeVertices.pop();
            verticeAntecesor = pilaDeVertices.pop();
        }
        int verticeConMayorPeso = -1;
        double pesoMayor = 0.0;
        for (AdyacenteConPeso adyacenteConPeso: adyacentes) {
            int verticeEnTurno = adyacenteConPeso.getIndiceDeVertice();
            if (!marcados.estaVerticeMarcado(verticeEnTurno) && verticeEnTurno != verticeAntecesor
                    && verticeEnTurno != fuente) {
                if (grafo.peso(vertice, verticeEnTurno) > pesoMayor) {
                    pesoMayor = grafo.peso(vertice, verticeEnTurno);
                    verticeConMayorPeso = verticeEnTurno;
                }
            }
        }

        if (verticeAux != -1) {
            pilaDeVertices.push(verticeAntecesor);
            pilaDeVertices.push(verticeAux);
        }

        return verticeConMayorPeso;
    }


    private boolean puedeAvanzar() throws ExcepcionAristaNoExiste {//no usado
        int contador = 0;
        List<AdyacenteConPeso> adyacentes = grafo.listaDeAdyacencias.get(fuente);
        int cantDeAdyacentesDeLaFuente = adyacentes.size();
        for (AdyacenteConPeso verticeConPeso : adyacentes) {
            int vertice = verticeConPeso.getIndiceDeVertice();
            if (marcados.estaVerticeMarcado(vertice) || (grafo.peso(fuente, vertice)) <= 0) {
                contador++;
            } else {
                return false;
            }
        }

        return (contador == cantDeAdyacentesDeLaFuente);
    }

    public int getFlujoMax() {
        return flujoMax;
    }

}
