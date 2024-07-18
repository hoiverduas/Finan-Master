package com.hd.finanMaster.service;

import com.hd.finanMaster.exception.InsufficientFundsException;

import java.math.BigDecimal;

public interface ITransactionService {


    String deposit(Long accountId, BigDecimal amount);
    String withdraw(Long accountId, BigDecimal amount) throws InsufficientFundsException;
    String transfer(Long sourceAccountId, Long targetAccountId, BigDecimal amount) throws InsufficientFundsException;

}
