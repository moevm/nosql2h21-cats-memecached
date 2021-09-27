package info.moevm.se.nosqlcatsmemecached.utils;

import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemcachedAdapter {

    @Autowired
    protected MemcachedClient client;

    public MemcachedClient client() {
        return client;
    }

}
