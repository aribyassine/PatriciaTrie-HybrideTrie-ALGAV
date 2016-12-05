package projet.algav;

import projet.algav.benchmarck.Shakespeare;
import projet.algav.trie.Hybride;
import projet.algav.trie.Patricia;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {


    public static void main(String[] args) {
        String exemple = "A quel genial professeur de dactylographie sommes nous redevables de la superbe phrase ci dessous, un modele du genre, que toute dactylo connait par coeur puisque elle fait appel a chacune des touches du clavier de la machine a ecrire ?";
        String[] tab1 = {"romane", "romanus", "romulus", "rubens", "ruber", "rubicon", "rubic"};
        String[] tab2 = exemple.split(" ");
        String[] tab3 = {"aa", "ab", "ba", "bb", "ca", "cb","c"};

        List<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();

        for (String s : tab1)
            list1.add(s);
        for (String s : tab2)
            list2.add(s);
        for (String s : tab3)
            list3.add(s);

        //list1 = Shakespeare.shakespeareFileToArray("Shakespeare/comedy_errors.txt");
        Patricia p = new Patricia();
        Hybride h = new Hybride();
        Hybride he = new Hybride();
        for (String s : list1) {
            p.ajouter(s);
            h.ajouter(s);
        }

        FileTools.generateHtmlFile(p, "p");
        FileTools.generateHtmlFile(p.toHybride(), "pc");
        FileTools.generateHtmlFile(h.toPatricia(), "hc");
        FileTools.generateHtmlFile(h, "h");
    }
}
