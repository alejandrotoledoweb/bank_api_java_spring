package com.bankapi.api.service.impl;

import com.bankapi.api.dto.AccountDto;
import com.bankapi.api.dto.ClientDto;
import com.bankapi.api.exceptions.ClientNotFoundException;
import com.bankapi.api.models.Account;
import com.bankapi.api.models.Client;
import com.bankapi.api.repository.AccountRepository;
import com.bankapi.api.repository.ClientRepository;
import com.bankapi.api.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private ClientRepository clientRepository;

    public AccountServiceImpl(ClientRepository clientRepository, AccountRepository accountRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(long clientId, AccountDto accountDto) {
        Account account = mapToEntity(accountDto);

        Client client = clientRepository.findById(clientId).orElseThrow(() ->new ClientNotFoundException("Client accounts not found"));
        account.setClient(client);

        Account newAccount = accountRepository.save(account);

        return mapToDto(newAccount);
    }

    @Override
    public List<AccountDto> getAccountByClientId(long id) {
        return null;
    }

    @Override
    public AccountDto getAccountById(int reviewId, long clientId) {
        return null;
    }

    @Override
    public AccountDto updateAccount(long clientId, int accountId, AccountDto accountDto) {
        return null;
    }

    @Override
    public void deleteAccount(long clientId, int accountId) {

    }

    private Account mapToEntity(AccountDto accountDto) {
        Account account = new Account();

        account.setId(accountDto.getId());
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountType(accountDto.getAccountType());
        account.setInitialBalance(accountDto.getInitialBalance());
        account.setState(accountDto.getState());

        return account;
    }

    private AccountDto mapToDto(Account account) {
        AccountDto accountDto = new AccountDto();

        accountDto.setId(account.getId());
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setInitialBalance(account.getInitialBalance());
        accountDto.setState(account.getState());

        return accountDto;
    }
}
