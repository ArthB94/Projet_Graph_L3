
import java.io.File;
import java.util.*;

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

        do{
            do{
                System.out.println("Quitter --> saisissez 0\nSinon table à charger : ");
                choix = sc.nextInt();
            }while((choix > 13)||(choix < 0));
            if((choix<=13)&&(choix>=1)){

                int numFile = choix;
                String filepath = "./src/Tables/Table" + numFile
                        + ".txt";
                ;
                System.out.println(
                        "----------------------------------------------------------------------------------------------------------------------------\n\nDébut\nTable "
                                + numFile + "\n----------------------------------------------------------------------------------------------------------------------------");
                int[][] tab = readTxtFile(filepath);

                // printTabInt(tab);
                TableauDeContrainte tabC = new TableauDeContrainte(filepath);

                System.out.println("\nDetection de circuit : \n");
                tabC.DetectionCircuit("on");
                if (!tabC.isCircuit) {
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

                    System.out.println("\n" + tabC);

                    System.out.println("\n----------------------------------------------------------------------------------------------------------------------------\n");

                    //Marges totales
                    System.out.println(tabC.CalculMarge());
                    System.out.println("\n----------------------------------------------------------------------------------------------------------------------------\n");
                    
                    //Chemin(s) critique(s)
                    System.out.println(tabC.CheminCritique());
                        
                    
                    
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
                if(tabC.isCircuit)
                System.out.println("\n----------------------------------------------------------------------------------------------------------------------------\n");
            }
            if(choix == 0)
            System.out.println("\n----------------------------------------------------------------------------------------------------------------------------\n\n"
            + "Fin de l'exécution du programme");
            }while(choix != 0);

        }

    }
