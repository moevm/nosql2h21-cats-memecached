package info.moevm.se.nosqlcatsmemecached.utils.memcached.importers;

import info.moevm.se.nosqlcatsmemecached.config.MemcachedConfig;
import info.moevm.se.nosqlcatsmemecached.dao.CatsDao;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.CatsMemcachedClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FromMapImporterImpl {

    private final CatsDao dao;

    private final CatsMemcachedClient client;

    private final MemcachedConfig config;

    public FromMapImporterImpl(CatsDao dao,
                               CatsMemcachedClient client,
                               MemcachedConfig config) {
        this.dao = dao;
        this.client = client;
        this.config = config;
    }

    public boolean from(Map<String, String> map) {
        dao.drop();
        map.forEach((key, value) -> client.add(key, config.getExpirationTime(), value));
        return false;
    }
}
