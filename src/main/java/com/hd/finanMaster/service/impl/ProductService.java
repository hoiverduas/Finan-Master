package com.hd.finanMaster.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.finanMaster.dto.*;
import com.hd.finanMaster.exception.BalanceCannotBeZeroException;
import com.hd.finanMaster.exception.BalanceNotZeroException;
import com.hd.finanMaster.exception.NotFoundException;
import com.hd.finanMaster.model.AccountStatus;
import com.hd.finanMaster.model.Client;
import com.hd.finanMaster.model.Product;
import com.hd.finanMaster.model.ProductType;
import com.hd.finanMaster.repository.IClientRepository;
import com.hd.finanMaster.repository.IProductRepository;
import com.hd.finanMaster.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepository;

    private final IClientRepository clientRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public ProductService(IProductRepository productRepository, IClientRepository clientRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.objectMapper = objectMapper;
    }

    private final String MESSAGE = "Client not found";
    private final String MESSAGE_BALANCE = "Savings account balance cannot be less than 0";


    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) throws BalanceCannotBeZeroException {

        Client client = clientRepository.findById(productRequestDTO.getClientId()).orElseThrow(
                ()-> new NotFoundException(MESSAGE));

        if (productRequestDTO.getType() == ProductType.SAVINGS_ACCOUNT && productRequestDTO.getBalance().compareTo(BigDecimal.ZERO) < 0 ){
              throw new BalanceCannotBeZeroException(MESSAGE_BALANCE);
        }

        String accountNumber = generateUniqueAccountNumber(productRequestDTO.getType());

        Product product = mapToEntity(productRequestDTO);
        product.setAccountNumber(accountNumber);
        product.setCreationDate(LocalDate.now());
        product.setClient(client);
        product.setModificationDate(LocalDate.now());
        product.setStatus(AccountStatus.ACTIVE);


        productRepository.save(product);

        return mapToDto(product);
    }

    @Override
    public void cancelProduct(Long productId) throws BalanceNotZeroException {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: "));

        if (product.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new BalanceNotZeroException("Only products with a balance of $0 can be canceled.");
        }

        product.setStatus(AccountStatus.CANCELED);
        product.setModificationDate(LocalDate.now());

        productRepository.save(product);
    }

    @Override
    public void performTransaction(Long productId, BigDecimal transactionAmount) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + productId));

        BigDecimal newBalance = product.getBalance().add(transactionAmount);
        product.setBalance(newBalance);

        productRepository.save(product);
    }


    private String generateUniqueAccountNumber(ProductType type) {
        String prefix = type == ProductType.SAVINGS_ACCOUNT ? "53" : "33";
        String accountNumber;
        do {
            accountNumber = prefix + String.format("%08d", new Random().nextInt(100000000));
        } while (productRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    private ProductResponseDTO mapToDto(Product product){
        return objectMapper.convertValue(product,ProductResponseDTO.class);
    }

    private  Product mapToEntity(ProductRequestDTO productRequestDTO){
        return objectMapper.convertValue(productRequestDTO,Product.class);
    }

}
