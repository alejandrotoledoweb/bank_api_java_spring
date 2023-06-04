package com.bankapi.api.service.impl;

import com.bankapi.api.dto.ClientDto;
import com.bankapi.api.dto.ClientResponse;
import com.bankapi.api.exceptions.ClientNotFoundException;
import com.bankapi.api.models.Client;
import com.bankapi.api.repository.ClientRepository;
import com.bankapi.api.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Override
    public ClientDto createClient(ClientDto clientDto) {
        Client client = mapToEntity(clientDto);

        Client newClient = clientRepository.save(client);

        return mapToDto(newClient);
    }

    @Override
    public ClientResponse getAllClients(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Client> clientList = clientRepository.findAll(pageable);
        List<Client> listOfClients = clientList.getContent();
        List<ClientDto> content =  listOfClients.stream().map(this::mapToDto).collect(Collectors.toList());

        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setContent(content);
        clientResponse.setPageNumber(clientList.getNumber());
        clientResponse.setPageSize(clientList.getSize());
        clientResponse.setTotalElements(clientList.getTotalElements());
        clientResponse.setTotalPages(clientList.getTotalPages());
        clientResponse.setLast(clientList.isLast());

        return clientResponse;
    }

    @Override
    public ClientDto getClientById(long id) {

        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Client could not be found"));
        return mapToDto(client);
    }

    @Override
    public ClientDto updateClient(ClientDto clientDto, long id) {

        Client client = clientRepository.findById(id).orElseThrow(()-> new ClientNotFoundException("Client could not be updated"));

        client.setName(clientDto.getName().toLowerCase());
        client.setStatus(clientDto.getStatus());
        client.setPassword(clientDto.getPassword());
        client.setAddress(clientDto.getAddress().toLowerCase());
        client.setAge(clientDto.getAge());
        client.setGender(clientDto.getGender().toLowerCase());
        client.setIdentification(clientDto.getIdentification());
        client.setPhoneNumber(clientDto.getPhoneNumber());

        Client updatedClient = clientRepository.save(client);

        return mapToDto(updatedClient);
    }

    @Override
    public ClientDto updateClientPatch(ClientDto clientDto, long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ClientNotFoundException("Client could not be updated"));


            if (clientDto.getName() != null) {
                client.setName(clientDto.getName());
            }
            if (clientDto.getPassword() != null) {
                client.setPassword(clientDto.getPassword());
            }
            if (clientDto.getStatus() != null) {
                client.setStatus(clientDto.getStatus());
            }
            if (clientDto.getGender() != null) {
                client.setGender(clientDto.getGender());
            }
            if (clientDto.getAge() >= 0) {
                client.setAge(clientDto.getAge());
            }
            if (clientDto.getIdentification() != null) {
                client.setIdentification(clientDto.getIdentification());
            }
            if (clientDto.getAddress() != null) {
                client.setAddress(clientDto.getAddress());
            }
            if (clientDto.getPhoneNumber() != null) {
                client.setPhoneNumber(clientDto.getPhoneNumber());
            }

            Client updatedClient = clientRepository.save(client);
            return mapToDto(updatedClient);
    }

    @Override
    public void deleteClient(long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Client could not be deleted"));
        clientRepository.delete(client);

    }

    private ClientDto mapToDto(Client client) {
        ClientDto clientDto = new ClientDto();

        clientDto.setName(client.getName().toLowerCase());
        clientDto.setStatus(client.getStatus());
        clientDto.setPassword(client.getPassword());
        clientDto.setAddress(client.getAddress().toLowerCase());
        clientDto.setAge(client.getAge());
        clientDto.setGender(client.getGender().toLowerCase());
        clientDto.setIdentification(client.getIdentification());
        clientDto.setPhoneNumber(client.getPhoneNumber());


        return clientDto;
    }

    private Client mapToEntity(ClientDto clientDto) {
        Client client = new Client();
        client.setName(clientDto.getName().toLowerCase());
        client.setStatus(clientDto.getStatus());
        client.setPassword(clientDto.getPassword());
        client.setAddress(clientDto.getAddress().toLowerCase());
        client.setAge(clientDto.getAge());
        client.setGender(clientDto.getGender().toLowerCase());
        client.setIdentification(clientDto.getIdentification());
        client.setPhoneNumber(clientDto.getPhoneNumber());
        return client;
    }
}
