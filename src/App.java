
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
        for (int i = 1; i < 12; i++) {
            int numFile = i;
            String filepath = "C:/Users/arthu/OneDrive/Documents/VsCode/Projet_Graph_L3/src/Tables/Table" + numFile
                    + ".txt";
            ;
            System.out.println(
                    "***************************************************************************************************************************************\nDébut\n");
            int[][] tab = readTxtFile(filepath);

            printTabInt(tab);
            TableauDeContrainte tabC = new TableauDeContrainte(filepath);

            if (tabC.DetectionCircuit() || tabC.DetectionNegativité()) {
                System.out.println("Ce n'est pas un graph d'ordonnancement");
            } else {
                System.out.println("Ce graph d'ordonnancement est valide");
            }

            // System.out.println(tabC.toString());
            tabC.SetRangs();
            System.out.println(tabC.toString());
        }

    }
}
