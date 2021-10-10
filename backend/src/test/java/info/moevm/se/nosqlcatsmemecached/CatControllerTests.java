package info.moevm.se.nosqlcatsmemecached;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import info.moevm.se.nosqlcatsmemecached.controllers.CatsController;
import info.moevm.se.nosqlcatsmemecached.dao.CatsDao;
import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(CatsController.class)
public class CatControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CatsDao catsDao;

    @SneakyThrows
    @Test
    public void getAllCatsRequest() {
        Cat cat = new Cat();
        cat.setBreedName("Cool cat");

        List<Cat> allCatsList = List.of(cat);
        given(catsDao.getAllCats()).willReturn(allCatsList);

        mvc.perform(get("/cats")
            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(1)))
           .andExpect(jsonPath("$[0].breedName", is(cat.getBreedName())));
    }
}
