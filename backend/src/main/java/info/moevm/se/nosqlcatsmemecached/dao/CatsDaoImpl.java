package info.moevm.se.nosqlcatsmemecached.dao;

import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import info.moevm.se.nosqlcatsmemecached.utils.cat.CatUtils;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.CatsMemcachedClient;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class CatsDaoImpl implements CatsDao {

    private final int EXP_TIME_IN_SECONDS = 30000;

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

    @SneakyThrows
    private boolean addStringCharacteristics(Map<String, String> map, String breedName) {
        boolean status = true;
        for (Map.Entry<String, String> characteristic : map.entrySet()) {
            if (characteristic.getValue() != null) {
                status = status & client.add(
                    breedName + "." + characteristic.getKey(), EXP_TIME_IN_SECONDS, characteristic.getValue()).get();
            }
        }
        return status;
    }

    @SneakyThrows
    private boolean addCompoundCharacteristics(Map<String, ?> map, String breedName) {
        boolean status = true;
        for (Map.Entry<String, ?> characteristic : map.entrySet()) {
            if (characteristic.getValue() != null) {
                status = status & addToTuple(
                    characteristic.getKey() + "." +
                        characteristic.getValue().toString().replace(" ", "_"), breedName);
                status = status & client.add(breedName + "." + characteristic.getKey(),
                    EXP_TIME_IN_SECONDS, characteristic.getValue()).get();
            }
        }
        return status;
    }

    @SneakyThrows
    private boolean addToTuple(String key, String value) {
        String tupleString = (String) client.get(key);
        if (tupleString == null) {
            return client.add(key, EXP_TIME_IN_SECONDS, value).get();
        }
        Set<String> uniqueValues = Arrays.stream(tupleString.split(";")).collect(Collectors.toSet());
        if (!uniqueValues.add(value)) {
            return false;
        }
        return client.set(key, EXP_TIME_IN_SECONDS, String.join(";", uniqueValues)).get();
    }

    @Override
    public List<Cat> getAllCats() {
        String[] breedNames = ((String) client.get("all_cats")).split(";");
        return Arrays.stream(breedNames).map(this::getCat).collect(Collectors.toList());
    }

    @Override
    public Cat getCat(String breedName) {
        return CatUtils.catFromKeyValueList(
            client.getAllKeys().stream().filter(key -> key.startsWith(breedName))
                  .map(key -> Arrays.asList(key.split("\\.")[1], client.get(key))).collect(Collectors.toList())
        );
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
