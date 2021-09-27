package info.moevm.se.nosqlcatsmemecached.config;

import java.net.InetSocketAddress;
import lombok.SneakyThrows;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemcachedConfiguration {

    @Value("${memcached.hostname}")
    protected String hostname;

    @Value("${memcached.port}")
    protected int port;

    @SneakyThrows
    @Bean
    public MemcachedClient memcachedClient() {
        return new MemcachedClient(new InetSocketAddress(hostname, port));
    }
}
