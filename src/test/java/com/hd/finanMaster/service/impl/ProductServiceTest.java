package com.hd.finanMaster.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.finanMaster.dto.ProductRequestDTO;
import com.hd.finanMaster.dto.ProductResponseDTO;
import com.hd.finanMaster.exception.BalanceCannotBeZeroException;
import com.hd.finanMaster.exception.BalanceNotZeroException;
import com.hd.finanMaster.exception.NotFoundException;
import com.hd.finanMaster.model.AccountStatus;
import com.hd.finanMaster.model.Client;
import com.hd.finanMaster.model.Product;
import com.hd.finanMaster.model.ProductType;
import com.hd.finanMaster.repository.IClientRepository;
import com.hd.finanMaster.repository.IProductRepository;
import com.hd.finanMaster.service.impl.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private IProductRepository productRepository;

    @Mock
    private IClientRepository clientRepository;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct() throws BalanceCannotBeZeroException {
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setClientId(1L);
        requestDTO.setType(ProductType.SAVINGS_ACCOUNT);
        requestDTO.setBalance(BigDecimal.ZERO);

        Client client = new Client();
        client.setId(1L);

        when(clientRepository.findById(requestDTO.getClientId())).thenReturn(Optional.of(client));
        when(productRepository.existsByAccountNumber(anyString())).thenReturn(false);

        Product product = new Product();
        product.setId(1L);

        when(objectMapper.convertValue(requestDTO, Product.class)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(objectMapper.convertValue(product, ProductResponseDTO.class)).thenReturn(new ProductResponseDTO());

        ProductResponseDTO responseDTO = productService.createProduct(requestDTO);

        assertNotNull(responseDTO);
        verify(clientRepository, times(1)).findById(requestDTO.getClientId());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void cancelProduct() throws BalanceNotZeroException {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setBalance(BigDecimal.ZERO);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.cancelProduct(productId);

        assertEquals(AccountStatus.CANCELED, product.getStatus());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void performTransaction() {
        Long productId = 1L;
        BigDecimal transactionAmount = new BigDecimal("100.00");

        Product product = new Product();
        product.setId(productId);
        product.setBalance(new BigDecimal("500.00"));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.performTransaction(productId, transactionAmount);

        assertEquals(new BigDecimal("600.00"), product.getBalance());
        verify(productRepository, times(1)).save(product);
    }
}
