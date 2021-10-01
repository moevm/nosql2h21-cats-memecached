package info.moevm.se.nosqlcatsmemecached.dao;

import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.CatsMemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class CatsDaoImpl implements CatsDao {

    private final static int ONE_HUNDRED_DAYS = 60 * 60 * 24 * 100;
    private final CatsMemcachedClient client;

    public CatsDaoImpl(CatsMemcachedClient client) {
        this.client = client;
    }

    @Override
    public OperationFuture<Boolean> addCat(Cat cat) {
        return client.add(cat.getBreedName(), 30, cat);
    }

    @Override
    public List<Cat> getAllCats() {
        List<Cat> cats = new ArrayList<>();
        return cats;
    }

    @Override
    public Cat getCat(Long id) {
        return (Cat) client.get("cat");
    }

    @Override
    public boolean updateCat(Long id, Cat cat) {
        return false;
    }

    @Override
    public boolean deleteCat(Long id) {
        return false;
    }

}
