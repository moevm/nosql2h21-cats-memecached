package info.moevm.se.nosqlcatsmemecached.utils.memcached.importers;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.moevm.se.nosqlcatsmemecached.dao.CatsDao;
import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import java.io.File;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class JsonCatsImporterImpl implements CatsImporter {

    private final CatsDao dao;

    public JsonCatsImporterImpl(CatsDao dao) {
        this.dao = dao;
    }

    @SneakyThrows
    @Override
    public boolean from(File input) {
        dao.drop();
        String jsonString = String.join("", Files.readLines(input, Charsets.UTF_8));
        return readJson(jsonString);
    }

    @Override
    public boolean from(String jsonString) {
        dao.drop();
        return readJson(jsonString);
    }

    private boolean readJson(String input) {
        System.out.println(input);
        List<Cat> cats = new Gson().fromJson(input, new TypeToken<List<Cat>>() {
        }.getType());
        return cats.stream().allMatch(dao::addCat);
    }
}
