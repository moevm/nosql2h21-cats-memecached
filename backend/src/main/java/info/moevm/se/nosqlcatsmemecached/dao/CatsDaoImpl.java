package info.moevm.se.nosqlcatsmemecached.dao;

import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import info.moevm.se.nosqlcatsmemecached.models.cat.CatFilter;
import info.moevm.se.nosqlcatsmemecached.models.cat.CatQuery;
import info.moevm.se.nosqlcatsmemecached.utils.cat.CatUtils;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.CatsMemcachedClient;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.MemcachedUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

    @Override
    public List<Cat> getCatsByQuery(CatQuery query) {
        return query.getFilters().stream()
                    .map(this::getCatsByFilter)
                    .reduce((lhs, rhs) -> {
                        lhs.retainAll(rhs);
                        return lhs;
                    }).orElseGet(HashSet::new).stream()
                    .map(this::getCat)
                    .filter(cat -> !cat.getBreedName().isBlank())
                    .collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public void drop() {
        client.flush().get();
    }

    private Set<String> getCatsByFilter(CatFilter catFilter) {
        return IntStream.rangeClosed(catFilter.getMin(), catFilter.getMax())
                        .mapToObj(value -> client.get(getFilterString(catFilter.getLocalized(), value)))
                        .map(String::valueOf)
                        .map(memcachedUtils::tupleFrom)
                        .reduce((lhs, rhs) -> {
                            lhs.addAll(rhs);
                            return lhs;
                        }).orElseGet(HashSet::new);
    }

    private String getFilterString(String localized, int value) {
        return String.format(
            "%s.%s",
            localized.replace(" ", "_")
                     .replace("-", "_")
                     .toLowerCase(),
            value
        );
    }
}
