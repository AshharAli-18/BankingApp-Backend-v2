package com.Training.BankingApp;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Order(1)
    @Test
    public void testLoginCustomerSuccess() throws Exception {
        String loginRequest = "{\"email\":\"sheikh@gmail.com\",\"password\":\"12345\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/loginCustomer")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(loginRequest))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Order(2)
    @Test
    public void testLoginCustomerFailure() throws Exception {
        String loginRequest = "{\"username\":\"ashae@gmail.com\",\"password\":\"12345\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/loginCustomer")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(loginRequest))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Order(3)
    @Test
    public void testLoginAdminSuccess() throws Exception {
        String loginRequest = "{\"email\":\"usama@gmail.com\",\"password\":\"12345\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/loginAdmin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(loginRequest))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        // Add more expectations as needed
    }

    @Order(4)
    @Test
    public void testLoginAdminFailure() throws Exception {
        String loginRequest = "{\"email\":\"usama@gmail.com\",\"password\":\"123456\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/loginAdmin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(loginRequest))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        // Add more expectations as needed
    }

    @Order(5)
    @Test
    public void testGetUsersSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getUsers")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(Matchers.greaterThanOrEqualTo(1))));
        // Add more expectations as needed
    }

    @Order(6)
    @Test
    public void testGetUsersForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getUsers")
                        .with(SecurityMockMvcRequestPostProcessors.user("customer")
                                .roles("CUSTOMER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}