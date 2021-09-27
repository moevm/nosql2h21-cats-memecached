package info.moevm.se.nosqlcatsmemecached.repository;

import info.moevm.se.nosqlcatsmemecached.utils.MemcachedAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CatMemcachedRepository implements HelloWorldRepository {

    @Autowired
    protected MemcachedAdapter memcachedAdapter;

    @Override
    public void set(String key, String value) {
        memcachedAdapter.client().set(key, 0, value);
    }

    @Override
    public String get(String key) {
        return String.valueOf(memcachedAdapter.client().get(key));
    }
}
