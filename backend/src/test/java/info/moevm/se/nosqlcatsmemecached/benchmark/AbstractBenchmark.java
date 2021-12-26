package info.moevm.se.nosqlcatsmemecached.benchmark;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
abstract public class AbstractBenchmark {
    private final static Integer MEASUREMENT_ITERATIONS = 5;
    private final static Integer WARMUP_ITERATIONS = 5;

    @Test
    public void executeJmhRunner() throws RunnerException {
        Options opt = new OptionsBuilder()
                .warmupIterations(WARMUP_ITERATIONS)
                .measurementIterations(MEASUREMENT_ITERATIONS)
                .forks(0)
                .threads(1)
                .shouldDoGC(true)
                .shouldFailOnError(true)
                .build();
        new Runner(opt).run();
    }
}
