package com.brabos.bahia.cursoSpring.resources;

import com.brabos.bahia.cursoSpring.domain.Category;
import com.brabos.bahia.cursoSpring.dto.CategoryDTO;
import com.brabos.bahia.cursoSpring.services.CategoryService;
import com.brabos.bahia.cursoSpring.services.exceptions.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> find(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(categoryService.find(id));
    }

    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> list = categoryService.findAll().stream().map(obj -> new CategoryDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody Category category){
        category = categoryService.insert(category);
        URI uri  = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody Category category, @PathVariable("id") Integer id){
        category.setId(id);
        category = categoryService.update(category);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
