package com.brabos.bahia.cursoSpring.services;

import com.brabos.bahia.cursoSpring.domain.Category;
import com.brabos.bahia.cursoSpring.repositories.CategoryRepository;

import com.brabos.bahia.cursoSpring.services.exceptions.DataIntegrityException;
import com.brabos.bahia.cursoSpring.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Category update(Category category) {
        find(category.getId());
        return categoryRepository.save(category);
    }

    public void delete(Integer id) {
        find(id);
        try {
            categoryRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível deletar uma categoria que possui produtos");
        }

    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
