package pe.msbaek.tddcases.bookloan.loan;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse errorResponse = ErrorResponse.builder(e, HttpStatus.BAD_REQUEST, e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse.toString(), HttpStatus.BAD_REQUEST);
    }
}