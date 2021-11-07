package info.moevm.se.nosqlcatsmemecached.utils.memcached;

import info.moevm.se.nosqlcatsmemecached.config.MemcachedConfig;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.spy.memcached.MemcachedClient;
import org.springframework.stereotype.Repository;

@Repository
public class CatsMemcachedClient extends MemcachedClient {
    public CatsMemcachedClient(MemcachedConfig data) throws IOException {
        super(new InetSocketAddress(data.getHostname(), data.getPort()));
    }

    public List<String> getAllKeys() {
        return getUniqueSlabIds().stream()
                .map(slabId -> String.format("cachedump %s 0", slabId))
                .map(this::getStats)
                .map(Map::values).flatMap(Collection::stream)
                .map(Map::keySet).flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    protected Set<String> getUniqueSlabIds() {
        return this.getStats("items").values().stream()
                .map(Map::keySet).flatMap(Collection::stream)
                .map(propertyName -> propertyName.split(":")[1])
                .collect(Collectors.toSet());
    }
}
