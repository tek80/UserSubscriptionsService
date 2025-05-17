package ru.tek8080.usersubscriptionsservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tek8080.usersubscriptionsservice.dto.TopSubscriptionDTO;
import ru.tek8080.usersubscriptionsservice.repositories.SubscriptionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticSubscriptionsServiceImpl implements StatisticSubscriptionsService {
    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TopSubscriptionDTO> getTopSubscriptions(Integer limit) {
        Pageable pageable = PageRequest.of(0, limit);
        Page<TopSubscriptionDTO> pageSubscriptionWithUserCounts = subscriptionRepository.findSubscriptionWithUserCounts(pageable);
        return pageSubscriptionWithUserCounts.get().toList();
    }
}
