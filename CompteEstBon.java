import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

public class CompteEstBon {
    enum Operateurs {
        addition, soustraction, division, multiplication
    }

    public static boolean trouve = false;

    public static void main(String[] args) {
        int[] tab = { Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]) };

        int result = Integer.parseInt(args[6]);
        compteEstBon(tab, result);
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

    public static int compteEstBon(int[] tab, int attendu) {
        if (trouve)
            return 1;
        for (int i = 0; i < tab.length - 1; i++) {
            for (Operateurs op : Operateurs.values()) {
                int[] solution = new int[2];
                // String solution = " ";
                int res = calculer(op, tab[i], tab[i + 1]);
                solution=adjSolution(solution, tab[i], tab[i+1], op);
                // solution = adjSolutionString(solution, tab[i], tab[i + 1], op);
                if (res == attendu) {
                    System.out.println("compte est bon");
                    for(int j=0;j<solution.length;j++){
                    System.out.println(solution[j]+" "+solution[j+2]+" "+solution[j+1]);
                    }
                    // System.out.println(solution);
                    trouve = true;
                    return 1;
                }
                if (res <= 0)
                    return 1;
                int[] newTab = new int[tab.length - 1];
                for (int j = 0; j < newTab.length - 1; j++) {
                    newTab[j] = tab[j + 2];
                }
                newTab[newTab.length - 1] = res;
                triDecroissantTab(newTab);
                compteEstBon(newTab, attendu);
            }
            /*
             * // adjSolution(tab, 5, 1, Operateurs.addition); int res =
             * calculer(Operateurs.addition, tab[i], tab[i + 1]); System.out.println(tab[i]
             * + "+" + tab[i + 1] + "=" + res); if (res == attendu) {
             * System.out.println("compte est bon"); return 1; } if (res > 1000) return 1;
             * res = calculer(Operateurs.soustraction, tab[i], tab[i + 1]);
             * System.out.println(tab[i] + "-" + tab[i + 1] + "=" + res); if (res ==
             * attendu) { System.out.println("compte est bon"); return 1; } res =
             * calculer(Operateurs.division, tab[i], tab[i + 1]); System.out.println(tab[i]
             * + "/" + tab[i + 1] + "=" + res); if (res == attendu) {
             * System.out.println("compte est bon"); return 1; } res =
             * calculer(Operateurs.multiplication, tab[i], tab[i + 1]);
             * System.out.println(tab[i] + "*" + tab[i + 1] + "=" + res); if (res ==
             * attendu) { System.out.println("compte est bon"); return 1; } int[] newTab =
             * new int[tab.length - 1]; for (int j = 0; j < newTab.length - 1; j++) {
             * newTab[j] = tab[j + 2]; } newTab[newTab.length - 1] = res;
             * triDecroissantTab(newTab); compteEstBon(newTab, attendu);
             */
            // compteEstBon(newTab, attendu);
        }
        return 1;

        /**
         * tester toutes les operations possibles entre chaque nombre
         */
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

    public static void triDecroissantTab(int[] array) {
        Arrays.sort(array);
        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }
}