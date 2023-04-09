
import java.io.File;
import java.util.Scanner;

public class App {

    // Fonction qui lit un fichier texte et retourne un tableau de contraintes
    public static int[][] readTxtFile(String filepath) throws Exception {
        File file = new File(filepath);
        Scanner sc = new Scanner(file);
        int[][] tab = new int[0][0];
        int i = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] lineArray = line.split(" ");
            int[] lineInt = new int[lineArray.length];
            for (int j = 0; j < lineArray.length; j++) {
                lineInt[j] = Integer.parseInt(lineArray[j]);
            }
            tab = addLineInt(tab, lineInt);
            i++;
        }
        sc.close();
        return tab;
    }

    // Ajouter une ligne au tableau de contraintes
    public static int[][] addLineInt(int[][] tab, int[] lineInt) {
        int[][] newTab = new int[tab.length + 1][];
        for (int i = 0; i < tab.length; i++) {
            newTab[i] = tab[i];
        }
        newTab[newTab.length - 1] = lineInt;
        return newTab;
    }

    // Afficher le tableau de contraintes
    public static void printTabInt(int[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int choix=0;
        boolean propriété=true;

        do{
            System.out.println("Tester un tableau de contraintes --> saisissez 1\nQuitter --> saisissez 0");
            choix = sc.nextInt();
        }while((choix > 1)||(choix < 0));        

        while(choix!=0){

            System.out.println("\n----------------------------------------------------------------------------------------------------------------------------\n");

            int choix1=0;
            do{
                System.out.println("Tableau de contraintes à charger : ");
                choix1 = sc.nextInt();
            }while((choix1 > 13)||(choix1 < 1));
            
            int numFile = choix1;
            //FIXME:
            String filepath = "C:/Users/matth/Downloads/Projet_Theorie_Graph_L3/Projet_Graph_L3/src/Tables/Table" + numFile
                    + ".txt";           // String filepath = ./src/Tables/Table" + numFile + ".txt"; faut remplacer par ça
            System.out.println(
                        "----------------------------------------------------------------------------------------------------------------------------\n\nDébut\nTable "
                                + numFile + "\n----------------------------------------------------------------------------------------------------------------------------");
            int[][] tab = readTxtFile(filepath);

            // printTabInt(tab);

            TableauDeContrainte tabC = new TableauDeContrainte(filepath);

            //Tableau de contraintes avec la matrice
            //dateTot et DateTard = -1 car pas encore calculé (on le fera par la suite)
            System.out.println("\n" + tabC);

            System.out.println("\n----------------------------------------------------------------------------------------------------------------------------\n");

            //détection de circuit pour savoir si notre graphe est un graphe d'ordonnancement
            System.out.println("\nDetection de circuit : \n");
            tabC.DetectionCircuit("on");

            if((tabC.isCircuit)||(tabC.DetectionNegativité()))
            propriété = false;

            if (propriété==true) {
                    System.out.println("\nLe graphe est cyclique");

                    System.out.println("\nRangs :");
                    tabC.orderByRang();

                    System.out.println("\n----------------------------------------------------------------------------------------------------------------------------\n");

                    //Calendriers
                    tabC.setDatesTot();
                    tabC.PrintCalendrier("tot");
                    System.out.println("\n----------------------------------------------------------------------------------------------------------------------------\n");
                    tabC.setDatesTard();
                    tabC.PrintCalendrier("tard");

                    System.out.println("\n----------------------------------------------------------------------------------------------------------------------------\n");

                    //Marges totales
                    tabC.setMargeTotale();
                    System.out.println(tabC.DisplayMarge());
                    System.out.println("\n----------------------------------------------------------------------------------------------------------------------------\n");
                    
                    //Chemin(s) critique(s)
                    //System.out.println(tabC);
                        
                    
                    
                    // tabC.setDateTot();


                    /*
                    * 
                    * 
                    * System.out.println("setDateTard : ");
                    * tabC.setDateTard();
                    * System.out.println("PrintDateTot : ");
                    * tabC.PrintCalendrier("tot");
                    * tabC.orderByNom();
                    * System.out.println(tabC);
                    */
                }
                else{
                    do{
                        System.out.println("Quitter --> saisissez 0\nChoisir un autre tableau de contraintes à charger (car circuit) : ");
                        choix = sc.nextInt();
                    }while((choix > 13)||(choix < 0));
                }

            do{
                System.out.println("Tester à nouveau un tableau de contraintes --> saisissez 1\nQuitter --> saisissez 0");
                choix = sc.nextInt();
            }while((choix > 1)||(choix < 0)); 
           
            }
            System.out.println("\n----------------------------------------------------------------------------------------------------------------------------\n\n"
            + "Fin de l'exécution du programme");
        }
}
