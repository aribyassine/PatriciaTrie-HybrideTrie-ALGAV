package projet.algav;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import projet.algav.trie.Hybride;
import projet.algav.trie.Patricia;

import java.projet.algav.trie.PatriciaBenchmark;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class App {


    public static void main(String[] args) {
        String exemple = "A quel genial professeur de dactylographie sommes nous redevables de la superbe phrase ci dessous, un modele du genre, que toute dactylo connait par coeur puisque elle fait appel a chacune des touches du clavier de la machine a ecrire ?";
        String[] tab1 = {"a", "ab", "ac", "ad", "af", "abc", "abcd", "abcde", "abcdef", "romane", "romanus", "romulus", "rubens", "ruber", "rubicon", "rubicundus", "rubic", "rom"};
        String[] tab2 = exemple.split(" ");
        String[] tab3 = {"aa", "ab", "ba", "bb", "ca", "cb"};

        Options opt = new OptionsBuilder()
                .include(PatriciaBenchmark.class.getName())
                .measurementIterations(2)
                .measurementTime(TimeValue.milliseconds(50))
                .warmupIterations(2)
                .warmupTime(TimeValue.milliseconds(50))
                .timeUnit(TimeUnit.MILLISECONDS)
                .forks(1)
                .resultFormat(ResultFormatType.JSON)
                .result("result.json")
                .build();
        try {
            new Runner(opt).run();
        } catch (RunnerException e) {
            e.printStackTrace();
        }
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();

        for (String s : tab2)
            list1.add(s);
        for (String s : tab2)
            list2.add(s);
        for (String s : tab1)
            list3.add(s);
        for (String s : tab2)
            list3.add(s);

        Patricia p = new Patricia();
        Hybride h = new Hybride();
        Hybride h3 = new Hybride();
        for (String s : list1) {
            p.ajouter(s);
        }
        for (String s : list1) {
            h.ajouterMotPuisEquilibre(s);
        }
        for (String s : list1) {
            System.out.println(p.prefixe(s) + " " + s + " " + p.motsAyantCommePrefixe(s));
        }


//        FileTools.addWordsFromFile(p,"Shakespeare\\comedy_errors.txt");
//        FileTools.addWordsFromFile(h,"Shakespeare\\comedy_errors.txt");
        FileTools.generateHtmlFile(p, "p");
        FileTools.generateHtmlFile((Patricia) p.clone(), "pc");
        FileTools.generateHtmlFile(h, "h");
        FileTools.generateHtmlFile(p.toHybride(), "f");
    }
}
