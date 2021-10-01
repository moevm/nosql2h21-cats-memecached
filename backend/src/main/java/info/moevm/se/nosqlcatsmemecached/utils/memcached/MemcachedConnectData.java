package info.moevm.se.nosqlcatsmemecached.utils.memcached;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class MemcachedConnectData {
    @Value("${memcached.hostname}")
    private String hostname;
    @Value("${memcached.port}")
    private int port;
}
