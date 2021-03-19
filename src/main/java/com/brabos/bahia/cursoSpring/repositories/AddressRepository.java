package com.brabos.bahia.cursoSpring.repositories;

import com.brabos.bahia.cursoSpring.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
