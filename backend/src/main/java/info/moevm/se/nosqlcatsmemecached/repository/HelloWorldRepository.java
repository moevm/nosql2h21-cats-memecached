package info.moevm.se.nosqlcatsmemecached.repository;

public interface HelloWorldRepository {
    void set(String key, String value);

    String get(String key);
}
