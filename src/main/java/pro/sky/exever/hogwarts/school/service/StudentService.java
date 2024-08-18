package pro.sky.exever.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.exever.hogwarts.school.exception.StudentNotFoundException;
import pro.sky.exever.hogwarts.school.model.Student;
import pro.sky.exever.hogwarts.school.repository.StudentRepository;
import pro.sky.exever.hogwarts.school.service.common.SimpleServiceImpl;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService extends SimpleServiceImpl<Student, StudentRepository> {
    private final Logger log = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        super(repo);
        this.repo = repo;
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        log.info("Searching student by age between {} and {}", min, max);
        return repo.findByAgeBetween(min, max);
    }

    public Collection<Student> findStudentsByAge(int i) {
        log.info("Searching student by exact age {}", i);
        return repo.findByAge(i);
    }

    public Collection<Student> findStudentsByAgeGreaterThanEqual(Integer min) {
        log.info("Searching student by age >= {}", min);
        return repo.findStudentsByAgeGreaterThanEqual(min);
    }

    public Collection<Student> findStudentsByAgeLessThanEqual(Integer max) {
        log.info("Searching student by age <= {}", max);
        return repo.findStudentsByAgeLessThanEqual(max);
    }

    public Collection<Student> findByFacultyId(Long i) {
        log.info("Searching student by faculty ID {}", i);
        var result = repo.findByFacultyId_Id(i);
        if (result.isEmpty()) {
            log.error("Нет студентов на факультете с ID {}", i);
            throw new StudentNotFoundException("Нет студентов на факультете с id %d".formatted(i));
        }
        return result;
    }


    public Integer getStudentsCount() {
        log.info("Counting students by SQL query");
        return repo.getStudentsCount();
    }

    public Integer getStudentsAverageAge() {
        log.info("Calculating students average age by SQL query");
        return repo.getStudentsAverageAge();
    }

    public List<Student> getStudentsLastFive() {
        log.info("Last 5 students by SQL query");
        return repo.getStudentsLastFive();
    }
}
