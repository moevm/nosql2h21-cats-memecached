package info.moevm.se.nosqlcatsmemecached.utils.memcached;

import info.moevm.se.nosqlcatsmemecached.config.MemcachedConfig;
import info.moevm.se.nosqlcatsmemecached.utils.cat.CatUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.spy.memcached.internal.OperationFuture;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemcachedUtils {

    private final MemcachedConfig config;

    private final CatsMemcachedClient client;

    private final CatUtils catUtils;

    public Set<String> tupleFrom(String tupleString) {
        if (tupleString.equals("null")) {
            return new HashSet<>();
        }
        return Arrays.stream(tupleString.split(config.getTupleSeparator().replace("\"", ""))).collect(Collectors.toSet());
    }

    public String stringFrom(Set<String> uniqueValues) {
        return String.join(config.getTupleSeparator().replace("\"",""), uniqueValues);
    }

    public String keyName(String... items) {
        return String.join(config.getFieldSeparator(), items).replaceAll("\"", "");
    }

    @SneakyThrows
    public boolean addStringCharacteristics(Map<String, String> map, String breedName) {
        boolean status = true;
        for (Map.Entry<String, String> characteristic : map.entrySet()) {
            if (characteristic.getValue() != null) {
                status = status & client.add(
                    keyName(breedName, characteristic.getKey()), config.getExpirationTime(),
                    characteristic.getValue()).get();
            }
        }
        return status;
    }

    @SneakyThrows
    public boolean addCompoundCharacteristics(Map<String, ?> map, String breedName) {
        boolean status = true;
        for (Map.Entry<String, ?> characteristic : map.entrySet()) {
            if (characteristic.getValue() != null) {
                status = status & addToTuple(
                    keyName(characteristic.getKey(),
                        characteristic.getValue().toString().replace(" ", "_")), breedName);
                status = status & client.add(keyName(breedName, characteristic.getKey()),
                    config.getExpirationTime(), characteristic.getValue()).get();
            }
        }
        return status;
    }

    @SneakyThrows
    public boolean addToTuple(String key, String value) {
        String tupleString = (String) client.get(key);
        if (tupleString == null) {
            return client.add(key, config.getExpirationTime(), value).get();
        }
        Set<String> uniqueValues = tupleFrom(tupleString);
        if (!uniqueValues.add(value)) {
            return false;
        }
        return client.set(key, config.getExpirationTime(), stringFrom(uniqueValues)).get();
    }

    public boolean deleteCompoundAndStringCharacteristics(String breedName) {
        final Set<String> compoundKeys = catUtils.compoundCharacteristics();
        final Set<String> stringKeys = catUtils.stringCharacteristics();

        boolean status = Stream.concat(compoundKeys.stream(), stringKeys.stream())
                               .map(secondKey -> client.delete(keyName(breedName, secondKey)))
                               .map(this::uncheckedGet).reduce(true, (a, b) -> a && b);
        final List<String> allKeys = client.getAllKeys();
        compoundKeys.forEach(compoundKey -> {
            allKeys.stream().filter(k -> k.startsWith(compoundKey))
                   .forEach(filteredCompoundKey -> deleteFromTuple(filteredCompoundKey, breedName));
        });
        return status;
    }

    @SneakyThrows
    private boolean uncheckedGet(OperationFuture<Boolean> future) {
        return future.get();
    }

    @SneakyThrows
    public boolean deleteFromTuple(String key, String value) {
        String tupleString = (String) client.get(key);
        if (tupleString == null) {
            return true;
        }
        Set<String> uniqueValues = tupleFrom(tupleString);
        uniqueValues.remove(value);
        return client.set(key, config.getExpirationTime(), stringFrom(uniqueValues)).get();
    }
}
