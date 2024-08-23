package pro.sky.exever.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.exever.hogwarts.school.exception.StudentNotFoundException;
import pro.sky.exever.hogwarts.school.model.Faculty;
import pro.sky.exever.hogwarts.school.repository.FacultyRepository;
import pro.sky.exever.hogwarts.school.service.common.SimpleServiceImpl;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Stream;

@Service
public class FacultyService extends SimpleServiceImpl<Faculty, FacultyRepository> {
    private final Logger log = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository repo;

    public FacultyService(FacultyRepository repo) {
        super(repo);
        this.repo = repo;
    }

    public Collection<Faculty> findByColorIgnoreCase(String color) {
        log.info("Searching faculties by color {}", color);
        return repo.findByColorIgnoreCase(color);
    }

    public Collection<Faculty> findByNameIgnoreCase(String name) {
        log.info("Searching faculties by name {}", name);
        return repo.findByNameIgnoreCase(name);
    }

    public Collection<Faculty> findByNameLikeIgnoreCaseAndColorLikeIgnoreCase(String name, String color) {
        log.info("Searching faculties by name {} and color {}", name, color);
        return repo.findByNameLikeIgnoreCaseAndColorLikeIgnoreCase(name, color);
    }

    public Faculty findByStudentId(long id) {
        log.info("Searching faculties by student ID {}", id);
        var result = repo.findByStudents_Id(id);
        if (result == null) {
            log.warn("Студент с ID {} не найден", id);
            throw new StudentNotFoundException(id);
        }
        return result;
    }

    public String longestFacultyName() {
        return repo.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length)).orElseThrow();
    }

    public Long streamOptimisation() {
        long startTime=System.currentTimeMillis();

        long numbersNumber=1_000_000;
        Long sum=Stream.iterate(1L, a -> a +1).limit(numbersNumber).reduce(0L, Long::sum);
        log.debug("Stream {}ms {}", System.currentTimeMillis() - startTime, sum);

        startTime=System.currentTimeMillis();
        sum=(numbersNumber*(numbersNumber+1))/2L;
        log.debug("SumOfN {}ms {}", System.currentTimeMillis() - startTime, sum);

        return sum;
    }
}