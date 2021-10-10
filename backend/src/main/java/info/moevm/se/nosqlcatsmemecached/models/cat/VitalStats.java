package info.moevm.se.nosqlcatsmemecached.models.cat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class VitalStats implements Serializable {
    @SerializedName("Length")
    private String length;
    @SerializedName("Life Span")
    private String lifeSpan;
    @SerializedName("Origin")
    private String origin;
    @SerializedName("Weight")
    private String weight;
}
