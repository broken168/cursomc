package com.brabos.bahia.cursoSpring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class ClientOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date time;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "clientOrder")
    private Payment payment;


    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    private Address deliveryAddress;

    @OneToMany(mappedBy = "id.clientOrder")
    private Set<OrderItem> orderItems = new HashSet<>();

    public double getTotalPrice(){
        double soma = 0;
        for(OrderItem x : orderItems){
            soma += x.getSubtotal();
        }
        return soma;
    }

    public ClientOrder() {
    }

    public ClientOrder(Integer id, Date time, Client client, Address deliveryAddress) {
        super();
        this.id = id;
        this.time = time;
        this.client = client;
        this.deliveryAddress = deliveryAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientOrder clientOrder = (ClientOrder) o;
        return Objects.equals(id, clientOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        NumberFormat mf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        final StringBuilder sb = new StringBuilder("Pedido {");
        sb.append("Número do pedido:").append(id);
        sb.append("\n, Instante:").append(sdf.format(time));
        sb.append("\n, Situação do pagamento: ").append(payment.getState().getDescription());
        sb.append("\n, Cliente:").append(client.getName());
        sb.append("\n, Detalhes: ");
        for(OrderItem x : orderItems){
            sb.append(x.toString());
        }
        sb.append("Preço total: ").append(mf.format(getTotalPrice()));
        sb.append('}');
        return sb.toString();
    }
}
