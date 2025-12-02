public class NoDistancia {
    public int origem;
    public int destino;
    public double distancia;
    public NoDistancia prox;

    public NoDistancia(int origem, int destino, double distancia){
        this.origem = origem;
        this.destino = destino;
        this.distancia = distancia;
        this.prox = null;
    }
}
