package info.moevm.se.nosqlcatsmemecached.utils.memcached.importers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest()
class JsonCatsImporterImplTest {
    @Autowired
    private CatsImporter importer;

    @Test
    void fromString() {
        boolean status = importer.from(
                new File(this.getClass().getClassLoader().getResource("all-cats.json").getFile()));
        assertTrue(status);
    }
}