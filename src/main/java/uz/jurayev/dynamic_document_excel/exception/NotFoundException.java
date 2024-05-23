package uz.jurayev.dynamic_document_excel.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import uz.jurayev.dynamic_document_excel.enam.ResponseMessage;

@Getter
public class NotFoundException extends RuntimeException {

    private final HttpStatus httpStatus;

    public NotFoundException(String message) {
        super(message + " " + ResponseMessage.NOT_FOUND.getMessage());
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}
