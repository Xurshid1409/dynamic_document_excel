package uz.jurayev.dynamic_document_excel.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import uz.jurayev.dynamic_document_excel.enam.ResponseMessage;

@Getter
public class AlreadyExistsException extends RuntimeException {

    private final HttpStatus httpStatus;

    public AlreadyExistsException(String message) {
        super(message + " " + ResponseMessage.ALREADY_EXISTS.getMessage());
        this.httpStatus = HttpStatus.ALREADY_REPORTED;
    }

}
