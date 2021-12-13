/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import bo.edu.uagrm.ficct.ed2.arboles.*;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Ramirez Pineda Ricky Roy
 */
public class TestArbol {

    public static void main(String[] args) throws ExcepcionOrdenNoValido, ExcepcionClaveNoExiste {
        //IArbolBusqueda<String, String> arbol;
        ArbolBinarioBusqueda<String, String> arbolABB;
        ArbolMViasBusqueda<Integer, String> arbolAMV;
        Scanner entrada = new Scanner(System.in);

        System.out.print("Elija un tipo de arbol (ABB, AVL, AMV, AB): ");
        String tipoArbol = entrada.next();
        switch (tipoArbol) {
            case "ABB":
                //arbol = new ArbolBinarioBusqueda<>();
                System.out.println("----------------------------------------");
                System.out.println("        ARBOL BINARIO DE BUSQUEDA");
                System.out.println("----------------------------------------");
                break;
            case "AVL":
                //arbol = new AVL<>();
                System.out.println("----------------------------------------");
                System.out.println("                ARBOL AVL               ");
                System.out.println("----------------------------------------");
                break;
            case "AMV":
                //arbol = new ArbolMViasBusqueda<>(4);
                System.out.println("\n----------------------------------------");
                System.out.println("        ARBOL MVIAS DE BUSQUEDA");
                System.out.println("----------------------------------------");
                break;
            case "AB":
                //arbol = new ArbolB<>(4);
                System.out.println("----------------------------------------");
                System.out.println("                  ARBOL B               ");
                System.out.println("----------------------------------------");
                break;
            default:
                System.out.println("Tipo de arbol invalido. Usando ArbolBinarioBusqueda");
                //arbol = new ArbolBinarioBusqueda<>();
                tipoArbol = "ABB";
                break;
        }

        /*--------------------------------------------------------------------------
                               EJERCICIOS DEL PRACTICO #1
        --------------------------------------------------------------------------*/

        List<String> clavesInOrden = new ArrayList<>();
        clavesInOrden.add("CA");
        clavesInOrden.add("CF");
        clavesInOrden.add("CP");
        clavesInOrden.add("CZ");
        clavesInOrden.add("EK");
        clavesInOrden.add("FE");
        clavesInOrden.add("HM");
        clavesInOrden.add("LP");
        clavesInOrden.add("MK");
        clavesInOrden.add("TA");
        clavesInOrden.add("VB");

        List<String> valoresInOrden = new ArrayList<>();
        valoresInOrden.add("Juan");
        valoresInOrden.add("Pedro");
        valoresInOrden.add("Carlos");
        valoresInOrden.add("Jhon");
        valoresInOrden.add("Pepe");
        valoresInOrden.add("Smith");
        valoresInOrden.add("Meg");
        valoresInOrden.add("Marge");
        valoresInOrden.add("Homero");
        valoresInOrden.add("Bart");
        valoresInOrden.add("Lisa");

        List<String> clavesPostOrden = new ArrayList<>();
        clavesPostOrden.add("CF");
        clavesPostOrden.add("CA");
        clavesPostOrden.add("CZ");
        clavesPostOrden.add("CP");
        clavesPostOrden.add("FE");
        clavesPostOrden.add("EK");
        clavesPostOrden.add("LP");
        clavesPostOrden.add("MK");
        clavesPostOrden.add("VB");
        clavesPostOrden.add("TA");
        clavesPostOrden.add("HM");

        List<String> valoresPostOrden = new ArrayList<>();
        valoresPostOrden.add("Pedro");
        valoresPostOrden.add("Juan");
        valoresPostOrden.add("Jhon");
        valoresPostOrden.add("Carlos");
        valoresPostOrden.add("Smith");
        valoresPostOrden.add("Pepe");
        valoresPostOrden.add("Marge");
        valoresPostOrden.add("Homero");
        valoresPostOrden.add("Lisa");
        valoresPostOrden.add("Bart");
        valoresPostOrden.add("Meg");

        List<String> clavesPreOrden = new ArrayList<>();
        clavesPreOrden.add("HM");
        clavesPreOrden.add("EK");
        clavesPreOrden.add("CP");
        clavesPreOrden.add("CA");
        clavesPreOrden.add("CF");
        clavesPreOrden.add("CZ");
        clavesPreOrden.add("FE");
        clavesPreOrden.add("TA");
        clavesPreOrden.add("MK");
        clavesPreOrden.add("LP");
        clavesPreOrden.add("VB");

        List<String> valoresPreOrden = new ArrayList<>();
        valoresPreOrden.add("Meg");
        valoresPreOrden.add("Pepe");
        valoresPreOrden.add("Carlos");
        valoresPreOrden.add("Juan");
        valoresPreOrden.add("Pedro");
        valoresPreOrden.add("Jhon");
        valoresPreOrden.add("Smith");
        valoresPreOrden.add("Bart");
        valoresPreOrden.add("Homero");
        valoresPreOrden.add("Marge");
        valoresPreOrden.add("Lisa");

        if (tipoArbol.equals("AVL")) {
            arbolABB = new AVL<>();
            //1. Para el árbol AVL implemente el método insertar
            System.out.println("EJ#1) Metodo de insertar: ");
            System.out.println(arbolABB);
            arbolABB.insertar("HK", "Juan");
            arbolABB.insertar("EK", "Pedro");
            arbolABB.insertar("TA", "Pepe");
            arbolABB.insertar("VB", "Juana");
            arbolABB.insertar("CA", "Maria");
            arbolABB.insertar("FE", "Jhon");

            //2. Para el árbol AVL implemente el método eliminar
            System.out.println("EJ#2) Metodo de eliminar: ");
            arbolABB.eliminar("HK");
            System.out.println(arbolABB);
        }

        if (tipoArbol.equals("ABB")) {
            System.out.println("Claves InOrden para la reconstruccion: " + clavesInOrden);
            System.out.println("Claves PostOrden para la reconstruccion: " + clavesPostOrden);
            //System.out.println("Claves PreOrden para la reconstruccion: " + clavesPreOrden + "\n");

            /*10. Implemente un método que reciba en listas de parámetros las llaves y
            valores de los recorridos en postorden e inorden respectivamente y que
            reconstruya el árbol binario original. Su método no debe usar el método insertar*/
            System.out.println("EJ#10) Reconstruccion del arbol binario de busqueda: \n");
            arbolABB = new ArbolBinarioBusqueda<>(
                    clavesInOrden, valoresInOrden, clavesPostOrden, valoresPostOrden, false);
            System.out.println(arbolABB);

            /*7. Implemente un método iterativo con el recorrido en inorden que retorne la
            cantidad de nodos que tienen ambos hijos distintos de vacío en un árbol binario*/
            System.out.println("EJ#7) cant de nodos con ambos hijos distinto de vacio (InOrden): "
                    +arbolABB.ambosHijosInOrden());

            /*8. Implemente un método recursivo que retorne la cantidad de nodos que tienen
            un solo hijo no vació*/
            System.out.println("EJ#8) cant de nodos con un solo hijo distinto de vacio: "+arbolABB.unSoloHijo());

            /*9. Implemente un método iterativo con la lógica de un recorrido en inOrden
            que retorne el número de hijos vacios que tiene un árbol binario.*/
            System.out.println("EJ#9) cant de hijos vacios (InOrden): "+arbolABB.hijosVacioInOrden());

            /*11. Implemente un método privado que reciba un nodo binario de un árbol binario
            y que retorne cuál sería su predecesor inorden de la clave de dicho nodo.*/
            System.out.println("EJ#11) predecesor del nodo raiz: " + arbolABB.predecesor());

            /*13. Implemente una clase ArbolBinarioBusquedaEnteroCadena que usando como
            base el ArbolBinarioBusqueda ya no sea un árbol genérico, si no un árbol binario
            de búsqueda con claves enteras y valores cadena*/
            ArbolBinarioBusquedaEnteroCadena arbolEnteroCadena = new ArbolBinarioBusquedaEnteroCadena();
            arbolEnteroCadena.insertar(55, "Juan");
            arbolEnteroCadena.insertar(35, "Pedro");
            System.out.println(arbolEnteroCadena);

            /*15. Para un árbol binario de búsqueda implemente un método que reciba como
            parámetro otro árbol y que retorne verdadero si los arboles son similares,
            falso en caso contrario.*/
            System.out.println("EJ#15) ¿Los arboles son iguales?: "+arbolABB.sonSimilares(arbolABB));
        }

        if (tipoArbol.equals("AMV")) {
            arbolAMV = new ArbolMViasBusqueda<>(4);
            //5. Para el árbol mvias implemente el método insertar
            System.out.println("EJ#5) Metodo de insertar: ");
            arbolAMV.insertar(80, "Juan");
            arbolAMV.insertar(120, "Pedro");
            arbolAMV.insertar(200, "Carlos");
            arbolAMV.insertar(50, "Pepe");
            arbolAMV.insertar(70, "Juana");
            arbolAMV.insertar(75, "Maria");
            arbolAMV.insertar(98, "Janna");
            arbolAMV.insertar(110, "Lucia");
            arbolAMV.insertar(130, "Daniel");
            arbolAMV.insertar(140, "Rodolfo");
            arbolAMV.insertar(150, "Francis");
            arbolAMV.insertar(400, "Jhon");
            arbolAMV.insertar(500, "Luisa");
            arbolAMV.insertar(560, "Isabela");
            arbolAMV.insertar(72, "Pepe");
            arbolAMV.insertar(134, "Maggi");
            arbolAMV.insertar(160, "Bart");
            arbolAMV.insertar(170, "Homero");
            arbolAMV.insertar(190, "Lisa");
            arbolAMV.insertar(158, "Marge");
            System.out.println(arbolAMV);

            //6. Para el árbol mvias implemente el método eliminar
            System.out.println("EJ#6) Metodo de eliminar: ");
            arbolAMV.eliminar(560);
            arbolAMV.eliminar(80);
            arbolAMV.eliminar(160);
            System.out.println(arbolAMV);

            /*12. Implemente un método que retorne verdadero si solo hay nodos completos
            en el nivel n de un árbol m vias. Falso en caso contrario.*/
            System.out.println("EJ#12) Solo nodos completos en nivelN?: " + arbolAMV.nodosCompletosEnNivelN(2));

            /*14. Para un árbol m vías implementar un método que reciba otro árbol de parámetro
            y que retorne verdadero si los arboles son similares. Falso en caso contrario.*/
            System.out.println("EJ#14) ¿Los arboles son similares?: " + arbolAMV.sonSimilares(arbolAMV));
        }

        if (tipoArbol.equals("AB")) {
            arbolAMV = new ArbolB<>(4);
            //3. Para el árbol B implemente el método insertar
            System.out.println("EJ#3) Metodo de insertar: ");
            arbolAMV.insertar(300, "Juan");
            arbolAMV.insertar(500, "Pedro");
            arbolAMV.insertar(100, "Carlos");
            arbolAMV.insertar(50, "Pepe");
            arbolAMV.insertar(400, "Juana");
            arbolAMV.insertar(800, "Maria");
            arbolAMV.insertar(90, "Janna");
            arbolAMV.insertar(91, "Lucia");
            arbolAMV.insertar(70, "Daniel");
            arbolAMV.insertar(75, "Rodolfo");
            arbolAMV.insertar(99, "Francis");
            System.out.println(arbolAMV);

            //4. Para el árbol B implemente el método eliminar NO LO HICE :(
            System.out.println("EJ#4) Metodo de insertar: ");

        }
    }
}
