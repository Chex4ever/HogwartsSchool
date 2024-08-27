package pro.sky.exever.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.exever.hogwarts.school.exception.StudentNotFoundException;
import pro.sky.exever.hogwarts.school.model.Student;
import pro.sky.exever.hogwarts.school.repository.StudentRepository;
import pro.sky.exever.hogwarts.school.service.common.SimpleServiceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;

@Service
public class StudentService extends SimpleServiceImpl<Student, StudentRepository> {
    private final Logger log = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository repo;
    private final Object flag = new Object();

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

    public List<String> getStudentsNamesWithAWithStream() {
        return repo.findAll().stream()
                .map(s -> s.getName().split(" ")[0].toUpperCase())
                .filter(f -> f.startsWith("A"))
                .sorted()
                .toList();
    }

    public BigDecimal getStudentsAverageAgeWithStream() {
        double average = repo.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElseThrow();
        return BigDecimal.valueOf(average).setScale(2, RoundingMode.HALF_UP);
    }

    public void printParallel() {
        List<Student> students = repo.findAll();
        if (students.size() < 6) {
            throw new StudentNotFoundException("Нужно 6 студентов, найдено " + students.size());
        }
        printStudents(students, 0, 1);
        new Thread(() -> printStudents(students, 2, 3)).start();
        new Thread(() -> printStudents(students, 4, 5)).start();
    }

    private void printStudents(List<Student> students, int... nums) {
        for (int num : nums) {
            System.out.println(students.get(num));
        }
    }

    public void printSynchronized() {
        List<Student> students = repo.findAll();
        if (students.size() < 6) {
            throw new StudentNotFoundException("Нужно 6 студентов, найдено " + students.size());
        }
        printStudentsSync(students, 0, 1);
        new Thread(() -> printStudentsSync(students, 2, 3)).start();
        new Thread(() -> printStudentsSync(students, 4, 5)).start();
    }

    private void printStudentsSync(List<Student> students, int... nums) {
        synchronized (flag) {
            for (int num : nums) {
                System.out.println(students.get(num) + ", thread " + Thread.currentThread().getId());
            }
        }
    }
}
