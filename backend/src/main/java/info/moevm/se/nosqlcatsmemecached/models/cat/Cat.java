package info.moevm.se.nosqlcatsmemecached.models.cat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cat implements Serializable {
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
