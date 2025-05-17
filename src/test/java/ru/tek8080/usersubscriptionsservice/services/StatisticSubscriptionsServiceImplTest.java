package ru.tek8080.usersubscriptionsservice.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.tek8080.usersubscriptionsservice.dto.TopSubscriptionDTO;
import ru.tek8080.usersubscriptionsservice.repositories.SubscriptionRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StatisticSubscriptionsServiceImplTest {
    @Mock
    private SubscriptionRepository statisticsRepository;
    @InjectMocks
    private StatisticSubscriptionsServiceImpl statisticSubscriptionsService;

    @Test
    void getTopSubscriptions_ReturnListTopSubscriptions() {
        //given
        TopSubscriptionDTO top1 = new TopSubscriptionDTO(1L, "YouTube Premium", 5);
        TopSubscriptionDTO top2 = new TopSubscriptionDTO(3L, "Яндекс.Плюс", 4);
        TopSubscriptionDTO top3 = new TopSubscriptionDTO(2L, "VK Музыка", 2);
        List<TopSubscriptionDTO> topSubscriptions = List.of(top1, top2, top3);
        Mockito.when(statisticsRepository.findSubscriptionWithUserCounts(PageRequest.of(0, 3)))
                .thenReturn(new PageImpl<>(topSubscriptions));
        //when
        List<TopSubscriptionDTO> actualResult = statisticSubscriptionsService.getTopSubscriptions(3);
        //then
        assertEquals(topSubscriptions, actualResult);
        Mockito.verify(statisticsRepository).findSubscriptionWithUserCounts(PageRequest.of(0, 3));

    }

}
