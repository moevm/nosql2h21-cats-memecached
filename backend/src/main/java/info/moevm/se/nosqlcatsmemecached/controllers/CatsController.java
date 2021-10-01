package info.moevm.se.nosqlcatsmemecached.controllers;

import info.moevm.se.nosqlcatsmemecached.dao.CatsDao;
import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cats")
public class CatsController {

    @Autowired
    private CatsDao catsDao;

    @GetMapping()
    public List<Cat> getAllCats() {
        return catsDao.getAllCats();
    }

    @GetMapping("/{id}")
    public Cat getCat(@PathVariable("id") Long id) {
        return catsDao.getCat(id);
    }

    @SneakyThrows
    @PostMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cat> addCat(@RequestBody Cat cat) {
        Boolean isCorrect = catsDao.addCat(cat).get();
        if (isCorrect) {
            return new ResponseEntity<Cat>(HttpStatus.CREATED);
        }
        return new ResponseEntity<Cat>(HttpStatus.BAD_REQUEST);
    }
}
