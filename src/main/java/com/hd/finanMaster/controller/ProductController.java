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
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRequestDTO));
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> cancelProduct(@PathVariable Long productId) throws  BalanceNotZeroException {
        productService.cancelProduct(productId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transaction/{productId}")
    public ResponseEntity<Void> performTransaction(@PathVariable Long productId, @RequestBody TransactionRequestDTO  transactionRequestDTO) {
        productService.performTransaction(productId, transactionRequestDTO.getAmount());
        return ResponseEntity.ok().build();
    }

}
