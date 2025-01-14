package com.mercadolibre.be_java_hisp_w28_g10.exception;

import com.mercadolibre.be_java_hisp_w28_g10.dto.exception.ExceptionDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Exception handling controller to manage application-specific exceptions.
 * <p>
 * This controller uses Spring's @ControllerAdvice to handle exceptions thrown by controllers
 * and return appropriate HTTP responses along with error details formatted in {@link ExceptionDTO}.
 * </p>
 */
@ControllerAdvice
public class ExceptionController {

    /**
     * Handles {@link NotFoundException} and returns a 404 Not Found response.
     *
     * @param e the thrown NotFoundException
     * @return ResponseEntity containing an {@link ExceptionDTO} with error details and HTTP status 404.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFound(NotFoundException e){
        ExceptionDTO ExceptionDTO = new ExceptionDTO(e.getMessage());
        return new ResponseEntity<>(ExceptionDTO, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link BadRequestException} and returns a 400 Bad Request response.
     *
     * @param e the thrown BadRequestException
     * @return ResponseEntity containing an {@link ExceptionDTO} with error details and HTTP status 400.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(BadRequestException e) {
        ExceptionDTO dto = new ExceptionDTO(e.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link LoadJSONDataException} and returns a 400 Bad Request response.
     *
     * @param e the thrown LoadJSONDataException
     * @return ResponseEntity containing an {@link ExceptionDTO} with error details and HTTP status 400.
     */
    @ExceptionHandler(LoadJSONDataException.class)
    public ResponseEntity<?> loadJSONDataException(LoadJSONDataException e) {
        ExceptionDTO dto = new ExceptionDTO(e.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link ConflictException} and returns a 409 Conflict response.
     *
     * @param e the thrown ConflictException
     * @return ResponseEntity containing an {@link ExceptionDTO} with error details and HTTP status 409.
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> conflicttException(ConflictException e) {
        ExceptionDTO dto = new ExceptionDTO(e.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
    }

    /**
     * Handles {@link IllegalArgumentException} and returns a 400 Bad Request response.
     *
     * @param e the thrown IllegalArgumentException
     * @return ResponseEntity containing an {@link ExceptionDTO} with error details and HTTP status 400.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException(IllegalArgumentException e) {
        ExceptionDTO dto = new ExceptionDTO(e.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    // Manejo de ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }
}
