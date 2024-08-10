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
import pro.sky.exever.hogwarts.school.model.Student;
import pro.sky.exever.hogwarts.school.repository.StudentRepository;
import pro.sky.exever.hogwarts.school.search.*;
import pro.sky.exever.hogwarts.school.service.StudentService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pro.sky.exever.hogwarts.school.Constants.*;
import static pro.sky.exever.hogwarts.school.TestUrlGenerator.generateUrl;

@WebMvcTest(StudentController.class)
class StudentControllerMvcTest implements SimpleControllerMvcTest<Student, StudentRepository, StudentController>, Timer {

    @SpyBean
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentController studentController;

    @Autowired
    private MockMvc mockMvc;

    @Override
    public StudentController createController() {
        return studentController;
    }

    @Override
    public StudentService createService() {
        return studentService;
    }

    @Override
    public StudentRepository createRepository() {
        return studentRepository;
    }

    @Override
    public Student createEntity() {
        return STUDENT1;
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
        assertNotNull(studentController);
        assertNotNull(studentService);
        assertNotNull(mockMvc);
        assertNotNull(studentRepository);
    }

    @Test
    void findByAgeBetweenTest() throws Exception {
        String methodName = "findByAgeBetween";
        String url = generateUrl(StudentController.class, methodName, Integer.class, Integer.class);
        when(studentRepository.findStudentsByAgeGreaterThanEqual(AGE_MIN_VALUE)).thenReturn(Collections.singleton(STUDENT1));
        when(studentRepository.findStudentsByAgeLessThanEqual(AGE_MAX_VALUE)).thenReturn(Collections.singleton(STUDENT2));
        when(studentRepository.findByAgeBetween(AGE_MIN_VALUE, AGE_MAX_VALUE)).thenReturn(Collections.singleton(STUDENT3));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(url + "?" + AGE_MIN_PARAM + "=" + AGE_MIN_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].name").value(STUDENT1.getName()));
        Mockito.verify(studentService, Mockito.times(1)).findStudentsByAgeGreaterThanEqual(AGE_MIN_VALUE);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(url + "?" + AGE_MAX_PARAM + "=" + AGE_MAX_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].name").value(STUDENT2.getName()));
        Mockito.verify(studentService, Mockito.times(1)).findStudentsByAgeLessThanEqual(AGE_MAX_VALUE);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(url + "?" + AGE_MIN_PARAM + "=" + AGE_MIN_VALUE + "&" + AGE_MAX_PARAM + "=" + AGE_MAX_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].name").value(STUDENT3.getName()));
        Mockito.verify(studentService, Mockito.times(1)).findByAgeBetween(AGE_MIN_VALUE, AGE_MAX_VALUE);
    }

    @Test
    void findByAgeBetweenTestNegative() throws Exception {
        String methodName = "findByAgeBetween";
        String url = generateUrl(StudentController.class, methodName, Integer.class, Integer.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(url + "?"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findStudentsByFacultyTest() throws Exception {
        String methodName = "findByFacultyId";
        String param = "id";
        long facultyId = 1L;
        String url = generateUrl(StudentController.class, methodName, Long.class) + "?" + param + "=" + facultyId;
        when(studentRepository.findByFacultyId_Id(facultyId)).thenReturn(Collections.singletonList(STUDENT1));
        mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isOk());
        Mockito.verify(studentService, Mockito.times(1)).findByFacultyId(facultyId);
    }

    @Test
    void findStudentsByFacultyTestNegative() throws Exception {
        String methodName = "findByFacultyId";
        String param = "id";
        long facultyId = 666L;
        String url = generateUrl(StudentController.class, methodName, Long.class) + "?" + param + "=" + facultyId;
        mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isBadRequest());
        Mockito.verify(studentService, Mockito.times(1)).findByFacultyId(facultyId);
    }

}