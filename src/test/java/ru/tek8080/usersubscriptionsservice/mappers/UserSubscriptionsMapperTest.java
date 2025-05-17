package ru.tek8080.usersubscriptionsservice.mappers;

import org.junit.jupiter.api.Test;
import ru.tek8080.usersubscriptionsservice.dto.UserSubscriptionsDTO;
import ru.tek8080.usersubscriptionsservice.entities.SubscriptionEntity;
import ru.tek8080.usersubscriptionsservice.entities.UserEntity;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserSubscriptionsMapperTest {
    @Test
    void toUserSubscriptionsDTO() {
        //given
        Set<SubscriptionEntity> subscriptions = new HashSet<>();
        Set<UserEntity> users = new HashSet<>();

        SubscriptionEntity testSubscription = new SubscriptionEntity(1L, "YouTube Premium", users);
        subscriptions.add(testSubscription);
        UserEntity testUser = new UserEntity(1L, "Ivan", subscriptions);
        users.add(testUser);
        //when
        UserSubscriptionsDTO dto = UserSubscriptionsMapper.INSTANCE.toUserSubscriptionsDTO(testUser);
        //then
        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals("Ivan", dto.name());
        assertEquals(1, dto.subscriptions().size());
    }

}