package info.moevm.se.nosqlcatsmemecached.utils.memcached.exporters;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.CatsMemcachedClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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
