package com.brabos.bahia.cursoSpring.domain;


import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderItemPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "client_order_id")
    private ClientOrder clientOrder;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ClientOrder getClientOrder() {
        return clientOrder;
    }

    public void setClientOrder(ClientOrder clientOrder) {
        this.clientOrder = clientOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        OrderItemPK that = (OrderItemPK) object;
        return clientOrder.equals(that.clientOrder) && product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientOrder, product);
    }
}
