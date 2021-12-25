package info.moevm.se.nosqlcatsmemecached.benchmark;

import info.moevm.se.nosqlcatsmemecached.dao.CatsDao;
import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import org.openjdk.jmh.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static info.moevm.se.nosqlcatsmemecached.utils.cat.CatUtils.getListCatsFromFile;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
public class CRUDBenchmark extends AbstractBenchmark {
    private static CatsDao dao;

    @Autowired
    void setDao(CatsDao dao) {
        CRUDBenchmark.dao = dao;
    }

    @State(Scope.Benchmark)
    public static class DaoState {
        @Param({"1", "11", "21", "31", "41", "51", "61"})
        int size;

        Cat cat;

        String abyssinianCat = "Abyssinian";

        String birmanCat = "Birman";

        @Setup(Level.Invocation)
        public void init() {
            cat = getListCatsFromFile("test_cat.json").get(0);

            List<Cat> cats = getListCatsFromFile("all-cats.json");
            cats.stream().limit(size).forEach(c -> dao.addCat(c));
        }

        @TearDown(Level.Invocation)
        public void shutdown() {
            dao.drop();
        }
    }

    @Benchmark
    public void createBenchmarkMethod(DaoState state) {
        dao.addCat(state.cat);
    }

    @Benchmark
    public void readBenchmarkMethod(DaoState state) {
        dao.getCat(state.birmanCat);
    }

    @Benchmark
    public void updateBenchmarkMethod(DaoState state) {
        dao.updateCat(state.birmanCat, state.cat);
    }

    @Benchmark
    public void deleteBenchmarkMethod(DaoState state) {
        dao.deleteCat(state.abyssinianCat);
    }
}
