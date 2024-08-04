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
import pro.sky.exever.hogwarts.school.model.Faculty;
import pro.sky.exever.hogwarts.school.repository.FacultyRepository;
import pro.sky.exever.hogwarts.school.service.FacultyService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static pro.sky.exever.hogwarts.school.Constants.FACULTY1;
import static pro.sky.exever.hogwarts.school.TestUrlGenerator.generateUrl;

class FacultyControllerRestTest implements SimpleControllerRestTest<Faculty, FacultyController>, Timer {

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private JdbcTemplate jdbc;

    @SpyBean
    private FacultyService facultyService;

    @Mock
    private FacultyRepository facultyRepository;

    @Override
    public FacultyController createController() {
        return facultyController;
    }

    @Override
    public FacultyService createService() {
        return facultyService;
    }

    @Override
    public Faculty createEntity() {
        return FACULTY1;
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
        assertNotNull(facultyController);
        assertNotNull(facultyService);
        assertNotNull(rest);
        assertNotNull(jdbc);
        assertNotNull(facultyRepository);
    }

    @Test
    void findByNameAndColor() {
        String methodName = "findByNameAndColor";
        String nameParam = "name";
        String nameValue = "Griffindor";
        String colorParam = "color";
        String colorValue = "rainbow";
        String url = generateUrl(FacultyController.class, methodName, String.class, String.class);
        rest.getForObject(url + "?" + nameParam + "=" + nameValue, Faculty[].class);
        Mockito.verify(facultyService, Mockito.times(1)).findByNameIgnoreCase(nameValue);
        rest.getForObject(url + "?" + colorParam + "=" + colorValue, Faculty[].class);
        Mockito.verify(facultyService, Mockito.times(1)).findByColorIgnoreCase(colorValue);
        rest.getForObject(url + "?" + nameParam + "=" + nameValue + "&" + colorParam + "=" + colorValue, Faculty[].class);
        Mockito.verify(facultyService, Mockito.times(1)).findByNameLikeIgnoreCaseAndColorLikeIgnoreCase(nameValue, colorValue);
        assertSame(HttpStatus.BAD_REQUEST, rest.exchange(url + "?", HttpMethod.GET, null, String.class).getStatusCode());
    }

    @Test
    void findByStudentIdTest() {
        String methodName = "findByStudentId";
        String param = "id";
        long studentId = 3L;
        String url = generateUrl(FacultyController.class, methodName, long.class) + "?" + param + "=" + studentId;
        rest.getForObject(url, String.class);
        Mockito.verify(facultyService, Mockito.times(1)).findByStudentId(studentId);
    }
}