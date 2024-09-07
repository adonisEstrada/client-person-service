package com.adonis.nttdata.presentation.controller;


import com.adonis.nttdata.presentation.presenter.ClientPresenter;
import com.adonis.nttdata.service.ClientService;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Generated
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/createClient")
    public ClientPresenter saveClient(@RequestBody ClientPresenter clientPresenter) {
        return clientService.saveClient(clientPresenter);
    }

    @GetMapping("/getClientById")
    public ClientPresenter getClientById(@RequestParam("clientId") UUID clientId) {
        return clientService.toPresenter(clientService.getClientById(clientId));
    }

    @PutMapping("/updateClient")
    public ClientPresenter updateClient(@RequestBody ClientPresenter clientPresenter) {
        return clientService.updateClient(clientPresenter);
    }

    @DeleteMapping("/deleteClientById")
    public void deleteClientById(@RequestParam("clientId") UUID clientId) {
        clientService.deleteClientById(clientId);
    }

    @GetMapping("/getClients")
    public List<ClientPresenter> getClients() {
        return clientService.getClients();
    }

    @GetMapping("/getClientByDni")
    public ClientPresenter getClientByDni(@RequestParam("dni") String dni) {
        return clientService.getClientByDni(dni);
    }

    @PostMapping("/createClients")
    public List<ClientPresenter> saveClients(@RequestBody List<ClientPresenter> clientPresenter) {
        return clientService.saveClients(clientPresenter);
    }
}
