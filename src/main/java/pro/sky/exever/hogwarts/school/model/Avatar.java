package pro.sky.exever.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pro.sky.exever.hogwarts.school.model.common.EntityWithId;

import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "avatar")

public class Avatar extends EntityWithId {
    private String filePath;
    private long fileSize;
    private String mediaType;
    private byte[] preview;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Student student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avatar avatar = (Avatar) o;
        return fileSize == avatar.fileSize && Objects.equals(filePath, avatar.filePath) && Objects.equals(mediaType, avatar.mediaType) && Objects.deepEquals(preview, avatar.preview) && Objects.equals(student, avatar.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath, fileSize, mediaType, Arrays.hashCode(preview), student);
    }

    @Override
    public String toString() {
        return "{" +
                "\"filePath\":\"" + filePath + "\"" +
                ", \"fileSize\":" + fileSize +
                ", \"mediaType\":\"" + mediaType + "\"" +
                ", \"student\":" + student.getId() +
                '}';
    }
}
