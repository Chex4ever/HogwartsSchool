package pro.sky.exever.hogwarts.school.repository;

import org.springframework.stereotype.Repository;
import pro.sky.exever.hogwarts.school.model.Faculty;
import pro.sky.exever.hogwarts.school.repository.common.SimpleRepository;

import java.util.Collection;

@Repository
public interface FacultyRepository extends SimpleRepository<Faculty> {
    Collection<Faculty> findByColorIgnoreCase(String color);

    Collection<Faculty> findByNameIgnoreCase(String name);

    Collection<Faculty> findByNameLikeIgnoreCaseAndColorLikeIgnoreCase(String name, String color);

    Faculty findByStudents_Id(long id);
}
