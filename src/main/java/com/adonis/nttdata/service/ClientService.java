package com.adonis.nttdata.service;

import com.adonis.nttdata.entitiy.Client;
import com.adonis.nttdata.presentation.presenter.ClientPresenter;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    ClientPresenter saveClient(ClientPresenter clientPresenter) ;

    Client getClientById(UUID clientId);

    ClientPresenter updateClient(ClientPresenter clientPresenter);

    void deleteClientById(UUID clientId);

    ClientPresenter toPresenter(Client client);

    List<ClientPresenter> getClients();

    ClientPresenter getClientByDni(String identificationNumber);

    List<ClientPresenter> saveClients(List<ClientPresenter> clientPresenter) ;
}
