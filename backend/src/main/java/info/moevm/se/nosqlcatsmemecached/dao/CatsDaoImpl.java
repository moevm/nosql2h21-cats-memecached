package info.moevm.se.nosqlcatsmemecached.dao;

import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import info.moevm.se.nosqlcatsmemecached.utils.cat.CatUtils;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.CatsMemcachedClient;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Primary
public class CatsDaoImpl implements CatsDao {

    private final CatsMemcachedClient client;

    public CatsDaoImpl(CatsMemcachedClient client) {
        this.client = client;
    }

    @Override
    public boolean addCat(Cat cat) {
        String breedName = cat.getBreedName().replace(" ", "_");
        boolean status = addToTuple("all_cats", breedName);
        status = status & addCompoundCharacteristics(CatUtils.compoundCharacteristicsAsMap(cat), breedName);
        status = status & addStringCharacteristics(CatUtils.stringCharacteristicsAsMap(cat), breedName);
        return status;
    }

    private boolean addStringCharacteristics(Map<String, String> map, String breedName) {
        boolean status = true;
        for (Map.Entry<String, String> characteristic : map.entrySet()) {
            if (characteristic.getValue() != null) {
                status = status & addToTuple(
                        breedName + "." + characteristic.getKey(), characteristic.getValue());
            }
        }
        return status;
    }

    private boolean addCompoundCharacteristics(Map<String, ?> map, String breedName) {
        boolean status = true;
        for (Map.Entry<String, ?> characteristic : map.entrySet()) {
            if (characteristic.getValue() != null) {
                status = status & addToTuple(
                        characteristic.getKey() + "." +
                                characteristic.getValue().toString().replace(" ", "_"), breedName);
            }
        }
        return status;
    }

    @SneakyThrows
    private boolean addToTuple(String key, String value) {
        String tupleString = (String) client.get(key);
        boolean status;
        if (tupleString == null) {
            status = client.add(key, 300, value).get();
        } else {
            Set<String> uniqueValues = Arrays.stream(tupleString.split(";")).collect(Collectors.toSet());
            boolean isNewValue = uniqueValues.add(value);
            if (!isNewValue) {
                return false;
            }
            status = client.set(key, 300, String.join(";", uniqueValues)).get();
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
