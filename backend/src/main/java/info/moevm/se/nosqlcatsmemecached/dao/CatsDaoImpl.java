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
    public boolean addCat(Cat cat) {
        String breedName = cat.getBreedName();
        boolean status = addToTuple("all_cats", breedName);
        status = status & addCharacteristics(cat.getCharacteristics().getAsMap(), cat.getBreedName());
        return status;
    }

    private boolean addCharacteristics(Map<String, Integer> characteristics, String breedName) {
        boolean status = true;
        for (Map.Entry<String, Integer> characteristic : characteristics.entrySet()) {
            if (characteristic.getValue() != null) {
                status = status & addToTuple(characteristic.getKey() + "." + characteristic.getValue(), breedName);
            }
        }
        return status;
    }

    @SneakyThrows
    private boolean addToTuple(String key, String value) {
        String tupleString = (String) client.get(key);
        boolean status;
        if (tupleString == null) {
            status = client.add(key, 60, value).get();
        } else {
            Set<String> uniqueValues = Arrays.stream(tupleString.split(";")).collect(Collectors.toSet());
            boolean isNewValue = uniqueValues.add(key);
            if (!isNewValue) {
                return false;
            }
            status = client.set(key, 60, String.join(";", uniqueValues)).get();
        }
        return status;
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
