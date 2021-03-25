package com.brabos.bahia.cursoSpring.domain;

import com.brabos.bahia.cursoSpring.domain.enums.PaymentState;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;

@Entity
@JsonTypeName("paymentWithCard")
public class PaymentWithCard extends Payment{

    private static final long serialVersionUID = 1L;

    private Integer installmentsNumber;

    public PaymentWithCard(){

    }

    public PaymentWithCard(Integer id, PaymentState condition, ClientOrder clientOrder, Integer installmentsNumber) {
        super(id, condition, clientOrder);
        this.installmentsNumber = installmentsNumber;
    }

    public Integer getInstallmentsNumber() {
        return installmentsNumber;
    }

    public void setInstallmentsNumber(Integer installmentsNumber) {
        this.installmentsNumber = installmentsNumber;
    }
}
