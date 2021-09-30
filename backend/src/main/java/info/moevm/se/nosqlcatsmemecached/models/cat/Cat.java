package info.moevm.se.nosqlcatsmemecached.models.cat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cat {
    private String breedName;
    private String care;
    private Characteristics characteristics;
    private String childrenAndPets;
    private String colorAndGrooming;
    private String health;
    private String history;
    private String personality;
    private String roundImgUrl;
    private String shortDescription;
    private String size;
    private VitalStats vitalStats;
}