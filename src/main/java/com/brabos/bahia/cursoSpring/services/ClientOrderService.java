package com.brabos.bahia.cursoSpring.services;

import com.brabos.bahia.cursoSpring.domain.ClientOrder;
import com.brabos.bahia.cursoSpring.repositories.ClientOrderRepository;
import com.brabos.bahia.cursoSpring.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientOrderService {

    @Autowired
    private ClientOrderRepository clientOrderRepository;

    public ClientOrder find(Integer id){
        Optional<ClientOrder> category = clientOrderRepository.findById(id);
        return category.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado para id " + id));
    }
}
