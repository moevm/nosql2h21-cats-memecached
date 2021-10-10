package info.moevm.se.nosqlcatsmemecached.models.cat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Characteristics implements Serializable {
    @SerializedName("Affectionate with Family")
    private Integer affectionateWithFamily;
    @SerializedName("Amount of Shedding")
    private Integer amountOfShedding;
    @SerializedName("Easy to Groom")
    private Integer easyToGroom;
    @SerializedName("Friendly Toward Strangers")
    private Integer friendlyTowardStrangers;
    @SerializedName("General Health")
    private Integer generalHealth;
    @SerializedName("Intelligence")
    private Integer intelligence;
    @SerializedName("Kid-Friendly")
    private Integer kidFriendly;
    @SerializedName("Pet Friendly")
    private Integer petFriendly;
    @SerializedName("Potential for Playfulness")
    private Integer potentialForPlayfulness;
    @SerializedName("Tendency to Vocalize")
    private Integer tendencyToVocalize;

    public Map<String, Integer> getAsMap(){
        Map<String, Integer> map = new HashMap<>();
        map.put("affectionate_with_family", getAffectionateWithFamily());
        map.put("amount_of_shedding", getAmountOfShedding());
        map.put("easy_to_groom", getEasyToGroom());
        map.put("friendly_toward_strangers", getFriendlyTowardStrangers());
        map.put("general_health", getGeneralHealth());
        map.put("intelligence", getIntelligence());
        map.put("kid_friendly", getKidFriendly());
        map.put("pet_friendly", getPetFriendly());
        map.put("potential_for_playfulness", getPotentialForPlayfulness());
        map.put("tendency_to_vocalize", getTendencyToVocalize());
        return map;
    }
}
