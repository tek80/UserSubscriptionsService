package ru.tek8080.usersubscriptionsservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(
        @NotNull
        Long id,
        @NotNull(message = "Имя пользователя должно быть заполнено")
        @NotBlank(message = "Имя пользователя должно быть пусто")
        String name
) {
}
