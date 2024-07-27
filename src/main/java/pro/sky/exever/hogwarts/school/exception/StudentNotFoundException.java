package pro.sky.exever.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long message) {
        super("Студент со следующим ID не найден: " + message);
    }
}
