package info.moevm.se.nosqlcatsmemecached.models.cat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Characteristics implements Serializable {
    private Integer affectionateWithFamily;
    private Integer amountOfShedding;
    private Integer easyToGroom;
    private Integer friendlyTowardStrangers;
    private Integer generalHealth;
    private Integer intelligence;
    private Integer kidFriendly;
    private Integer petFriendly;
    private Integer potentialForPlayfulness;
    private Integer tendencyToVocalize;
}
