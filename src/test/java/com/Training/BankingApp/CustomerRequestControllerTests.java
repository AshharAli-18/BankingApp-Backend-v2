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
public class CustomerRequestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Order(1)
    @Test
    public void testCreateCustomerRequestSuccess() throws Exception {
        String requestDto = "{"
                + "\"accountType\":\"Savings\","
                + "\"balance\":10000,"
                + "\"username\":\"testcaseuser\","
                + "\"email\":\"testuser@example.com\","
                + "\"phoneNumber\":1234567890,"
                + "\"name\":\"Test User\","
                + "\"password\":\"12345\","
                + "\"address\":\"123 Test St\","
                + "\"cnic\":\"1234567890123\""
                + "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/customerRequest")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestDto))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Customer Request Sent Successfully!"));
    }


    @Order(2)
    @Test
    public void testGetAllRequestsSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getAllRequests")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(Matchers.greaterThanOrEqualTo(0))));
    }

    @Order(3)
    @Test
    public void testGetAllRequestsForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getAllRequests")
                        .with(SecurityMockMvcRequestPostProcessors.user("customer")
                                .roles("CUSTOMER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Order(4)
    @Test
    public void testGetRequestSuccess() throws Exception {
        int requestId = 15; // Use a valid request ID for your tests
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getRequest/{id}", requestId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Order(5)
    @Test
    public void testGetRequestForbidden() throws Exception {
        int requestId = 15; // Use a valid request ID for your tests
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getRequest/{id}", requestId)
                        .with(SecurityMockMvcRequestPostProcessors.user("customer")
                                .roles("CUSTOMER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Order(7)
    @Test
    public void testDeleteRequestSuccess() throws Exception {
        int requestId = 21; // Use a valid request ID for your tests
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/deleteRequest/{id}", requestId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Order(6)
    @Test
    public void testDeleteRequestForbidden() throws Exception {
        int requestId = 17; // Use a valid request ID for your tests
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/deleteRequest/{id}", requestId)
                        .with(SecurityMockMvcRequestPostProcessors.user("customer")
                                .roles("CUSTOMER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
