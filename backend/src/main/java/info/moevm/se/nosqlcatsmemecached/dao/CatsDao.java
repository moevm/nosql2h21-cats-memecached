package info.moevm.se.nosqlcatsmemecached.dao;

import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;

import info.moevm.se.nosqlcatsmemecached.models.cat.CatQuery;
import java.util.List;
import java.util.Map;

public interface CatsDao {
    boolean addCat(Cat cat);

    List<Cat> getAllCats();

    Cat getCat(String key);

    boolean updateCat(String key, Cat cat);

    boolean deleteCat(String key);

    List<Cat> getCatsByQuery(CatQuery query);

    Map<String, String> dump();
}
