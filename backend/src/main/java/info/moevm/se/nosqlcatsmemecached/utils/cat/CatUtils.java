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
    private Map<String, Function<Characteristics, Integer>> characteristicsKeyToGetter;

    @InjectMemcachedName("info.moevm.se.nosqlcatsmemecached.models.cat.VitalStats")
    private Map<String, Function<VitalStats, String>> vitalStatsKeyToGetter;

    public Map<String, Integer> characteristicsAsMap(Cat cat) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (var entry : characteristicsKeyToGetter.entrySet()) {
            hashMap.put(entry.getKey(), entry.getValue().apply(cat.getCharacteristics()));
        }
        return hashMap;
    }

    public Map<String, String> vitalStatsAsMap(Cat cat) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (var entry : vitalStatsKeyToGetter.entrySet()) {
            hashMap.put(entry.getKey(), entry.getValue().apply(cat.getVitalStats()));
        }
        return hashMap;
    }
}
