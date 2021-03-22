package com.brabos.bahia.cursoSpring;

import com.brabos.bahia.cursoSpring.domain.*;
import com.brabos.bahia.cursoSpring.domain.enums.ClientType;
import com.brabos.bahia.cursoSpring.domain.enums.PaymentState;
import com.brabos.bahia.cursoSpring.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class CursoSpringApplication implements CommandLineRunner {

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

	public static void main(String[] args) {
		SpringApplication.run(CursoSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
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

		cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProducts().add(p2);

		p1.getCategories().add(cat1);
		p2.getCategories().addAll(Arrays.asList(cat1, cat2));
		p3.getCategories().add(cat1);

		State state1 = new State(null, "Minas Gerais");
		State state2 = new State(null, "São Paulo");

		City city1 = new City(null, "Uberlância", state1);
		City city2 = new City(null, "São Paulo", state2);
		City city3 = new City(null, "Campinas", state2);

		state1.getCities().add(city1);
		state2.getCities().addAll(Arrays.asList(city2, city3));

		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		productRepository.saveAll(Arrays.asList(p1, p2, p3));

		stateRepository.saveAll(Arrays.asList(state1, state2));
		cityRepository.saveAll(Arrays.asList(city1, city2, city3));

		Client cli1 = new Client(null, "Maria Silva", "maria@gmail.com", "3455453564", ClientType.REGULARPERSON);
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
