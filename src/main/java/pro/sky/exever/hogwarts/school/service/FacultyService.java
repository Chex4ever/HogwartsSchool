package pro.sky.exever.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.exever.hogwarts.school.exception.StudentNotFoundException;
import pro.sky.exever.hogwarts.school.model.Faculty;
import pro.sky.exever.hogwarts.school.repository.FacultyRepository;
import pro.sky.exever.hogwarts.school.service.common.SimpleServiceImpl;

import java.util.Collection;

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
}