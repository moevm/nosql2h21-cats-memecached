package info.moevm.se.nosqlcatsmemecached.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@Getter
public class MemcachedConfig {

    @Value("${memcached.hostname}")
    private String hostname;

    @Value("${memcached.port}")
    private int port;

    @Value("${memcached.field_separator}")
    private String fieldSeparator;

    @Value("${memcached.tuple_separator}")
    private String tupleSeparator;

    @Value("${memcached.expiration_time}")
    private int expirationTime;

}
