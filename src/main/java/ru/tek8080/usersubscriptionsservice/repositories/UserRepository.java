package ru.tek8080.usersubscriptionsservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tek8080.usersubscriptionsservice.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
