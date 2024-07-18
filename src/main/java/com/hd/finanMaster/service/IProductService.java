package com.hd.finanMaster.service;

import com.hd.finanMaster.dto.ProductRequestDTO;
import com.hd.finanMaster.dto.ProductResponseDTO;
import com.hd.finanMaster.exception.BalanceCannotBeZeroException;
import com.hd.finanMaster.exception.BalanceNotZeroException;

import java.math.BigDecimal;

public interface IProductService {

    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) throws BalanceCannotBeZeroException;
    void cancelProduct(Long productId) throws BalanceNotZeroException;
    void performTransaction(Long productId, BigDecimal transactionAmount);
}

