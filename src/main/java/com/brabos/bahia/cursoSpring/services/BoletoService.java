package com.brabos.bahia.cursoSpring.services;

import com.brabos.bahia.cursoSpring.domain.PaymentWithBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {

    public void fillPaymentWithBoleto(PaymentWithBoleto payment, Date time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        payment.setDueData(calendar.getTime());
    }
}
