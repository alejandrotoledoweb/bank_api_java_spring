package com.bankapi.api.service;

import com.bankapi.api.dto.ClientDto;
import com.bankapi.api.dto.ClientResponse;

public interface ClientService {

    ClientDto createClient(ClientDto clientDto);
    ClientResponse getAllClients(int pageNumber, int pageSize);

    ClientDto getClientById(long id);

    ClientDto updateClient(ClientDto clientDto, long id);
    ClientDto updateClientPatch(ClientDto clientDto, long id);

    void deleteClient(long id);
}
