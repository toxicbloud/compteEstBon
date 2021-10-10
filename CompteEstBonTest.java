import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

public class CompteEstBonTest {
    enum Operateurs {
        addition, soustraction, division, multiplication
    }

    public static int plusproche = 0;
    public static int nombreAppel = 0;
    public static StringBuffer affichage = new StringBuffer();

    public static void main(String[] args) {
        int[] tab = { Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]) };

        int result = Integer.parseInt(args[6]);
        long start1 = System.currentTimeMillis();
        if (compteEstBon(tab, result)) {
            System.out.println("Le compte est bon!!");
            System.out.println("Calcul :");
            // on retourne l affichage des etapes pour etre plus logique
            afficherEtapes();
        } else {
            System.out.println("Pas de solution exacte.");
            System.out.println("La valeur la plus proche est : " + plusproche);
            System.out.println("Calcul :");
            affichage = new StringBuffer();
            compteEstBon(tab, plusproche);
            afficherEtapes();

        }
        long end1 = System.currentTimeMillis();
        System.out.println("\n STATS");
        System.out.println("nombres d'appel : " + nombreAppel);
        System.out.println("Elapsed Time in milli seconds: " + (end1 - start1));

    }

    /**
     * 
     * @param op  operateur
     * @param nb1 operande 1
     * @param nb2 operande 2
     * @return resultat ou -1 si l operation n est pas interessante
     */
    public static int calculer(Operateurs op, int nb1, int nb2) {
        int res = -1;
        switch (op) {
            case addition:
                res = nb1 + nb2;
                break;
            case soustraction:
                if (nb1 >= nb2) {
                    res = nb1 - nb2;
                }
                break;
            case division:
                if (nb1 % nb2 == 0 && nb2 != 0) {
                    res = nb1 / nb2;
                }
                break;
            case multiplication:
                res = nb1 * nb2;
                break;
        }
        return res;
    }

    /**
     * fonction recursive
     * 
     * @param tab     tableau d operande
     * @param attendu nombre dont on doit trouver le calcul
     * @return boolen indiquant si on peut trouver une solution
     */
    public static Boolean compteEstBon(int[] tab, int attendu) {
        // triDecroissantTab(tab);
        nombreAppel++;
        for (int z : tab) {
            if (z == attendu)
                return true;
        }
        for (int i = 0; i < tab.length-1; i++) {
                for (Operateurs op : Operateurs.values()) {
                    int res = calculer(op, tab[i], tab[i+1]);
                    if (res > 0) {
                        if (Math.abs(attendu - plusproche) > Math.abs(attendu - res)) {
                            plusproche = res;
                        }
                        /**
                         * on cree un nouveau tableau contenant le resulat moins les deux operandes qui
                         * ont donne le resultat et on ajoute aussi les operandes restantes
                         */
                        int[] newTab = new int[tab.length - 1];
                        for (int j = 0; j < newTab.length - 1; j++) {
                            newTab[j] = tab[j + 2];
                        }
                        newTab[newTab.length - 1] = res;
                        // on relance compteEstBon avec le nouveau tableau
                        if (compteEstBon(newTab, attendu)) {
                            affichage.append(
                                    tab[i] + " " + operateurToString(op) + " " + tab[i + 1] + " = " + res + " EOL");
                            return true;
                        }
                    }

            }
        }
        return false;
    }

    /**
     * 
     * @param op operateur
     * @return symbole correspondant a l operateur
     */
    public static String operateurToString(Operateurs op) {
        switch (op) {
            case addition:
                return "+";
            case soustraction:
                return "-";
            case division:
                return "/";
            case multiplication:
                return "*";
            default:
                return "error";
        }
    }

    /**
     * tri le tableau de maniere decroissante
     * 
     * @param array tableau d entier
     */
    public static void triDecroissantTab(int[] array) {
        Arrays.sort(array);
        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }

    public static void afficherEtapes() {
        String[] aff = affichage.toString().split("EOL");
        for (int i = aff.length - 1; i >= 0; i--) {
            System.out.println(aff[i]);
        }
    }
}