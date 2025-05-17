package ru.tek8080.usersubscriptionsservice.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import ru.tek8080.usersubscriptionsservice.dto.NewUserDTO;
import ru.tek8080.usersubscriptionsservice.dto.UserDTO;
import ru.tek8080.usersubscriptionsservice.services.UserService;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @Test
    void createUser_ReturnsCreatedUser() throws BindException {
        //given
        NewUserDTO newUserDTO = new NewUserDTO("Ivan");
        BindingResult bindingResult = new MapBindingResult(Map.of(), "newUserDTO");
        Mockito.when(userService.createUser(newUserDTO)).thenReturn(new UserDTO(1L, "Ivan"));
        // when
        ResponseEntity<UserDTO> response = userController.createUser(newUserDTO, bindingResult);
        //then
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(new UserDTO(1L, "Ivan"), response.getBody());
    }

    @Test
    void createUser_ReturnsBadRequest()  {
        //given
        NewUserDTO newUserDTO = new NewUserDTO(" ");
        BindingResult bindingResult = new MapBindingResult(Map.of(), "newUserDTO");
        bindingResult.addError(new FieldError("newUserDTO", "name", "error"));
        //when
        var exception = assertThrows(BindException.class, () -> userController.createUser(newUserDTO, bindingResult));
        //then
        assertEquals(List.of(new FieldError("newUserDTO", "name", "error")), exception.getAllErrors());
        Mockito.verifyNoInteractions(userService);
    }

    @Test
    void getUser_ReturnsUser(){
        //given
        UserDTO userDTO = new UserDTO(1L, "Ivan");
        Mockito.when(userService.findUserById(1L)).thenReturn(userDTO);
        // when
        ResponseEntity<UserDTO> response = userController.getUser(1L);
        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new UserDTO(1L, "Ivan"), response.getBody());
    }

    @Test
    void updateUser_ReturnsUpdatedUser() throws BindException {
        //give
        UserDTO userDTO = new UserDTO(1L, "Ivan");
        BindingResult bindingResult = new MapBindingResult(Map.of(), "UserDTO");
        Mockito.when(userService.updateUser(userDTO)).thenReturn(userDTO);
        //when
        ResponseEntity<UserDTO> response = userController.updateUser(userDTO, bindingResult);
        //then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new UserDTO(1L, "Ivan"), response.getBody());
    }

    @Test
    void updateUser_ReturnsBadRequest()  {
        //given
        UserDTO userDTO = new UserDTO(1L, "  ");
        BindingResult bindingResult = new MapBindingResult(Map.of(), "UserDTO");
        bindingResult.addError(new FieldError("UserDTO", "name", "error"));
        //when
        var exception = assertThrows(BindException.class, () -> userController.updateUser(userDTO, bindingResult));
        //then
        assertEquals(List.of(new FieldError("UserDTO", "name", "error")), exception.getAllErrors());
        Mockito.verifyNoInteractions(userService);
    }

    @Test
    void deleteUser_Successfully() {
        //when
        ResponseEntity<Void> response = userController.deleteUser(1L);
        //then
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(userService).deleteUser(1L);
    }

}