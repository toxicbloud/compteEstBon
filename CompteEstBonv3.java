import java.util.Arrays;

class CompteEstBonv3 {
    private static StringBuffer affichage = new StringBuffer();
    private static char[] operateurs = { '+','-', '*', '/' }; // permet l affichage 

    private static int plusproche=0;
    private static int nombreAppel=0;

    private static long end1,start1; // variable pour stocker les chrono

    static boolean compteEstBon(int[] t,int nb, int attendu) {
        nombreAppel++;
        for (int i = 0; i < nb; i++) {
            if (t[i] == attendu) {
                return true;
            }
            for (int j = i + 1; j < nb; j++)
                for (int k = 0; k < 4; k++) { //attention tu avais mis int k = i ??
                    int res = calculer(k, t[i], t[j]);
                    if (res > 0) {
                        /**
                         * on regarde si le resultat obtenu est plus proche du resultat attendu que le dernier
                         * pour le cas ou on ne peut pas trouver de solution exacte
                         */
                        if (Math.abs(attendu - plusproche) > Math.abs(attendu - res)) {
                            plusproche = res;
                        }
                        int tempi = t[i], tempj = t[j];
                        t[i] = res;
                        t[j] = t[nb - 1];

                        //nouveau tableau plus petit de 1 avec les deux operande en moins
                        /*int[] newTab = new int[nb - 1];
                        for (int z = 0; z < newTab.length - 1; z++) {
                            newTab[z] = t[z + 2];
                        }
                        newTab[newTab.length - 1] = res; */
                        // triDecroissantTab(newTab);
                        if (compteEstBon(t,nb-1,attendu)) {
                            affichage.append(tempi + " " + operateurs[k] + " " + tempj
                                    + " = " + res + "EOL"); // les etapes ne sont pas dans un ordre logique donc on prevoit de split pour retourner l affichage , EOL pour End of Line
                            return true;
                        }
                        t[i] = tempi;
                        t[j] = tempj;
                    }
                }
        }
        return false;
    }
    /**
     * 
     * @param op  operation represente par un entier
     * @param nb1 operande 1
     * @param nb2 operande 2
     * @return resultat ou -1 si l operation n est pas interessante
     */
    public static int calculer(int op, int nb1, int nb2) {
        switch (op) {
            case 0:
                return nb1 + nb2;
            case 1:
                if (nb1 > nb2) {
                    return nb1 - nb2;
                } else {
                    return -1;
                }
            case 2:
                return nb1 * nb2;
            case 3:
                if (nb1 % nb2 == 0) {
                    return nb1 / nb2;
                }
        }
        return -1;
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

    /**
     * affiche les etapes dans un ordre logique 
     * en inversant le buffer
     */
    public static void afficherEtapes() {
        String[] aff = affichage.toString().split("EOL");
        for (int i = aff.length - 1; i >= 0; i--) {
            System.out.println(aff[i]);
        }
    }
    /**
     * statistiques d execution du programme
     */
    public static void afficherStats(){
        System.out.println("\n====STATISTIQUES====");
        System.out.println("nombre d'appels de la fonction compteEstBon : "+nombreAppel);
        System.out.println("Temps ecoule en ms: " + (end1 - start1));
    }

    public static void main(String[] args) {
        int[] tab = { Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), // on recupere les operandes
            Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]) };
        
        int attendu = Integer.parseInt(args[6]);
        start1 = System.currentTimeMillis(); // demarrage du chronometre
        if (compteEstBon(tab,tab.length, attendu)) {
            System.out.println("Le compte est bon !!");
            System.out.println("Calcul :");
            afficherEtapes();
        } else {
            System.out.println("Pas de solution exacte");
            System.out.println("La valeur la plus proche est : "+plusproche);
            System.out.println("Calcul :");
            // on relance compte est bon pour la valeur plusproche et on n oubli pas de nettoyer le buffer des etapes
            affichage=new StringBuffer();
            compteEstBon(tab,tab.length, plusproche);
            afficherEtapes();
        }
        end1 = System.currentTimeMillis(); // arret chronometre
        afficherStats();
    }
}
