package info.moevm.se.nosqlcatsmemecached.models.cat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import info.moevm.se.nosqlcatsmemecached.annotations.MemcachedName;
import java.io.Serializable;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cat implements Serializable {
    @MemcachedName("breed_name")
    private String breedName;

    @MemcachedName("care")
    private String care;

    private Characteristics characteristics;

    @MemcachedName("children_and_pets")
    private String childrenAndPets;

    @MemcachedName("color_and_grooming")
    private String colorAndGrooming;

    @MemcachedName("health")
    private String health;

    @MemcachedName("history")
    private String history;

    @MemcachedName("personality")
    private String personality;

    @MemcachedName("round_img_url")
    private String roundImgUrl;

    @MemcachedName("short_description")
    private String shortDescription;

    @MemcachedName("size")
    private String size;

    private VitalStats vitalStats;
}
