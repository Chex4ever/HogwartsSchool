package pro.sky.exever.hogwarts.school.service;

import org.springframework.stereotype.Service;
import pro.sky.exever.hogwarts.school.model.Student;
import pro.sky.exever.hogwarts.school.repository.StudentRepository;
import pro.sky.exever.hogwarts.school.service.common.SimpleServiceImpl;

@Service
public class StudentService extends SimpleServiceImpl<Student, StudentRepository> {

    public StudentService(StudentRepository repo) {
        super(repo);
    }
}
