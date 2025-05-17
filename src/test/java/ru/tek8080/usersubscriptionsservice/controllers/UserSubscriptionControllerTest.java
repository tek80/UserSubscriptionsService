package ru.tek8080.usersubscriptionsservice.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.tek8080.usersubscriptionsservice.dto.SubscriptionDTO;
import ru.tek8080.usersubscriptionsservice.dto.UserSubscriptionsDTO;
import ru.tek8080.usersubscriptionsservice.services.UserSubscriptionService;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UserSubscriptionControllerTest {
    @Mock
    private UserSubscriptionService userSubscriptionService;
    @InjectMocks
    private UserSubscriptionController userSubscriptionController;

    @Test
    void addSubscriptionToUser_SuccessfullyAdded() {
        //given
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO(1L, "YouTube Premium");
        Mockito.when(userSubscriptionService.addSubscriptionToUser(1L, 1L)).thenReturn(subscriptionDTO);
        //when
        ResponseEntity<SubscriptionDTO> response = userSubscriptionController.addSubscriptionToUser(1L, 1L);
        //then
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(subscriptionDTO, response.getBody());
    }

    @Test
    void getAllSubscriptionsFromUser() {
        //given
        Set<SubscriptionDTO> subscriptions = Set.of(new SubscriptionDTO(1L, "YouTube Premium"));
        UserSubscriptionsDTO userSubscriptionsDTO = new UserSubscriptionsDTO(1L, "Ivan", subscriptions);
        Mockito.when(userSubscriptionService.getUserSubscriptions(1L)).thenReturn(userSubscriptionsDTO);
        //when
        ResponseEntity<UserSubscriptionsDTO> response = userSubscriptionController.getAllSubscriptionsFromUser(1L);
        //then
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userSubscriptionsDTO, response.getBody());
    }

    @Test
    void deleteSubscriptionFromUser() {
        //when
        ResponseEntity<Void> response = userSubscriptionController.deleteSubscriptionFromUser(1L, 1L);
        //then
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}