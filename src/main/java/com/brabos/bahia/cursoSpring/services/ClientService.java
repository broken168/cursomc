package com.brabos.bahia.cursoSpring.services;

import com.brabos.bahia.cursoSpring.domain.Category;
import com.brabos.bahia.cursoSpring.domain.Client;
import com.brabos.bahia.cursoSpring.dto.CategoryDTO;
import com.brabos.bahia.cursoSpring.dto.ClientDTO;
import com.brabos.bahia.cursoSpring.repositories.ClientRepository;
import com.brabos.bahia.cursoSpring.services.exceptions.DataIntegrityException;
import com.brabos.bahia.cursoSpring.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client find(Integer id){
        Optional<Client> category = clientRepository.findById(id);
        return category.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado para id " + id));
    }

    public Client update(Client client) {
        Client newClient = find(client.getId());
        updateData(client, newClient);
        return clientRepository.save(newClient);
    }

    public void delete(Integer id) {
        find(id);
        try {
            clientRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível deletar uma categoria que possui produtos");
        }

    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clientRepository.findAll(pageRequest);
    }

    public Client fromDTO(ClientDTO clientDTO){
        return new Client(clientDTO.getId(), clientDTO.getName(), clientDTO.getEmail(), null, null);
    }

    private void updateData(Client client, Client newClient) {
        newClient.setName(client.getName());
        newClient.setEmail(client.getEmail());
    }
}
