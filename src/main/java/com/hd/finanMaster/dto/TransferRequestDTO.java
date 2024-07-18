package com.hd.finanMaster.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequestDTO {

    private Long sourceAccountId;
    private Long targetAccountId;
    private BigDecimal amount;
}
