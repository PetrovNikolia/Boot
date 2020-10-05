package com.petrov.Boot.rest;

import com.petrov.Boot.persist.entity.Product;
import com.petrov.Boot.persist.repo.ProductRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RequestMapping("/api/v1/product")
@RestController
public class ProductResource {

    private final ProductRepository productRepository;

    @Autowired
    public ProductResource(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<Product> findAll(){
        return productRepository.findAll();
    }

    @GetMapping(path = "/{id}/id", produces = "application/json")
    public Product findById(@PathVariable("id") int id) throws ChangeSetPersister.NotFoundException {
        return productRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @PostMapping(consumes ="application/json")
    public Product createProduct(@RequestBody Product product){
        if(product.getId()!=null){
            throw new IllegalArgumentException("id not ok");
        }
        productRepository.save(product);
        return product;
    }

    @PutMapping(consumes ="application/json", produces = "application/json")
    public Product updateProduct(@RequestBody Product product){
        productRepository.save(product);
        return product;
    }

    @DeleteMapping(path = "/{id}/id", produces = "application/json")
    public void deleteById(@PathVariable("id") int id){
        productRepository.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> illegalArgumentException(IllegalArgumentException ex){
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex){
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
