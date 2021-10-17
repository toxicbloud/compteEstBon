import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;


public class CompteEstBonOLD {
    enum Operateurs {
        addition, soustraction, division, multiplication
    }

    public static boolean trouve = false;
    public static int plusproche = -99999999;
    public static int nombreAppel=0;
    public static StringBuffer affichage = new StringBuffer();

    public static void main(String[] args) {
        int[] tab = { Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]) };

        int result = Integer.parseInt(args[6]);
        long start1 = System.currentTimeMillis();
        compteEstBon(tab, result);
        long end1 = System.currentTimeMillis();      
        System.out.println("Elapsed Time in milli seconds: "+ (end1-start1));
        System.out.println(affichage);
    }

    public static int calculer(Operateurs op, int nb1, int nb2) {
        int res = -1;
        switch (op) {
            case addition:
                res = nb1 + nb2;
                break;
            case soustraction:
                if (nb1 >= nb2) {
                    res = nb1 - nb2;
                    break;
                }
            case division:
                if (nb1 % nb2 == 0 && nb2 != 0) {
                    res = nb1 / nb2;
                }
            case multiplication:
                res = nb1 * nb2;
                break;
        }
        return res;
    }

    public static Boolean compteEstBon(int[] tab, int attendu) {
        triDecroissantTab(tab);
        nombreAppel++;
        if (trouve)
            return true;
        for (int i = 0; i < tab.length - 1; i++) {
            for (Operateurs op : Operateurs.values()) {
                int res = calculer(op, tab[i], tab[i + 1]);
                if (res == attendu) {
                    System.out.println("compte est bon");
                    System.out.println("nombres d'appel : "+nombreAppel);
                    // System.out.println(solution);
                    affichage.append(tab[i] +" "+intToString(operateurToInt(op)) + " "+ tab[i+1] +"\n");
                    trouve = true;
                    return true;
                }
                if (res <= 0)
                    break; // si tu break tu gagne *10 en appel , continue 
                int[] newTab = new int[tab.length - 1];
                for (int j = 0; j < newTab.length - 1; j++) {
                    newTab[j] = tab[j + 2];
                }
                newTab[newTab.length - 1] = res;
                compteEstBon(newTab, attendu);
            }
        }
        return false;
    }

    public static void afficherTableau(int[] tab) {
        System.out.println(" afficher tableau ");
        for (int i : tab) {
            System.out.print(i + " ");
        }
        System.out.println("\n---------");
    }

    public static int[] adjSolution(int[] tab, int nb1, int nb2, Operateurs op) {
        int[] newtab = new int[tab.length + 3];
        for (int i = 0; i < tab.length; i++)
            newtab[i] = tab[i];
        newtab[tab.length - 3 + 1] = nb1;
        newtab[tab.length - 2 + 1] = nb2;
        newtab[tab.length - 1 + 1] = operateurToInt(op);
        return newtab;
    }

    public static String adjSolutionString(String solution, int nb1, int nb2, Operateurs op) {
        solution += " \n";
        solution += nb1 + " " + operateurToInt(op) + " " + nb2;
        return solution;
    }

    public static int operateurToInt(Operateurs op) {
        int opInt = 0;
        for (int i = 0; i < Operateurs.values().length; i++) {
            if (Operateurs.values()[i] == op)
                opInt = i;
        }
        return opInt;
    }

    public static String intToString(int i){
        switch (i) {
            case 0:
                return "+";
            case 1:
                return "-";
            case 2:
                return "/";
            case 3:
                return "*";
            default:
                break;
        }
        return "null";
    }

    public static void triDecroissantTab(int[] array) {
        Arrays.sort(array);
        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }
}