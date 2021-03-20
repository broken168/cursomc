package com.brabos.bahia.cursoSpring.resources;

import com.brabos.bahia.cursoSpring.services.ClientOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders")
public class ClientOrderResource {

    @Autowired
    private ClientOrderService clientOrderService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> find(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(clientOrderService.find(id));
    }
}
