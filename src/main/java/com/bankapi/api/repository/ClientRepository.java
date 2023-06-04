package com.bankapi.api.repository;

import com.bankapi.api.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long>  {
}
