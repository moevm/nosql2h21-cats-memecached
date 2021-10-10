package info.moevm.se.nosqlcatsmemecached.dao;

import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.CatsMemcachedClient;
import java.util.ArrayList;
import java.util.List;
import net.spy.memcached.internal.OperationFuture;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class CatsDaoImpl implements CatsDao {

    private final CatsMemcachedClient client;

    public CatsDaoImpl(CatsMemcachedClient client) {
        this.client = client;
    }

    @Override
    public OperationFuture<Boolean> addCat(Cat cat) {
        return client.add(cat.getBreedName(), 60, cat);
    }

    // TODO need to be implemented when db is ready
    @Override
    public List<Cat> getAllCats() {
        return new ArrayList<>();
    }

    // TODO need to be implemented when db is ready
    @Override
    public Cat getCat(String key) {
        return (Cat) client.get(key);
    }

    // TODO need to be implemented when db is ready
    @Override
    public boolean updateCat(String key, Cat cat) {
        return false;
    }

    // TODO need to be implemented when db is ready
    @Override
    public boolean deleteCat(String key) {
        return false;
    }
}
