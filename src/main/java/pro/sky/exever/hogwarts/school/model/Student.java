package pro.sky.exever.hogwarts.school.model;

import jakarta.persistence.*;
import lombok.*;
import pro.sky.exever.hogwarts.school.model.common.EntityWithId;

import java.util.Objects;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student extends EntityWithId {
    private String name;
    private int age;
    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty facultyId;

    @OneToOne(mappedBy = "student", fetch = FetchType.LAZY)
    private Avatar avatar;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
