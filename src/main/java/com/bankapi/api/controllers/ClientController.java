package com.bankapi.api.controllers;

import com.bankapi.api.dto.ClientDto;
import com.bankapi.api.dto.ClientResponse;
import com.bankapi.api.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    @GetMapping("clients")
    public ResponseEntity<ClientResponse> getClients(
            @RequestParam(value="pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value="pageSize", defaultValue = "10", required = false) int pageSize
    ) {

        return new ResponseEntity<>(clientService.getAllClients(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("clients/{id}")
    public ResponseEntity<ClientDto> clientDetail(@PathVariable long id) {
        return new ResponseEntity<>(clientService.getClientById(id), HttpStatus.OK);
    }

    @PostMapping("/clients")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientDto clientDto) {


        return new ResponseEntity<>(clientService.createClient(clientDto), HttpStatus.CREATED);
    }

    @PutMapping("/clients/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClientDto> updateClient(@RequestBody ClientDto clientDto, @PathVariable("id") long clientId) {

        ClientDto updatedClientDtoResponse = clientService.updateClient(clientDto, clientId);
        return new ResponseEntity<>(updatedClientDtoResponse, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/clients/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClientDto> updateClientPatch(@RequestBody ClientDto clientDto, @PathVariable("id") long clientId) {

        ClientDto updatedClientDtoResponse = clientService.updateClientPatch(clientDto, clientId);
        return new ResponseEntity<>(updatedClientDtoResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable("id") long clientId) {
        clientService.deleteClient(clientId);
        return new ResponseEntity<>("Client deleted successfully", HttpStatus.ACCEPTED);
    }

}
