package info.moevm.se.nosqlcatsmemecached.models.cat;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class CatQuery {
    private String search;
    private List<CatFilter> filters;
}
