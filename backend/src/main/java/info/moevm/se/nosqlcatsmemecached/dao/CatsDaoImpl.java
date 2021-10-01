package info.moevm.se.nosqlcatsmemecached.dao;

import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.CatsMemcachedClient;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
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

    public List<String> getCachedKeys() {
        return client.getStats("items").values().stream()
                     .map(Map::keySet).flatMap(Collection::stream)
                     .map(propertyName -> propertyName.split(":")[1])
                     .collect(Collectors.toSet()).stream()
                     .map(slabId -> String.format("cachedump %s 0", slabId))
                     .map(client::getStats)
                     .map(Map::values).flatMap(Collection::stream)
                     .map(Map::keySet).flatMap(Collection::stream)
                     .collect(Collectors.toList());
    }
}
