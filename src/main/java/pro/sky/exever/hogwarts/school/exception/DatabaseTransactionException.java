package pro.sky.exever.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DatabaseTransactionException extends RuntimeException {
    public DatabaseTransactionException(String message) {
        super("Не удалось записать в базу данных: " + message);
    }
}
