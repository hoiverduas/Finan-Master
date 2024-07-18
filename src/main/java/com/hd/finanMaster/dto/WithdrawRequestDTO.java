package com.hd.finanMaster.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawRequestDTO {

    private Long accountId;
    private BigDecimal amount;
}
