package com.hd.finanMaster.controller;

import com.hd.finanMaster.dto.ProductRequestDTO;
import com.hd.finanMaster.dto.ProductResponseDTO;
import com.hd.finanMaster.dto.TransactionRequestDTO;
import com.hd.finanMaster.exception.BalanceCannotBeZeroException;
import com.hd.finanMaster.exception.BalanceNotZeroException;
import com.hd.finanMaster.service.impl.ClientService;
import com.hd.finanMaster.service.impl.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO) throws BalanceCannotBeZeroException {
        // Creates a new product and returns the created product details with a 201 Created status.
        ProductResponseDTO response = productService.createProduct(productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> cancelProduct(@PathVariable Long productId) throws BalanceNotZeroException {
        // Cancels the product with the given ID if its balance is zero, and returns a confirmation message.
        String message = productService.cancelProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PostMapping("/transaction/{productId}")
    public ResponseEntity<String> performTransaction(@PathVariable Long productId, @RequestBody TransactionRequestDTO transactionRequestDTO) {
        // Performs a transaction on the specified product and returns a message with the new balance.
        String message = productService.performTransaction(productId, transactionRequestDTO.getAmount());
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
