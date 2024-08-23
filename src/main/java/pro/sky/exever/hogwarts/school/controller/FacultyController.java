package pro.sky.exever.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.exever.hogwarts.school.controller.common.SimpleControllerImpl;
import pro.sky.exever.hogwarts.school.model.Faculty;
import pro.sky.exever.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController extends SimpleControllerImpl<Faculty, FacultyService> {
    private final FacultyService service;

    protected FacultyController(FacultyService service) {
        super(service);
        this.service = service;
    }

    @Operation(operationId = "findFacultyByNameAndColor", summary = "Find faculty by name and color")
    @GetMapping(value = "/find")
    public Collection<Faculty> findByNameAndColor(@RequestParam(value = "name", required = false) String name,
                                                  @RequestParam(value = "color", required = false) String color) {
        if (name == null && color != null) {
            return service.findByColorIgnoreCase(color);
        } else if (name != null && color == null) {
            return service.findByNameIgnoreCase(name);
        } else if (name != null) {
            return service.findByNameLikeIgnoreCaseAndColorLikeIgnoreCase(name, color);
        }
        throw new IllegalArgumentException("name and/or color are required");
    }

    @Operation(operationId = "findFacultyByStudentId", summary = "Find faculty by student ID")
    @GetMapping(value = "/by-student")
    public Faculty findByStudentId(@RequestParam long id) {
        return service.findByStudentId(id);
    }

    @Operation(operationId = "longestFacultyName", summary = "Find longest faculty name with stream")
    @GetMapping(value = "/longest-name")
    public String longestFacultyName() {
        return service.longestFacultyName();
    }

    @Operation(operationId = "streamOptimisation", summary = "Stream optimisation")
    @GetMapping(value = "/formula")
    public Long streamOptimisation() {
        return service.streamOptimisation();
    }
}