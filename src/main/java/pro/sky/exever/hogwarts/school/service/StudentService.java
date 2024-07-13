package pro.sky.exever.hogwarts.school.service;

import org.springframework.stereotype.Service;
import pro.sky.exever.hogwarts.school.model.Student;
import pro.sky.exever.hogwarts.school.repository.StudentRepository;
import pro.sky.exever.hogwarts.school.service.common.SimpleServiceImpl;

import java.util.Collection;

@Service
public class StudentService extends SimpleServiceImpl<Student, StudentRepository> {
    StudentRepository repo;

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

    public Collection<Student> FindByFacultyId(Long i) {
        return repo.findByFacultyId_Id(i);
    }


}
