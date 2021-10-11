package info.moevm.se.nosqlcatsmemecached.controllers;

import info.moevm.se.nosqlcatsmemecached.dao.CatsDao;
import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.importers.CatsImporter;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/cats")
public class CatsController {

    private final CatsDao catsDao;

    private final CatsImporter importer;

    public CatsController(CatsDao catsDao, CatsImporter importer) {
        this.catsDao = catsDao;
        this.importer = importer;
    }

    @GetMapping()
    public List<Cat> getAllCats() {
        return catsDao.getAllCats();
    }

    @GetMapping("/{key}")
    public Cat getCat(@PathVariable("key") String key) {
        return catsDao.getCat(key);
    }

    @SneakyThrows
    @PostMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addCat(@RequestBody Cat cat) {
        boolean isCorrect = catsDao.addCat(cat);
        if (isCorrect) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @SneakyThrows
    @PutMapping()
    public ResponseEntity<Void> importDatabase() {
        boolean isCorrect = importer.from(new File("all-cats.json"));
        if (isCorrect) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
