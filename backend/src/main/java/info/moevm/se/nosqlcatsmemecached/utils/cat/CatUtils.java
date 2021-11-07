package info.moevm.se.nosqlcatsmemecached.utils.cat;

import info.moevm.se.nosqlcatsmemecached.annotations.InjectMemcachedName;
import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import info.moevm.se.nosqlcatsmemecached.models.cat.Characteristics;
import info.moevm.se.nosqlcatsmemecached.models.cat.VitalStats;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class CatUtils {

    @InjectMemcachedName(value = "info.moevm.se.nosqlcatsmemecached.models.cat.Characteristics", type = "getter")
    private Map<String, Method> characteristicsKeyToGetter;

    @InjectMemcachedName(value = "info.moevm.se.nosqlcatsmemecached.models.cat.VitalStats", type = "getter")
    private Map<String, Method> vitalStatsKeyToGetter;

    @InjectMemcachedName(value = "info.moevm.se.nosqlcatsmemecached.models.cat.Cat", type = "getter")
    private Map<String, Method> catsKeyToGetter;

    @InjectMemcachedName(value = "info.moevm.se.nosqlcatsmemecached.models.cat.Characteristics", type = "setter")
    private Map<String, Method> characteristicsKeyToSetter;

    @InjectMemcachedName(value = "info.moevm.se.nosqlcatsmemecached.models.cat.VitalStats", type = "setter")
    private Map<String, Method> vitalStatsKeyToSetter;

    @InjectMemcachedName(value = "info.moevm.se.nosqlcatsmemecached.models.cat.Cat", type = "setter")
    private Map<String, Method> catsKeyToSetter;

    @SneakyThrows
    public <T> Map<String, T> compoundCharacteristicsAsMap(Cat cat) {
        HashMap<String, T> hashMap = new HashMap<>();
        for (var entry : characteristicsKeyToGetter.entrySet()) {
            hashMap.put(entry.getKey(), cast(entry.getValue().invoke(cat.getCharacteristics())));
        }
        for (var entry : vitalStatsKeyToGetter.entrySet()) {
            hashMap.put(entry.getKey(), cast(entry.getValue().invoke(cat.getVitalStats())));
        }
        return hashMap;
    }

    @SneakyThrows
    public Map<String, String> stringCharacteristicsAsMap(Cat cat) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (var entry : catsKeyToGetter.entrySet()) {
            hashMap.put(entry.getKey(), cast(entry.getValue().invoke(cat)));
        }
        return hashMap;
    }

    public Set<String> compoundCharacteristics() {
        return Stream.concat(characteristicsKeyToGetter.keySet().stream(), vitalStatsKeyToGetter.keySet().stream())
                     .collect(Collectors.toSet());
    }

    public Set<String> stringCharacteristics() {
        return catsKeyToGetter.keySet();
    }

    @SuppressWarnings("all")
    private static <T, U> U cast(T obj) {
        return (U) obj;
    }

    public Cat catFromKeyValueList(List<List<Object>> listOfKeyValuePairs) {
        Cat cat = new Cat();
        VitalStats vitalStats = new VitalStats();
        Characteristics characteristics = new Characteristics();

        listOfKeyValuePairs.forEach(lst -> setField(lst.get(0), lst.get(1), cat, vitalStats, characteristics));

        cat.setCharacteristics(characteristics);
        cat.setVitalStats(vitalStats);
        return cat;
    }

    @SneakyThrows
    @SuppressWarnings("all")
    private void setField(Object key, Object value, Cat cat, VitalStats vitalStats,
                                 Characteristics characteristics) {
        if (catsKeyToSetter.containsKey(key)) {
            catsKeyToSetter.get(key).invoke(cat, new Object[] {value});
        } else if (vitalStatsKeyToSetter.containsKey(key)) {
            vitalStatsKeyToSetter.get(key).invoke(vitalStats, new Object[] {value});
        } else if (characteristicsKeyToSetter.containsKey(key)) {
            characteristicsKeyToSetter.get(key).invoke(characteristics, new Object[] {value});
        }
    }
}
