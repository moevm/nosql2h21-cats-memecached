package info.moevm.se.nosqlcatsmemecached.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.moevm.se.nosqlcatsmemecached.dao.CatsDao;
import info.moevm.se.nosqlcatsmemecached.models.cat.Cat;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CatsController.class)
public class CatControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CatsDao catsDao;

    private List<Cat> allCatsList;

    @Before
    public void before() {
        allCatsList = Stream.of("Cool cat", "Another cool cat").map(breedName -> {
            var cat = new Cat();
            cat.setBreedName(breedName);
            return cat;
        }).collect(Collectors.toList());
    }

    @SneakyThrows
    @Test
    public void getAllCatsRequest() {
        given(catsDao.getAllCats()).willReturn(allCatsList);

        mvc.perform(get("/cats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].breedName", is(allCatsList.get(0).getBreedName())))
                .andExpect(jsonPath("$[1].breedName", is(allCatsList.get(1).getBreedName())));
    }

    @SneakyThrows
    @Test
    public void getCatByIdRequest() {
        given(catsDao.getCat("cool_cat")).willReturn(allCatsList.get(0));

        mvc.perform(get("/cats/cool_cat")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.breedName", is(allCatsList.get(0).getBreedName())));
    }

    @SneakyThrows
    @SuppressWarnings("all")
    @Test
    public void addCatRequest() {

        // success case
        given(catsDao.addCat(any(Cat.class))).willReturn(true);
        mvc.perform(post("/cats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(allCatsList.get(0))))
                .andExpect(status().is2xxSuccessful());

        // fail case
        given(catsDao.addCat(any(Cat.class))).willReturn(true);
        mvc.perform(post("/cats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(allCatsList.get(0))))
                .andExpect(status().isBadRequest());
    }
}
