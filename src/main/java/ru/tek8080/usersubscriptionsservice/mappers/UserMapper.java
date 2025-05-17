package ru.tek8080.usersubscriptionsservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.tek8080.usersubscriptionsservice.dto.UserDTO;
import ru.tek8080.usersubscriptionsservice.entities.UserEntity;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toUserDTO(UserEntity userEntity);
}
