package uz.jurayev.dynamic_document_excel.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ErrorException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

}
