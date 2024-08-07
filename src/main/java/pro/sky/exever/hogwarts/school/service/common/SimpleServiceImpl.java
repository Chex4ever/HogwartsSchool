package pro.sky.exever.hogwarts.school.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pro.sky.exever.hogwarts.school.exception.EntityNotFoundException;
import pro.sky.exever.hogwarts.school.model.common.EntityWithId;
import pro.sky.exever.hogwarts.school.repository.common.SimpleRepository;
import pro.sky.exever.hogwarts.school.search.SearchRequest;
import pro.sky.exever.hogwarts.school.search.SearchSpecification;

import java.util.List;
import java.util.Optional;

public abstract class SimpleServiceImpl<E extends EntityWithId, R extends SimpleRepository<E>> implements SimpleService<E> {
    private final R repo;

    @Autowired
    public SimpleServiceImpl(R repo) {
        this.repo = repo;
    }

    @Override
    public Optional<E> create(E entity) {
        return Optional.of(repo.save(entity));
    }

    @Override
    public Optional<E> get(long id) {
        return repo.findById(id);
    }

    @Override
    public List<E> getAll() {
        return repo.findAll().stream().toList();
    }

    @Override
    public List<E> findByPage(Pageable pageable) {
        return repo.findAll(pageable).stream().toList();
    }

    @Override
    public Optional<E> update(E entity) {
        return Optional.of(repo.save(entity));
    }

    @Override
    public E delete(long id) {
        E entity = get(id).orElseThrow(() -> new EntityNotFoundException(id));
        repo.deleteById(id);
        return entity;
    }

    @Override
    public void deleteAll() {
        repo.deleteAll();
    }

    @Override
    public Page<E> advSearch(SearchRequest request) {
        SearchSpecification<E> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        return repo.findAll(specification, pageable);
    }
}
