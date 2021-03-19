package com.brabos.bahia.cursoSpring.services;

import com.brabos.bahia.cursoSpring.domain.Client;
import com.brabos.bahia.cursoSpring.repositories.ClientRepository;
import com.brabos.bahia.cursoSpring.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client find(Integer id){
        Optional<Client> category = clientRepository.findById(id);
        return category.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado para id " + id));
    }
}
