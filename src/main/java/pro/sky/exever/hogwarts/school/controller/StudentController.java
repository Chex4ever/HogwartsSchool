package pro.sky.exever.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import pro.sky.exever.hogwarts.school.controller.common.SimpleControllerImpl;
import pro.sky.exever.hogwarts.school.model.Student;
import pro.sky.exever.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController extends SimpleControllerImpl<Student, StudentService> {
    StudentService studentService;

    protected StudentController(StudentService studentService) {
        super(studentService);
        this.studentService = studentService;
    }

    @Operation(operationId = "findByAgeBetween", summary = "Search students in age between")
    @GetMapping(value = "/find")
    Collection<Student> findByAgeBetween(@RequestParam(value = "ageMin", required = false) Integer min, @RequestParam(value = "ageMax", required = false) Integer max) {
        if (min != null && max == null) {
            return studentService.findStudentsByAgeGreaterThanEqual(min);
        } else if (min == null && max != null) {
            return studentService.findStudentsByAgeLessThanEqual(max);
        } else if (min != null && max != null) {
            return studentService.findByAgeBetween(min, max);
        }
        return null;
    }

    @Operation(operationId = "findStudentsByFaculty", summary = "Find students by faculty")
    @GetMapping(value = "/faculty/{id}")
    Collection<Student> findStudentsByFaculty(@PathVariable long id) {
        return studentService.FindByFacultyId(id);
    }
}
