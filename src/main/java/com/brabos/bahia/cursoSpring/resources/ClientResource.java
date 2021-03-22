package com.brabos.bahia.cursoSpring.resources;


import com.brabos.bahia.cursoSpring.domain.Category;
import com.brabos.bahia.cursoSpring.domain.Client;
import com.brabos.bahia.cursoSpring.dto.CategoryDTO;
import com.brabos.bahia.cursoSpring.dto.ClientDTO;
import com.brabos.bahia.cursoSpring.dto.NewClientDTO;
import com.brabos.bahia.cursoSpring.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

    @Autowired
    private ClientService clientService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Client> find(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(clientService.find(id));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody NewClientDTO newClientDTO){
        Client client = clientService.fromDTO(newClientDTO);
        client = clientService.insert(client);
        URI uri  = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping()
    public ResponseEntity<List<ClientDTO>> findAll() {
        List<ClientDTO> list = clientService.findAll().stream().map(obj -> new ClientDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody ClientDTO clientDTO, @PathVariable("id") Integer id){
        Client client = clientService.fromDTO(clientDTO);
        client.setId(id);
        client = clientService.update(client);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping(value = "/page")
    public ResponseEntity<Page> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
                                         @RequestParam(value = "orderBy", defaultValue = "name")String orderBy,
                                         @RequestParam(value = "direction", defaultValue = "ASC")String direction)
    {
        Page<ClientDTO> list = clientService.findPage(page, linesPerPage, orderBy, direction).map(obj -> new ClientDTO(obj));
        return ResponseEntity.ok().body(list);
    }
}
