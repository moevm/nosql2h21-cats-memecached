package info.moevm.se.nosqlcatsmemecached.utils.memcached.exporters;

import com.google.gson.Gson;
import info.moevm.se.nosqlcatsmemecached.dao.CatsDao;
import org.springframework.stereotype.Service;

@Service
public class JsonCatsExporterImpl implements CatsExporter {

    private final CatsDao dao;

    public JsonCatsExporterImpl(CatsDao dao) {
        this.dao = dao;
    }

    @Override
    public String export() {
        return new Gson().toJson(dao.getAllCats());
    }
}
