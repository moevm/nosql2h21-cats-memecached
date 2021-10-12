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
public class Cat implements Serializable {
    @SerializedName("breed_name")
    @MemcachedName("breed_name")
    private String breedName;

    @MemcachedName("care")
    private String care;

    private Characteristics characteristics;

    @SerializedName("children_and_pets")
    @MemcachedName("children_and_pets")
    private String childrenAndPets;

    @SerializedName("color_and_grooming")
    @MemcachedName("color_and_grooming")
    private String colorAndGrooming;

    @MemcachedName("health")
    private String health;

    @MemcachedName("history")
    private String history;

    @MemcachedName("personality")
    private String personality;

    @SerializedName("round_img_url")
    @MemcachedName("round_img_url")
    private String roundImgUrl;

    @SerializedName("short_description")
    @MemcachedName("short_description")
    private String shortDescription;

    @MemcachedName("size")
    private String size;

    @SerializedName("vital_stats")
    private VitalStats vitalStats;
}
