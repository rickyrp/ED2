package bo.edu.uagrm.ficct.ed2.grafos.pesados;

/**
 *
 * @author  Ramirez Pineda Ricky Roy
 */
public class AdyacenteConPeso implements Comparable<AdyacenteConPeso>{
    private int indiceDeVertice;
    private double peso;

    public AdyacenteConPeso (int vertice) {
        this.indiceDeVertice = vertice;
    }

    public AdyacenteConPeso (int vertice, double peso) {
        this.indiceDeVertice = vertice;
        this.peso = peso;
    }

    public int getIndiceDeVertice() {
        return this.indiceDeVertice;
    }

    public double getPeso() {
        return this.peso;
    }

    public void setIndiceDeVertice(int vertice) {
        this.indiceDeVertice = vertice;
    }

    public void setPeso (double peso) {
        this.peso = peso;
    }

    @Override
    public int compareTo(AdyacenteConPeso vert) {
        Integer esteVertice = this.indiceDeVertice;
        Integer elOtroVertice = vert.indiceDeVertice;
        return esteVertice.compareTo(elOtroVertice);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.indiceDeVertice;
        return  hash;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if ( otro == null || getClass() != otro.getClass()) {
            return  false;
        }
        AdyacenteConPeso other = (AdyacenteConPeso) otro;
        return  this.indiceDeVertice == other.indiceDeVertice;
    }

}
