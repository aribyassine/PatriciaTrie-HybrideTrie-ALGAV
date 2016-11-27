package projet.algav.trie;

import java.util.ArrayList;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        String exemple = "A quel genial professeur de dactylographie sommes nous redevables de la superbe phrase ci dessous, un modele du genre, que toute dactylo connait par coeur puisque elle fait appel a chacune des touches du clavier de la machine a ecrire ?";
        String[] tab0 = {"a", "ab", "ac", "ad", "af", "abc", "abcd", "abcde", "abcdef","romane", "romanus", "romulus", "rubens", "ruber", "rubicon", "rubicundus", "rubic", "rom"};
        String[] tab1 = exemple.split(" ");
        ArrayList<String> list = new ArrayList<String>();
        for (String s : tab0)
            list.add(s);

        Trie trie;
        trie = new Patricia();
        for (String s : list) {
            trie.ajouter(s);
        }
        FileGenerator.generateHtmlFile(trie,"3d.js\\","Patricia");
        trie = new Hybride();
        for (String s : list) {
            trie.ajouter(s);
        }
        FileGenerator.generateHtmlFile(trie,"3d.js\\","Hybride");

        //System.out.println(gson.toJson(pt));
//        Collections.shuffle(list);
//        for (String s : list) {
//            pt.supprimer(s);
//            System.out.println("______________________________________\n" + s + "\n" + pt.listeMots());
//            System.out.println(pt);
//        }
    }
}
