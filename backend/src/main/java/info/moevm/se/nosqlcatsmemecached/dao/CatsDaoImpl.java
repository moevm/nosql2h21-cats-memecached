package info.moevm.se.nosqlcatsmemecached.dao;

import info.moevm.se.nosqlcatsmemecached.config.MemcachedConfig;
import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import info.moevm.se.nosqlcatsmemecached.utils.cat.CatUtils;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.CatsMemcachedClient;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.MemcachedUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
@RequiredArgsConstructor
public class CatsDaoImpl implements CatsDao {

    private final CatUtils catUtils;

    private final CatsMemcachedClient client;

    private final MemcachedUtils memcachedUtils;

    @Override
    public boolean addCat(Cat cat) {
        String breedName = cat.getBreedName().replace(" ", "_");
        boolean status = memcachedUtils.addToTuple("all_cats", breedName);
        status &= memcachedUtils.addCompoundCharacteristics(catUtils.compoundCharacteristicsAsMap(cat), breedName);
        status &= memcachedUtils.addStringCharacteristics(catUtils.stringCharacteristicsAsMap(cat), breedName);
        return status;
    }

    @Override
    public List<Cat> getAllCats() {
        var breedNames = memcachedUtils.tupleFrom(String.valueOf(client.get("all_cats")));
        return breedNames.stream().map(this::getCat).collect(Collectors.toList());
    }

    @Override
    public Cat getCat(String breedName) {
        return catUtils.catFromKeyValueList(
            client.getAllKeys().stream().filter(key -> key.startsWith(breedName))
                  .map(key -> Arrays.asList(key.split("\\.")[1], client.get(key))).collect(Collectors.toList())
        );
    }

    // TODO need to be optimized
    @Override
    public boolean updateCat(String key, Cat cat) {
        boolean status = deleteCat(key);
        status &= addCat(cat);
        return status;
    }

    @Override
    public boolean deleteCat(String key) {
        key = key.replaceAll(" ", "_");
        boolean status = memcachedUtils.deleteFromTuple("all_cats", key);
        status &= memcachedUtils.deleteCompoundAndStringCharacteristics(key);
        return status;
    }
}
