package com.example.AppointmentController.exception;

import com.elijah.doctorsappointmentbookingsystem.model.error.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ResponseStatus
@ControllerAdvice
public class DataAlreadyExistResponseEntityHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataAlreadyExistException.class)
    public ResponseEntity<ErrorMessage> dataAlreadyExistException(DataAlreadyExistException dataAlreadyExistException, WebRequest webRequest){
        ErrorMessage errorMessage = new ErrorMessage(dataAlreadyExistException.getMessage(), HttpStatus.CONFLICT);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorMessage);
    }
}
