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
public class DiGrafo extends Grafo{

    public DiGrafo() {
        super();
    }

    public DiGrafo(int nroDeVertices) throws ExcepcionNroVerticesInvalido {
        super(nroDeVertices);
    }

    @Override
    public int cantidadDeAristas() {
        //suma todos los size de cada lista
        int cant = 0;
        for (List<Integer> adyacentesDeUnVertice : this.listaDeAdyacencias) {
            cant = cant + adyacentesDeUnVertice.size();
        }
        return cant;
    }

    @Override
    public void insertarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaYaExiste {
        if (this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        adyacentesDelOrigen.add(posVerticeDestino);
        Collections.sort(adyacentesDelOrigen);
    }


    @Override
    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
        if (!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw  new ExcepcionAristaNoExiste();
        }
        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        int posicionDelDestino = adyacentesDelOrigen.indexOf(posVerticeDestino);
        adyacentesDelOrigen.remove(posicionDelDestino);
    }

    @Override
    public int gradoDeVertice(int posDeVertice) {
        //return super.gradoDeVertice(posDeVertice);
        throw new UnsupportedOperationException("Metodo no soportado en digrafo");
    }

    public int gradoDeEntradaDeVertice(int posDeVertice) {
        super.validarVertice(posDeVertice);
        int entradaDeVertice = 0;
        /*for (List<Integer> adyacentesDeUnVertice : super.listaDeAdyacencias) {//otra forma de hacerlo
            for (Integer posDeAdyacentes : adyacentesDeUnVertice) {
                if (posDeAdyacentes == posDeVertice) {
                    entradaDeVertice++;
                }
            }
        }*/
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
    /*2. Para un grafo dirigido implementar los métodos insertarVertice, insertarArista,
    eliminarVertice, eliminarArista, cantidadDeVertices, cantidadDeArista, gradoDeVertice*/

    /*3. Para un grafo dirigido implementar método o clase para encontrar si hay ciclos
    sin usar matriz de caminos.*/
    public boolean hayCiclo2daForma() {
        List<Integer> estado = new LinkedList<>();
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            estado.add(i, 0);//marco todos los vetices con cero
        }
        boolean hayCiclo = hayCiclo2daForma(estado,0);

        for (int i = 0; i < estado.size() && !hayCiclo; i++) {
            if (estado.get(i) == 0) {
                hayCiclo = hayCiclo2daForma(estado,i);
            }
        }

        return hayCiclo;
    }

    private boolean hayCiclo2daForma(List<Integer> estado, int posVertice) {
        estado.set(posVertice, 1);
        Iterable<Integer> adyacentesDeVerticeEnTurno = this.adyacentesDeVertice(posVertice);
        for (Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno) {
            if (estado.get(posVerticeAdyacente) == 0) {
                this.hayCiclo2daForma(estado, posVerticeAdyacente);
                if (this.hayCiclo2daForma(estado, posVerticeAdyacente)) {
                    return true;
                }
            } else {
                if (estado.get(posVerticeAdyacente) != 10000)
                    return true;
            }
        }
        estado.set(posVertice, 10000); //10000 sera mi valor de "infinito"

        return false;
    }

    /*4. Para un grafo dirigido implementar método o clase para encontrar si hay ciclos
    usando la matriz de caminos.*/
    public boolean hayCiclo() {
        AlgoritmoDeWarshall matriz = new AlgoritmoDeWarshall(this);
        return matriz.diagonalHayAlgunUno();
    }

    /*5. Para un grafo dirigido implementar un método o clase que sea capas de
    retornar los componentes de las islas que existen en dicho digrafo.*/
    public List<List<Integer>>componentesDeLasIslas() {
        Isla islas = new Isla(this);
        return islas.getComponentesDeLaIsla();
    }

    /*6. Para un grafo dirigido implemente un método o clase para encontrar la matriz
    de caminos de dicho grafo dirigido*/
    public String matrizDeCaminos() {
        AlgoritmoDeWarshall warshall = new AlgoritmoDeWarshall(this);
        return warshall.toString();
    }

    /*7. Para un grafo dirigido implementar un método o clase que permita determinar
    si el digrafo es débilmente conexo*/
    public boolean esDebilmenteConexo() {
        DFS dfsAux = new DFS(this, 0);
        while (!dfsAux.hayCaminosATodos()) {
            //en el recorrido estan los vertices que estan marcados
            Iterable<Integer> recorrido= dfsAux.obtenerRecorrido();
            int vertice = adyacenteNoMarcadoDeUnVerticeMarcado(dfsAux, recorrido);
            if (vertice == -1) {
                return false;
            } else {
                dfsAux.procesarDFS(vertice);
            }
        }

        return true;
    }

    private int adyacenteNoMarcadoDeUnVerticeMarcado(DFS dfAux, Iterable<Integer> recorrido) {
        for (Integer posVertice : recorrido) {
            for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
                if (this.existeAdyacencia(i, posVertice) && !dfAux.hayCaminoAVertice(i)) {
                    return i;
                }
            }
        }

        //retorna -1 si no encuentra un adyacente no marcado, de los vertices marcados
        return -1;
    }

    /*8. Para un grafo dirigido implementar un método o clase que permita determinar
    si el digrafo es fuertemente conexo*/
    public boolean esFuertementeConexo() {
        AlgoritmoDeWarshall matriz = new AlgoritmoDeWarshall(this);
        return matriz.soloHayUnos();
    }

    /*11. Para un grafo dirigido implementar un algoritmo para encontrar el número de
    islas que hay en el grafo*/
    public int cantidadDeIslas() {
        if (this.cantidadDeVertices() == 0) {//si no hay ningun vertice en el grafo
            return 0;
        }
        DFS dfsAux = new DFS(this, 0);
        int cant = 0;

        while (!dfsAux.hayCaminosATodos()) {//si estan todos marcados
            Iterable<Integer> recorrido = dfsAux.obtenerRecorrido();
            int vertice = adyacenteNoMarcadoDeUnVerticeMarcado(dfsAux, recorrido);
            if (vertice == -1) {
                cant++;
                for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
                    if (!dfsAux.hayCaminoAVertice(i)) {
                        dfsAux.procesarDFS(i);
                        i = this.listaDeAdyacencias.size();
                    }
                }
            } else {
                dfsAux.procesarDFS(vertice);
            }
        }

        return cant + 1;
    }

    /*12. Para un grafo dirigido implementar el algoritmo de Wharsall, que luego muestre
    entre que vértices hay camino.*/
    public String caminosAVertice() {
        AlgoritmoDeWarshall warshall = new AlgoritmoDeWarshall(this);
        return warshall.caminosAVertice();
    }

    /*14. Para un grafo dirigido implementar un algoritmo que retorne cuántas componentes
    fuertemente conexas hay en dicho grafo. Definimos formalmente un componente
    fuertemente conectado, C, de un grafo G, como el mayor subconjunto de vértices C (que es
    un subconjunto de los vértices del grafo G) tal que para cada pareja de vértices v,w
    pertenecen a C tenemos una ruta desde v hasta w y una ruta desde w hasta v.*/
    public int cantDeComponentesFuertemeteConexas() {
        if (this.cantidadDeVertices() == 0) {//si no hay ningun vertice en el grafo
            return 0;
        }
        DFS dfsAux = new DFS(this, 0);
        DFS dfsAux2 = new DFS(this, 0);
        int cant = 0;

        while (!dfsAux.hayCaminosATodos()) {//si estan todos marcados
            Iterable<Integer> recorrido = dfsAux2.obtenerRecorrido();
            int vertice = adyacenteNoMarcadoDeUnVerticeMarcado(dfsAux2, recorrido);
            if (vertice == -1) {
                if (esFuertementeConexo(recorrido)) {
                    cant++;
                }
                for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
                    if (!dfsAux.hayCaminoAVertice(i)) {
                        dfsAux2 = new DFS(this, i);
                        dfsAux.procesarDFS(i);
                        i = this.listaDeAdyacencias.size();
                    }
                }
            } else {
                dfsAux.procesarDFS(vertice);
                dfsAux2.procesarDFS(vertice);
            }
        }

        Iterable<Integer> recorrido = dfsAux2.obtenerRecorrido();
        if (esFuertementeConexo(recorrido)) {
            cant++;
        }

        return cant;
    }

    private boolean esFuertementeConexo(Iterable<Integer> recorrido) {
        for (Integer verticeDeLaIslaEnTurno: recorrido) {
            DFS recorridoDFS = new DFS(this, verticeDeLaIslaEnTurno);
            for (Integer verticeEnTurno: recorrido) {
                if (!verticeEnTurno.equals(verticeDeLaIslaEnTurno)) {//sin los lazos
                    if (!recorridoDFS.hayCaminoAVertice(verticeEnTurno)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /*18. Para un grafo dirigido implementar al algoritmo de ordenamiento topológico.
    Debe mostrar cual es el orden de los vértices según este algoritmo.*/
    public Iterable<Integer> algoritmoOT() {
        AlgoritmoOrdenamientoTopologico algoritmoT = new AlgoritmoOrdenamientoTopologico(this);
        return algoritmoT.elRecorrido();
    }

    /*--------------------------------------------------------------------------
                             A D I C I O N A L E S
    --------------------------------------------------------------------------*/

    /*Como saber si hay ciclo ?
    Calculo la matriz de caminos, y si en cualquier posicion de la diagonal
    principal hay algun 1 entoces el digrafo tiene ciclo*/
    //implementado (linea de codigo: 132)


    /*Calculo la matriz de caminos, y si solo hay unos en toda la matriz
    entoces es fuertemente conexo*/
    //implementado (linea de codigo: 180)

    //Realizo un recorrido DFS por cada vertice, y si en cada recorrido quedan
    // todos marcados, es fuertemente conexo y  retorno true.
    public  boolean esFuertementeConexo2daForma() {
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            DFS recorrido = new DFS(this, i);
            if (!recorrido.hayCaminosATodos()) {
                return false;
            }
        }
        return true;
    }

    /*Como saber si el digrafo es debilmente conexo?
    Realizo un DFS y si en el recorrido quedan todos marcados es debilmente
    conexo, si no todos quedan marcados busco un vertice no marcado que tenga
    un adyacente marcado y lo proceso en mi DFS y asi sucesivamente. Si no todos
    quedan marcados y no encuentro un vertice no marcado que tenga un adyacente
    marcado entoces no es debilmente conexo.*/
    //implementado (linea de codigo: 149)


    //Mi digrafo lo convierto en un grafo (grafo no dirigido) y a ese grafo
    //pregunto si es conexo, si es conexo ese grafo, entoces quiere decir que mi
    //digrafo es debilmente conexo
    public boolean esDebilmenteConexo2daForma() throws ExcepcionNroVerticesInvalido,
            ExcepcionAristaYaExiste {
        Grafo grafoAux = new Grafo(this.cantidadDeVertices());
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            List<Integer> adyacentes = this.listaDeAdyacencias.get(i);
            for (Integer vertice: adyacentes) {
                if (!grafoAux.existeAdyacencia(i, vertice)) {
                    grafoAux.insertarArista(i, vertice);
                }
            }
        }
        return grafoAux.esConexo();
    }

    //Otra forma de hacer el ejercicio 5
    public String componentesDeLasIslas2daForma() {
        if (this.cantidadDeVertices() == 0) {//si no hay ningun vertice en el grafo
            return "";
        }
        String s = "";
        int cant = 0;
        DFS dfsAux = new DFS(this, 0);
        DFS dfsAux2 = new DFS(this, 0);

        while(!dfsAux.hayCaminosATodos()) {
            Iterable<Integer> recorrido = dfsAux2.obtenerRecorrido();
            int adyacente = adyacenteNoMarcadoDeUnVerticeMarcado(dfsAux2, recorrido);
            if (adyacente == -1) {
                s = s + "Isla " +cant+ " : " + recorrido + "\n";
                cant++;
                for(int i = 0; i < this.cantidadDeVertices(); i++) {
                    if (!dfsAux.hayCaminoAVertice(i)) {//pregunta si esta marcado ese vertice
                        dfsAux2 = new DFS(this, i);
                        dfsAux.procesarDFS(i);
                        i = this.cantidadDeVertices();
                    }
                }
            } else {
                dfsAux.procesarDFS(adyacente);
                dfsAux2.procesarDFS(adyacente);
            }
        }

        Iterable<Integer> recorrido = dfsAux2.obtenerRecorrido();
        if (dfsAux.hayCaminosATodos()) {
            s = s + "Isla " +cant+ " : " + recorrido + "\n";
        }

        return s;
    }


}
