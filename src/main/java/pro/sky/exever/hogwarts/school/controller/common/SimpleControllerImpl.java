package pro.sky.exever.hogwarts.school.controller.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import pro.sky.exever.hogwarts.school.exception.DatabaseTransactionException;
import pro.sky.exever.hogwarts.school.exception.EntityNotFoundException;
import pro.sky.exever.hogwarts.school.model.common.EntityWithId;
import pro.sky.exever.hogwarts.school.search.SearchRequest;
import pro.sky.exever.hogwarts.school.service.common.SimpleService;

import java.util.List;

public abstract class SimpleControllerImpl<E extends EntityWithId, S extends SimpleService<E>> implements SimpleController<E> {
    private final S service;

    protected SimpleControllerImpl(S service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<E> create(E entity) {
        return service.create(entity).map(ResponseEntity::ok).orElseThrow(() -> new DatabaseTransactionException(entity.toString()));
    }

    @Override
    public ResponseEntity<E> update(E entity) {
        return service.update(entity).map(ResponseEntity::ok).orElseThrow(() -> new EntityNotFoundException(entity.getId()));
    }

    @Override
    public ResponseEntity<E> get(long id) {
        return service.get(id).map(ResponseEntity::ok).orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public List<E> findByPage(Integer page, Integer size) {
        if (size == null) {
            size = 5;
        }

        return service.findByPage(PageRequest.of(page, size));
    }

    @Override
    public ResponseEntity<List<E>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    public Page<E> advSearch(SearchRequest request) {
        return service.advSearch(request);
    }

    @Override
    public ResponseEntity<E> delete(long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @Override
    public ResponseEntity<E> deleteAll() {
        service.deleteAll();
        return ResponseEntity.ok().build();
    }
}
