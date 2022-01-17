package ui;

import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionAristaNoExiste;
import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionAristaYaExiste;
import bo.edu.uagrm.ficct.ed2.grafos.excepciones.ExcepcionNroVerticesInvalido;
import bo.edu.uagrm.ficct.ed2.grafos.nopesados.DiGrafo;
import bo.edu.uagrm.ficct.ed2.grafos.nopesados.Grafo;
import bo.edu.uagrm.ficct.ed2.grafos.pesados.*;

import java.util.Scanner;

/**
 *
 * @author  Ramirez Pineda Ricky Roy
 */
public class TestGrafo {

    public static void main(String[] args) throws ExcepcionAristaYaExiste, ExcepcionNroVerticesInvalido,
            ExcepcionAristaNoExiste {

        Scanner entrada = new Scanner(System.in);

        System.out.print("Elija un tipo de grafo (G, DG, GP, DGP): ");
        String tipoGrafo = entrada.next();
        switch (tipoGrafo) {
            case "G":
                System.out.println("----------------------------------------");
                System.out.println("                 GRAFO                  ");
                System.out.println("----------------------------------------");
                break;
            case "DG":
                System.out.println("----------------------------------------");
                System.out.println("                DIGRAFO                 ");
                System.out.println("----------------------------------------");
                break;
            case "GP":
                System.out.println("----------------------------------------");
                System.out.println("              GRAFO PESADO              ");
                System.out.println("----------------------------------------");
                break;
            case "DGP":
                System.out.println("----------------------------------------");
                System.out.println("             DIGRAFO PESADO             ");
                System.out.println("----------------------------------------");
                break;
            default:
                System.out.println("Tipo de grafo invalido. Usando Grafo");
                tipoGrafo = "G";
                break;
        }

        /*--------------------------------------------------------------------------
                               EJERCICIOS DEL PRACTICO #2
        --------------------------------------------------------------------------*/

        if (tipoGrafo.equals("G")) {
            /*1. Para un grafo no dirigido implementar los métodos insertarVertice, insertarArista,
            eliminarVertice, eliminarArista, cantidadDeVertices, cantidadDeArista, gradoDeVertice*/
            System.out.println("EJ#1) \n");
            Grafo g = new Grafo(5);
            g.insertarArista(0,3);
            g.insertarArista(0,4);
            g.insertarArista(1,2);
            g.insertarArista(1,4);
            g.insertarArista(2,3);
            g.insertarVertice();
            g.eliminarArista(0, 4);
            g.eliminarVertice(4);
            g.insertarVertice();
            g.insertarVertice();
            g.insertarArista(4, 5);
            g.insertarArista(5, 6);
            g.insertarArista(6, 4);

            System.out.println(g);
            System.out.println("cantidad de vertices: " + g.cantidadDeVertices());
            System.out.println("cantidad de aristas: " + g.cantidadDeAristas());
            System.out.println("Grado del vertice: " + g.gradoDeVertice(0) + "\n");

            /*9. Para un grafo no dirigido implementar un método o clase que permita encontrar
            si en dicho grafo hay ciclo.*/
            System.out.println("EJ#9) Hay ciclo?: " + g.hayCiclo2daForma() + "\n");

            /*10. Para un grafo no dirigido implementar método o clase para encontrar los componentes
            de las diferentes islas que hay en dicho grafo*/
            System.out.println("EJ#10) Componentes de las islas: "+ g.componentesDeLasIslas());
        }

        if (tipoGrafo.equals("DG")) {
            /*2. Para un grafo dirigido implementar los métodos insertarVertice, insertarArista,
            eliminarVertice, eliminarArista, cantidadDeVertices, cantidadDeArista, gradoDeVertice*/
            System.out.println("EJ#2) \n");

            DiGrafo dg = new DiGrafo(11);
            dg.insertarArista(0, 8);
            dg.insertarArista(0, 10);
            dg.insertarArista(9, 8);
            dg.insertarArista(1, 4);
            dg.insertarArista(4, 5);
            dg.insertarArista(5, 2);
            dg.insertarArista(5, 3);
            dg.insertarArista(2, 7);
            dg.insertarArista(7, 5);
            dg.insertarVertice();
            dg.insertarVertice();
            dg.insertarArista(11, 12);
            dg.eliminarArista(11, 12);
            dg.eliminarVertice(12);
            dg.eliminarVertice(11);

            System.out.println(dg);
            System.out.println("cantidad de vertices: " + dg.cantidadDeVertices());
            System.out.println("cantidad de aristas: " +dg.cantidadDeAristas());
            System.out.println("Grado de salida del vertice: " + dg.gradoDeSalidaDeVertice(0));
            System.out.println("Grado de entrada del vertice: " + dg.gradoDeEntradaDeVertice(0) + "\n");

            /*3. Para un grafo dirigido implementar método o clase para encontrar si hay ciclos
            sin usar matriz de caminos.*/
            System.out.println("EJ#3) Hay ciclo (sin matriz de caminos)?: " + dg.hayCiclo2daForma() + "\n");

            /*4. Para un grafo dirigido implementar método o clase para encontrar si hay ciclos
            usando la matriz de caminos.*/
            System.out.println("EJ#4) Hay ciclo?: " + dg.hayCiclo() + "\n");

            /*5. Para un grafo dirigido implementar un método o clase que sea capas de
            retornar los componentes de las islas que existen en dicho digrafo.*/
            System.out.println("EJ#5) Componentes de las islas: "+ dg.componentesDeLasIslas() + "\n");

            /*6. Para un grafo dirigido implemente un método o clase para encontrar la matriz
            de caminos de dicho grafo dirigido*/
            System.out.println("EJ#6) Matriz de caminos: " + dg.matrizDeCaminos() + "\n");

            /*7. Para un grafo dirigido implementar un método o clase que permita determinar
            si el digrafo es débilmente conexo*/
            System.out.println("EJ#7) Debilemente Conexo?: " + dg.esDebilmenteConexo() + "\n");

            /*8. Para un grafo dirigido implementar un método o clase que permita determinar
            si el digrafo es fuertemente conexo*/
            System.out.println("EJ#8) Fuertemente Conexo?: " + dg.esFuertementeConexo() + "\n");

            /*11. Para un grafo dirigido implementar un algoritmo para encontrar el número de
            islas que hay en el grafo*/
            System.out.println("EJ#11) Cantidad de islas: " + dg.cantidadDeIslas() + "\n");

            /*12. Para un grafo dirigido implementar el algoritmo de Wharsall, que luego muestre
            entre que vértices hay camino.*/
            System.out.println("EJ#12) Camino entre vertices: \n" + dg.caminosAVertice() + "\n");

            /*14. Para un grafo dirigido implementar un algoritmo que retorne cuántas componentes
            fuertemente conexas hay en dicho grafo. Definimos formalmente un componente
            fuertemente conectado, C, de un grafo G, como el mayor subconjunto de vértices C (que es
            un subconjunto de los vértices del grafo G) tal que para cada pareja de vértices v,w
            pertenecen a C tenemos una ruta desde v hasta w y una ruta desde w hasta v.*/
            System.out.println("EJ#14) cant de componentes : " + dg.cantDeComponentesFuertemeteConexas() + "\n");

            /*18. Para un grafo dirigido implementar al algoritmo de ordenamiento topológico.
            Debe mostrar cual es el orden de los vértices según este algoritmo.*/
            DiGrafo dg2 = new DiGrafo(8);
            dg2.insertarArista(0,1);
            dg2.insertarArista(1,3);
            dg2.insertarArista(2,1);
            dg2.insertarArista(3,4);
            dg2.insertarArista(4,5);
            dg2.insertarArista(5,7);
            dg2.insertarArista(7, 6);
            System.out.println("EJ#18) Ordenamiento topologico : " + dg2.algoritmoOT());
        }

        if (tipoGrafo.equals("GP")) {
            GrafoPesado gp = new GrafoPesado(10);
            gp.insertarArista(0,1,5.0);
            gp.insertarArista(0,2,10.0);
            gp.insertarArista(0,3,8.0);
            gp.insertarArista(1,3,6.0);
            gp.insertarArista(1,5,5.0);
            gp.insertarArista(2,3,7.0);
            gp.insertarArista(2,4,8.0);
            gp.insertarArista(2,7,15.0);
            gp.insertarArista(3,5,11.0);
            gp.insertarArista(3,4,5.0);
            gp.insertarArista(4,7,3.0);
            gp.insertarArista(4,6,4.0);
            gp.insertarArista(5,6,9.0);
            gp.insertarArista(5,8,7.0);
            gp.insertarArista(6,8,4.0);
            gp.insertarArista(6,9,6.0);
            gp.insertarArista(6,7,12.0);
            gp.insertarArista(7,9,12.0);
            gp.insertarArista(8,9,7.0);

            /*16. Para un grafo no dirigido pesado implementar el algoritmo de Kruskal que
            muestre cual es el grafo encontrado por el algoritmo*/
            System.out.println("EJ#16) Algoritmo De Kruskal");
            AlgoritmoDeKruskal kruskal = new AlgoritmoDeKruskal(gp);
            System.out.println(kruskal.getNuevoGrafo());

            /*17. Para un grafo no dirigido pesado implementar el algoritmo de Prim que
            muestre cual es el grafo encontrado por el algoritmo*/
            System.out.println("EJ#17) Algoritmo De Prim");
            AlgoritmoDePrim prim = new AlgoritmoDePrim(gp);
            System.out.println(prim.getNuevoGrafo());
        }

        if (tipoGrafo.equals("DGP")) {
            DiGrafoPesado dgp = new DiGrafoPesado(6);
            dgp.insertarArista(0,1, 50);
            dgp.insertarArista(0,2, 10);
            dgp.insertarArista(0,4, 60);
            dgp.insertarArista(0,5, 100);
            dgp.insertarArista(1,3, 50);
            dgp.insertarArista(1,4, 15);
            dgp.insertarArista(2,1, 5);
            dgp.insertarArista(3,0, 80);
            dgp.insertarArista(3,5, 20);
            dgp.insertarArista(4,5, 20);
            dgp.insertarArista(5,1, 40);
            dgp.insertarArista(5,2, 70);

            /*13. Para un grafo dirigido pesado usando la implementación del algoritmo de Floyd-Wharsall
            encontrar los caminos de costo mínimo entre un vértice a y el resto. Mostrar los costos
            y cuáles son los caminos*/
            System.out.println("EJ#13) Algoritmo De Floyd-Warshall");
            AlgoritmoFloydWarshall floydWarshall = new AlgoritmoFloydWarshall(dgp);
            System.out.println(floydWarshall.caminosDeCosteMinimoEntreElVertice(0));

            /*15. Para un grafo dirigido pesado implementar el algoritmo de Dijkstra que muestre
            con que vértices hay caminos de costo mínimo partiendo desde un vértice v, con qué costo
            y cuáles son los caminos.*/
            System.out.println("EJ#15) Algoritmo De Dijkstra");
            System.out.println(dgp.caminoAVerticesYCostos(0));

            //19. Para un grafo dirigido pesado implementar el algoritmo de Ford-Fulkerson
            DiGrafoPesado dgp2 = new DiGrafoPesado(8);
            dgp2.insertarArista(0, 1, 20.0);
            dgp2.insertarArista(0, 2, 40.0);
            dgp2.insertarArista(0, 3, 10.0);
            dgp2.insertarArista(1, 4, 50.0);
            dgp2.insertarArista(2, 1, 20.0);
            dgp2.insertarArista(2, 6, 20.0);
            dgp2.insertarArista(3, 5, 20.0);
            dgp2.insertarArista(3, 6, 30.0);
            dgp2.insertarArista(4, 5, 15.0);
            dgp2.insertarArista(4, 7, 70.0);
            dgp2.insertarArista(5, 4, 45.0);
            dgp2.insertarArista(5, 7, 40.0);
            dgp2.insertarArista(6, 7, 10.0);

            System.out.println("EJ#19) Algoritmo De Ford-Fulkerson");
            AlgoritmoFordFulkerson ford = new AlgoritmoFordFulkerson(dgp2);
            System.out.println("Flujo Maximo: " + ford.getFlujoMax());
        }

    }
}
