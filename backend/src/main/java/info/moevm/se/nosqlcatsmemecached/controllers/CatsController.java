package info.moevm.se.nosqlcatsmemecached.controllers;

import info.moevm.se.nosqlcatsmemecached.dao.CatsDao;
import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import info.moevm.se.nosqlcatsmemecached.models.cat.CatQuery;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.exporters.CatsExporter;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.importers.CatsImporter;
import java.util.List;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cats")
public class CatsController {

    private static final Logger LOG = LoggerFactory.getLogger(CatsController.class);

    private final CatsDao catsDao;

    private final CatsImporter importer;

    private final CatsExporter exporter;

    public CatsController(CatsDao catsDao,
                          @Qualifier("jsonCatsImporterImpl") CatsImporter importer,
                          @Qualifier("jsonCatsExporterImpl") CatsExporter exporter) {
        this.catsDao = catsDao;
        this.importer = importer;
        this.exporter = exporter;
    }

    @GetMapping
    public List<Cat> getAllCats() {
        return catsDao.getAllCats();
    }

    @GetMapping("/{key}")
    public Cat getCat(@PathVariable("key") String key) {
        return catsDao.getCat(key);
    }

    @PostMapping("/filter")
    public List<Cat> getCatsByQuery(@RequestBody CatQuery query) {
        return catsDao.getCatsByQuery(query);
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<Void> addCat(@RequestBody Cat cat) {
        boolean isCorrect = catsDao.addCat(cat);
        if (isCorrect) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @SneakyThrows
    @PostMapping("/import")
    public ResponseEntity<Void> importDatabase(@RequestBody String catsJson) {
        boolean isCorrect = importer.from(catsJson);
        if (isCorrect) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/export")
    public String exportDatabase() {
        return exporter.export();
    }

}
