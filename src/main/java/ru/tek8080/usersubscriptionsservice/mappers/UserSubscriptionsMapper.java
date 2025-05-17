package ru.tek8080.usersubscriptionsservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.tek8080.usersubscriptionsservice.dto.UserSubscriptionsDTO;
import ru.tek8080.usersubscriptionsservice.entities.UserEntity;


@Mapper
public interface UserSubscriptionsMapper {
    UserSubscriptionsMapper INSTANCE = Mappers.getMapper(UserSubscriptionsMapper.class);

    UserSubscriptionsDTO toUserSubscriptionsDTO(UserEntity userEntity);
}