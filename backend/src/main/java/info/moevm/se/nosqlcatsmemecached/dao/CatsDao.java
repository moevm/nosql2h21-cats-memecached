package info.moevm.se.nosqlcatsmemecached.dao;

import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import net.spy.memcached.internal.OperationFuture;

import java.util.List;

public interface CatsDao {
    OperationFuture<Boolean> addCat(Cat cat);

    List<Cat> getAllCats();

    Cat getCat(String key);

    boolean updateCat(String key, Cat cat);

    boolean deleteCat(String key);
}
