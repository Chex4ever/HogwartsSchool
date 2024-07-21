package pro.sky.exever.hogwarts.school.repository;

import org.springframework.stereotype.Repository;
import pro.sky.exever.hogwarts.school.model.Avatar;
import pro.sky.exever.hogwarts.school.repository.common.SimpleRepository;

import java.util.Optional;

@Repository
public interface AvatarRepository extends SimpleRepository<Avatar> {
    Optional<Avatar> findByStudent_Id(Long studentId);
}
