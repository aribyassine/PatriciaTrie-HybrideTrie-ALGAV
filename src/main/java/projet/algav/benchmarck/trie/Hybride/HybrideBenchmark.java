package projet.algav.benchmarck.trie.Hybride;

import org.openjdk.jmh.annotations.Benchmark;
import projet.algav.benchmarck.trie.AllFilesBenchmarkContainer;
import projet.algav.benchmarck.trie.AllWordsBenchmarkContainer;
import projet.algav.trie.Hybride;

import java.util.Iterator;


/**
 * Created by Yassine on 03/12/2016.
 */
public class HybrideBenchmark {
    @Benchmark
    public void benchmark_ajouter_par_fichier_apres_construction(AllFilesBenchmarkContainer fc) {
        for (String s : fc.motQuiNExistePasEnAnglais)
            fc.getHybride().ajouter(s);
    }

    public Hybride benchmark_ajouter_par_fichier(AllFilesBenchmarkContainer fc) {
        Iterator<String> iterator = fc.getIterator();
        while (iterator.hasNext())
            fc.getHybride().ajouter(iterator.next());
        return fc.getHybride();
    }

    public void benchmark_ajouter_tout_les_mots(AllWordsBenchmarkContainer wc) {
        while (wc.iterator.hasNext())
            wc.getHybride().ajouter(wc.getIterator().next());
    }
}
