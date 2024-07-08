package pro.sky.exever.hogwarts.school.controller.common;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.exever.hogwarts.school.model.common.EntityWithId;
import pro.sky.exever.hogwarts.school.search.SearchRequest;

import java.util.List;

public interface SimpleController<E extends EntityWithId> {
    @PostMapping
    @Operation(operationId = "Create", summary = "Create")
    ResponseEntity<E> create(@RequestBody E entity);

    @PutMapping
    @Operation(operationId = "Update", summary = "Update")
    ResponseEntity<E> update(@RequestBody E entity);

    @GetMapping(path = "/{id}")
    @Operation(operationId = "GetById", summary = "GetById")
    ResponseEntity<E> get(@PathVariable long id);

    @GetMapping
    @Operation(operationId = "GetAll", summary = "GetAll")
    ResponseEntity<List<E>> getAll();

    @DeleteMapping(path = "/{id}")
    @Operation(operationId = "Delete", summary = "Delete")
    ResponseEntity<E> delete(@PathVariable long id);

    @DeleteMapping
    @Operation(operationId = "DropTable", summary = "DropTable")
    ResponseEntity<E> deleteAll();

    @Operation(operationId = "Search", summary = "Search by specification")
    @PostMapping(value = "/search")
    Page<E> search(@RequestBody SearchRequest request);
}
