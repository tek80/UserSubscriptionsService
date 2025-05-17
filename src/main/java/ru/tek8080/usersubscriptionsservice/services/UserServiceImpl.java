package ru.tek8080.usersubscriptionsservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tek8080.usersubscriptionsservice.dto.NewUserDTO;
import ru.tek8080.usersubscriptionsservice.dto.UserDTO;
import ru.tek8080.usersubscriptionsservice.entities.UserEntity;
import ru.tek8080.usersubscriptionsservice.exceptions.UserNotFoundException;
import ru.tek8080.usersubscriptionsservice.mappers.UserMapper;
import ru.tek8080.usersubscriptionsservice.repositories.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDTO createUser(NewUserDTO newUserDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(newUserDTO.name());
        userEntity = userRepository.save(userEntity);
        log.info("Добавлен пользователь с id={}", userEntity.getId());
        return UserMapper.INSTANCE.toUserDTO(userEntity);
    }

    @Override
    @Transactional
    public UserDTO updateUser(UserDTO userDTO) {
        UserEntity userEntity = getUserEntity(userDTO.id());
        userEntity.setName(userDTO.name());
        userEntity = userRepository.save(userEntity);
        log.info("Обновлен пользователь с id={}", userEntity.getId());
        return UserMapper.INSTANCE.toUserDTO(userEntity);
    }

    @Override
    public UserDTO findUserById(Long id) {
        UserEntity userEntity = getUserEntity(id);
        return UserMapper.INSTANCE.toUserDTO(userEntity);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        UserEntity userEntity = getUserEntity(id);
        userEntity.getSubscriptions().clear();
        userRepository.delete(userEntity);
        log.info("Пользователь с id={} удален", userEntity.getId());
    }


    private UserEntity getUserEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("Пользователь с id=%s не найден", id)));
    }

}
