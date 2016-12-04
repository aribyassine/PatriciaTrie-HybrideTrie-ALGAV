package java.projet.algav.trie;

import org.openjdk.jmh.annotations.Benchmark;


/**
 * Created by Yassine on 03/12/2016.
 */
public class PatriciaBenchmark {
    @Benchmark
    public double benchmark_logarithm_jdk() {
        return java.lang.Math.log(42);
    }

    @Benchmark
    public double benchmark_logarithm_apache_common() {
        return org.apache.commons.math3.util.FastMath.log(42);
    }
}
