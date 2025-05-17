package ru.tek8080.usersubscriptionsservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tek8080.usersubscriptionsservice.dto.TopSubscriptionDTO;
import ru.tek8080.usersubscriptionsservice.services.StatisticSubscriptionsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionStatisticController {
    private final StatisticSubscriptionsService statisticSubscriptionsService;

    @GetMapping("/top")
    public ResponseEntity<List<TopSubscriptionDTO>> getTopSubscriptions() {
        List<TopSubscriptionDTO> topSubscriptions = statisticSubscriptionsService.getTopSubscriptions(3);
        return ResponseEntity.ok(topSubscriptions);
    }


}
