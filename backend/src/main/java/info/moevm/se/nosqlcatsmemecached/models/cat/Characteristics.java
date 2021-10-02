package info.moevm.se.nosqlcatsmemecached.models.cat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

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
}
