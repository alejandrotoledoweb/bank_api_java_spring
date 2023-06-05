package com.bankapi.api.service;

import com.bankapi.api.dto.AccountDto;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(long clientId, AccountDto accountDto);

    List<AccountDto> getAccountsByClientId(long id);

    AccountDto getAccountById(long clientId, long accountId);

    AccountDto updateAccount(long clientId, long accountId, AccountDto accountDto);

    void deleteAccount(long clientId, long accountId);
}
