package info.moevm.se.nosqlcatsmemecached.models.cat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import info.moevm.se.nosqlcatsmemecached.annotations.MemcachedName;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Characteristics implements Serializable {

    @MemcachedName("affectionate_with_family")
    private Integer affectionateWithFamily;

    @MemcachedName("amount_of_shedding")
    private Integer amountOfShedding;

    @MemcachedName("easy_to_groom")
    private Integer easyToGroom;

    @MemcachedName("friendly_toward_strangers")
    private Integer friendlyTowardStrangers;

    @MemcachedName("general_health")
    private Integer generalHealth;

    @MemcachedName("intelligence")
    private Integer intelligence;

    @MemcachedName("kid_friendly")
    private Integer kidFriendly;

    @MemcachedName("pet_friendly")
    private Integer petFriendly;

    @MemcachedName("potential_for_playfulness")
    private Integer potentialForPlayfulness;

    @MemcachedName("tendency_to_vocalize")
    private Integer tendencyToVocalize;
}
