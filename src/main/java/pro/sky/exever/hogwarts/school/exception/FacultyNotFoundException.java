package pro.sky.exever.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FacultyNotFoundException extends RuntimeException {
    public FacultyNotFoundException(Long message) {
        super("Факультет со следующим ID не найден: " + message);
    }
}
