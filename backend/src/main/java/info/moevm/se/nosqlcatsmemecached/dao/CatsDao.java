package info.moevm.se.nosqlcatsmemecached.dao;

import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;

import java.util.List;

public interface CatsDao {
    boolean addCat(Cat cat);

    List<Cat> getAllCats();

    Cat getCat(Long id);

    boolean updateCat(Long id, Cat cat);

    boolean deleteCat(Long id);
}
