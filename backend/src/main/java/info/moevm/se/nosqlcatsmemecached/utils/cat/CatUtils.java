package info.moevm.se.nosqlcatsmemecached.utils.cat;

import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class CatUtils {

    private static Map<String, Function<Cat, ?>> keyToGetter;

    static {
        Arrays.stream(Cat.class.getDeclaredMethods()).filter(m -> m.getName().startsWith("get"))
                                     .map(method -> new Function<>() {
                                         @SneakyThrows
                                         @Override
                                         public Object apply(Object cat) {
                                             return method.invoke(cat);
                                         }
                                     })
                                 .collect(Collectors.toList());
    }

    public static Map<String, ?> asMap(Cat cat) {
        return keyToGetter.entrySet().stream()
                          .collect(Collectors.toMap(Entry::getKey, e -> e.getValue().apply(cat)));
    }

}
