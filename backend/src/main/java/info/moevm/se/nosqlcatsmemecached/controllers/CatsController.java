package info.moevm.se.nosqlcatsmemecached.controllers;

import info.moevm.se.nosqlcatsmemecached.dao.CatsDao;
import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
