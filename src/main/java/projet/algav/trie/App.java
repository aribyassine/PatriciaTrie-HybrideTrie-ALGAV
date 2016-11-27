package projet.algav.trie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        String exemple = "A quel genial professeur de dactylographie sommes nous redevables de la superbe phrase ci dessous, un modele du genre, que toute dactylo connait par coeur puisque elle fait appel a chacune des touches du clavier de la machine a ecrire ?";
        String[] tab0 = {"a", "ab", "ac", "ad", "af", "abc", "abcd", "abcde", "abcdef", "romane", "romanus", "romulus", "rubens", "ruber", "rubicon", "rubicundus", "rubic", "rom"};
        String[] tab1 = exemple.split(" ");
        String[] tab2 = {"ac", "ab", "bcd", "cd", "d"};
        String[] tab3 = {"ac", "ad", "bc", "cde", "e"};
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();
        for (String s : tab2)
            list1.add(s);
        for (String s : tab3)
            list2.add(s);
        for (String s : tab2)
            list3.add(s);
        for (String s : tab3)
            list3.add(s);

        Hybride p1 = new Hybride();
        Hybride p2 = new Hybride();
        Hybride p3 = new Hybride();

        for (String s : list1) {
            p1.ajouter(s);
        }
        for (String s : list2) {
            p2.ajouter(s);
        }
        for (String s : list3) {
            p3.ajouter(s);
        }
        FileTools.generateHtmlFile(p1, "3d.js\\", "a");
        FileTools.generateHtmlFile(p2, "3d.js\\", "b");
        p1.fusion(p2);
        FileTools.generateHtmlFile(p1, "3d.js\\", "c");
        FileTools.generateHtmlFile(p3, "3d.js\\", "d");
        //System.out.println(p1.fusion(p2));
//        FileTools.addWordsFromFile(pt,"Shakespeare\\romeo_juliet.txt");
//        FileTools.addWordsFromFile(ht,"Shakespeare\\romeo_juliet.txt");

        //System.out.println(gson.toJson(pt));
//        Collections.shuffle(list);
//        for (String s : list) {
//            pt.supprimer(s);
//            System.out.println("______________________________________\n" + s + "\n" + pt.listeMots());
//            System.out.println(pt);
//        }
    }
}
