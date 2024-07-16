package pro.sky.exever.hogwarts.school.model.common;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@MappedSuperclass
public abstract class EntityWithId implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
}
