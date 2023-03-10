import java.util.ArrayList;
import java.util.List;

public class TableauDeContrainte {
    // j'ai fait une liste de tache pour faire des parcour plus facilement mais ce
    // n'est peut etre pas utile, le plus important étant la matrice pour l'instant
    // pour la fonction de detection de circuit
    ArrayList<Tache> taches;
    String[][] matrice;
    private int nbSommets;
    private int nbArcs;

    public TableauDeContrainte(ArrayList<Tache> taches) {
        this.taches = taches;
    }

    public TableauDeContrainte(int[][] tab) {
        // initialistaion des attributs
        taches = new ArrayList<Tache>();
        // nomTache permet de savoir quels seronts les prédécésseurs d'omega
        List<String> nomTaches = new ArrayList<String>();
        // initialisation d'une matrice avec des "." pour les cases vides
        matrice = new String[tab.length + 3][tab.length + 3];
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                matrice[i][j] = ".";
            }
        }

        // ajout de la tache alpha
        taches.add(new Tache(new String("a"), 0, new ArrayList<Tache>()));
        matrice[1][0] = "a";
        matrice[0][1] = "a";
        // ajout des taches
        for (int i = 0; i < tab.length; i++) {
            taches.add(new Tache(Integer.toString(tab[i][0]), tab[i][1], new ArrayList<Tache>()));
            matrice[0][i + 2] = Integer.toString(tab[i][0]);
            matrice[i + 2][0] = Integer.toString(tab[i][0]);
            nomTaches.add(Integer.toString(tab[i][0]));

        }
        // ajout de la tache omega
        taches.add(new Tache(new String("w"), 0, new ArrayList<Tache>()));
        matrice[0][tab.length + 2] = "w";
        matrice[tab.length + 2][0] = "w";
        // ajout des contraintes
        for (int i = 0; i < tab.length; i++) {
            // si la tache n'a pas de prédécésseur on lui met alpha comme prédécésseur
            if (tab[i].length == 2) {
                taches.get(i + 1).contraintes.add(taches.get(0));
                matrice[1][i + 2] = "0";
            }
            // on ajoute les prédécésseurs de la tache
            for (int j = 2; j < tab[i].length; j++) {
                taches.get(i + 1).contraintes.add(taches.get(tab[i][j]));
                matrice[tab[i][j] + 1][i + 2] = Integer.toString(taches.get(tab[i][j]).duree);
                nomTaches.remove(Integer.toString(tab[i][j]));
            }
        }
        // on ajoute les prédécésseurs d'omega
        for (int j = 0; j < nomTaches.size(); j++) {
            taches.get(taches.size() - 1).contraintes.add(taches.get(Integer.parseInt(nomTaches.get(j))));
            matrice[Integer.parseInt(nomTaches.get(j)) + 1][tab.length + 2] = Integer
                    .toString(taches.get(Integer.parseInt(nomTaches.get(j))).duree);
        }

    }

    public boolean DetectionNegativité() {
        for (Tache t : taches) {
            if (t.duree < 0) {
                System.out.println("La tache " + t.nom + " a une duree negative");
                return true;
            }
        }
        System.out.println("Il n'y a pas de duree negative");
        return false;
    }

    public boolean DetectionCircuit() {
        System.out.println("Il y a un seul point d entrée, a\nIl y a un seul point de sortie, w");
        System.out.println("Detection de circuit");
        System.out.println("Methode de suppression des points d'entrée");
        System.out.println(this.MatriceToString());
        // initialisation de la liste des sommets à étudier
        ArrayList<Integer> sommets = new ArrayList<Integer>();
        for (int i = 0; i < matrice.length; i++) {
            sommets.add(i);
        }
        // initialisation de la variable permettant de savoir si on est dans une boucle
        int sizePres = 0;
        while (sommets.size() != sizePres) {
            sizePres = sommets.size();
            // boucle permettant de savoir si un sommet est un point d'entrée
            for (int j = 0; j < sommets.size(); j++) {
                boolean pointEntré = true;
                for (int i = 0; i < sommets.size(); i++) {
                    // si une case de la colone n'est pas vide on est pas sur un point d'entrée
                    if (i != 0 && matrice[sommets.get(i)][sommets.get(j)] != ".") {
                        pointEntré = false;

                    }
                }
                if (pointEntré && j != 0) {
                    // si on est sur un point d'entrée on supprime se sommet de la liste des sommets
                    // à considérer
                    System.out.println("Points d'entrée: " + matrice[sommets.get(j)][0]);
                    System.out.println("Suppression de " + matrice[sommets.get(j)][0]);
                    sommets.remove(sommets.get(j));
                    // si le la liste des sommets ne posséde plus qu'un element alors il n'y a pas
                    // de circuit
                    if (sommets.size() == 1) {
                        System.out.println("Il n'y a pas de circuit");
                        return false;
                    }
                    // sinon on affiche la liste des sommets restants
                    String sommet = "";
                    for (int i = 1; i < sommets.size(); i++) {
                        sommet += matrice[sommets.get(i)][0] + " ";
                    }
                    System.out.println("Sommets: " + sommet);

                    // on afficher l'état de la matrice, c'est peut étre provisoir
                    /*
                     * System.out.println("matrice\n");
                     * String m = "";
                     * for (int y = 0; y < sommets.size(); y++) {
                     * for (int z = 0; z < sommets.size(); z++) {
                     * m += matrice[sommets.get(y)][sommets.get(z)] + "\t";
                     * ;
                     * }
                     * m += "\n";
                     * }
                     * System.out.println(m);
                     */
                    ///////////////////////////

                    j--;
                }

            }
        }
        // si il n'y a pas eu de suppression de sommet alors il y a un circuit
        System.out.println("Il y a un circuit");
        return true;

    }

    // set les rangs des taches qui par défaut sont à -1 à vérifier si ça fonctionne
    // bien
    public void SetRangs() {
        taches.get(0).setRang(0);
        boolean good = false;
        while (good == false) {
            good = true;
            for (Tache t : taches) {
                if (!t.setRang()) {
                    good = false;
                }
                ;
            }
        }

    }

    public TableauDeContrainte(String filepath) throws Exception {
        this(App.readTxtFile(filepath));
    }

    public ArrayList<Tache> getTaches() {
        return taches;
    }

    public void setTaches(ArrayList<Tache> taches) {
        this.taches = taches;
    }

    public int getNbArcs() {
        for (Tache tache : taches) {
            nbArcs += tache.contraintes.size();
        }
        return nbArcs;
    }

    // retourne un affichage propre de la matrice
    public String MatriceToString() {
        String str = "";
        str += "Matrice:\n";
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                str += matrice[i][j] + "\t";
            }
            str += "\n";
        }
        return str;
    }

    @Override
    public String toString() {
        String str = "TableauDeContrainte\n";
        str += "Nombre de sommets: " + getNbSommets() + "\n";
        str += "Nombre d'arcs: " + getNbArcs() + "\n";
        for (Tache tache : taches) {
            str += tache.toString() + "\n";
        }
        str += this.MatriceToString();
        return str;

    }

    public int getNbSommets() {
        nbSommets = taches.size();
        return nbSommets;
    }

}
