package ru.tek8080.usersubscriptionsservice.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.tek8080.usersubscriptionsservice.TestcontainersConfiguration;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@AutoConfigureMockMvc
class SubscriptionStatisticControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql("/sql/add_users_and_subs.sql")
    void getTopSubscriptions() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/subscriptions/top");
        //when
        mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                  {"id":1,"title":"YouTube Premium","userCount":4},
                                  {"id":2,"title":"VK Музыка","userCount":3},
                                  {"id":3,"title":"Яндекс.Плюс","userCount":2}]
                                """)
                );
    }

}