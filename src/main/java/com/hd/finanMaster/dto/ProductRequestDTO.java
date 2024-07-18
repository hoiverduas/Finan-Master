package com.hd.finanMaster.dto;

import com.hd.finanMaster.model.ProductType;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

@Data
public class ProductRequestDTO {

    private ProductType type;
    private Long clientId;
    private BigDecimal balance;
    private boolean exemptFromGMF;

}
