package info.moevm.se.nosqlcatsmemecached.models.cat;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CatFilter {
    String id;
    int min;
    int max;
    String localized;
}
