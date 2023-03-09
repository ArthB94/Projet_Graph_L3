import java.util.ArrayList;

public class Tache {
    String nom;
    int duree;
    ArrayList<Tache> contraintes;

    public Tache(String nom, int duree, ArrayList<Tache> contraintes) {
        this.nom = nom;
        this.duree = duree;
        this.contraintes = contraintes;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public ArrayList<Tache> getContraintes() {
        return contraintes;
    }

    public void setContraintes(ArrayList<Tache> contraintes) {
        this.contraintes = contraintes;
    }

    @Override
    public String toString() {
        String ContraintesNames = "[";
        for (Tache tache : contraintes) {
            ContraintesNames += tache.getNom();
            if (contraintes.indexOf(tache) != contraintes.size() - 1) {
                ContraintesNames += ", ";
            }
        }
        return "[" + nom + ", " + duree + "," + ContraintesNames + "]]";
    }

}
