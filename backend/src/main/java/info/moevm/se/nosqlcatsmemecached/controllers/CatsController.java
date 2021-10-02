package info.moevm.se.nosqlcatsmemecached.controllers;

import info.moevm.se.nosqlcatsmemecached.dao.CatsDao;
import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cats")
public class CatsController {

    private final CatsDao catsDao;

    public CatsController(CatsDao catsDao) {
        this.catsDao = catsDao;
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
    public ResponseEntity<Cat> addCat(@RequestBody Cat cat) {
        Boolean isCorrect = catsDao.addCat(cat).get();
        if (isCorrect) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
