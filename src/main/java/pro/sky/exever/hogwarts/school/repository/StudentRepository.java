package pro.sky.exever.hogwarts.school.repository;

import org.springframework.stereotype.Repository;
import pro.sky.exever.hogwarts.school.model.Student;
import pro.sky.exever.hogwarts.school.repository.common.SimpleRepository;

import java.util.Collection;

@Repository
public interface StudentRepository extends SimpleRepository<Student> {
    Collection<Student> findByAgeBetween(int min, int max);

    Collection<Student> findStudentsByAgeGreaterThanEqual(int min);

    Collection<Student> findStudentsByAgeLessThanEqual(int max);

    Collection<Student> findByAge(int i);

    Collection<Student> findByFacultyId_Id(long i);
}
