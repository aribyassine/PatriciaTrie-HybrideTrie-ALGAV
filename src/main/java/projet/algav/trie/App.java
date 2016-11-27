package projet.algav.trie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
/*        String exemple = "A quel genial professeur de dactylographie sommes nous redevables de la superbe phrase ci dessous, un modele du genre, que toute dactylo connait par coeur puisque elle fait appel a chacune des touches du clavier de la machine a ecrire ?";
        String[] tab0 = {"a", "ab", "ac", "ad", "af", "abc", "abcd", "abcde", "abcdef", "romane", "romanus", "romulus", "rubens", "ruber", "rubicon", "rubicundus", "rubic", "rom"};
        String[] tab1 = exemple.split(" ");
        ArrayList<String> list = new ArrayList<String>();
        for (String s : tab0)
            list.add(s);
        for (String s : tab1)
            list.add(s);*/

        Trie pt = new Patricia();
        Trie ht = new Hybride();
/*        for (String s : list) {
            pt.ajouter(s);
            ht.ajouter(s);
        }*/

        FileTools.addWordsFromFile(pt,"Shakespeare\\romeo_juliet.txt");
        FileTools.addWordsFromFile(ht,"Shakespeare\\romeo_juliet.txt");
        FileTools.generateHtmlFile(pt, "3d.js\\", "Patricia");
        FileTools.generateHtmlFile(ht, "3d.js\\", "Hybride");

        //System.out.println(gson.toJson(pt));
//        Collections.shuffle(list);
//        for (String s : list) {
//            pt.supprimer(s);
//            System.out.println("______________________________________\n" + s + "\n" + pt.listeMots());
//            System.out.println(pt);
//        }
    }
}
