package ru.tek8080.usersubscriptionsservice.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import ru.tek8080.usersubscriptionsservice.exceptions.SubscriptionNotFoundException;
import ru.tek8080.usersubscriptionsservice.exceptions.UserNotFoundException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ApiErrorHandlerTest {

    private final ApiErrorHandler apiErrorHandler = new ApiErrorHandler();

    @Test
    void handleBindException() {
        //given
        BindException bindException = new BindException(new ArrayList<>(), "errors");
        //when
        ResponseEntity<ProblemDetail> response = apiErrorHandler.handleBindException(bindException);
        //then
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void handleUserNotFoundException() {
        //given
        UserNotFoundException userNotFoundException = new UserNotFoundException("User not found");
        //when
        ResponseEntity<ProblemDetail> response = apiErrorHandler.handleUserNotFoundOrSubscriptionNotFoundException(userNotFoundException);
        //then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getProperties());
        assertEquals("User not found", response.getBody().getProperties().get("error"));
    }

    @Test
    void handleSubscriptionNotFoundException() {
        //given
        SubscriptionNotFoundException subscriptionNotFoundException = new SubscriptionNotFoundException("Subscription not found");
        //when
        ResponseEntity<ProblemDetail> response = apiErrorHandler.handleUserNotFoundOrSubscriptionNotFoundException(subscriptionNotFoundException);
        //then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getProperties());
        assertEquals("Subscription not found", response.getBody().getProperties().get("error"));
    }


}