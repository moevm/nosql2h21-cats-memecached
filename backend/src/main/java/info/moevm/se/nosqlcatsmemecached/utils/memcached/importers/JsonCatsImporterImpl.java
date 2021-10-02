package info.moevm.se.nosqlcatsmemecached.utils.memcached.importers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.moevm.se.nosqlcatsmemecached.dao.CatsDao;
import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class JsonCatsImporterImpl implements CatsImporter {

    @Override
    public boolean from(String input) {
        return readJson(input);
    }

    private boolean readJson(String input) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Cat>>() {
        }.getType();
        List<Cat> cats = gson.fromJson(input, type);
        System.out.println(cats);
        return true;
    }
}
