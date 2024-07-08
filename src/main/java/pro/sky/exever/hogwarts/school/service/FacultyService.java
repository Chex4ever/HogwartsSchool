package pro.sky.exever.hogwarts.school.service;

import org.springframework.stereotype.Service;
import pro.sky.exever.hogwarts.school.model.Faculty;
import pro.sky.exever.hogwarts.school.repository.FacultyRepository;
import pro.sky.exever.hogwarts.school.service.common.SimpleServiceImpl;

@Service
public class FacultyService extends SimpleServiceImpl<Faculty, FacultyRepository> {

    public FacultyService(FacultyRepository repo) {
        super(repo);
    }
}
