package pro.sky.exever.hogwarts.school.controller.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.exever.hogwarts.school.model.common.EntityWithId;
import pro.sky.exever.hogwarts.school.repository.common.SimpleRepository;
import pro.sky.exever.hogwarts.school.search.SearchRequest;
import pro.sky.exever.hogwarts.school.service.common.SimpleService;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pro.sky.exever.hogwarts.school.TestUrlGenerator.generateUrl;

@WebMvcTest
public interface SimpleControllerMvcTest<TestEntity extends EntityWithId, TestRepository extends SimpleRepository<TestEntity>, TestController extends SimpleController<TestEntity>> {
    static void checkResultFields(HashMap<String, String> fields, ResultActions resultActions) throws Exception {
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            resultActions.andExpect(jsonPath("$." + entry.getKey()).value(entry.getValue()));
        }
    }

    @SuppressWarnings("unchecked")
    static <TestEntity extends EntityWithId> HashMap<String, String> getFields(TestEntity entity) throws JsonProcessingException {
        return new ObjectMapper().readValue(entity.toString(), HashMap.class);
    }

    @SuppressWarnings("unchecked")
    private static <TestEntity extends EntityWithId> TestEntity updateEntity(TestEntity oldEntity) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IntrospectionException {
        var newEntity = oldEntity.getClass().getDeclaredConstructor().newInstance();
        newEntity.setId(oldEntity.getId() + 1);
        Field[] fields = oldEntity.getClass().getDeclaredFields();
        for (Field field : fields) {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), newEntity.getClass());
            Object oldFieldValue = pd.getReadMethod().invoke(oldEntity);
            Object newFieldValue;
            Class<?> type = field.getType();
            if (type.equals(String.class)) {
                newFieldValue = oldFieldValue + "updated";
            } else {
                newFieldValue = oldFieldValue;
            }
            if (type.equals(int.class)) {
                newFieldValue = (Integer) oldFieldValue + 1;
            }
            pd.getWriteMethod().invoke(newEntity, newFieldValue);
        }
        return (TestEntity) newEntity;
    }

    TestController createController();

    SimpleService<TestEntity> createService();

    TestRepository createRepository();

    TestEntity createEntity();

    SearchRequest createSearchRequest();

    MockMvc createMockMvc();

    @BeforeEach
    default void initDb() {
    }

    @AfterEach
    default void clearDb() {
        TestRepository TestRepository = createRepository();
        TestRepository.deleteAll();
    }

    @Test
    default void createTest() throws Exception {
        TestController controller = createController();
        TestRepository repo = createRepository();
        TestEntity entity = createEntity();
        MockMvc mvc = createMockMvc();
        when(repo.save(entity)).thenReturn(entity);
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post(generateUrl(controller.getClass())).content(entity.toString()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        checkResultFields(getFields(entity), resultActions);
    }

    @Test
    default void updateTest() throws Exception {
        var mvc = createMockMvc();
        var controller = createController();
        var repo = createRepository();
        var oldEntity = createEntity();
        var newEntity = updateEntity(oldEntity);
        var service = createService();
        when(repo.save(oldEntity)).thenReturn(oldEntity);
        when(repo.save(newEntity)).thenReturn(newEntity);
        mvc.perform(MockMvcRequestBuilders.post(generateUrl(controller.getClass())).content(oldEntity.toString()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.put(generateUrl(controller.getClass())).content(newEntity.toString()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        checkResultFields(getFields(newEntity), resultActions);
        Mockito.verify(service, Mockito.times(1)).create(oldEntity);
        Mockito.verify(service, Mockito.times(1)).update(newEntity);

    }

    @Test
    default void getTest() throws Exception {
        var controller = createController();
        var service = createService();
        var repo = createRepository();
        var entity = createEntity();
        var mvc = createMockMvc();
        long testId = 1L;
        String url = generateUrl(controller.getClass()) + "/" + testId;
        when(repo.findById(any())).thenReturn(Optional.ofNullable(entity));
        assert entity != null;
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isOk());
        checkResultFields(getFields(entity), resultActions);
        Mockito.verify(service, Mockito.times(1)).get(testId);

    }

    @Test
    default void getTestNegative() throws Exception {
        var mvc = createMockMvc();
        var controller = createController();
        var service = createService();
        var repo = createRepository();
        long testId = 2L;
        String url = generateUrl(controller.getClass()) + "/" + testId;
        when(repo.findById(any())).thenReturn(Optional.empty());
        mvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isBadRequest());
        Mockito.verify(service, Mockito.times(1)).get(testId);

    }

    @Test
    default void getAllTest() throws Exception {
        var mvc = createMockMvc();
        var controller = createController();
        var service = createService();
        var repo = createRepository();
        var entity = createEntity();
        var allEntities = Collections.singletonList(entity);
        String url = generateUrl(controller.getClass(), "getAll");
        when(repo.findAll()).thenReturn(allEntities);
        mvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).getAll();

    }

    @Test
    default void searchTest() throws Exception {
        var controller = createController();
        var service = createService();
        var mvc = createMockMvc();
        var searchRequest = createSearchRequest();
        String url = generateUrl(controller.getClass(), "advSearch", SearchRequest.class) + "/search";
        mvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(searchRequest))).andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).advSearch(searchRequest);
    }

    @Test
    default void deleteTest() throws Exception {
        var mvc = createMockMvc();
        var controller = createController();
        var service = createService();
        var repo = createRepository();
        var entity = createEntity();
        long testId = 1L;
        assert entity != null;
        String url = generateUrl(controller.getClass()) + "/" + testId;
        when(repo.findById(testId)).thenReturn(Optional.of(entity));
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.delete(url)).andExpect(status().isOk());
        checkResultFields(getFields(entity), resultActions);
        Mockito.verify(service, Mockito.times(1)).get(testId);
        Mockito.verify(service, Mockito.times(1)).delete(testId);
    }

    @Test
    default void deleteAllTest() throws Exception {
        var mvc = createMockMvc();
        var controller = createController();
        var service = createService();
        String url = generateUrl(controller.getClass(), "deleteAll");
        mvc.perform(MockMvcRequestBuilders.delete(url)).andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).deleteAll();
    }
}