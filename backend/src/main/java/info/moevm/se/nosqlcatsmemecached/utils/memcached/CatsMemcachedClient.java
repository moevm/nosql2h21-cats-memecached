package info.moevm.se.nosqlcatsmemecached.utils.memcached;

import net.spy.memcached.MemcachedClient;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.InetSocketAddress;

@Repository
public class CatsMemcachedClient extends MemcachedClient {
    public CatsMemcachedClient(MemcachedConnectData data) throws IOException {
        super(new InetSocketAddress(data.getHostname(), data.getPort()));
    }
}
