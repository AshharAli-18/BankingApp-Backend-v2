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
public class TransferControllerTests {

    @Autowired
    private MockMvc mockMvc;

//    @Order(1)
//    @Test
//    public void testTransferMoneySuccess() throws Exception {
//        String transferRequest = "{"
//                + "\"fromAccountId\":28,"
//                + "\"toAccountNumber\":\"3pPubAL_RTubyO\","
//                + "\"amount\":100,"
//                + "\"email\":\"user@example.com\","
//                + "\"otp\":\"123456\""
//                + "}";
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/transferMoney")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(transferRequest)
//                        .with(SecurityMockMvcRequestPostProcessors.user("customer")
//                                .roles("CUSTOMER")))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Money transferred successfully"));
//    }


    @Order(1)
    @Test
    public void testTransferMoneyFailure() throws Exception {
        String transferRequest = "{\"fromAccountId\":\"12345\",\"toAccountNumber\":\"67890\",\"amount\":1000.00}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transferMoney")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(transferRequest)
                        .with(SecurityMockMvcRequestPostProcessors.user("customer")
                                .roles("CUSTOMER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Order(2)
    @Test
    public void testGetAllTransfersSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getAllTransfers")
                        .param("page", "0")
                        .param("size", "10")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin")
                                .roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(Matchers.greaterThanOrEqualTo(0))));
    }

    @Order(3)
    @Test
    public void testGetAllTransfersForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getAllTransfers")
                        .param("page", "0")
                        .param("size", "10")
                        .with(SecurityMockMvcRequestPostProcessors.user("customer")
                                .roles("CUSTOMER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
