package com.mercadolibre.be_java_hisp_w28_g10.exception;

import com.mercadolibre.be_java_hisp_w28_g10.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFound(NotFoundException e){
        ExceptionDTO ExceptionDTO = new ExceptionDTO(e.getMessage());
        return new ResponseEntity<>(ExceptionDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(BadRequestException e) {
        ExceptionDTO dto = new ExceptionDTO(e.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(LoadJSONDataException.class)
    public ResponseEntity<?> loadJSONDataException(LoadJSONDataException e) {
        ExceptionDTO dto = new ExceptionDTO(e.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> conflicttException(ConflictException e) {
        ExceptionDTO dto = new ExceptionDTO(e.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(SaveOperationException.class)
    public ResponseEntity<?> conflicttException(SaveOperationException e) {
        ExceptionDTO dto = new ExceptionDTO(e.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}
