package info.moevm.se.nosqlcatsmemecached.utils.cat;

import info.moevm.se.nosqlcatsmemecached.annotations.InjectMemcachedName;
import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import info.moevm.se.nosqlcatsmemecached.models.cat.Characteristics;
import info.moevm.se.nosqlcatsmemecached.models.cat.VitalStats;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class CatUtils {

    @InjectMemcachedName("info.moevm.se.nosqlcatsmemecached.models.cat.Characteristics")
    private static Map<String, Function<Characteristics, Integer>> characteristicsKeyToGetter;

    @InjectMemcachedName("info.moevm.se.nosqlcatsmemecached.models.cat.VitalStats")
    private static Map<String, Function<VitalStats, String>> vitalStatsKeyToGetter;

    @InjectMemcachedName("info.moevm.se.nosqlcatsmemecached.models.cat.Cat")
    private static Map<String, Function<Cat, String>> catsKeyToGetter;

    public static <T> Map<String, T> compoundCharacteristicsAsMap(Cat cat) {
        HashMap<String, T> hashMap = new HashMap<>();
        for (var entry : characteristicsKeyToGetter.entrySet()) {
            hashMap.put(entry.getKey(), cast(entry.getValue().apply(cat.getCharacteristics())));
        }
        for (var entry : vitalStatsKeyToGetter.entrySet()) {
            hashMap.put(entry.getKey(), cast(entry.getValue().apply(cat.getVitalStats())));
        }
        return hashMap;
    }

    public static Map<String, String> stringCharacteristicsAsMap(Cat cat) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (var entry : catsKeyToGetter.entrySet()) {
            hashMap.put(entry.getKey(), entry.getValue().apply(cat));
        }
        return hashMap;
    }

    @SuppressWarnings("all")
    private static <T, U> U cast(T obj) {
        return (U) obj;
    }
}
