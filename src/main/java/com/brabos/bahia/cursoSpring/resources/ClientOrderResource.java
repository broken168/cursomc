package com.brabos.bahia.cursoSpring.resources;

import com.brabos.bahia.cursoSpring.domain.ClientOrder;
import com.brabos.bahia.cursoSpring.services.ClientOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/orders")
public class ClientOrderResource {

    @Autowired
    private ClientOrderService clientOrderService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientOrder> find(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(clientOrderService.find(id));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody ClientOrder clientOrder){
        clientOrder = clientOrderService.insert(clientOrder);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(clientOrder.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
