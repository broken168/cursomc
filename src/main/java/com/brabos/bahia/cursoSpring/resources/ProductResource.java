package com.brabos.bahia.cursoSpring.resources;

import com.brabos.bahia.cursoSpring.domain.Product;
import com.brabos.bahia.cursoSpring.dto.ProductDTO;
import com.brabos.bahia.cursoSpring.resources.utils.URL;
import com.brabos.bahia.cursoSpring.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> find(@PathVariable("id") Integer id){
        Product product = productService.find(id);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping()
    public ResponseEntity<Page> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "name", defaultValue = "") String name,
                                         @RequestParam(value = "categories", defaultValue = "") String categories,
                                         @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
                                         @RequestParam(value = "orderBy", defaultValue = "name")String orderBy,
                                         @RequestParam(value = "direction", defaultValue = "ASC")String direction)
    {
        String decodedName = URL.decodeParam(name);
        List<Integer> ids = URL.decodeIntList(categories);
        Page<ProductDTO> list = productService.search(decodedName, ids, page, linesPerPage, orderBy, direction).map(obj -> new ProductDTO(obj));
        return ResponseEntity.ok().body(list);
    }
}
