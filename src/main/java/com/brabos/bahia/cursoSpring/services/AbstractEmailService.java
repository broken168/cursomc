package com.brabos.bahia.cursoSpring.services;

import com.brabos.bahia.cursoSpring.domain.ClientOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmation(ClientOrder order) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromClientOrder(order);
        sendEmail(sm);
    }


    protected SimpleMailMessage prepareSimpleMailMessageFromClientOrder(ClientOrder order){
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(order.getClient().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Pedido confirmado! Código " + order.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(order.toString());
        return sm;
    }
}
