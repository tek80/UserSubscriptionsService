package ru.tek8080.usersubscriptionsservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.tek8080.usersubscriptionsservice.dto.SubscriptionDTO;
import ru.tek8080.usersubscriptionsservice.entities.SubscriptionEntity;



@Mapper
public interface SubscriptionMapper {
    SubscriptionMapper INSTANCE = Mappers.getMapper(SubscriptionMapper.class);
    SubscriptionDTO toSubscriptionDTO(SubscriptionEntity subscriptionEntity);
}