package com.brabos.bahia.cursoSpring.services;

import com.brabos.bahia.cursoSpring.domain.*;
import com.brabos.bahia.cursoSpring.domain.enums.PaymentState;
import com.brabos.bahia.cursoSpring.repositories.ClientOrderRepository;
import com.brabos.bahia.cursoSpring.repositories.OrderItemRepository;
import com.brabos.bahia.cursoSpring.repositories.PaymentRepository;
import com.brabos.bahia.cursoSpring.security.UserSS;
import com.brabos.bahia.cursoSpring.services.exceptions.AuthorizationException;
import com.brabos.bahia.cursoSpring.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
public class ClientOrderService {

    @Autowired
    private ClientOrderRepository clientOrderRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private EmailService emailService;


    public ClientOrder find(Integer id){
        Optional<ClientOrder> category = clientOrderRepository.findById(id);
        return category.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado para id " + id));
    }

    public Page<ClientOrder> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        UserSS user = UserService.authenticated();
        if(user == null){
            throw new AuthorizationException("Acesso negado");
        }
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Client client = clientService.find(user.getId());
        return clientOrderRepository.findByClient(client, pageRequest);
    }

    @Transactional
    public ClientOrder insert(ClientOrder clientOrder) {
        clientOrder.setId(null);
        clientOrder.setTime(new Date());
        clientOrder.setClient(clientService.find(clientOrder.getClient().getId()));
        clientOrder.getPayment().setState(PaymentState.PENDING);
        clientOrder.getPayment().setClientOrder(clientOrder);
        if(clientOrder.getPayment() instanceof PaymentWithBoleto){
            PaymentWithBoleto payment = (PaymentWithBoleto) clientOrder.getPayment();
            boletoService.fillPaymentWithBoleto(payment, clientOrder.getTime());
        }
        clientOrder = clientOrderRepository.save(clientOrder);
        paymentRepository.save(clientOrder.getPayment());
        for(OrderItem x : clientOrder.getOrderItems()){
            x.setDiscount(0.0);
            x.setProduct(productService.find(x.getProduct().getId()));
            x.setPrice(x.getProduct().getPrice());
            x.setClientOrder(clientOrder);
        }
        orderItemRepository.saveAll(clientOrder.getOrderItems());
        emailService.sendOrderConfirmation(clientOrder);
        return clientOrder;
    }
}
