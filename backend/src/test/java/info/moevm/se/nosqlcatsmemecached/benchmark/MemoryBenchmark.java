package info.moevm.se.nosqlcatsmemecached.benchmark;


import info.moevm.se.nosqlcatsmemecached.dao.CatsDao;
import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.CatsMemcachedClient;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static info.moevm.se.nosqlcatsmemecached.utils.cat.CatUtils.getListCatsFromFile;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MemoryBenchmark {
    @Autowired
    CatsDao dao;

    @Autowired
    CatsMemcachedClient client;

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65})
    void test(int size) {
        dao.drop();
        List<Cat> cats = getListCatsFromFile("all-cats.json");
        cats.stream().limit(size).forEach(c -> dao.addCat(c));
        System.out.println("Size = " + size + ": " + client.getBytes() + "bytes");
    }
}
