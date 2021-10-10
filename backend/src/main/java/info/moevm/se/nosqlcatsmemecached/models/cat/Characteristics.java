package info.moevm.se.nosqlcatsmemecached.models.cat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import info.moevm.se.nosqlcatsmemecached.annotations.InjectMemcachedName;
import info.moevm.se.nosqlcatsmemecached.annotations.MemcachedName;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Component
@Scope("prototype")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Characteristics implements Serializable {

    @InjectMemcachedName
    private Map<String, Method> map;

    @SerializedName("Affectionate with Family")
    @MemcachedName("affectionate_with_family")
    private Integer affectionateWithFamily;

    @SerializedName("Amount of Shedding")
    @MemcachedName("amount_of_shedding")
    private Integer amountOfShedding;

    @SerializedName("Easy to Groom")
    @MemcachedName("easy_to_groom")
    private Integer easyToGroom;

    @SerializedName("Friendly Toward Strangers")
    @MemcachedName("friendly_toward_strangers")
    private Integer friendlyTowardStrangers;

    @SerializedName("General Health")
    @MemcachedName("general_health")
    private Integer generalHealth;

    @SerializedName("Intelligence")
    @MemcachedName("intelligence")
    private Integer intelligence;

    @SerializedName("Kid-Friendly")
    @MemcachedName("kid_friendly")
    private Integer kidFriendly;

    @SerializedName("Pet Friendly")
    @MemcachedName("pet_friendly")
    private Integer petFriendly;

    @SerializedName("Potential for Playfulness")
    @MemcachedName("potential_for_playfulness")
    private Integer potentialForPlayfulness;

    @SerializedName("Tendency to Vocalize")
    @MemcachedName("tendency_to_vocalize")
    private Integer tendencyToVocalize;

    @SneakyThrows
    public Map<String, Integer> getAsMap() {
        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<String, Method> entry : map.entrySet()) {
            result.put(entry.getKey(), (Integer) entry.getValue().invoke(this));
        }
        return result;
    }
}
