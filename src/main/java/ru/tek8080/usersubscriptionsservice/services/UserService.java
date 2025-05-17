package ru.tek8080.usersubscriptionsservice.services;

import ru.tek8080.usersubscriptionsservice.dto.NewUserDTO;
import ru.tek8080.usersubscriptionsservice.dto.UserDTO;

public interface UserService {
    UserDTO createUser(NewUserDTO newUserDTO);

    UserDTO updateUser(UserDTO userDTO);

    UserDTO findUserById(Long id);

    void deleteUser(Long id);
}
