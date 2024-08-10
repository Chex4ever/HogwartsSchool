package pro.sky.exever.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pro.sky.exever.hogwarts.school.model.common.EntityWithId;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "faculty")
public class Faculty extends EntityWithId {
    private String name;
    private String color;
    @JsonIgnore
    @OneToMany(mappedBy = "facultyId", fetch = FetchType.LAZY)
    private Set<Student> students;

    public Faculty() {
    }

    public Faculty(Long id, String name, String color) {
        this.name = name;
        this.color = color;
        this.setId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(name, faculty.name) && Objects.equals(color, faculty.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color);
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + this.getId() + ", " +
                "\"name\":\"" + name + "\", " +
                "\"color\":\"" + color + "\"" +
                '}';
    }
}
