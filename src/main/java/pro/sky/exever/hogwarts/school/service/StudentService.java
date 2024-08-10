package pro.sky.exever.hogwarts.school.service;

import org.springframework.stereotype.Service;
import pro.sky.exever.hogwarts.school.exception.StudentNotFoundException;
import pro.sky.exever.hogwarts.school.model.Student;
import pro.sky.exever.hogwarts.school.repository.StudentRepository;
import pro.sky.exever.hogwarts.school.service.common.SimpleServiceImpl;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService extends SimpleServiceImpl<Student, StudentRepository> {
    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        super(repo);
        this.repo = repo;
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return repo.findByAgeBetween(min, max);
    }

    public Collection<Student> findStudentsByAge(int i) {
        return repo.findByAge(i);
    }

    public Collection<Student> findStudentsByAgeGreaterThanEqual(Integer min) {
        return repo.findStudentsByAgeGreaterThanEqual(min);
    }

    public Collection<Student> findStudentsByAgeLessThanEqual(Integer max) {
        return repo.findStudentsByAgeLessThanEqual(max);
    }

    public Collection<Student> findByFacultyId(Long i) {
        var result = repo.findByFacultyId_Id(i);
        if (result.isEmpty()) throw new StudentNotFoundException("Нет студентов на факультете с id %d".formatted(i));
        return result;
    }


    public Integer getStudentsCount() {
        return repo.getStudentsCount();
    }

    public Integer getStudentsAverageAge() {
        return repo.getStudentsAverageAge();
    }

    public List<Student> getStudentsLastFive() {
        return repo.getStudentsLastFive();
    }
}
