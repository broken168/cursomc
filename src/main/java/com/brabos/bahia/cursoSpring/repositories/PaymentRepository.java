package com.brabos.bahia.cursoSpring.repositories;

import com.brabos.bahia.cursoSpring.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
