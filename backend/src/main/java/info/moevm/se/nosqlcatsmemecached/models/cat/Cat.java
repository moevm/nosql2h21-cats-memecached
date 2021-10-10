package info.moevm.se.nosqlcatsmemecached.models.cat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cat implements Serializable {
    @SerializedName("breed_name")
    private String breedName;
    private String care;
    private Characteristics characteristics;
    @SerializedName("children_and_pets")
    private String childrenAndPets;
    @SerializedName("color_and_grooming")
    private String colorAndGrooming;
    private String health;
    private String history;
    private String personality;
    @SerializedName("round_img_url")
    private String roundImgUrl;
    @SerializedName("short_description")
    private String shortDescription;
    private String size;
    @SerializedName("vital_stats")
    private VitalStats vitalStats;
}
