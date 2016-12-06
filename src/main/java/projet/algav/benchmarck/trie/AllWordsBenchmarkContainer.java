package projet.algav.benchmarck.trie;

import org.openjdk.jmh.annotations.*;
import projet.algav.benchmarck.Shakespeare;
import projet.algav.trie.Hybride;
import projet.algav.trie.Patricia;

import java.util.Iterator;
import java.util.List;

@State(Scope.Benchmark)
public class AllWordsBenchmarkContainer {
    @Param({"projet ", "algorithmique", "chronophage", "amusant","ils","lui","anticonstitutionnellement","universite","pierre","marie","curie"})
    public String motQuiNExistePasEnAnglais;
    /*    @Param({"projet ",
                "algorithmique",
                "chronophage",
                "amusant",
                "ils",
                "lui",
                "anticonstitutionnellement",
                "universite",
                "pierre",
                "marie",
                "curie",
                "enemy",
                "queen",
                "margaret",
                "then",
                "you",
                "belike",
                "suspect",
                "these",
                "noblemen",
                "as"
        })*/
    public String motAChercher;
    //@Param({"question", "archbishop", "of", "york", "then", "take", "my", "lord", "of", "westmoreland", "this", "schedule", "for", "this", "contains", "our", "general", "grievances", "each", "several", "article", "herein", "redress"})
    public String motASupprimer;
    public String[] motsASupprimer = {"question", "archbishop", "of", "york", "then", "take", "my", "lord", "of", "westmoreland", "this", "schedule", "for", "this", "contains", "our", "general", "grievances", "each", "several", "article", "herein", "redress"};
    List<String> list;
    public Iterator<String> iterator;

    public Iterator<String> getIterator() {
        return iterator;
    }

    public Patricia getPatricia() {

        return patricia;
    }

    public Hybride getHybride() {
        return hybride;
    }

    public Patricia patricia;
    public Hybride hybride;


    @Setup(Level.Trial)
    public void init() {
        list = Shakespeare.shakespeareFileToArray("Shakespeare/merge.txt");
        iterator = list.iterator();
        patricia = new Patricia();
        hybride = new Hybride();
        for (String mot : list) {
            patricia.ajouter(mot);
            hybride.ajouter(mot);
        }
    }

    @Setup(Level.Invocation)
    public void initIter() {
        patricia.supprimer(motQuiNExistePasEnAnglais);
        hybride.supprimer(motQuiNExistePasEnAnglais);
    }

    @TearDown(Level.Trial)
    public void fin() {
        System.out.println("\n" + patricia.comptageMots());
        System.out.println(hybride.comptageMots());
        iterator = list.iterator();
        patricia = new Patricia();
        hybride = new Hybride();
    }
}
