package com.hd.finanMaster.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestDTO {

    private BigDecimal transactionAmount;
}
