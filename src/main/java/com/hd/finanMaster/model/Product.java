package com.hd.finanMaster.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ProductType type;
    @Column(unique = true, length = 10)
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private BigDecimal balance;
    private boolean exemptFromGMF;
    private LocalDate creationDate;
    private LocalDate modificationDate;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
