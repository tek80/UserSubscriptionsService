package ru.tek8080.usersubscriptionsservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import ru.tek8080.usersubscriptionsservice.dto.TopSubscriptionDTO;
import ru.tek8080.usersubscriptionsservice.entities.SubscriptionEntity;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

    @Query(value = "select new ru.tek8080.usersubscriptionsservice.dto.TopSubscriptionDTO(s.id, s.title , size(s.users)) from SubscriptionEntity s order by size(s.users) desc")
    Page<TopSubscriptionDTO> findSubscriptionWithUserCounts(Pageable pageable);
}
