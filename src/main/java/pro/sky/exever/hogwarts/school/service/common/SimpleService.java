package pro.sky.exever.hogwarts.school.service.common;

import org.springframework.data.domain.Page;
import pro.sky.exever.hogwarts.school.model.common.EntityWithId;
import pro.sky.exever.hogwarts.school.search.SearchRequest;

import java.util.List;
import java.util.Optional;

public interface SimpleService<E extends EntityWithId> {

    Optional<E> create(E e);

    Optional<E> get(long id);

    List<E> getAll();

    Optional<E> update(E record);

    E delete(long id);

    void deleteAll();

    Page<E> search(SearchRequest request);
}
