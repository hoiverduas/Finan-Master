package com.hd.finanMaster.exception;


import com.hd.finanMaster.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionConfiguration {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handler(NotFoundException e){
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage());
      return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotClientAgeException.class)
    public ResponseEntity<ExceptionDto> handleNotClientAgeException(NotClientAgeException e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }
}
