package com.bankapi.api.controllers;

import com.bankapi.api.dto.AccountDto;
import com.bankapi.api.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping("/clients/{clientId}/accounts")
    public ResponseEntity<AccountDto> createAccount(@PathVariable(value="clientId") long clientId,
                                                    @RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(clientId, accountDto), HttpStatus.CREATED);
    }

    @GetMapping("/clients/{clientId}/accounts")
    public ResponseEntity<List<AccountDto>> getAccountsByClientId(@PathVariable(value="clientId") long clientId) {
        List<AccountDto> accountsList = accountService.getAccountsByClientId(clientId);
        return new ResponseEntity<>(accountsList, HttpStatus.OK);
    }

    @GetMapping("/clients/{clientId}/accounts/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable(value="clientId") long clientId, @PathVariable(value="id") int id) {
        AccountDto accountDto = accountService.getAccountById(clientId, id);
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

    @PutMapping("/clients/{clientId}/accounts/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable(value="clientId") long clientId,
                                                    @PathVariable(value="id") long accountId,
                                                    @RequestBody AccountDto accountDto) {
        AccountDto accountDtoResponse = accountService.updateAccount(clientId, accountId, accountDto);
        return new ResponseEntity<>(accountDtoResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/clients/{clientId}/accounts/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable(value="clientId") long clientId,
                                                @PathVariable(value="id") long accountId) {
        accountService.deleteAccount(clientId, accountId);
        return new ResponseEntity<>("Account deleted successfully", HttpStatus.ACCEPTED);
    }
}
