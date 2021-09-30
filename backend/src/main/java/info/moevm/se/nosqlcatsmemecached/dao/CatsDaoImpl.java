package info.moevm.se.nosqlcatsmemecached.dao;

import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import info.moevm.se.nosqlcatsmemecached.models.cat.Characteristics;
import info.moevm.se.nosqlcatsmemecached.models.cat.VitalStats;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class CatsDaoImpl implements CatsDao {
    @Override
    public boolean addCat(Cat cat) {
        return false;
    }

    @Override
    public List<Cat> getAllCats() {
        Cat cat = demoCat();
        List<Cat> cats = new ArrayList<>();
        cats.add(cat);
        return cats;

    }

    @Override
    public Cat getCat(Long id) {
        return demoCat();
    }

    @Override
    public boolean updateCat(Long id, Cat cat) {
        return false;
    }

    @Override
    public boolean deleteCat(Long id) {
        return false;
    }

    private Cat demoCat() {
        Cat cat = new Cat();
        cat.setBreedName("Abyssinian");
        cat.setCare("The short, fine coat of the Abyssinian is easily...");
        Characteristics characteristics = new Characteristics();
        characteristics.setAffectionateWithFamily(3);
        characteristics.setAmountOfShedding(3);
        characteristics.setEasyToGroom(3);
        characteristics.setGeneralHealth(5);
        characteristics.setKidFriendly(5);
        cat.setCharacteristics(characteristics);
        cat.setHealth("Both pedigreed cats and mixed-breed cats have");
        VitalStats vitalStats = new VitalStats();
        vitalStats.setLength("12 to 16 inches");
        vitalStats.setLifeSpan("9 to 15 years");
        vitalStats.setOrigin("Southeast Asia");
        vitalStats.setWeight("6 to 10 pounds");
        cat.setVitalStats(vitalStats);
        return cat;
    }
}
