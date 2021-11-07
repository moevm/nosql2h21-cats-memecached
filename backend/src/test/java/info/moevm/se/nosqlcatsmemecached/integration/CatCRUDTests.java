package info.moevm.se.nosqlcatsmemecached.integration;

import info.moevm.se.nosqlcatsmemecached.dao.CatsDao;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.importers.CatsImporter;
import java.io.File;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CatCRUDTests {

    @Autowired
    private CatsDao dao;

    @Autowired
    private CatsImporter importer;

    @BeforeTestClass
    @SuppressWarnings("all")
    public void setUpMemcachedDatabase() {
        //        importer.from(
        //            new File(CatCRUDTests.class.getClassLoader().getResource("all-cats.json").getFile()));
    }

    @Test
    public void allCatsTest() {
        importer.from(
            new File(CatCRUDTests.class.getClassLoader().getResource("all-cats.json").getFile()));
        System.out.println(dao.getAllCats());
    }
}
