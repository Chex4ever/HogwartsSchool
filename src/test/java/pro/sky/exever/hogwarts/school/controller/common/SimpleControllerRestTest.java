package pro.sky.exever.hogwarts.school.controller.common;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import pro.sky.exever.hogwarts.school.model.common.EntityWithId;
import pro.sky.exever.hogwarts.school.search.SearchRequest;
import pro.sky.exever.hogwarts.school.service.common.SimpleService;

import static pro.sky.exever.hogwarts.school.TestUrlGenerator.generateUrl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public interface SimpleControllerRestTest<TestEntity extends EntityWithId, TestController extends SimpleController<TestEntity>> {
    TestController createController();

    SimpleService<TestEntity> createService();

    TestEntity createEntity();

    TestRestTemplate createTestRestTemplate();

    JdbcTemplate createJdbcTemplate();

    @BeforeEach
    default void initDb() {
        var jdbc = createJdbcTemplate();
        var entity = createEntity();
        String sql = "delete from " + entity.getClass().getSimpleName().toLowerCase();
        jdbc.update(sql);
    }

    @AfterEach
    default void clearDb() {
        var jdbc = createJdbcTemplate();
        var entity = createEntity();
        String sql = "delete from " + entity.getClass().getSimpleName().toLowerCase();
        jdbc.update(sql);
    }

    @Test
    default void createTest() {
        var controller = createController();
        var service = createService();
        var entity = createEntity();
        var rest = createTestRestTemplate();
        String url = generateUrl(controller.getClass());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        rest.postForObject(url, new HttpEntity<>(entity.toString(), headers), String.class);
        Mockito.verify(service, Mockito.times(1)).create(entity);
    }

    @Test
    default void updateTest() {
        var controller = createController();
        var service = createService();
        var entity = createEntity();
        var rest = createTestRestTemplate();
        String url = generateUrl(controller.getClass());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        rest.put(url, new HttpEntity<>(entity.toString(), headers));
        Mockito.verify(service, Mockito.times(1)).update(entity);
    }

    @Test
    default void getTest() {
        var controller = createController();
        var service = createService();
        var rest = createTestRestTemplate();
        String url = generateUrl(controller.getClass());
        rest.getForObject(url + "/0", String.class);
        Mockito.verify(service, Mockito.times(1)).get(0L);
    }

    @Test
    default void getAllTest() {
        var controller = createController();
        var service = createService();
        var rest = createTestRestTemplate();
        String url = generateUrl(controller.getClass(), "getAll");
        rest.getForObject(url, String.class);
        Mockito.verify(service, Mockito.times(1)).getAll();
    }

    @Test
    default void searchTest() {
        var controller = createController();
        var service = createService();
        var rest = createTestRestTemplate();
        var searchRequest = new SearchRequest();
        String url = generateUrl(controller.getClass(), "advSearch", SearchRequest.class);
        rest.postForObject(url + "/search", searchRequest, String.class);
        Mockito.verify(service, Mockito.times(1)).advSearch(searchRequest);
    }

    @Test
    default void deleteTest() {
        var controller = createController();
        var service = createService();
        var rest = createTestRestTemplate();
        String url = generateUrl(controller.getClass(), "delete", Long.class);
        rest.delete(url + "/0");
        Mockito.verify(service, Mockito.times(1)).delete(0L);
    }

    @Test
    default void deleteAllTest() {
        var controller = createController();
        var service = createService();
        var rest = createTestRestTemplate();
        String url = generateUrl(controller.getClass(), "deleteAll");
        rest.delete(url);
        Mockito.verify(service, Mockito.times(1)).deleteAll();
    }
}