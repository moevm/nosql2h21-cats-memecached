package info.moevm.se.nosqlcatsmemecached.runners;

import info.moevm.se.nosqlcatsmemecached.utils.memcached.CatsMemcachedClient;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.importers.CatsImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Profile("!localhost")
public class MemcachedInitializationRunner implements ApplicationRunner {

    @Autowired
    private CatsImporter importer;

    @Autowired
    private CatsMemcachedClient client;

    private static final Logger LOG = LoggerFactory.getLogger(MemcachedInitializationRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG.info("Cleaning up memcached data");
        client.flush().get();
        LOG.info("Initializing memcached data from default file");
        importer.from(new File(MemcachedInitializationRunner.class
                .getClassLoader().getResource("all-cats-new.json").getFile())
        );
    }
}
