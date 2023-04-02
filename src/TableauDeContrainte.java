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
    String orderBy;
    boolean isCircuit = true;

    public TableauDeContrainte(ArrayList<Tache> taches) {
        this.taches = taches;
    }

    public TableauDeContrainte(int[][] tab) {

        // initialistaion des attributs
        orderBy = "nom";
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
        // on ajoute les successeurs
        setSuccesseurs();
        // on ajoute les rangs
        setRangs();

    }

    public void setSuccesseurs() {
        for (int i = 0; i < taches.size(); i++) {
            taches.get(i).successeurs = new ArrayList<Tache>();
            for (int j = 0; j < taches.size(); j++) {
                if (matrice[i + 1][j + 1] != ".") {
                    taches.get(i).successeurs.add(taches.get(j));
                }
            }
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

    public boolean DetectionCircuit(String affichage) {
        if (affichage != "off") {

            System.out.println("Il y a un seul point d entrée, a\nIl y a un seul point de sortie, w");
            System.out.println("Detection de circuit");
            System.out.println("Methode de suppression des points d'entrée");
            System.out.println(this.MatriceToString());
        }
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
                    if (affichage != "off") {
                        System.out.println("Points d'entrée: " + matrice[sommets.get(j)][0]);
                        System.out.println("Suppression de " + matrice[sommets.get(j)][0]);
                    }
                    sommets.remove(sommets.get(j));
                    // si le la liste des sommets ne posséde plus qu'un element alors il n'y a pas
                    // de circuit
                    if (sommets.size() == 1) {
                        if (affichage != "off") {
                            System.out.println("Il n'y a pas de circuit");
                        }
                        isCircuit = false;
                        return false;
                    }
                    // sinon on affiche la liste des sommets restants
                    String sommet = "";
                    for (int i = 1; i < sommets.size(); i++) {
                        sommet += matrice[sommets.get(i)][0] + " ";
                    }
                    if (affichage != "off") {
                        System.out.println("Sommets: " + sommet);
                    }

                    // on afficher l'état de la matrice, c'est peut étre provisoir
                    if (affichage == "m") {
                        System.out.println("matrice\n");
                        String m = "";
                        for (int y = 0; y < sommets.size(); y++) {
                            for (int z = 0; z < sommets.size(); z++) {
                                m += matrice[sommets.get(y)][sommets.get(z)] + "\t";
                                ;
                            }
                            m += "\n";
                        }
                        System.out.println(m);
                    }

                    ///////////////////////////

                    j--;
                }

            }
        }
        // si il n'y a pas eu de suppression de sommet alors il y a un circuit
        System.out.println("Il y a un circuit");
        isCircuit = true;
        return true;

    }

    // set les rangs des taches de manière récursive
    private void initRang(Tache t) {
        // si il y a un circuit le rang ne peut pas être set car il y a des boucles
        // infinies
        if (isCircuit == true) {
            DetectionCircuit("off");
        }
        if (isCircuit == false) {
            if (t.rang == -1) {
                t.rang = 0;
            }
            // vérifie si les successeurs ont un rang plus grand que le rang calculé par le
            // chein actuel
            for (Tache t2 : t.successeurs) {
                if (t2.rang < t.rang + 1) {
                    t2.rang = t.rang + 1;
                    initRang(t2);
                }

            }
        } else {
            System.out.println("Il y a un circuit impossible de set les rangs");
        }

    }

    // utilise initRang pour set les rangs des aches en commencant par la tache 0
    public void setRangs() {
        if (isCircuit == true) {
            DetectionCircuit("off");
        }
        if (isCircuit == false) {
            initRang(taches.get(0));
        } else {

            System.out.println("Il y a un circuit impossible de set les rangs");
        }
    }

    // set le calendrier au plus tot
    public void setDatesTot() {
        if (isCircuit == true) {
            return;
        }
        if (taches.get(0).rang == -1)
            setRangs();
        if (orderBy != "rang")
            orderByRang();
        taches.get(0).setDateTot();

    }

    // Set les dates aux plus tard de manière récursive en partant de la dernièer
    // tache et en metant a jour la date au plus tard de ses prédécesseurs
    public void setDatesTard() {
        if (isCircuit == true) {
            return;
        }
        if (taches.get(0).rang == -1)
            setRangs();
        if (orderBy != "rang")
            orderByRang();
        taches.get(taches.size() - 1).setDateTard();
    }

    // permet d'ordoner les taches par rang
    public void orderByRang() {
        if (taches.get(0).rang == -1) {
            System.out.println("les rangs n ont pas été set");
            return;
        }
        ArrayList<Tache> ordTaches = new ArrayList<Tache>();
        ArrayList<Tache> nonOrdTaches = new ArrayList<Tache>();
        int rangMin;
        nonOrdTaches.addAll(taches);
        while (nonOrdTaches.size() != 0) {
            rangMin = nonOrdTaches.get(0).rang;
            for (Tache t : nonOrdTaches) {
                if (t.rang < rangMin) {
                    rangMin = t.rang;
                }
            }
            for (int i = 0; i < nonOrdTaches.size(); i++) {
                Tache t = nonOrdTaches.get(i);

                if (t.rang == rangMin) {
                    ordTaches.add(t);
                    nonOrdTaches.remove(t);

                }

            }
        }
        taches = ordTaches;
        orderBy = "rang";
    }

    // permet d'ordonner les taches par nom de manière croissante
    public void orderByNom() {
        ArrayList<Tache> ordTaches = new ArrayList<Tache>();
        ArrayList<Tache> nonOrdTaches = new ArrayList<Tache>();
        int nomMin;
        nonOrdTaches.addAll(taches);
        nonOrdTaches.remove(0);
        nonOrdTaches.remove(nonOrdTaches.size() - 1);
        ordTaches.add(taches.get(0));
        while (nonOrdTaches.size() != 0) {
            nomMin = Integer.parseInt(nonOrdTaches.get(0).nom);
            for (Tache t : nonOrdTaches) {
                if (Integer.parseInt(t.nom) < nomMin) {
                    nomMin = Integer.parseInt(t.nom);
                }
            }
            for (int i = 0; i < nonOrdTaches.size(); i++) {
                Tache t = nonOrdTaches.get(i);

                if (Integer.parseInt(t.nom) == nomMin) {
                    ordTaches.add(t);
                    nonOrdTaches.remove(t);

                }

            }
        }
        ordTaches.add(taches.get(taches.size() - 1));
        taches = ordTaches;
        orderBy = "nom";
    }

    // affiche le calendrier au plus tot des taches
    public void PrintCalendrier(String affichage) {
        System.out.println("\nCalendrier au plus " + affichage + ":");
        // Choix du type d'affichage
        if (affichage == "tot") {
            setDatesTot();
            System.out.println("Rang\tTache\tPred\t\tDate\t");
        }
        if (affichage == "tard") {
            setDatesTard();
            System.out.println("Rang\tTache\tSucc\t\tDate\t");
        }
        // Creation des entétes

        // parcour des taches pour afficher les infos en ligne
        for (Tache t : taches) {
            String ligne = "";
            ligne += t.rang + "\t";
            ligne += t.nom + "(" + t.duree + ")" + "\t";
            if (affichage == "tot") {
                for (int i = 0; i < t.contraintes.size(); i++) {
                    ligne += t.contraintes.get(i).nom;

                    ligne += "." + t.contraintes.get(i).dateTot;

                    if (i != t.contraintes.size() - 1) {
                        ligne += ",";
                    }
                }
            }
            if (affichage == "tard") {
                for (int i = 0; i < t.successeurs.size(); i++) {
                    ligne += t.successeurs.get(i).nom;
                    ligne += "." + t.successeurs.get(i).dateTard;

                    if (i != t.successeurs.size() - 1) {
                        ligne += ",";
                    }
                }
            }

            ligne += "\t\t";
            if (affichage == "tot") {
                ligne += t.dateTot + "\t";
            }
            if (affichage == "tard") {
                ligne += t.dateTard + "\t";
            }
            System.out.println(ligne);
        }
        // je ne sais pas comment vous voulez l'afficher donc c'est un autre option
        /*
         * Affichage en colonne
         * String ligne1 = "Rang\t+";
         * String ligne2 = "Tache\t";
         * String ligne3 = "Pred\t";
         * String ligne4 = "Date\t";
         * for (Tache t : taches) {
         * ligne1 += t.rang + "\t";
         * ligne2 += t.nom + "(" + t.duree + ")" + "\t";
         * for (int i = 0; i < t.contraintes.size(); i++) {
         * ligne3 += t.contraintes.get(i).nom;
         * if (affichage == "tot") {
         * ligne3 += "." + t.contraintes.get(i).dateTot;
         * }
         * if (i != t.contraintes.size() - 1) {
         * ligne3 += ",";
         * }
         * }
         * ligne3 += "\t";
         * if (affichage == "tot") {
         * ligne4 += t.dateTot + "\t";
         * }
         * if (affichage == "tard") {
         * ligne4 += t.dateTard + "\t";
         * }
         * 
         * } System.out.println(ligne1+"\n"+ligne2+"\n"+ligne3+"\n"+ligne4+"\n");
         */
    }

    // Constructeur d'un tableau ne prenant en entrée qu'un chemin
    public TableauDeContrainte(String filepath) throws Exception {
        this(App.readTxtFile(filepath));
    }

    // geter et seter
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

    public int getNbSommets() {
        return nbSommets;
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

}
