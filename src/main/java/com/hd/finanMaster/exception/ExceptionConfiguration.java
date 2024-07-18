package com.hd.finanMaster.exception;


import com.hd.finanMaster.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionConfiguration {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDTO> handler(NotFoundException e){
        ExceptionDTO exceptionDto = new ExceptionDTO(e.getMessage());
      return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotClientAgeException.class)
    public ResponseEntity<ExceptionDTO> handleNotClientAgeException(NotClientAgeException e) {
        ExceptionDTO exceptionDto = new ExceptionDTO(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BalanceNotZeroException.class)
    public ResponseEntity<ExceptionDTO> handleBalanceNotZeroException(BalanceNotZeroException e) {
        ExceptionDTO exceptionDto = new ExceptionDTO(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BalanceCannotBeZeroException.class)
    public ResponseEntity<ExceptionDTO> handleBalanceCannotBeZeroException(BalanceCannotBeZeroException e) {
        ExceptionDTO exceptionDto = new ExceptionDTO(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }
}
