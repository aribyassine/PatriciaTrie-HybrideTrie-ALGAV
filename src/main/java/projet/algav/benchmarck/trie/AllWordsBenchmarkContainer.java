package projet.algav.benchmarck.trie;

import org.openjdk.jmh.annotations.*;
import projet.algav.benchmarck.Shakespeare;
import projet.algav.trie.Hybride;
import projet.algav.trie.Patricia;

import java.util.Iterator;
import java.util.List;

@State(Scope.Benchmark)
public class AllWordsBenchmarkContainer {
    @Param({"dernière", "construction", "structures", "afficher", "successifs"})
    public String[] motQuiNExistePasEnAnglais;
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
        hybride=new Hybride();
        for (String mot : list) {
            patricia.ajouter(mot);
            hybride.ajouter(mot);
        }
    }
    @TearDown(Level.Trial)
    public void fin() {
        System.out.println("\n"+patricia.comptageMots());
        System.out.println(hybride.comptageMots());
    }
}
