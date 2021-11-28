package info.moevm.se.nosqlcatsmemecached.utils.memcached.exporters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.CatsMemcachedClient;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DumpMapExporterImpl implements CatsExporter {

    private final CatsMemcachedClient client;

    @SneakyThrows
    @Override
    public String export() {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(
            client.getAllKeys().stream()
                  .collect(Collectors.toMap(
                      String::valueOf,
                      key -> String.valueOf(client.get(key)))
                  )
        );
    }
}
