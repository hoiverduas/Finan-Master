package com.hd.finanMaster.controller;

import com.hd.finanMaster.dto.TransactionRequestDTO;
import com.hd.finanMaster.dto.TransferRequestDTO;
import com.hd.finanMaster.exception.InsufficientFundsException;
import com.hd.finanMaster.dto.WithdrawRequestDTO;
import com.hd.finanMaster.service.impl.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        // Deposit money into the specified account and return a confirmation message.
        String message = transactionService.deposit(transactionRequestDTO.getAccountId(), transactionRequestDTO.getAmount());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody WithdrawRequestDTO withdrawRequestDTO) throws InsufficientFundsException {
        // Withdraw money from the specified account and return a confirmation message.
        // Throws InsufficientFundsException if the account has insufficient funds.
        String message = transactionService.withdraw(withdrawRequestDTO.getAccountId(), withdrawRequestDTO.getAmount());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws InsufficientFundsException {
        // Transfer money between accounts and return a confirmation message.
        // Throws InsufficientFundsException if the source account has insufficient funds.
        String message = transactionService.transfer(transferRequestDTO.getSourceAccountId(), transferRequestDTO.getTargetAccountId(), transferRequestDTO.getAmount());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
