package info.moevm.se.nosqlcatsmemecached.models.cat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import info.moevm.se.nosqlcatsmemecached.annotations.MemcachedName;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class VitalStats implements Serializable {
    @SerializedName("Length")
    @MemcachedName("length")
    private String length;

    @SerializedName("Life Span")
    @MemcachedName("life_span")
    private String lifeSpan;

    @SerializedName("Origin")
    @MemcachedName("origin")
    private String origin;

    @SerializedName("Weight")
    @MemcachedName("weight")
    private String weight;
}
