package com.hd.finanMaster.service.impl;

import com.hd.finanMaster.exception.InsufficientFundsException;
import com.hd.finanMaster.exception.NotFoundException;
import com.hd.finanMaster.model.Product;
import com.hd.finanMaster.model.Transaction;
import com.hd.finanMaster.model.TransactionType;
import com.hd.finanMaster.repository.IProductRepository;
import com.hd.finanMaster.repository.ITransactionRepository;
import com.hd.finanMaster.service.impl.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private ITransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deposit_success() {
        Long accountId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100);
        Product account = new Product();
        account.setBalance(BigDecimal.ZERO);

        when(productRepository.findById(accountId)).thenReturn(Optional.of(account));

        String result = transactionService.deposit(accountId, amount);

        assertEquals("Se ha realizado un depósito en su cuenta por un monto de: " + amount, result);
        assertEquals(amount, account.getBalance());
        verify(productRepository, times(1)).save(account);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void deposit_accountNotFound() {
        Long accountId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100);

        when(productRepository.findById(accountId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> transactionService.deposit(accountId, amount));
        assertEquals("Account not found with ID: " + accountId, exception.getMessage());
    }

    @Test
    void withdraw_success() throws InsufficientFundsException {
        Long accountId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100);
        Product account = new Product();
        account.setBalance(BigDecimal.valueOf(200));

        when(productRepository.findById(accountId)).thenReturn(Optional.of(account));

        String result = transactionService.withdraw(accountId, amount);

        assertEquals("Se ha realizado un retiro de su cuenta por un monto de: " + amount, result);
        assertEquals(BigDecimal.valueOf(100), account.getBalance());
        verify(productRepository, times(1)).save(account);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void withdraw_insufficientFunds() {
        Long accountId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100);
        Product account = new Product();
        account.setBalance(BigDecimal.valueOf(50));

        when(productRepository.findById(accountId)).thenReturn(Optional.of(account));

        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class, () -> transactionService.withdraw(accountId, amount));
        assertEquals("Insufficient funds for withdrawal.", exception.getMessage());
    }

    @Test
    void withdraw_accountNotFound() {
        Long accountId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100);

        when(productRepository.findById(accountId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> transactionService.withdraw(accountId, amount));
        assertEquals("Account not found with ID: " + accountId, exception.getMessage());
    }

    @Test
    void transfer_success() throws InsufficientFundsException {
        Long sourceAccountId = 1L;
        Long targetAccountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(100);
        Product sourceAccount = new Product();
        sourceAccount.setBalance(BigDecimal.valueOf(200));
        Product targetAccount = new Product();
        targetAccount.setBalance(BigDecimal.valueOf(50));

        when(productRepository.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(productRepository.findById(targetAccountId)).thenReturn(Optional.of(targetAccount));

        String result = transactionService.transfer(sourceAccountId, targetAccountId, amount);

        assertEquals("Se ha realizado una transferencia de: " + amount + " desde la cuenta con ID: " + sourceAccountId + " a la cuenta con ID: " + targetAccountId, result);
        assertEquals(BigDecimal.valueOf(100), sourceAccount.getBalance());
        assertEquals(BigDecimal.valueOf(150), targetAccount.getBalance());
        verify(productRepository, times(1)).save(sourceAccount);
        verify(productRepository, times(1)).save(targetAccount);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void transfer_insufficientFunds() {
        Long sourceAccountId = 1L;
        Long targetAccountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(100);
        Product sourceAccount = new Product();
        sourceAccount.setBalance(BigDecimal.valueOf(50));
        Product targetAccount = new Product();
        targetAccount.setBalance(BigDecimal.valueOf(50));

        when(productRepository.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(productRepository.findById(targetAccountId)).thenReturn(Optional.of(targetAccount));

        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class, () -> transactionService.transfer(sourceAccountId, targetAccountId, amount));
        assertEquals("Insufficient funds for transfer.", exception.getMessage());
    }

    @Test
    void transfer_sourceAccountNotFound() {
        Long sourceAccountId = 1L;
        Long targetAccountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(100);

        when(productRepository.findById(sourceAccountId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> transactionService.transfer(sourceAccountId, targetAccountId, amount));
        assertEquals("Source account not found with ID: " + sourceAccountId, exception.getMessage());
    }

    @Test
    void transfer_targetAccountNotFound() {
        Long sourceAccountId = 1L;
        Long targetAccountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(100);
        Product sourceAccount = new Product();
        sourceAccount.setBalance(BigDecimal.valueOf(200));

        when(productRepository.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(productRepository.findById(targetAccountId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> transactionService.transfer(sourceAccountId, targetAccountId, amount));
        assertEquals("Target account not found with ID: " + targetAccountId, exception.getMessage());
    }
}
