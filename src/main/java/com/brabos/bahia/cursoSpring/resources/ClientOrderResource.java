package com.brabos.bahia.cursoSpring.resources;

import com.brabos.bahia.cursoSpring.domain.ClientOrder;
import com.brabos.bahia.cursoSpring.domain.enums.Profile;
import com.brabos.bahia.cursoSpring.dto.CategoryDTO;
import com.brabos.bahia.cursoSpring.security.UserSS;
import com.brabos.bahia.cursoSpring.services.ClientOrderService;
import com.brabos.bahia.cursoSpring.services.UserService;
import com.brabos.bahia.cursoSpring.services.exceptions.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
        UserSS user = UserService.authenticated();
        if(user == null || !id.equals(user.getId())){
            throw new AuthorizationException("Acesso negado");
        }
        return ResponseEntity.ok().body(clientOrderService.find(id));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody ClientOrder clientOrder){
        clientOrder = clientOrderService.insert(clientOrder);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(clientOrder.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping()
    public ResponseEntity<Page<ClientOrder>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
                                         @RequestParam(value = "orderBy", defaultValue = "time")String orderBy,
                                         @RequestParam(value = "direction", defaultValue = "DESC")String direction)
    {
        Page<ClientOrder> list = clientOrderService.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }
}
