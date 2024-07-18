package com.hd.finanMaster.repository;

import com.hd.finanMaster.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {

    boolean existsByAccountNumber(String accountNumber);
}
