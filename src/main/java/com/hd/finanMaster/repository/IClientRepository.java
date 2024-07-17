package com.hd.finanMaster.repository;

import com.hd.finanMaster.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository<Client,Long> {

}
