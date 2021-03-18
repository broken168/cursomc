package com.brabos.bahia.cursoSpring.repositories;

import com.brabos.bahia.cursoSpring.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
