package pro.sky.exever.hogwarts.school.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.exever.hogwarts.school.model.Student;
import pro.sky.exever.hogwarts.school.repository.common.SimpleRepository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends SimpleRepository<Student> {
    Collection<Student> findByAgeBetween(int min, int max);

    Collection<Student> findStudentsByAgeGreaterThanEqual(int min);

    Collection<Student> findStudentsByAgeLessThanEqual(int max);

    Collection<Student> findByAge(int i);

    Collection<Student> findByFacultyId_Id(long i);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer getStudentsCount();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Integer getStudentsAverageAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getStudentsLastFive();
}
