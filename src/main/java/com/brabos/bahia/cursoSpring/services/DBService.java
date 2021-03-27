package com.brabos.bahia.cursoSpring.services;

import com.brabos.bahia.cursoSpring.domain.*;
import com.brabos.bahia.cursoSpring.domain.enums.ClientType;
import com.brabos.bahia.cursoSpring.domain.enums.PaymentState;
import com.brabos.bahia.cursoSpring.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ClientOrderRepository clientOrderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private BCryptPasswordEncoder pe;


    public void instantiateTestDatabase() throws ParseException {
        Category cat1 = new Category(null, "Informática");
        Category cat2 = new Category(null, "Escritório");
        Category cat3 = new Category(null, "Perfumes");
        Category cat4 = new Category(null, "Tech");
        Category cat5 = new Category(null, "Rural");
        Category cat6 = new Category(null, "Moderno");
        Category cat7 = new Category(null, "Antigo");

        Product p1 = new Product(null, "Computador", 2000.00);
        Product p2 = new Product(null, "Impressora", 800.00);
        Product p3 = new Product(null, "Mouse", 80.00);
        Product p4 = new Product(null, "Mesa de Escritório", 300.00);
        Product p5 = new Product(null, "Toalha", 50.00);
        Product p6 = new Product(null, "Colcha", 200.00);
        Product p7 = new Product(null, "TV true color", 1200.00);
        Product p8 = new Product(null, "Roçadeira", 800.00);
        Product p9 = new Product(null, "Abajour", 100.00);
        Product p10 = new Product(null, "Pendente", 180.00);
        Product p11 = new Product(null, "Shampoo", 90.00);

        cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProducts().addAll(Arrays.asList(p2, p4));
        cat3.getProducts().addAll(Arrays.asList(p5, p6));
        cat4.getProducts().addAll(Arrays.asList(p1, p2, p3, p7));
        cat5.getProducts().add(p8);
        cat6.getProducts().addAll(Arrays.asList(p9, p10));
        cat7.getProducts().add(p11);

        p1.getCategories().addAll(Arrays.asList(cat1, cat4));
        p2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
        p3.getCategories().addAll(Arrays.asList(cat1, cat4));
        p4.getCategories().addAll(Arrays.asList(cat2));
        p5.getCategories().addAll(Arrays.asList(cat3));
        p6.getCategories().addAll(Arrays.asList(cat3));
        p7.getCategories().addAll(Arrays.asList(cat4));
        p8.getCategories().addAll(Arrays.asList(cat5));
        p9.getCategories().addAll(Arrays.asList(cat6));
        p10.getCategories().addAll(Arrays.asList(cat6));
        p11.getCategories().addAll(Arrays.asList(cat7));

        State state1 = new State(null, "Minas Gerais");
        State state2 = new State(null, "São Paulo");

        City city1 = new City(null, "Uberlância", state1);
        City city2 = new City(null, "São Paulo", state2);
        City city3 = new City(null, "Campinas", state2);

        state1.getCities().add(city1);
        state2.getCities().addAll(Arrays.asList(city2, city3));

        categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

        stateRepository.saveAll(Arrays.asList(state1, state2));
        cityRepository.saveAll(Arrays.asList(city1, city2, city3));

        Client cli1 = new Client(null, "Maria Silva", "chaves123.gs@gmail.com", "3455453564", ClientType.REGULARPERSON, pe.encode("123"));
        cli1.getTelephones().addAll(Arrays.asList("3545451", "53454371"));
        Address e1 = new Address(null, "Rua Flores", "300", "Apto 203", "Jardim", "3434543", cli1, city1);
        Address e2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "3465198", cli1, city2);

        cli1.getAddresses().addAll(Arrays.asList(e1, e2));

        clientRepository.save(cli1);
        addressRepository.saveAll(Arrays.asList(e1, e2));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        ClientOrder ped1 = new ClientOrder(null, sdf.parse("30/09/2020 10:32"), cli1, e1);
        ClientOrder ped2 = new ClientOrder(null, sdf.parse("10/10/2020 19:32"), cli1, e2);

        Payment pay1 = new PaymentWithCard(null, PaymentState.PAID, ped1, 6);
        ped1.setPayment(pay1);

        Payment pay2 = new PaymentWithBoleto(null, PaymentState.PENDING, ped2, sdf.parse("20/10/2020 02:30"), null);
        ped2.setPayment(pay2);

        cli1.getClientOrders().addAll(Arrays.asList(ped1, ped2));

        clientOrderRepository.saveAll(Arrays.asList(ped1, ped2));
        paymentRepository.saveAll(Arrays.asList(pay1, pay2));

        OrderItem ip1 = new OrderItem(ped1, p1, 0.0, 1, 2000.00);
        OrderItem ip2 = new OrderItem(ped1, p3, 0.0, 2, 80.0);
        OrderItem ip3 = new OrderItem(ped2, p2, 100.00, 1, 800.00);

        ped1.getOrderItems().addAll(Arrays.asList(ip1, ip2));
        ped2.getOrderItems().add(ip3);

        p1.getOrderItems().add(ip3);
        p2.getOrderItems().add(ip1);
        p2.getOrderItems().add(ip2);

        orderItemRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
    }
}
