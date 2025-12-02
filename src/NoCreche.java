public class NoCreche {
    public int id;
    public String creche;
    public NoCreche prox;

    public NoCreche(int id, String creche){
        this.id = id;
        this.creche = creche;
        this.prox = null;
    }

}
