package com.adonis.nttdata.repository;

import com.adonis.nttdata.entitiy.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends CrudRepository<Client, UUID> {


    Optional<Client> findByIdentification(String dni);
}
