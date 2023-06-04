package com.bankapi.api.controllers;

import com.bankapi.api.dto.AccountDto;
import com.bankapi.api.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/clients/{clientId}/account")
    public ResponseEntity<AccountDto> createAccount(@PathVariable(value="clientId") long clientId,
                                                    @RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(clientId, accountDto), HttpStatus.CREATED);
    }
}
