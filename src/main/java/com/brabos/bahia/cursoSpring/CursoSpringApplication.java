package com.brabos.bahia.cursoSpring;

import com.brabos.bahia.cursoSpring.domain.*;
import com.brabos.bahia.cursoSpring.domain.enums.ClientType;
import com.brabos.bahia.cursoSpring.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

	public static void main(String[] args) {
		SpringApplication.run(CursoSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");

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

		categoryRepository.saveAll(Arrays.asList(cat1, cat2));
		productRepository.saveAll(Arrays.asList(p1, p2, p3));

		stateRepository.saveAll(Arrays.asList(state1, state2));
		cityRepository.saveAll(Arrays.asList(city1, city2, city3));

		Client cli1 = new Client(null, "Maria Silva", "maria@gmail.com", "3455453564", ClientType.REGULARPERSON);
		cli1.getTelephone().addAll(Arrays.asList("3545451", "53454371"));
		Address e1 = new Address(null, "Rua Flores", "300", "Apto 203", "Jardim", "3434543", cli1, city1);
		Address e2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "3465198", cli1, city2);

		cli1.getAddresses().addAll(Arrays.asList(e1, e2));

		clientRepository.save(cli1);
		addressRepository.saveAll(Arrays.asList(e1, e2));


	}
}
