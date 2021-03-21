package com.brabos.bahia.cursoSpring.services;

import com.brabos.bahia.cursoSpring.domain.Category;
import com.brabos.bahia.cursoSpring.repositories.CategoryRepository;

import com.brabos.bahia.cursoSpring.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category find(Integer id){
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada para id " + id));
    }

    public Category insert(Category category){
        category.setId(null);
        return categoryRepository.save(category);
    }
}
