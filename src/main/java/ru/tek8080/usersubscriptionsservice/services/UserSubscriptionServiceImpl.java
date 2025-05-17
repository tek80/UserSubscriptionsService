package ru.tek8080.usersubscriptionsservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tek8080.usersubscriptionsservice.dto.SubscriptionDTO;
import ru.tek8080.usersubscriptionsservice.dto.UserSubscriptionsDTO;
import ru.tek8080.usersubscriptionsservice.entities.SubscriptionEntity;
import ru.tek8080.usersubscriptionsservice.entities.UserEntity;
import ru.tek8080.usersubscriptionsservice.exceptions.SubscriptionNotFoundException;
import ru.tek8080.usersubscriptionsservice.exceptions.UserNotFoundException;
import ru.tek8080.usersubscriptionsservice.mappers.SubscriptionMapper;
import ru.tek8080.usersubscriptionsservice.mappers.UserSubscriptionsMapper;
import ru.tek8080.usersubscriptionsservice.repositories.SubscriptionRepository;
import ru.tek8080.usersubscriptionsservice.repositories.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSubscriptionServiceImpl implements UserSubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public SubscriptionDTO addSubscriptionToUser(Long userId, Long subscriptionId) {
        UserEntity userEntity = getUserEntity(userId);
        SubscriptionEntity subscriptionEntity = getSubscriptionEntity(subscriptionId);
        userEntity.getSubscriptions().add(subscriptionEntity);
        userRepository.save(userEntity);
        log.info("Пользователю id={} добавлена подписка id={}", userId, subscriptionId);
        return SubscriptionMapper.INSTANCE.toSubscriptionDTO(subscriptionEntity);
    }

    @Override
    @Transactional
    public void removeSubscriptionFromUser(Long userId, Long subscriptionId) {
        UserEntity userEntity = getUserEntity(userId);
        SubscriptionEntity subscriptionEntity = getSubscriptionEntity(subscriptionId);
        userEntity.getSubscriptions().remove(subscriptionEntity);
        userRepository.save(userEntity);
        log.info("Пользователю id={} удалена подписка id={}", userId, subscriptionId);
    }

    @Override
    @Transactional(readOnly = true)
    public UserSubscriptionsDTO getUserSubscriptions(Long userId) {
        UserEntity userEntity = getUserEntity(userId);
        return UserSubscriptionsMapper.INSTANCE.toUserSubscriptionsDTO(userEntity);
    }

    private UserEntity getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("Пользователь с id=%s не найден", userId)));
    }

    private SubscriptionEntity getSubscriptionEntity(Long subscriptionId) {
        return subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionNotFoundException(String.format("Подписка с id=%s не найдена", subscriptionId)));
    }
}
