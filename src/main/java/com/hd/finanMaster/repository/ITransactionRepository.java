package com.hd.finanMaster.repository;

import com.hd.finanMaster.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransactionRepository extends JpaRepository<Transaction,Long> {


}
