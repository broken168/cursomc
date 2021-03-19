package com.brabos.bahia.cursoSpring.repositories;

import com.brabos.bahia.cursoSpring.domain.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientOrderRepository extends JpaRepository<ClientOrder, Integer> {
}
