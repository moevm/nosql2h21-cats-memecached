package info.moevm.se.nosqlcatsmemecached.controllers;

import info.moevm.se.nosqlcatsmemecached.utils.memcached.exporters.CatsExporter;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.importers.CatsImporter;
import info.moevm.se.nosqlcatsmemecached.utils.memcached.importers.FromMapImporterImpl;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memcached")
public class MemcachedController {

    private final FromMapImporterImpl importer;

    @Qualifier("dumpMapExporterImpl")
    private final CatsExporter exporter;

    public MemcachedController(FromMapImporterImpl importer,
                               @Qualifier("dumpMapExporterImpl") CatsExporter exporter) {
        this.importer = importer;
        this.exporter = exporter;
    }

    @GetMapping("/dump")
    public String dumpMemcachedPairs() {
        return exporter.export();
    }

    @PostMapping("/import")
    public ResponseEntity<Void> importDatabase(@RequestBody Map<String, String> map) {
        boolean isCorrect = importer.from(map);
        if (isCorrect) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
