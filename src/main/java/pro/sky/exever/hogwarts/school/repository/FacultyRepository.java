package pro.sky.exever.hogwarts.school.repository;

import org.springframework.stereotype.Repository;
import pro.sky.exever.hogwarts.school.model.Faculty;
import pro.sky.exever.hogwarts.school.repository.common.SimpleRepository;

@Repository
public interface FacultyRepository extends SimpleRepository<Faculty> {
}
