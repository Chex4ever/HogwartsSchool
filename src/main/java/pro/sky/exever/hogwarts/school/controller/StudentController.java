package pro.sky.exever.hogwarts.school.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.exever.hogwarts.school.controller.common.SimpleControllerImpl;
import pro.sky.exever.hogwarts.school.model.Student;
import pro.sky.exever.hogwarts.school.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController extends SimpleControllerImpl<Student, StudentService> {

    protected StudentController(StudentService service) {
        super(service);
    }
}
