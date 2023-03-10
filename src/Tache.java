import java.util.ArrayList;

public class Tache {
    String nom;
    int duree;
    int rang = -1;
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

    public int getRang() {
        return rang;
    }

    // permet de modifier le rang de la tache en parametre
    public void setRang(int rang) {
        this.rang = rang;
    }

    // permet d'utiliser SetRangs de la class TableauDeContrainte
    public boolean setRang() {
        if (rang != 0) {
            if (contraintes.get(0).getRang() == -1) {
                setRang(-1);
                ;
                return false;
            } else {
                setRang(contraintes.get(0).getRang() + 1);

                for (Tache tache : contraintes) {
                    if (tache.getRang() > rang) {
                        rang = tache.getRang() + 1;
                    }
                }

            }
        }
        return true;
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
        return "[" + nom + ", " + duree + ", " + ContraintesNames + "]" + ", Rang= " + rang + "]";
    }

}
