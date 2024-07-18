package com.hd.finanMaster.controller;

import com.hd.finanMaster.dto.TransactionRequestDTO;
import com.hd.finanMaster.dto.TransferRequestDTO;
import com.hd.finanMaster.dto.WithdrawRequestDTO;
import com.hd.finanMaster.exception.ExceptionConfiguration;
import com.hd.finanMaster.exception.InsufficientFundsException;
import com.hd.finanMaster.service.impl.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
                .setControllerAdvice(new ExceptionConfiguration())  // Agregar el manejador de excepciones global
                .build();
    }

    @Test
    void deposit_success() throws Exception {
        when(transactionService.deposit(anyLong(), any(BigDecimal.class))).thenReturn("Se ha realizado un depósito en su cuenta por un monto de: 100");

        mockMvc.perform(post("/transactions/deposit")
                        .contentType("application/json")
                        .content("{\"accountId\":1,\"amount\":100}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Se ha realizado un depósito en su cuenta por un monto de: 100"));

        verify(transactionService, times(1)).deposit(anyLong(), any(BigDecimal.class));
    }

    @Test
    void withdraw_success() throws Exception, InsufficientFundsException {
        when(transactionService.withdraw(anyLong(), any(BigDecimal.class))).thenReturn("Se ha realizado un retiro de su cuenta por un monto de: 100");

        mockMvc.perform(post("/transactions/withdraw")
                        .contentType("application/json")
                        .content("{\"accountId\":1,\"amount\":100}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Se ha realizado un retiro de su cuenta por un monto de: 100"));

        verify(transactionService, times(1)).withdraw(anyLong(), any(BigDecimal.class));
    }

    @Test
    void withdraw_insufficientFunds() throws Exception, InsufficientFundsException {
        when(transactionService.withdraw(anyLong(), any(BigDecimal.class))).thenThrow(new InsufficientFundsException("Insufficient funds for withdrawal."));

        mockMvc.perform(post("/transactions/withdraw")
                        .contentType("application/json")
                        .content("{\"accountId\":1,\"amount\":100}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient funds for withdrawal."));

        verify(transactionService, times(1)).withdraw(anyLong(), any(BigDecimal.class));
    }

    @Test
    void transfer_success() throws Exception, InsufficientFundsException {
        when(transactionService.transfer(anyLong(), anyLong(), any(BigDecimal.class))).thenReturn("Se ha realizado una transferencia de: 100 desde la cuenta con ID: 1 a la cuenta con ID: 2");

        mockMvc.perform(post("/transactions/transfer")
                        .contentType("application/json")
                        .content("{\"sourceAccountId\":1,\"targetAccountId\":2,\"amount\":100}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Se ha realizado una transferencia de: 100 desde la cuenta con ID: 1 a la cuenta con ID: 2"));

        verify(transactionService, times(1)).transfer(anyLong(), anyLong(), any(BigDecimal.class));
    }

    @Test
    void transfer_insufficientFunds() throws Exception, InsufficientFundsException {
        when(transactionService.transfer(anyLong(), anyLong(), any(BigDecimal.class))).thenThrow(new InsufficientFundsException("Insufficient funds for transfer."));

        mockMvc.perform(post("/transactions/transfer")
                        .contentType("application/json")
                        .content("{\"sourceAccountId\":1,\"targetAccountId\":2,\"amount\":100}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient funds for transfer."));

        verify(transactionService, times(1)).transfer(anyLong(), anyLong(), any(BigDecimal.class));
    }
}

