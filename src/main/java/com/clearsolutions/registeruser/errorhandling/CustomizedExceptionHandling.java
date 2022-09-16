package com.clearsolutions.registeruser.errorhandling;

import com.clearsolutions.registeruser.errorhandling.exeptions.NotNullFieldsException;
import com.clearsolutions.registeruser.errorhandling.exeptions.NotValidDateArguments;
import com.clearsolutions.registeruser.errorhandling.exeptions.UserNotFoundException;
import com.clearsolutions.registeruser.errorhandling.exeptions.UserNotValidAge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomizedExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFound() {
        ExceptionResponse response = new ExceptionResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage("User is not found by this id.");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotValidAge.class)
    public ResponseEntity<Object> notValidAge() {
        ExceptionResponse response = new ExceptionResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage("Invalid birthdate. Valid age is more then 18 years old.");

        return new ResponseEntity<>(response, HttpStatus.LOCKED);
    }

    @ExceptionHandler(NotValidDateArguments.class)
    public ResponseEntity<Object> notValidDateArguments() {
        ExceptionResponse response = new ExceptionResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage("Invalid date arguments. First date must be earlier then second.");

        return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(NotNullFieldsException.class)
    public ResponseEntity<Object> notNullFields() {
        ExceptionResponse response = new ExceptionResponse();
        response.setDateTime(LocalDateTime.now());
        response.setMessage("Fields 'email', 'first name', 'last name' and 'birthdate' cannot be empty.");

        return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }
}
