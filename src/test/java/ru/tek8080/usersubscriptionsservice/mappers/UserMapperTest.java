package ru.tek8080.usersubscriptionsservice.mappers;

import org.junit.jupiter.api.Test;
import ru.tek8080.usersubscriptionsservice.dto.UserDTO;
import ru.tek8080.usersubscriptionsservice.entities.SubscriptionEntity;
import ru.tek8080.usersubscriptionsservice.entities.UserEntity;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserMapperTest {
    @Test
    void toUserDTO() {
        //given
        Set<SubscriptionEntity> subscriptionEntities = new HashSet<>();
        UserEntity userEntity = new UserEntity(1L, "Ivan", subscriptionEntities);
        //when
        UserDTO userDTO = UserMapper.INSTANCE.toUserDTO(userEntity);
        //then
        assertNotNull(userDTO);
        assertEquals(1L, userDTO.id());
        assertEquals("Ivan", userDTO.name());
    }

}