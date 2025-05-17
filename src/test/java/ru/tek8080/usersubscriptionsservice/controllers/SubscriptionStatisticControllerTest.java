package ru.tek8080.usersubscriptionsservice.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.tek8080.usersubscriptionsservice.dto.TopSubscriptionDTO;
import ru.tek8080.usersubscriptionsservice.services.StatisticSubscriptionsService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SubscriptionStatisticControllerTest {
    @Mock
    private StatisticSubscriptionsService statisticSubscriptionsService;
    @InjectMocks
    private SubscriptionStatisticController subscriptionStatisticController;

    @Test
    void getTopSubscriptions() {
        //given
        TopSubscriptionDTO top1 = new TopSubscriptionDTO(1L, "YouTube Premium", 5);
        TopSubscriptionDTO top2 = new TopSubscriptionDTO(3L, "Яндекс.Плюс", 4);
        TopSubscriptionDTO top3 = new TopSubscriptionDTO(2L, "VK Музыка", 2);
        List<TopSubscriptionDTO> topSubscriptions = List.of(top1, top2, top3);
        Mockito.when(statisticSubscriptionsService.getTopSubscriptions(3)).thenReturn(topSubscriptions);
        //when
        ResponseEntity<List<TopSubscriptionDTO>> response = subscriptionStatisticController.getTopSubscriptions();
        //then
        assertNotNull(response);
        assertEquals(topSubscriptions, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}