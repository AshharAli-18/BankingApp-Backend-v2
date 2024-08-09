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
public class AccountControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Order(1)
    @Test
    public void testGetAccountSuccess() throws Exception {
        long accountId = 24; // Use a valid account ID for your tests
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getAccount/{accountId}", accountId)
                        .with(SecurityMockMvcRequestPostProcessors.user("customer")
                                .roles("CUSTOMER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Order(2)
    @Test
    public void testGetAccountByUserIdSuccess() throws Exception {
        long userId = 39; // Use a valid user ID for your tests
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getAccoutByUserId/{userId}", userId)
                        .with(SecurityMockMvcRequestPostProcessors.user("customer")
                                .roles("CUSTOMER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Order(3)
    @Test
    public void testGetAllAccountsSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getAllAccounts")
                        .param("page", "0")
                        .param("size", "10")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(Matchers.greaterThanOrEqualTo(0))));
    }

    @Order(4)
    @Test
    public void testGetAllAccountsForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getAllAccounts")
                        .param("page", "0")
                        .param("size", "10")
                        .with(SecurityMockMvcRequestPostProcessors.user("customer")
                                .roles("CUSTOMER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }


    @Order(5)
    @Test
    public void testDeleteAccountSuccess() throws Exception {
        Long accountId = 1L; // Use a valid account ID for your tests
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/deleteAccount/{id}", accountId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Order(6)
    @Test
    public void testCreateAccountSuccess() throws Exception {
        String createRequest = "{"
                + "\"accountType\":\"Savings\","
                + "\"balance\":20000,"
                + "\"username\":\"newuser\","
                + "\"email\":\"newuser@example.com\","
                + "\"phoneNumber\":1234567890,"
                + "\"name\":\"New User\","
                + "\"password\":\"password456\","
                + "\"address\":\"789 New St\","
                + "\"cnic\":\"1234567890125\""
                + "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/createAccount")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(createRequest)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Account Created Successfully!"));
    }


    @Order(8)
    @Test
    public void testUpdateAccountForbidden() throws Exception {
        Long accountId = 1L; // Use a valid account ID for your tests
        String updateRequest = "{"
                + "\"accountType\":\"Checking\","
                + "\"balance\":15000,"
                + "\"username\":\"updateduser\","
                + "\"email\":\"updateduser@example.com\","
                + "\"phoneNumber\":9876543210,"
                + "\"name\":\"Updated User\","
                + "\"password\":\"newpassword123\","
                + "\"address\":\"456 Updated St\","
                + "\"cnic\":\"1234567890124\""
                + "}";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/updateAccount/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(updateRequest)
                        .with(SecurityMockMvcRequestPostProcessors.user("customer")
                                .roles("CUSTOMER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Order(9)
    @Test
    public void testDeleteAccountForbidden() throws Exception {
        Long accountId = 1L; // Use a valid account ID for your tests
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/deleteAccount/{id}", accountId)
                        .with(SecurityMockMvcRequestPostProcessors.user("customer")
                                .roles("CUSTOMER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
