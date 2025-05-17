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
@Sql("/sql/add_users_and_subs.sql")
class UserSubscriptionControllerIT {
    @Autowired
    MockMvc mockMvc;


    @Test
    void addSubscriptionToUser_OK() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/users/1/subscriptions/3");
        //when
        mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                {                                    
                                    "id": 3,
                                    "title": "Яндекс.Плюс"                                    
                                }""")
                );
    }

    @Test
    void addSubscriptionToUser_UserNotFound() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/users/555/subscriptions/3");
        //when
        mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isNotFound(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json("""
                                    {                                         
                                      "error": "Пользователь с id=555 не найден"
                                    }
                                """)
                );
    }

    @Test
    void addSubscriptionToUser_SubscriptionNotFound() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/users/1/subscriptions/999");
        //when
        mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isNotFound(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json("""
                                    {                                         
                                      "error": "Подписка с id=999 не найдена"
                                    }
                                """)
                );
    }

    @Test
    void getAllSubscriptionsFromUser_Ok() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/users/3/subscriptions");
        //when
        mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                  "id":3,
                                  "name":"Dasha",
                                  "subscriptions":[
                                            {"id":3,"title":"Яндекс.Плюс"},
                                            {"id":1,"title":"YouTube Premium"}
                                            ]
                                }
                                """)
                );
    }

    @Test
    void getAllSubscriptionsFromUser_UserNotFound() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/users/555/subscriptions");
        //when
        mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isNotFound(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json("""
                                    {                                         
                                      "error": "Пользователь с id=555 не найден"
                                    }
                                """)
                );
    }

    @Test
    void deleteSubscriptionFromUser_Ok() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.delete("/api/v1/users/5/subscriptions/2");
        //when
        mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isNoContent()
                );
    }

    @Test
    void deleteSubscriptionFromUser_UserNotFound() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.delete("/api/v1/users/555/subscriptions/3");
        //when
        mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isNotFound(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json("""
                                    {                                         
                                      "error": "Пользователь с id=555 не найден"
                                    }
                                """)
                );
    }

    @Test
    void deleteSubscriptionFromUser_SubscriptionNotFound() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.delete("/api/v1/users/1/subscriptions/999");
        //when
        mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isNotFound(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json("""
                                    {                                         
                                      "error": "Подписка с id=999 не найдена"
                                    }
                                """)
                );
    }

}