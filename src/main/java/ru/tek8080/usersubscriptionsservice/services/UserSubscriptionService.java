package ru.tek8080.usersubscriptionsservice.services;

import ru.tek8080.usersubscriptionsservice.dto.SubscriptionDTO;
import ru.tek8080.usersubscriptionsservice.dto.UserSubscriptionsDTO;

public interface UserSubscriptionService {
    SubscriptionDTO addSubscriptionToUser(Long userId, Long subscriptionId);
    void removeSubscriptionFromUser(Long userId, Long subscriptionId);
    UserSubscriptionsDTO getUserSubscriptions(Long userId);
}
