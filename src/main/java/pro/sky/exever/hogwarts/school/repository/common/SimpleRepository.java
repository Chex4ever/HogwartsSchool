package pro.sky.exever.hogwarts.school.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import pro.sky.exever.hogwarts.school.model.common.EntityWithId;

@NoRepositoryBean
public interface SimpleRepository<E extends EntityWithId> extends JpaRepository<E, Long>, JpaSpecificationExecutor<E> {
}
