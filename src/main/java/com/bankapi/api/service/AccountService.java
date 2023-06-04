package com.bankapi.api.service;

import com.bankapi.api.dto.AccountDto;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(long clientId, AccountDto accountDto);

    List<AccountDto> getAccountByClientId(long id);

    AccountDto getAccountById(int reviewId, long clientId);

    AccountDto updateAccount(long clientId, int accountId, AccountDto accountDto);

    void deleteAccount(long clientId, int accountId);
}
