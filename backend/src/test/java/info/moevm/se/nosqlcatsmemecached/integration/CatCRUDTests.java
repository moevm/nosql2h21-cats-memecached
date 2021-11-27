package info.moevm.se.nosqlcatsmemecached.integration;

import info.moevm.se.nosqlcatsmemecached.dao.CatsDao;
import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.importers.CatsImporter;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CatCRUDTests {

    @Autowired
    private CatsDao dao;

    @Autowired
    private CatsImporter importer;

    private static final Logger LOG = LoggerFactory.getLogger(CatCRUDTests.class);

    @Test
    public void allCatsTest() {
        final List<Cat> allCats = dao.getAllCats();
        LOG.info("Cat list size: " + allCats.size());
        LOG.info("Cat breeds list: " + allCats.stream().map(Cat::getBreedName).collect(Collectors.joining(", ")));
        final Cat catToBeDeleted = allCats.get(0);
        dao.deleteCat(catToBeDeleted.getBreedName());
        final List<Cat> newList = dao.getAllCats();
        LOG.info("Cat list size after deletion: " + newList.size());
        LOG.info("Cat breeds list after deletion: " + newList.stream().map(Cat::getBreedName).collect(Collectors.joining(", ")));
    }
}
