package ru.tek8080.usersubscriptionsservice.dto;

import java.util.Set;

public record UserSubscriptionsDTO(Long id, String name, Set<SubscriptionDTO> subscriptions) {
}
