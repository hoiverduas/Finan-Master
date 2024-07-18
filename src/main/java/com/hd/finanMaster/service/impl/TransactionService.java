package com.hd.finanMaster.service.impl;

import com.hd.finanMaster.exception.InsufficientFundsException;
import com.hd.finanMaster.exception.NotFoundException;
import com.hd.finanMaster.model.Product;
import com.hd.finanMaster.model.Transaction;
import com.hd.finanMaster.model.TransactionType;
import com.hd.finanMaster.repository.IProductRepository;
import com.hd.finanMaster.repository.ITransactionRepository;
import com.hd.finanMaster.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransactionService implements ITransactionService {

    private final IProductRepository productRepository;
    private final ITransactionRepository transactionRepository;

    @Autowired
    public TransactionService(IProductRepository productRepository, ITransactionRepository transactionRepository) {
        this.productRepository = productRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public String deposit(Long accountId, BigDecimal amount) {
        Product account = productRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found with ID: " + accountId));

        account.setBalance(account.getBalance().add(amount));
        productRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.CONSIGNACION);
        transaction.setAmount(amount);
        transaction.setDate(LocalDate.now());
        transaction.setTargetAccount(account);

        transactionRepository.save(transaction);

        return "A deposit of: " + amount + " has been made to your account.";
    }

    @Override
    public String withdraw(Long accountId, BigDecimal amount) throws InsufficientFundsException {
        Product account = productRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found with ID: " + accountId));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal.");
        }

        account.setBalance(account.getBalance().subtract(amount));
        productRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.RETIRO);
        transaction.setAmount(amount);
        transaction.setDate(LocalDate.now());
        transaction.setSourceAccount(account);

        transactionRepository.save(transaction);

        return "A withdrawal of: " + amount + " has been made from your account.";
    }

    @Override
    public String transfer(Long sourceAccountId, Long targetAccountId, BigDecimal amount) throws InsufficientFundsException {
        Product sourceAccount = productRepository.findById(sourceAccountId)
                .orElseThrow(() -> new NotFoundException("Source account not found with ID: " + sourceAccountId));

        Product targetAccount = productRepository.findById(targetAccountId)
                .orElseThrow(() -> new NotFoundException("Target account not found with ID: " + targetAccountId));

        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds for transfer.");
        }

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        targetAccount.setBalance(targetAccount.getBalance().add(amount));

        productRepository.save(sourceAccount);
        productRepository.save(targetAccount);

        // Registro de la transacción que incluye tanto el débito como el crédito
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.TRANSFERENCIA);
        transaction.setAmount(amount);
        transaction.setDate(LocalDate.now());
        transaction.setSourceAccount(sourceAccount);
        transaction.setTargetAccount(targetAccount);

        transactionRepository.save(transaction);

        return "Transfer of: " + amount + " has been made from account ID: " + sourceAccountId + " to account ID: " + targetAccountId;
    }
}
