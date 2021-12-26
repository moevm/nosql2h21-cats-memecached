package info.moevm.se.nosqlcatsmemecached.benchmark;

import info.moevm.se.nosqlcatsmemecached.utils.memcached.importers.CatsImporter;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
public class ImportBenchmark extends AbstractBenchmark {
    private static CatsImporter catsImporter;

    @Autowired
    void setCatsImporter(CatsImporter catsImporter) {
        ImportBenchmark.catsImporter = catsImporter;
    }

    @Benchmark
    public void someBenchmarkMethod() {
        catsImporter.from(new File(ImportBenchmark.class
                .getClassLoader().getResource("all-cats.json").getFile()));
    }
}
