package com.hd.finanMaster.dto;

import com.hd.finanMaster.model.AccountStatus;
import com.hd.finanMaster.model.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProductResponseDTO {

    private Long id;
    private ProductType type;
    private String accountNumber;
    private AccountStatus status;
    private BigDecimal balance;
    private boolean exemptFromGMF;
    private Long clientId;
    private LocalDate creationDate;
    private LocalDate modificationDate;

}
