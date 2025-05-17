package ru.tek8080.usersubscriptionsservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tek8080.usersubscriptionsservice.exceptions.SubscriptionNotFoundException;
import ru.tek8080.usersubscriptionsservice.exceptions.UserNotFoundException;

@RestControllerAdvice
@Slf4j
public class ApiErrorHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBindException(BindException exception) {
        log.error("Bind exception", exception);
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, "Ошибка в запросе");
        problemDetail.setProperty("errors", exception.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList());
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler({UserNotFoundException.class, SubscriptionNotFoundException.class})
    public ResponseEntity<ProblemDetail> handleUserNotFoundOrSubscriptionNotFoundException(RuntimeException exception) {
        log.error("Data not found", exception);
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND, "Данные не найдены");
        problemDetail.setProperty("error", exception.getMessage());
        return new ResponseEntity<>(problemDetail, HttpStatus.NOT_FOUND);
    }


}
