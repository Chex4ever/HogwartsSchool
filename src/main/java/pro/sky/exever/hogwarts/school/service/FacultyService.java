package pro.sky.exever.hogwarts.school.service;

import org.springframework.stereotype.Service;
import pro.sky.exever.hogwarts.school.model.Faculty;
import pro.sky.exever.hogwarts.school.repository.FacultyRepository;
import pro.sky.exever.hogwarts.school.service.common.SimpleServiceImpl;

import java.util.Collection;

@Service
public class FacultyService extends SimpleServiceImpl<Faculty, FacultyRepository> {
    FacultyRepository repo;

    public FacultyService(FacultyRepository repo) {
        super(repo);
        this.repo = repo;
    }

    public Collection<Faculty> findByColorIgnoreCase(String color) {
        return repo.findByColorIgnoreCase(color);
    }

    public Collection<Faculty> findByNameIgnoreCase(String name) {
        return repo.findByNameIgnoreCase(name);
    }

    public Collection<Faculty> findByNameLikeIgnoreCaseAndColorLikeIgnoreCase(String name, String color) {
        return repo.findByNameLikeIgnoreCaseAndColorLikeIgnoreCase(name, color);
    }

    public Faculty findByStudentId(long id) {
        return repo.findByStudents_Id(id);
    }
}