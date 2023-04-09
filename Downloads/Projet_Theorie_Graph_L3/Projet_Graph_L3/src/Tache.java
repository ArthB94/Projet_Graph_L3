import java.lang.reflect.Array;
import java.util.ArrayList;

public class Tache {
    String nom;
    int duree;
    int rang = -1;
    int dateTot = -1;
    int dateTard = -1;
    int marge = -1;
    ArrayList<Tache> contraintes;
    ArrayList<Tache> successeurs = new ArrayList<Tache>();

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

    public int getDateTot() {
        return dateTot;
    }

    // permet de modifier le dateTot de la tache en parametre
    public void setDateTot(int dateTot) {
        this.dateTot = dateTot;
    }

    // permet de modifier le dateTot de la tache en parametre de manière récursive
    public void setDateTot() {
        if (dateTot == -1) {
            dateTot = 0;
        }
        for (Tache succ : successeurs) {
            if (succ.dateTot == -1) {
                succ.dateTot = dateTot + duree;
            }
            // si la dateTot du successeur est plus petite que la dateTot calculée, alors on
            // remplace la valeur
            else {
                if (succ.dateTot < dateTot + duree) {
                    succ.dateTot = dateTot + duree;
                }
            }
            // on appelle la fonction récursive pour tous les successeurs
            succ.setDateTot();
        }

    }

    public int getDateTard() {
        return dateTard;
    }

    // permet de modifier le dateTard de la tache en parametre
    public void setDateTard(int dateTard) {
        this.dateTard = dateTard;
    }

    // permet de modifier le dateTard de la tache en parametre de manière récursive
    public void setDateTard() {
        if (dateTard == -1) {
            dateTard = dateTot;
        }

        for (Tache pred : contraintes) {
            if (pred.dateTard == -1) {
                pred.dateTard = dateTard - pred.duree;
            } else {
                // si la dateTard du prédécesseur est plus grande que la dateTard calculée,
                // alors on remplace la valeur
                if (pred.dateTard > dateTard - pred.duree) {
                    pred.dateTard = dateTard - pred.duree;
                }
            }
            // appel de la fonction récursive pour tous les prédécesseurs
            pred.setDateTard();
        }
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
        ContraintesNames += "]";
        String SuccesseursNames = "[";
        for (Tache tache : successeurs) {
            SuccesseursNames += tache.getNom();
            if (successeurs.indexOf(tache) != successeurs.size() - 1) {
                SuccesseursNames += ", ";
            }
        }
        SuccesseursNames += "]";
        return "[" + nom + ", " + duree + ", " + ContraintesNames + "," + SuccesseursNames + ", Rang= " + rang
                + ", DateTot= "
                + dateTot
                + ", DateTard= " + dateTard + ", MargeTotal=" + marge + "]";
    }

}
