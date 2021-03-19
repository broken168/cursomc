package com.brabos.bahia.cursoSpring.domain;

import com.brabos.bahia.cursoSpring.domain.enums.PaymentState;


import javax.persistence.*;
import java.io.Serializable;


@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    private Integer state;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="clientOrder_id")
    @MapsId
    private ClientOrder clientOrder;

    public Payment() {
    }

    public Payment(Integer id, PaymentState state, ClientOrder clientOrder) {
        super();
        this.id = id;
        this.state = state.getCode();
        this.clientOrder = clientOrder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PaymentState getEstado() {
        return PaymentState.toEnum(state);
    }

    public void setEstado(PaymentState state) {
        this.state = state.getCode();
    }

    public ClientOrder getOrder() {
        return clientOrder;
    }

    public void setOrder(ClientOrder clientOrder) {
        this.clientOrder = clientOrder;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Payment other = (Payment) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}