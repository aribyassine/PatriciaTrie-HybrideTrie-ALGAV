package projet.algav.benchmarck.trie;

import org.openjdk.jmh.annotations.*;
import projet.algav.benchmarck.Shakespeare;
import projet.algav.trie.Hybride;
import projet.algav.trie.Patricia;

import java.util.Iterator;
import java.util.List;

@State(Scope.Benchmark)
public class AllFilesBenchmarkContainer {
    @Param({"1henryiv.txt", "1henryvi.txt", "2henryiv.txt", "2henryvi.txt", "3henryvi.txt", "allswell.txt", "asyoulikeit.txt", "cleopatra.txt", "comedy_errors.txt", "coriolanus.txt", "cymbeline.txt", "hamlet.txt", "henryv.txt", "henryviii.txt", "john.txt", "julius_caesar.txt", "lear.txt", "lll.txt", "macbeth.txt", "measure.txt", "merchant.txt", "merry_wives.txt", "midsummer.txt", "much_ado.txt", "othello.txt", "pericles.txt", "richardii.txt", "richardiii.txt", "romeo_juliet.txt", "taming_shrew.txt", "tempest.txt", "timon.txt", "titus.txt", "troilus_cressida.txt", "twelfth_night.txt", "two_gentlemen.txt", "winters_tale.txt"})
    String fileName;
    String exemple = "A quel genial professeur de dactylographie sommes nous redevables de la superbe phrase ci dessous, un modele du genre, que toute dactylo connait par coeur puisque elle fait appel a chacune des touches du clavier de la machine a ecrire ?";

    public String[] motQuiNExistePasEnAnglais = exemple.split(" ");
    List<String> list;

    public Iterator<String> getIterator() {
        return list.iterator();
    }

    public Patricia getPatricia() {

        return patricia;
    }

    public Hybride getHybride() {
        return hybride;
    }

    Patricia patricia;
    Hybride hybride;

    @Setup(Level.Iteration)
    public void init() {
        list = Shakespeare.shakespeareFileToArray("Shakespeare/" + fileName);
        patricia = new Patricia();
        hybride = new Hybride();
        for (String mot : list) {
            patricia.ajouter(mot);
            hybride.ajouter(mot);
        }
    }
}
