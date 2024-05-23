package uz.jurayev.dynamic_document_excel.enam;

import lombok.Getter;

@Getter
public enum ResponseMessage {

    NOT_FOUND("Not Found"),
    SUCCESSFULLY("Successfully"),
    SUCCESSFULLY_CREATED("Successfully created"),
    SUCCESSFULLY_UPDATED("Successfully updated"),
    SUCCESSFULLY_DELETED("Successfully deleted"),
    ALREADY_EXISTS("Already exists"),
    ERROR_CREATED("Error created"),
    ERROR_UPDATED("Error updated"),
    ERROR_DELETED("Error deleted"),
    ERROR("Error");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }
}
