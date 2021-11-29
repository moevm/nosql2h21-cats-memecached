package info.moevm.se.nosqlcatsmemecached.models.cat;

import java.util.List;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CatQuery {
    private List<CatFilter> filters;
}
