package com.example.product.controller;


import com.example.product.entity.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * http://localhost:8080/api/v1/products    |   GET     |   return list product
 * http://localhost:8080/api/v1/products    |   POST    |   create new product
 * http://localhost:8080/api/v1/products/1  |   DELETE  |   remove product
 * http://localhost:8080/api/v1/products/1  |   GET     |   find product by id
 * http://localhost:8080/api/v1/products/1  |   PUT     |   update product
 */

@RestController
@RequestMapping(path = "a[i/v1/products")

public class ProductController {

    @Autowired
     ProductService productService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Product>> getList() {
        return ResponseEntity.ok(productService.findAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Product> save(@RequestBody Product product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @RequestMapping(method = RequestMethod.GET, path = "{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Optional<Product> optionalProduct = productService.findById(id);
        if (!optionalProduct.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalProduct.get());
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "{id}")
    public ResponseEntity<Product> deleteById( @PathVariable String id){
        Optional<Product> optionalProduct =  productService.findById(id);
        if (!productService.findById(id).isPresent()){
            ResponseEntity.badRequest().build();
        }
        productService.deleteById(id);
        return  ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.PUT, path = "{id}")
    public ResponseEntity<Product> update(@PathVariable String id, @RequestBody Product product) {
        Optional<Product> optionalProduct = productService.findById(id);
        if (!optionalProduct.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        Product existProduct = optionalProduct.get();
        existProduct.setId(product.getId());
        existProduct.setProductName(product.getProductName());
        existProduct.setSlug(product.getSlug());
        existProduct.setDescription(product.getDescription());
        existProduct.setDetail(product.getDetail());
        existProduct.setImage(product.getImage());
        existProduct.setProducer(product.getProducer());
        existProduct.setCreator(product.getCreator());
        existProduct.setUpdater(product.getUpdater());
        existProduct.setDeleter(product.getDeleter());
        return ResponseEntity.ok(productService.save(existProduct));
    }
}
