package ru.tek8080.usersubscriptionsservice.services;

import ru.tek8080.usersubscriptionsservice.dto.TopSubscriptionDTO;

import java.util.List;

public interface StatisticSubscriptionsService {
    List<TopSubscriptionDTO> getTopSubscriptions(Integer limit);
}
