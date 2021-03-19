package com.brabos.bahia.cursoSpring.domain;

import com.brabos.bahia.cursoSpring.domain.enums.PaymentState;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class PaymentWithBoleto extends Payment {

    private static final long serialVersionUID = 1L;

    private Date dueData;
    private Date paymentData;

    public PaymentWithBoleto(){

    }

    public PaymentWithBoleto(Integer id, PaymentState condition, ClientOrder clientOrder, Date dueData, Date paymentData) {
        super(id, condition, clientOrder);
        this.dueData = dueData;
        this.paymentData = paymentData;
    }

    public Date getDueData() {
        return dueData;
    }

    public void setDueData(Date dueData) {
        this.dueData = dueData;
    }

    public Date getPaymentData() {
        return paymentData;
    }

    public void setPaymentData(Date paymentData) {
        this.paymentData = paymentData;
    }
}
