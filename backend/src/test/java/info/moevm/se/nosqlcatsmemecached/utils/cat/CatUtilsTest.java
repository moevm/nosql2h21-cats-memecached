package info.moevm.se.nosqlcatsmemecached.utils.cat;

import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import info.moevm.se.nosqlcatsmemecached.models.cat.Characteristics;
import info.moevm.se.nosqlcatsmemecached.models.cat.VitalStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CatUtilsTest {

    @Autowired
    CatUtils catUtils;

    @Test
    void test(){
        Cat cat = new Cat();
        Characteristics characteristics = new Characteristics();
        cat.setCharacteristics(characteristics);
        cat.setVitalStats(new VitalStats());

        System.out.println(catUtils.characteristicsAsMap(cat));
        System.out.println(catUtils.vitalStatsAsMap(cat));
    }
}