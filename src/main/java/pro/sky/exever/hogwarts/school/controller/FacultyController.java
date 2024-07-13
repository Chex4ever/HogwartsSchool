package pro.sky.exever.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import pro.sky.exever.hogwarts.school.controller.common.SimpleControllerImpl;
import pro.sky.exever.hogwarts.school.model.Faculty;
import pro.sky.exever.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController extends SimpleControllerImpl<Faculty, FacultyService> {
    FacultyService service;

    protected FacultyController(FacultyService service) {
        super(service);
        this.service = service;
    }

    @Operation(operationId = "findFacultyByNameAndColor", summary = "Find faculty by name and color")
    @GetMapping(value = "/find")
    Collection<Faculty> findByNameAndColor(@RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "color", required = false) String color) {
        if (name == null && color != null) {
            return service.findByColorIgnoreCase(color);
        } else if (name != null && color == null) {
            return service.findByNameIgnoreCase(name);
        } else if (name != null && color != null) {
            return service.findByNameLikeIgnoreCaseAndColorLikeIgnoreCase(name, color);
        }
        return null;
    }
    @Operation(operationId = "findFacultyByStudentId", summary = "Find faculty by student ID")
    @GetMapping(value = "/student/{id}")
    Faculty findByNameAndColor(@PathVariable long id) {
        return service.findByStudentId(id);
    }

}