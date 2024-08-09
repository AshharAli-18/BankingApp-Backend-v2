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
public class TransactionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Order(1)
    @Test
    public void testGetAllTransactionsSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getAllTransactions")
                        .param("page", "0")
                        .param("size", "10")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(Matchers.greaterThanOrEqualTo(0))));
    }

    @Order(2)
    @Test
    public void testGetAllTransactionsForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getAllTransactions")
                        .param("page", "0")
                        .param("size", "10")
                        .with(SecurityMockMvcRequestPostProcessors.user("customer")
                                .roles("CUSTOMER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Order(3)
    @Test
    public void testGetTransactionByAccountIdSuccess() throws Exception {
        long accountId = 28; // Use a valid account ID for your tests
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getTransactionByAccountId/{id}", accountId)
                        .with(SecurityMockMvcRequestPostProcessors.user("customer")
                                .roles("CUSTOMER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(Matchers.greaterThanOrEqualTo(0))));
    }

    @Order(4)
    @Test
    public void testGetTransactionByAccountIdFailure() throws Exception {
        long accountId = 12345; // Use a valid account ID for your tests
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getTransactionByAccountId/{id}", accountId)
                        .with(SecurityMockMvcRequestPostProcessors.user("customer")
                                .roles("CUSTOMER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(Matchers.greaterThanOrEqualTo(0))));
    }
    @Order(5)
    @Test
    public void testGetTransactionByTransferIdSuccess() throws Exception {
        long transferId = 65; // Use a valid transfer ID for your tests
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getTransactionByTransferId/{id}", transferId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(Matchers.greaterThanOrEqualTo(0))));
    }

    @Order(6)
    @Test
    public void testGetTransactionByTransferIdForbidden() throws Exception {
        long transferId = 65; // Use a valid transfer ID for your tests
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getTransactionByTransferId/{id}", transferId)
                        .with(SecurityMockMvcRequestPostProcessors.user("customer")
                                .roles("CUSTOMER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
