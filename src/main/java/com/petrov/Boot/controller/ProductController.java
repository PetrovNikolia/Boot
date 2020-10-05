package com.petrov.Boot.controller;

import com.petrov.Boot.persist.entity.Product;
import com.petrov.Boot.persist.repo.ProductRepository;
import com.petrov.Boot.persist.repo.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductRepository productRepository;


    @GetMapping
    public String allProduct(Model model,
                             @RequestParam(value = "sort", required = false) String sort,
                             @RequestParam(value = "max", required = false) Integer max,
                             @RequestParam(value = "min", required = false) Integer min,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size) {

        PageRequest pageRequest;
        if( sort==null ||sort.isEmpty()){
            pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(4), Sort.by(Sort.Direction.ASC, "id"));
        } else {
            pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(4), Sort.by(Sort.Direction.ASC, String.valueOf(sort)));
        }


        Specification<Product> spec = ProductSpecification.trueLiteral();

        if(max!=null){
            spec.and(ProductSpecification.priceLesserOrEqualsThan(max));
        }
        if(min!=null){
            spec.and(ProductSpecification.priceGreaterOrEqualsThan(min));
        }

        model.addAttribute("products", productRepository.findAll(spec,pageRequest));
        return "products";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model) {
        Product product = productRepository.findById(id).get();
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping("/update")
    public String updateProduct(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product";
        }
        productRepository.save(product);
        return "redirect:/product";
    }

    @DeleteMapping("{id}/remove")
    public String removeProduct(@RequestParam Integer id) {
        productRepository.deleteById(id);
        return "redirect:/product";
    }
}
