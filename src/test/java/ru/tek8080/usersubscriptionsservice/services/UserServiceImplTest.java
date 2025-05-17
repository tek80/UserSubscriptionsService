package ru.tek8080.usersubscriptionsservice.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tek8080.usersubscriptionsservice.dto.NewUserDTO;
import ru.tek8080.usersubscriptionsservice.dto.UserDTO;
import ru.tek8080.usersubscriptionsservice.entities.SubscriptionEntity;
import ru.tek8080.usersubscriptionsservice.entities.UserEntity;
import ru.tek8080.usersubscriptionsservice.exceptions.UserNotFoundException;
import ru.tek8080.usersubscriptionsservice.repositories.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_ReturnCreatedUser() {
        // given
        String name = "Oleg";
        Set<SubscriptionEntity> subscriptions = new HashSet<>();
        Mockito.when(userRepository.save(new UserEntity(null, "Oleg", subscriptions)))
                .thenReturn(new UserEntity(1L, "Oleg", subscriptions));
        //when
        UserDTO createdUser = userService.createUser(new NewUserDTO(name));

        //then
        assertEquals(new UserDTO(1L, "Oleg"), createdUser);
        Mockito.verify(userRepository, Mockito.times(1)).save(new UserEntity(null, "Oleg", subscriptions));
    }

    @Test
    void updateUser_ReturnUpdatedUser() {
        // given
        UserDTO userDTO = new UserDTO(1L, "Oleg");
        Set<SubscriptionEntity> subscriptions = new HashSet<>();
        UserEntity existUser = new UserEntity(1L, "Ivan", subscriptions);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(existUser));
        Mockito.when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        // when
        UserDTO updatedUser = userService.updateUser(new UserDTO(1L, "Oleg"));
        // then
        assertEquals(userDTO, updatedUser);
        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void updateUser_ReturnUserNotFoundException() {
        //given
        UserDTO userDTO = new UserDTO(555L, "Ivan");
        Mockito.when(userRepository.findById(555L)).thenReturn(Optional.empty());

        //when
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userDTO));
        // then
        Mockito.verify(userRepository, Mockito.times(1)).findById(555L);
    }

    @Test
    void deleteUser_SuccessfullyDeleteUser() {
        //given
        Set<SubscriptionEntity> subscriptions = new HashSet<>();
        Set<UserEntity> users = new HashSet<>();
        subscriptions.add(new SubscriptionEntity(1L, "YouTube Premium", users));
        subscriptions.add(new SubscriptionEntity(2L, "VK Музыка", users));
        UserEntity userEntity = new UserEntity(1L, "Ivan", subscriptions);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        //when
        userService.deleteUser(1L);
        //then
        assertTrue(userEntity.getSubscriptions().isEmpty());
        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deleteUser_ReturnUserNotFoundException() {
        //given
        Mockito.when(userRepository.findById(555L)).thenReturn(Optional.empty());
        //when
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(555L));
        //then
        Mockito.verify(userRepository, Mockito.times(1)).findById(555L);
    }

    @Test
    void findUserById_SuccessfullyFindUser() {
        //given
        UserDTO userDTO = new UserDTO(1L, "Ivan");
        Set<SubscriptionEntity> subscriptions = new HashSet<>();
        UserEntity existUser = new UserEntity(1L, "Ivan", subscriptions);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(existUser));
        //when
        UserDTO foundUser = userService.findUserById(1L);
        //then
        assertEquals(userDTO, foundUser);
        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void findUserById_ReturnUserNotFoundException() {
        //given
        Mockito.when(userRepository.findById(555L)).thenReturn(Optional.empty());
        //when
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(555L));
        //then
        Mockito.verify(userRepository, Mockito.times(1)).findById(555L);
    }


}