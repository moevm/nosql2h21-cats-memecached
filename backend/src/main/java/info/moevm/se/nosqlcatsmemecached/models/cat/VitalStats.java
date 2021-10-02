package info.moevm.se.nosqlcatsmemecached.models.cat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VitalStats implements Serializable {
    private String length;
    private String lifeSpan;
    private String origin;
    private String weight;
}
