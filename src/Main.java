import trie.*;

import java.util.ArrayList;
import java.util.Collections;

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

        String exemple = "A quel genial professeur de dactylographie sommes nous redevables de la superbe phrase ci dessous, un modele du genre, que toute dactylo connait par coeur puisque elle fait appel a chacune des touches du clavier de la machine a ecrire ?";
        String[] tab0 = {"a", "ab", "ac", "ad", "af", "abc", "abcd", "abcde", "abcdef"};
        String[] tab1 = {"romane", "romanus", "romulus", "rubens", "ruber", "rubicon", "rubicundus", "rubic", "rom"};
        String[] tab2 = exemple.split(" ");
        ArrayList<String> list = new ArrayList<String>();
        for (String s : tab2)
            list.add(s);
        for (String s : list)
            System.out.println(s);

        Patricia pt = new Patricia();
        for (String s : list) {
            pt.ajouter(s);
        }

        String[] tab3 = {"abcde","abc","abcd","ad","a","ab","ac","abcdef","af"};

        Collections.shuffle(list);
        for (String s : list) {
            pt.supprimer(s);
            System.out.println("______________________________________\n" + s + "\n" + pt.listeMots());
            System.out.println(pt);
        }


//        String t = "r";
//        System.out.println(pt.prefixe(t));
//        System.out.println(pt.listeSuffixe(t));
//        System.out.println(pt.ayantLePrefixe(t));
//
//
//        System.out.println(pt.recherche("romulus"));
//        System.out.println(pt.recherche("romu"));
//        System.out.println(pt.recherche("romuluss"));
    }
}