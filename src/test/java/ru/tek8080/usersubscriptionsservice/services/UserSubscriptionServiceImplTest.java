package ru.tek8080.usersubscriptionsservice.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tek8080.usersubscriptionsservice.dto.SubscriptionDTO;
import ru.tek8080.usersubscriptionsservice.dto.UserSubscriptionsDTO;
import ru.tek8080.usersubscriptionsservice.entities.SubscriptionEntity;
import ru.tek8080.usersubscriptionsservice.entities.UserEntity;
import ru.tek8080.usersubscriptionsservice.exceptions.SubscriptionNotFoundException;
import ru.tek8080.usersubscriptionsservice.exceptions.UserNotFoundException;
import ru.tek8080.usersubscriptionsservice.repositories.SubscriptionRepository;
import ru.tek8080.usersubscriptionsservice.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserSubscriptionServiceImplTest {
    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserSubscriptionServiceImpl userSubscriptionService;

    private UserEntity testUser;
    private SubscriptionEntity testSubscription;

    @BeforeEach
    void setUp() {
        Set<SubscriptionEntity> subscriptions = new HashSet<>();
        Set<UserEntity> users = new HashSet<>();
        testSubscription = new SubscriptionEntity(1L, "YouTube Premium", users);
        subscriptions.add(testSubscription);
        testUser = new UserEntity(1L, "Ivan", subscriptions);
        users.add(testUser);
    }

    @Test
    void addSubscriptionToUser_ReturnSubscriptionAdded() {
        //given
        Set<UserEntity> users = new HashSet<>();
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity(2L, "VK Музыка", users);
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(testUser));
        Mockito.when(subscriptionRepository.findById(2L)).thenReturn(java.util.Optional.of(subscriptionEntity));
        Mockito.when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        // when
        SubscriptionDTO subscriptionDTO = userSubscriptionService.addSubscriptionToUser(1L, 2L);
        // then
        assertEquals(new SubscriptionDTO(2L, "VK Музыка"), subscriptionDTO);
        assertEquals(2, testUser.getSubscriptions().size());
        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(subscriptionRepository, Mockito.times(1)).findById(2L);
        Mockito.verify(userRepository, Mockito.times(1)).save(testUser);
    }

    @Test
    void addSubscriptionToUser_ReturnUserNotFoundException() {
        //given
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        //when
        assertThrows(UserNotFoundException.class, () -> userSubscriptionService.addSubscriptionToUser(1L, 2L));
        // then
        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void addSubscriptionToUser_ReturnSubscriptionNotFoundException() {
        // given
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(testUser));
        Mockito.when(subscriptionRepository.findById(2L)).thenReturn(java.util.Optional.empty());
        //when
        assertThrows(SubscriptionNotFoundException.class, () -> userSubscriptionService.addSubscriptionToUser(1L, 2L));
        //then
        Mockito.verify(subscriptionRepository, Mockito.times(1)).findById(2L);
    }

    @Test
    void removeSubscriptionFromUser_SubscriptionRemoved() {
        // given
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(testUser));
        Mockito.when(subscriptionRepository.findById(1L)).thenReturn(java.util.Optional.of(testSubscription));
        //when
        userSubscriptionService.removeSubscriptionFromUser(1L, 1L);
        //then
        assertEquals(0, testUser.getSubscriptions().size());
        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(subscriptionRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(userRepository, Mockito.times(1)).save(testUser);
    }

    @Test
    void removeSubscriptionFromUser_ReturnUserNotFoundException() {
        //given
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        //when
        assertThrows(UserNotFoundException.class, () -> userSubscriptionService.removeSubscriptionFromUser(1L, 1L));
        //then
        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void removeSubscriptionFromUser_ReturnSubscriptionNotFoundException() {
        //given
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(testUser));
        Mockito.when(subscriptionRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        //when
        assertThrows(SubscriptionNotFoundException.class, () -> userSubscriptionService.removeSubscriptionFromUser(1L, 1L));
        //then
        Mockito.verify(subscriptionRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void getUserSubscriptions_returnUserSubscriptions() {
        //given
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(testUser));
        //when
        UserSubscriptionsDTO result = userSubscriptionService.getUserSubscriptions(1L);
        // then
        assertNotNull(result);
        assertEquals(1, result.subscriptions().size());
        assertEquals("Ivan", result.name());
        assertEquals(1L, result.id());
        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void getUserSubscriptions_returnUserNotFoundException() {
        //given
        Mockito.when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        //when
        assertThrows(UserNotFoundException.class, () -> userSubscriptionService.getUserSubscriptions(1L));
        //then
        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }

}