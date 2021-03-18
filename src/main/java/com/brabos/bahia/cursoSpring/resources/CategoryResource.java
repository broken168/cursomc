package com.brabos.bahia.cursoSpring.resources;

import com.brabos.bahia.cursoSpring.domain.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @GetMapping
    public List<Category> listar() {


        List<Category> list = new ArrayList<>();
        list.add(new Category(1, "Photos"));
        list.add(new Category(2, "Videos"));

        return list;
    }
}
