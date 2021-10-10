package info.moevm.se.nosqlcatsmemecached.utils.cat;

import info.moevm.se.nosqlcatsmemecached.annotations.InjectMemcachedName;
import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import info.moevm.se.nosqlcatsmemecached.models.cat.Characteristics;
import info.moevm.se.nosqlcatsmemecached.models.cat.VitalStats;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class CatUtils {

    @InjectMemcachedName("info.moevm.se.nosqlcatsmemecached.models.cat.Characteristics")
    private Map<String, Function<Characteristics, ?>> characteristicsKeyToGetter;

    @InjectMemcachedName("info.moevm.se.nosqlcatsmemecached.models.cat.VitalStats")
    private Map<String, Function<VitalStats, ?>> vitalStatsKeyToGetter;

    public Map<String, ?> characteristicsAsMap(Cat cat) {
        return characteristicsKeyToGetter.entrySet().stream()
                          .collect(Collectors.toMap(Entry::getKey, e -> e.getValue().apply(cat.getCharacteristics())));
    }
    public Map<String, ?> vitalStatsAsMap(Cat cat) {
        return vitalStatsKeyToGetter.entrySet().stream()
                          .collect(Collectors.toMap(Entry::getKey, e -> e.getValue().apply(cat.getVitalStats())));
    }

}
