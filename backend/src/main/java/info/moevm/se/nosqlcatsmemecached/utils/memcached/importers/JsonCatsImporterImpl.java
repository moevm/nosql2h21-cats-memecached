package info.moevm.se.nosqlcatsmemecached.utils.memcached.importers;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.moevm.se.nosqlcatsmemecached.dao.CatsDao;
import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Primary
public class JsonCatsImporterImpl implements CatsImporter {

    private final CatsDao dao;

    public JsonCatsImporterImpl(CatsDao dao) {
        this.dao = dao;
    }

    @SneakyThrows
    @Override
    public boolean from(File input) {
        String jsonString = String.join("", Files.readLines(input, Charsets.UTF_8));
        return readJson(jsonString);
    }

    private boolean readJson(String input) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Cat>>() {
        }.getType();
        List<Cat> cats = gson.fromJson(input, type);
        cats.forEach(this::saveCat);
        return true;
    }

    @SneakyThrows
    private boolean saveCat(Cat cat) {
        return dao.addCat(cat);
    }
}
