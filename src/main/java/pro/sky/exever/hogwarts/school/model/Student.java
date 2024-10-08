package pro.sky.exever.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pro.sky.exever.hogwarts.school.model.common.EntityWithId;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "student")
public class Student extends EntityWithId {
    private String name;
    private int age;
    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty facultyId;

    @JsonIgnore
    @OneToOne(mappedBy = "student", fetch = FetchType.LAZY)
    private Avatar avatar;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Student(Long id, String name, int age, Faculty facultyId) {
        this(name, age);
        this.setId(id);
        this.facultyId = facultyId;
    }

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
        return "{" +
                "\"id\":" + this.getId() + ", " +
                "\"name\":\"" + name + "\", " +
                "\"age\":" + age +
                '}';
    }
}
