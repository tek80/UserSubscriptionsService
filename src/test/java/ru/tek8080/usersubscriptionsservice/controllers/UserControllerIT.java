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
class UserControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Test
    void addUser_OK() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                          {
                            "name": "Ivan"
                          }                            
                        """);

        //when
        mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                {                                    
                                    "name": "Ivan"                                    
                                }""")
                );
    }


    @Test
    void addUser_BadRequest() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                          {
                            "name": " "
                          }                            
                        """);

        //when
        mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON),
                        content().json("""
                                {
                                    "errors": ["Имя пользователя должно быть пусто"]                                    
                                }""")
                );
    }

    @Test
    void getUser_OK() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/users/1");
        //when
        mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                    {
                                      "id": 1,
                                      "name": "Ivan"
                                    }
                                """)
                );
    }

    @Test
    void getUser_NotFound() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/users/555");
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
    void updateUser_OK() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.put("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                          {
                            "id": 2,
                            "name": "Petr"
                          }                            
                        """);

        //when
        mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                {     
                                    "id": 2,                               
                                    "name": "Petr"                                    
                                }""")
                );
    }

    @Test
    void updateUser_NotFound() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.put("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                          {
                            "id": 555,
                            "name": "Petr"
                          }                            
                        """);

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
    void deleteUser_OK() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.delete("/api/v1/users/5");

        //when
        mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpectAll(
                        status().isNoContent()
                );
    }

    @Test
    void deleteUser_NotFound() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.delete("/api/v1/users/555");
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
}