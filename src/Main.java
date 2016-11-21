import trie.*;

public class Main {

    public static void main(String[] args) {


//        String[] tab = {"b","bb","ba","bc","a", "ab", "abcd"};
//        String[] tab2 = {"romane", "romanus", "romulus", "rubens", "ruber", "rubicon", "rubicundus"};
//        String exemple = "A quel genial professeur de dactylographie sommes nous redevables de la superbe phrase ci dessous, un modele du genre, que toute dactylo connait par coeur puisque elle fait appel a chacune des touches du clavier de la machine a ecrire ?";
//        String[] tab3 = exemple.split(" ");
//        Hybride pt = new Hybride();
//        System.out.println(pt);
//        for (String s : tab) {
//            pt.ajouter(s);
//            System.out.println(pt);
//        }

        String[] tab = {"a","ab","ac","ad","af","abc","abcd","abcde","abcdef"};
        String[] tab2 = {"romane", "romanus", "romulus", "rubens", "ruber", "rubicon", "rubicundus", "rubic","a","ab","ac","ad","af","abc","abcd","abcde","abcdef"};
        String exemple = "A quel genial professeur de dactylographie sommes nous redevables de la superbe phrase ci dessous, un modele du genre, que toute dactylo connait par coeur puisque elle fait appel a chacune des touches du clavier de la machine a ecrire ?";
        String[] tab3 = exemple.split(" ");
        Patricia pt = new Patricia();
        for (String s : tab2) {
            pt.ajouter(s);
        }
        System.out.println(pt);
        for (int i = 0; i < tab2.length; i++) {
            int j = (int)(Math.random()*(double)tab.length);
            System.out.println(pt.supprimer(tab[j]));
            System.out.println(tab[j]);
            System.out.println(pt);
        }


        String t = "r";
        System.out.println(pt.prefixe(t));
        System.out.println(pt.listeSuffixe(t));
        System.out.println(pt.ayantLePrefixe(t));


        System.out.println(pt.recherche("romulus"));
        System.out.println(pt.recherche("romu"));
        System.out.println(pt.recherche("romuluss"));
    }
}