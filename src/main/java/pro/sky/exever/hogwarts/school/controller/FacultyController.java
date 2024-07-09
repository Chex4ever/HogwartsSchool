package pro.sky.exever.hogwarts.school.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.exever.hogwarts.school.controller.common.SimpleControllerImpl;
import pro.sky.exever.hogwarts.school.model.Faculty;
import pro.sky.exever.hogwarts.school.service.FacultyService;

@RestController
@RequestMapping("/faculty")
public class FacultyController extends SimpleControllerImpl<Faculty, FacultyService> {

    protected FacultyController(FacultyService service) {
        super(service);
    }
}
