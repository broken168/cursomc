package com.brabos.bahia.cursoSpring.services;

import com.brabos.bahia.cursoSpring.domain.Address;
import com.brabos.bahia.cursoSpring.domain.City;
import com.brabos.bahia.cursoSpring.domain.Client;
import com.brabos.bahia.cursoSpring.domain.enums.ClientType;
import com.brabos.bahia.cursoSpring.dto.ClientDTO;
import com.brabos.bahia.cursoSpring.dto.NewClientDTO;
import com.brabos.bahia.cursoSpring.repositories.AddressRepository;
import com.brabos.bahia.cursoSpring.repositories.CityRepository;
import com.brabos.bahia.cursoSpring.repositories.ClientRepository;
import com.brabos.bahia.cursoSpring.services.exceptions.DataIntegrityException;
import com.brabos.bahia.cursoSpring.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BCryptPasswordEncoder pe;

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
            throw new DataIntegrityException("Não é possível deletar porque há pedidos relacionados");
        }
    }

    @Transactional
    public Client insert(Client client){
        client.setId(null);
        client = clientRepository.save(client);
        addressRepository.saveAll(client.getAddresses());
        return client;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clientRepository.findAll(pageRequest);
    }

    public Client fromDTO(ClientDTO clientDTO){
        return new Client(clientDTO.getId(), clientDTO.getName(), clientDTO.getEmail(), null, null, null);
    }

    public Client fromDTO(NewClientDTO newClientDTO){
        City city = cityRepository.findById(newClientDTO.getCityId())
                .orElseThrow(() -> new ObjectNotFoundException("Nenhuma cidade encontrada para esse id " + newClientDTO.getCityId()));

        Client client = new Client(null, newClientDTO.getName(), newClientDTO.getEmail(),
                newClientDTO.getCpfOrCnpj(), ClientType.toEnum(newClientDTO.getType()),
                pe.encode(newClientDTO.getPassword()));

        Address address = new Address(null, newClientDTO.getPublicPlace(), newClientDTO.getNumber(),
                newClientDTO.getComplement(), newClientDTO.getNeighborhood(), newClientDTO.getCep(),
                client, city);

        client.getAddresses().add(address);
        client.getTelephones().add(newClientDTO.getTelephone1());
        if(newClientDTO.getTelephone2() != null){
            client.getTelephones().add(newClientDTO.getTelephone2());
        }
        if(newClientDTO.getTelephone3() != null){
            client.getTelephones().add(newClientDTO.getTelephone3());
        }
        return client;
    }

    private void updateData(Client client, Client newClient) {
        newClient.setName(client.getName());
        newClient.setEmail(client.getEmail());
    }
}
