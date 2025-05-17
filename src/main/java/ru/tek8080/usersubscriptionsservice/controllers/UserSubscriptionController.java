package ru.tek8080.usersubscriptionsservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tek8080.usersubscriptionsservice.dto.SubscriptionDTO;
import ru.tek8080.usersubscriptionsservice.dto.UserSubscriptionsDTO;
import ru.tek8080.usersubscriptionsservice.services.UserSubscriptionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/{id:\\d+}/subscriptions")
public class UserSubscriptionController {
    private final UserSubscriptionService userSubscriptionService;

    @PostMapping("/{sub_id:\\d+}")
    public ResponseEntity<SubscriptionDTO> addSubscriptionToUser(@PathVariable(name = "id") Long userId, @PathVariable(name = "sub_id") Long subId) {
        SubscriptionDTO subscriptionDTO = userSubscriptionService.addSubscriptionToUser(userId, subId);
        return new ResponseEntity<>(subscriptionDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<UserSubscriptionsDTO> getAllSubscriptionsFromUser(@PathVariable(name = "id") Long userId) {
        UserSubscriptionsDTO userSubscriptionsDTO = userSubscriptionService.getUserSubscriptions(userId);
        return new ResponseEntity<>(userSubscriptionsDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{sub_id:\\d+}")
    public ResponseEntity<Void> deleteSubscriptionFromUser(@PathVariable(name = "id") Long userId, @PathVariable(name = "sub_id") Long subId) {
        userSubscriptionService.removeSubscriptionFromUser(userId, subId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
