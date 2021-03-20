package com.brabos.bahia.cursoSpring.repositories;

import com.brabos.bahia.cursoSpring.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
