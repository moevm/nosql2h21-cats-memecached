package info.moevm.se.nosqlcatsmemecached.utils.memcached.importers;

import java.io.File;

public interface CatsImporter {
    boolean from(File input);
}
