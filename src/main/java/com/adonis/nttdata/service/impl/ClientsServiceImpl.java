package com.adonis.nttdata.service.impl;

import com.adonis.nttdata.client.AccountMovementServiceClient;
import com.adonis.nttdata.entitiy.Client;
import com.adonis.nttdata.exeption.ValidationException;
import com.adonis.nttdata.presentation.presenter.AccountPresenter;
import com.adonis.nttdata.presentation.presenter.ClientPresenter;
import com.adonis.nttdata.repository.ClientRepository;
import com.adonis.nttdata.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientsServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountMovementServiceClient accountService;

    @Override
    @Transactional
    public ClientPresenter saveClient(ClientPresenter clientPresenter) {
        Optional<Client> optionalClient = clientRepository.findByIdentification(clientPresenter.getIdentification());
        if (optionalClient.isPresent()) {
            throw new ValidationException("Client already exists");
        }
        Client client = new Client();
        client.setName(clientPresenter.getName());
        client.setGender(clientPresenter.getGender());
        client.setAge(clientPresenter.getAge());
        client.setIdentification(clientPresenter.getIdentification());
        client.setAddress(clientPresenter.getAddress());
        client.setPhoneNumber(clientPresenter.getPhoneNumber());
        client.setPassword(clientPresenter.getPassword());
        client.setStatus(clientPresenter.getStatus());
        Client clientSaved = clientRepository.save(client);
        return toPresenter(clientSaved);
    }

    @Override
    public Client getClientById(UUID clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ValidationException("Client was not found"));
        return client;
    }

    @Override
    public ClientPresenter updateClient(ClientPresenter clientPresenter) {
        UUID clientId = clientPresenter.getPersonId();
        if (clientPresenter.getPersonId() == null) {
            throw new ValidationException("Client must have ID");
        }
        Optional<Client> client = clientRepository.findById(clientId);
        if (client.isEmpty()) {
            throw new ValidationException("Client not found");
        }
        client.get().setName(clientPresenter.getName());
        client.get().setGender(clientPresenter.getGender());
        client.get().setAge(clientPresenter.getAge());
        client.get().setIdentification(clientPresenter.getIdentification());
        client.get().setAddress(clientPresenter.getAddress());
        client.get().setPhoneNumber(clientPresenter.getPhoneNumber());
        client.get().setPassword(clientPresenter.getPassword());
        client.get().setStatus(clientPresenter.getStatus());
        Client clientSaved = clientRepository.save(client.get());
        return toPresenter(clientSaved);

    }

    @Override
    public void deleteClientById(UUID clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        if (!client.isPresent()) {
            throw new ValidationException("Client was not found");
        }
        List<AccountPresenter> accountPresenters = accountService.getAccountsByClientId(clientId.toString());
        if (accountPresenters.size() == 0 && accountPresenters.isEmpty()) {
            clientRepository.deleteById(client.get().getPersonId());
        } else {
            throw new ValidationException("The bank account has movement");
        }


    }

    @Override
    public ClientPresenter toPresenter(Client client) {
        return ClientPresenter.builder()
                .personId(client.getPersonId())
                .clientId(client.getPersonId())
                .dni(client.getIdentification())
                .name(client.getName())
                .gender(client.getGender())
                .age(client.getAge())
                .address(client.getAddress())
                .identification(client.getIdentification())
                .password(client.getPassword())
                .status(client.isStatus())
                .accountPresenters(accountService.getAccountsByClientId(client.getPersonId().toString()))
                .build();
    }

    @Override
    public List<ClientPresenter> getClients() {
        List<ClientPresenter> result = new ArrayList<>();
        clientRepository.findAll().forEach(client -> result.add(toPresenter(client)));
        return result;
    }

    @Override
    public ClientPresenter getClientByDni(String dni) {
        Optional<Client> client = clientRepository.findByIdentification(dni);
        if (client.isPresent()) {
            return toPresenter(client.get());
        }
        return null;
    }

    @Override
    public List<ClientPresenter> saveClients(List<ClientPresenter> clientPresenter) {
        List<ClientPresenter> saved = new ArrayList<>();
        clientPresenter.forEach(clientPresenter1 -> saved.add(saveClient(clientPresenter1)));
        return saved;
    }
}
