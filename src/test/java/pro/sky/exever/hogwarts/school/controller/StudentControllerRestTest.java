package pro.sky.exever.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import pro.sky.exever.hogwarts.school.Timer;
import pro.sky.exever.hogwarts.school.controller.common.SimpleControllerRestTest;
import pro.sky.exever.hogwarts.school.model.Student;
import pro.sky.exever.hogwarts.school.repository.StudentRepository;
import pro.sky.exever.hogwarts.school.service.StudentService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;
import static pro.sky.exever.hogwarts.school.Constants.*;
import static pro.sky.exever.hogwarts.school.TestUrlGenerator.generateUrl;

class StudentControllerRestTest implements SimpleControllerRestTest<Student, StudentController>, Timer {

    @Autowired
    private StudentController controller;

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private JdbcTemplate jdbc;

    @SpyBean
    private StudentService service;

    @Mock
    private StudentRepository repo;

    @Override
    public StudentController createController() {
        return controller;
    }

    @Override
    public StudentService createService() {
        return service;
    }

    @Override
    public Student createEntity() {
        return STUDENT1;
    }

    @Override
    public TestRestTemplate createTestRestTemplate() {
        return rest;
    }

    @Override
    public JdbcTemplate createJdbcTemplate() {
        return jdbc;
    }


    @Test
    public void contextLoadsTest() {
        assertNotNull(controller);
        assertNotNull(service);
        assertNotNull(rest);
        assertNotNull(jdbc);
        assertNotNull(repo);
    }

    @Test
    void findByAgeBetweenTest() {
//        StudentControllerRestTest studentControllerRestTest = new StudentControllerRestTest();
//        studentControllerRestTest.
        String methodName = "findByAgeBetween";
        String url = generateUrl(StudentController.class, methodName, Integer.class, Integer.class);
        when(service.findStudentsByAgeGreaterThanEqual(AGE_MIN_VALUE)).thenReturn(Collections.singletonList(STUDENT1));

        rest.getForObject(url + "?" + AGE_MIN_PARAM + "=" + AGE_MIN_VALUE, Student[].class);
        Mockito.verify(service, Mockito.times(1)).findStudentsByAgeGreaterThanEqual(AGE_MIN_VALUE);
        rest.getForObject(url + "?" + AGE_MAX_PARAM + "=" + AGE_MAX_VALUE, Student[].class);
        Mockito.verify(service, Mockito.times(1)).findStudentsByAgeLessThanEqual(AGE_MAX_VALUE);
        rest.getForObject(url + "?" + AGE_MIN_PARAM + "=" + AGE_MIN_VALUE + "&" + AGE_MAX_PARAM + "=" + AGE_MAX_VALUE, Student[].class);
        Mockito.verify(service, Mockito.times(1)).findByAgeBetween(AGE_MIN_VALUE, AGE_MAX_VALUE);
        assertSame(HttpStatus.BAD_REQUEST, rest.exchange(url + "?", HttpMethod.GET, null, String.class).getStatusCode());
    }

    @Test
    void findStudentsByFacultyTest() {
        String methodName = "findByFacultyId";
        String param = "id";
        Long facultyId = 3L;
        String url = generateUrl(StudentController.class, methodName, Long.class) + "?" + param + "=" + facultyId;
        rest.getForObject(url, String.class);
        Mockito.verify(service, Mockito.times(1)).findByFacultyId(facultyId);
    }

}