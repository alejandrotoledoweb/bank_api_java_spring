package com.bankapi.api.service.impl;

import com.bankapi.api.dto.AccountDto;
import com.bankapi.api.exceptions.AccountIntegrityValidationException;
import com.bankapi.api.exceptions.AccountNotFoundException;
import com.bankapi.api.exceptions.ClientNotFoundException;
import com.bankapi.api.models.Account;
import com.bankapi.api.models.Client;
import com.bankapi.api.repository.AccountRepository;
import com.bankapi.api.repository.ClientRepository;
import com.bankapi.api.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        if (accountRepository.findByAccountNumber(accountDto.getAccountNumber()) != null) {
            throw new AccountIntegrityValidationException("An Account with this number already exists.");
        }

        clientRepository.save(client);

        Account newAccount = accountRepository.save(account);

        return mapToDto(newAccount);
    }

    @Override
    public List<AccountDto> getAccountsByClientId(long id) {
        List<Account> accounts = accountRepository.findByClientId(id);
        return accounts.stream().map(account -> mapToDto(account)).collect(Collectors.toList());
    }

    @Override
    public AccountDto getAccountById(long clientId,long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account could not be found"));
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ClientNotFoundException("Client could not be found"));

        if(account.getClient().getId() != client.getId()) {
            throw new AccountNotFoundException("This review does not belong to the pokemon");
        }
        return mapToDto(account);

    }

    @Override
    public AccountDto updateAccount(long clientId, long accountId, AccountDto accountDto) {
        Client client = clientRepository.findById(clientId).orElseThrow(()->
                new ClientNotFoundException("Client associated with the review not found"));
        Account account = accountRepository.findById(accountId).orElseThrow((()->
                new AccountNotFoundException("Account with associated client not found")));

        if(account.getClient().getId() != client.getId()) {
            throw new AccountNotFoundException("This account does not belong to the client");
        }

        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountType(accountDto.getAccountType());
        account.setInitialBalance(accountDto.getInitialBalance());
        account.setState(accountDto.getState());

        Account updatedAccount = accountRepository.save(account);
        return mapToDto(updatedAccount);
    }

    @Override
    public void deleteAccount(long clientId, long accountId) {
        Client client = clientRepository.findById(clientId).orElseThrow(()-> new ClientNotFoundException("Client associated with the account not found"));
        Account account = accountRepository.findById(accountId).orElseThrow((()-> new AccountNotFoundException("Account with associated client not found")));

        if(account.getClient().getId() != client.getId()) {
            throw new AccountNotFoundException("This account does not belong to the client");
        }

        accountRepository.delete(account);
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
