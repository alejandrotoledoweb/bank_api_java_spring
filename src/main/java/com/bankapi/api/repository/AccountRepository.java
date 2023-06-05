package com.bankapi.api.repository;

import com.bankapi.api.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByClientId(long clientId);
}
