
import java.io.File;
import java.util.Arrays;
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
        for (int i = 1; i < 14; i++) {
            int numFile = i;
            String filepath = "./src/Tables/Table" + numFile
                    + ".txt";
            ;
            System.out.println(
                    "***************************************************************************************************************************************\nDébut\nTable "
                            + numFile + "\n");
            int[][] tab = readTxtFile(filepath);

            // printTabInt(tab);
            TableauDeContrainte tabC = new TableauDeContrainte(filepath);
            System.out.println("\nDetection de circuit : \n");
            tabC.DetectionCircuit("on");
            if (!tabC.isCircuit) {
                System.out.println("\nLe graphe est cyclique\n");

                System.out.println("\nRangs :\n ");
                tabC.orderByRang();
                // tabC.setDateTot();

                System.out.println("\nSetDateTot :\n ");
                tabC.setDatesTot();
                tabC.PrintCalendrier("tot");
                System.out.println("\nSetDateTard :\n ");
                tabC.setDatesTard();
                tabC.PrintCalendrier("tard");
                ;

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

            System.out.println("\n" + tabC);

        }

    }
}
