package pro.sky.exever.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.exever.hogwarts.school.Timer;
import pro.sky.exever.hogwarts.school.controller.common.SimpleControllerMvcTest;
import pro.sky.exever.hogwarts.school.model.Faculty;
import pro.sky.exever.hogwarts.school.repository.FacultyRepository;
import pro.sky.exever.hogwarts.school.search.*;
import pro.sky.exever.hogwarts.school.service.FacultyService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pro.sky.exever.hogwarts.school.Constants.*;
import static pro.sky.exever.hogwarts.school.TestUrlGenerator.generateUrl;

@WebMvcTest(FacultyController.class)
class FacultyControllerMvcTest implements SimpleControllerMvcTest<Faculty, FacultyRepository, FacultyController>, Timer {

    @SpyBean
    private FacultyService facultyService;

    @MockBean
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyController facultyController;

    @Autowired
    private MockMvc mockMvc;

    @Override
    public FacultyController createController() {
        return facultyController;
    }

    @Override
    public FacultyService createService() {
        return facultyService;
    }

    @Override
    public FacultyRepository createRepository() {
        return facultyRepository;
    }

    @Override
    public Faculty createEntity() {
        return FACULTY1;
    }

    @Override
    public SearchRequest createSearchRequest() {
        FilterRequest filterRequest = new FilterRequest("name", Operator.LIKE, FieldType.STRING, "f", "", Collections.singletonList(""));
        SortRequest sortRequest = new SortRequest("name", SortDirection.ASC);
        return new SearchRequest(List.of(filterRequest), List.of(sortRequest), 0, 10);
    }

    @Override
    public MockMvc createMockMvc() {
        return mockMvc;
    }

    @Test
    public void contextLoadsTest() {
        assertNotNull(facultyController);
        assertNotNull(facultyService);
        assertNotNull(mockMvc);
        assertNotNull(facultyRepository);
    }

    @Test
    void findByNameAndColorTest() throws Exception {
        String methodName = "findByNameAndColor";
        String url = generateUrl(FacultyController.class, methodName, String.class, String.class);
        when(facultyRepository.findByColorIgnoreCase(FACULTY1_COLOR)).thenReturn(Collections.singleton(FACULTY1));
        when(facultyRepository.findByNameIgnoreCase(FACULTY2_NAME)).thenReturn(Collections.singleton(FACULTY2));
        when(facultyRepository.findByNameLikeIgnoreCaseAndColorLikeIgnoreCase(FACULTY3_NAME, FACULTY3_COLOR)).thenReturn(Collections.singleton(FACULTY3));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(url + "?color=" + FACULTY1_COLOR))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].color").value(FACULTY1.getColor()));
        Mockito.verify(facultyService, Mockito.times(1)).findByColorIgnoreCase(FACULTY1_COLOR);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(url + "?name=" + FACULTY2_NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].name").value(FACULTY2.getName()));
        Mockito.verify(facultyService, Mockito.times(1)).findByNameIgnoreCase(FACULTY2_NAME);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(url + "?name=" + FACULTY3_NAME + "&color=" + FACULTY3_COLOR))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].name").value(FACULTY3.getName()));
        Mockito.verify(facultyService, Mockito.times(1)).findByNameLikeIgnoreCaseAndColorLikeIgnoreCase(FACULTY3_NAME, FACULTY3_COLOR);
    }

    @Test
    void findByNameAndColornTestNegative() throws Exception {
        String methodName = "findByNameAndColor";
        String url = generateUrl(FacultyController.class, methodName, String.class, String.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(url + "?"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findByStudentIdTest() throws Exception {
        String methodName = "findByStudentId";
        String param = "id";
        long testId = 1L;
        String url = generateUrl(FacultyController.class, methodName, long.class) + "?" + param + "=" + testId;
        when(facultyRepository.findByStudents_Id(testId)).thenReturn(FACULTY1);
        mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isOk());
        Mockito.verify(facultyService, Mockito.times(1)).findByStudentId(testId);
    }

    @Test
    void findByStudentIdTestNegative() throws Exception {
        String methodName = "findByStudentId";
        String param = "id";
        long testId = 666L;
        String url = generateUrl(FacultyController.class, methodName, long.class) + "?" + param + "=" + testId;
        mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isBadRequest());
        Mockito.verify(facultyService, Mockito.times(1)).findByStudentId(testId);
    }

}