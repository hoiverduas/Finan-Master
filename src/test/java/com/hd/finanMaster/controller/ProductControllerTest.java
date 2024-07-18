package com.hd.finanMaster.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.finanMaster.controller.ProductController;
import com.hd.finanMaster.dto.ProductRequestDTO;
import com.hd.finanMaster.dto.ProductResponseDTO;
import com.hd.finanMaster.dto.TransactionRequestDTO;
import com.hd.finanMaster.model.ProductType;
import com.hd.finanMaster.service.impl.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct() throws Exception {
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setClientId(1L);
        requestDTO.setType(ProductType.SAVINGS_ACCOUNT);
        requestDTO.setBalance(BigDecimal.ZERO);

        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(1L);

        when(productService.createProduct(any(ProductRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(responseDTO.getId()));

        verify(productService, times(1)).createProduct(any(ProductRequestDTO.class));
    }

    @Test
    void cancelProduct() throws Exception {
        doNothing().when(productService).cancelProduct(anyLong());

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());

        verify(productService, times(1)).cancelProduct(anyLong());
    }

    @Test
    void performTransaction() throws Exception {
        TransactionRequestDTO requestDTO = new TransactionRequestDTO();
        requestDTO.setAmount(BigDecimal.valueOf(100));

        doNothing().when(productService).performTransaction(anyLong(), any(BigDecimal.class));

        mockMvc.perform(post("/products/transaction/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());

        verify(productService, times(1)).performTransaction(anyLong(), any(BigDecimal.class));
    }
}
