package ru.tek8080.usersubscriptionsservice.mappers;

import org.junit.jupiter.api.Test;
import ru.tek8080.usersubscriptionsservice.dto.SubscriptionDTO;
import ru.tek8080.usersubscriptionsservice.entities.SubscriptionEntity;
import ru.tek8080.usersubscriptionsservice.entities.UserEntity;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SubscriptionMapperTest {
    @Test
    void toSubscriptionDTO() {
        //given
        Set<UserEntity> users = new HashSet<>();
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity(1L, "YouTube Premium", users);
        //when
        SubscriptionDTO subscriptionDTO = SubscriptionMapper.INSTANCE.toSubscriptionDTO(subscriptionEntity);
        //then
        assertNotNull(subscriptionDTO);
        assertEquals(1L, subscriptionDTO.id());
        assertEquals("YouTube Premium", subscriptionDTO.title());
    }


}