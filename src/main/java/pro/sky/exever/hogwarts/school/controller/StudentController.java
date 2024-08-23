package pro.sky.exever.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.exever.hogwarts.school.controller.common.SimpleControllerImpl;
import pro.sky.exever.hogwarts.school.model.Student;
import pro.sky.exever.hogwarts.school.service.StudentService;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController extends SimpleControllerImpl<Student, StudentService> {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        super(studentService);
        this.studentService = studentService;
    }

    @Operation(operationId = "findByAgeBetween", summary = "Search students in age between")
    @GetMapping(value = "/find")
    public Collection<Student> findByAgeBetween(@RequestParam(value = "ageMin", required = false) Integer min, @RequestParam(value = "ageMax", required = false) Integer max) {
        if (min != null && max == null) {
            return studentService.findStudentsByAgeGreaterThanEqual(min);
        } else if (min == null && max != null) {
            return studentService.findStudentsByAgeLessThanEqual(max);
        } else if (min != null) {
            return studentService.findByAgeBetween(min, max);
        }
        throw new IllegalArgumentException("ageMin and/or ageMax are required");
    }

    @Operation(operationId = "findByFacultyId", summary = "Find students by faculty")
    @GetMapping(value = "/by-faculty")
    public Collection<Student> findByFacultyId(@RequestParam Long id) {
        return studentService.findByFacultyId(id);
    }

    @Operation(operationId = "getStudentsCount", summary = "Count students by query")
    @GetMapping(value = "/count")
    public Integer getStudentsCount() {
        return studentService.getStudentsCount();
    }

    @Operation(operationId = "getStudentsAverageAge", summary = "Calculate students average age")
    @GetMapping(value = "/average-age")
    public Integer getStudentsAverageAge() {
        return studentService.getStudentsAverageAge();
    }

    @Operation(operationId = "getStudentsLastFive", summary = "Get last five students")
    @GetMapping(value = "/last")
    public List<Student> getStudentsLastFive() {
        return studentService.getStudentsLastFive();
    }

    @Operation(operationId = "getStudentsNamesWithAWithStream", summary = "Get students names sorted in upper case starting with A using stream")
    @GetMapping(value = "/a-names")
    public List<String> getStudentsNamesWithAWithStream() {
        return studentService.getStudentsNamesWithAWithStream();
    }

    @Operation(operationId = "getStudentsAverageAgeWithStream", summary = "Calculate students average age with stream")
    @GetMapping(value = "/average-age-stream")
    public BigDecimal getStudentsAverageAgeWithStream() {
        return studentService.getStudentsAverageAgeWithStream();
    }
}
